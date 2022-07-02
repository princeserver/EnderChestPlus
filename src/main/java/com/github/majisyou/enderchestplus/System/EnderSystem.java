package com.github.majisyou.enderchestplus.System;

import com.github.majisyou.enderchestplus.EnderChestPlus;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;


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
        String playerData = toJson(inventory,0,26);

        EnderChestMySql statement = new EnderChestMySql();
        statement.deleteEnderChest(uuid,page);
        statement.insertEnderChest(uuid,page,playerData);
//        inventory.clear();
    }

    public static Integer CalculateCost(Player player,int page){
        int Cost = 0;

        for (int i = 2 ; i <= page;i++){
            int UnlockPage = getEClockPage(player);
            if(UnlockPage < i){
                Cost += 2000 * (i-1);
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
        if(eco.getBalance(player) - money < 0.0D){
//            plugin.getLogger().info("(BS)"+player.getName()+"の口座に十分なお金が無かったため、"+money+"Eを徴収できなかった");
            return false;
        }
        eco.withdrawPlayer(player,money);
        return true;
    }

    public static Inventory ReadEnderPage(OfflinePlayer player,Inventory inventory,int page){
        UUID uuid = player.getUniqueId();
        EnderChestMySql statement = new EnderChestMySql();
        JsonArray invData = (new Gson()).fromJson(statement.readEnderChest(uuid,page), JsonArray.class);
        if(invData != null){
            for (JsonElement jo : invData){
                ItemStack item = toItem(jo.getAsJsonObject());
                Integer index = getIndex(jo.getAsJsonObject());
                inventory.setItem(index,item);
            }
        }
        return inventory;
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

    public static String toJson(Inventory inv, int min, int max) {
        JsonArray inventory = new JsonArray();

        for (int i = min; i <= max; i++){
            ItemStack item = inv.getItem(i);
            if(item != null){
                JsonObject value = toJson(item,i);
                inventory.add(value);
            }
        }

        return (new Gson()).toJson(inventory);
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
