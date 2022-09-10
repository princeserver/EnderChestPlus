package com.github.majisyou.enderchestplus.System;

import com.github.majisyou.enderchestplus.EnderChestPlus;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;


import javax.xml.bind.DatatypeConverter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class EnderSystem {
    private static Map<UUID, Integer> data;

    private static final EnderChestPlus plugin = EnderChestPlus.getInstance();

    public static void RecordEnderPage(OfflinePlayer player, Inventory inventory , int page){
        UUID uuid = player.getUniqueId();
        String playerData = toJson(inventory,0,26,player);

        ItemStack itemStack = inventory.getItem(26+page);
        if(itemStack != null && !itemStack.getType().equals(Material.AIR)){
            ItemMeta itemMeta = itemStack.getItemMeta();
            PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
            NamespacedKey key = new NamespacedKey(plugin,"decode");
            String oldData = pdc.get(key,PersistentDataType.STRING);
            if(oldData != null  && oldData.equals(playerData)){
                return;
            }
        }

        try {
            EnderChestMySql statement = new EnderChestMySql();
            statement.deleteEnderChest(uuid,page);
            statement.insertEnderChest(uuid,page,playerData);
        }catch (Exception e){
            plugin.getLogger().info("(ECP)"+"mysqlRecordでエラー");
            if(player.getPlayer()!=null && itemStack != null){
                player.getPlayer().getWorld().dropItem(player.getPlayer().getLocation(),itemStack);
                player.getPlayer().sendMessage(ChatColor.RED+"エンダーチェストでバグが発生した、このアイテムを運営に渡して下さい");
                return;
            }
            plugin.getLogger().info("(ECP)"+player.getName()+"のエンダーチェストでバグが発生し、復元はできなさそう");
        }

//        inventory.clear();
    }


    public static void deleteEnderPage(OfflinePlayer player,int page){
        EnderChestMySql statement = new EnderChestMySql();
        statement.deleteEnderChest(player.getUniqueId(),page);
    }

    public static void deleteEnderChest(OfflinePlayer player){
        EnderChestMySql statement = new EnderChestMySql();
        statement.deleteLock(player.getUniqueId());
        statement.deleteEnderChestAll(player.getUniqueId());
        data.remove(player.getUniqueId());
    }

    public static Integer CalculateCost(Player player,int page){
        int Cost = 0;
        for (int i = 2 ; i <= page;i++){
            int UnlockPage = getEClockPage(player);
            if(UnlockPage < i){
                Cost += 1000* Math.pow (2,(i-1));
            }
        }

        return Cost;
    }

    public static boolean JudgeCost2(Player player, int page){
        return CalculateCost(player,page) < getBankMoney(player);
    }

    public static double getBankMoney(Player player){
        Economy eco  = EnderChestPlus.getEconomy();
        return eco.getBalance(player);
    }

    public static boolean removeBank(Player player,double money){
        Economy eco  = EnderChestPlus.getEconomy();
        if(money > getBankMoney(player)){
//            plugin.getLogger().info("(BS)"+player.getName()+"の口座に十分なお金が無かったため、"+money+"Eを徴収できなかった");
            return false;
        }
        eco.withdrawPlayer(player,money);
        return true;
    }

    public static Inventory ReadEnderPage(OfflinePlayer player,Inventory inventory,int page){
        UUID uuid = player.getUniqueId();
        EnderChestMySql statement = new EnderChestMySql();
        String playerdata = statement.readEnderChest(uuid,page);
        try {
            JsonArray invData = (new Gson()).fromJson(playerdata, JsonArray.class);
            if(invData != null){
                for (JsonElement jo : invData){
                    try {
                        ItemStack item = toItem(jo.getAsJsonObject());
                        Integer index = getIndex(jo.getAsJsonObject());
                        inventory.setItem(index,item);
                    }catch (Exception e){
                        plugin.getLogger().info("(ECP)"+"Jsonからアイテムに戻す作業でエラーを吐いた");
                        if(player.getPlayer()!=null){
                            player.getPlayer().sendMessage(ChatColor.RED+"エンダーチェストでバグが発生した");
                        }
                    }
                }
            }
            setPageData(page,inventory,playerdata);
            return inventory;
        }catch (Exception e){
            plugin.getLogger().info("(ECP)"+"JsonArrayに戻す作業でエラーを吐いた");
            plugin.getLogger().info(playerdata);
            if(player.getPlayer()!=null){
                player.getPlayer().sendMessage(ChatColor.RED+"エンダーチェストでバグが発生した");
            }
        }
        return Bukkit.createInventory(null, InventoryType.CHEST);
    }

    public static void setPageData(int page,Inventory inventory,String playerData){
        ItemStack itemStack = inventory.getItem(26+page);
        if(itemStack == null||itemStack.getType().equals(Material.AIR)){
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        NamespacedKey key = new NamespacedKey(plugin,"decode");
        PersistentDataContainer pdc = itemMeta.getPersistentDataContainer();
        pdc.set(key, PersistentDataType.STRING,playerData);
        itemStack.setItemMeta(itemMeta);
    }

    public static void saveEClock(OfflinePlayer player, int page){
        UUID uuid = player.getUniqueId();
        EnderChestMySql statement = new EnderChestMySql();
        statement.deleteLock(uuid);
        statement.insertLock(uuid,page);
    }

    public static void reloadEClock(){
        EnderChestMySql statement = new EnderChestMySql();
        data = statement.readEClockAll();
    }

    public static void onDisableEvent(){
        List<World> worlds = plugin.getServer().getWorlds();
        List<Player> onlinePlayers = new ArrayList<>();
        for (World world:worlds){
            onlinePlayers = world.getPlayers();
            for (Player player:onlinePlayers){
                switch (player.getOpenInventory().getTitle()){
                    case "エンダーチェストPage1"->{
                        EnderSystem.RecordEnderPage(player,player.getOpenInventory().getTopInventory(),1);
                    }
                    case "エンダーチェストPage2"->{
                        EnderSystem.RecordEnderPage(player,player.getOpenInventory().getTopInventory(),2);
                    }
                    case "エンダーチェストPage3"->{
                        EnderSystem.RecordEnderPage(player,player.getOpenInventory().getTopInventory(),3);
                    }
                    case "エンダーチェストPage4"->{
                        EnderSystem.RecordEnderPage(player,player.getOpenInventory().getTopInventory(),4);
                    }
                    case "エンダーチェストPage5"->{
                        EnderSystem.RecordEnderPage(player,player.getOpenInventory().getTopInventory(),5);
                    }
                    case "エンダーチェストPage6"->{
                        EnderSystem.RecordEnderPage(player,player.getOpenInventory().getTopInventory(),6);
                    }
                    case "エンダーチェストPage7"->{
                        EnderSystem.RecordEnderPage(player,player.getOpenInventory().getTopInventory(),7);
                    }
                    case "エンダーチェストPage8"->{
                        EnderSystem.RecordEnderPage(player,player.getOpenInventory().getTopInventory(),8);
                    }
                    case "エンダーチェストPage9"->{
                        EnderSystem.RecordEnderPage(player,player.getOpenInventory().getTopInventory(),9);
                    }
                }
                player.closeInventory();
            }
        }
    }


    public static void setEClockPage(OfflinePlayer player,int page){
        if(page > 0 && page < 10){
            saveEClock(player,page);
            data.replace(player.getUniqueId(),page);
        }else {
            plugin.getLogger().info("(ECP)"+player.getName()+"のページデータが10以上の値");
        }
    }

    public static boolean ECLockPageJudge(Player player,int page){
        int EClockPageNumber = getEClockPage(player);
        return page <= EClockPageNumber;
    }

    public static int getEClockPage(OfflinePlayer player){
        if(data.containsKey(player.getUniqueId())){
            Integer page = data.get(player.getUniqueId());
            if(page == null){
                saveEClock(player,1);
                data.put(player.getUniqueId(),1);
                EnderChestMySql statement = new EnderChestMySql();
                statement.initEnderChest(player.getUniqueId());
                return 1;
            }else {
                return page;
            }
        }else {
            saveEClock(player,1);
            data.put(player.getUniqueId(),1);
            EnderChestMySql statement = new EnderChestMySql();
            statement.initEnderChest(player.getUniqueId());
            return 1;
        }
    }

    public static JsonObject toJson(ItemStack itemStack, int index) {
        YamlConfiguration config = new YamlConfiguration();
        config.set("i", itemStack);
        String str = DatatypeConverter.printBase64Binary(config.saveToString().getBytes(StandardCharsets.UTF_8));
        JsonObject jo = new JsonObject();
        jo.addProperty("index", index);
        jo.addProperty("data", str);

        return jo;
    }

    public static String toJson(Inventory inv, int min, int max,OfflinePlayer player) {
        JsonArray inventory = new JsonArray();
        JsonArray JudgeData;

        for (int i = min; i <= max; i++){
            ItemStack item = inv.getItem(i);
            String Limit;
            if(item != null){
                JsonObject value = toJson(item,i);
                JudgeData = inventory.deepCopy();
                JudgeData.add(value);
                Limit = (new Gson()).toJson(JudgeData);
                if(Limit.length() >= 65535){
                    plugin.getLogger().info("(ECP)"+player.getName()+ "のページの(文字列)が大きすぎた");
                    if(player.getPlayer()!=null){
                        player.getPlayer().sendMessage(ChatColor.RED+"エンダーチェストで不具合が生じた");
                    }
                    for (int j = i; j <= max; j++){
                        item = inv.getItem(j);
                        if(item != null)
                        plugin.getLogger().info("(ECP)"+"inv."+j+":"+toJson(item,i));
                    }
                    break;
                }
                inventory.add(value);
            }
        }
        return (new Gson()).toJson(inventory);
        //必要ないけど念のために残しておく
//        String result = (new Gson()).toJson(inventory);
//        if(result.length() < 65535){
//            return result;
//        }
//        plugin.getLogger().info("(ECP)"+"ページの(文字列)が大きすぎた"+result.length());
//        result = result.substring(0,65534);
//        return result;
    }

    public static ItemStack toItem(JsonObject data) {
        YamlConfiguration config = new YamlConfiguration();
        ItemStack item = null;
        try {
            String raw = data.get("data").getAsString();
            config.loadFromString(new String(DatatypeConverter.parseBase64Binary(raw), StandardCharsets.UTF_8));
            item = config.getItemStack("i", (ItemStack)null);
        } catch (Exception var4) {
            plugin.getLogger().info("(ECP)"+"アイテムのデシリアライズに失敗しました(itemStack)");
        }
        return item;
    }

    public static Integer getIndex(JsonObject data) {
        try {
            return data.get("index").getAsInt();
        } catch (Exception var2) {
            plugin.getLogger().info("(ECP)"+"アイテムのデシリアライズに失敗しました(Index)");
            return null;
        }
    }

}
