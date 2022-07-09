package com.github.majisyou.enderchestplus.Gui;

import com.github.majisyou.enderchestplus.System.EnderSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiMaster {

    public static void openEnderChest(Player player,int page){
        Inventory inventory = Bukkit.createInventory(null,36,"エンダーチェストPage"+page);
        ItemStack EClock1 = GuiItem.EClock1();
        ItemStack EClock2 = GuiItem.EClock2();
        ItemStack EClock3 = GuiItem.EClock3();
        ItemStack EClock4 = GuiItem.EClock4();
        ItemStack EClock5 = GuiItem.EClock5();
        ItemStack EClock6 = GuiItem.EClock6();
        ItemStack EClock7 = GuiItem.EClock7();
        ItemStack EClock8 = GuiItem.EClock8();
        ItemStack EClock9 = GuiItem.EClock9();

        ItemStack[] GuiContainer = new  ItemStack[]{null,null,null,null,null,null,null,null,null,
                                                    null,null,null,null,null,null,null,null,null,
                                                    null,null,null,null,null,null,null,null,null,
                                                    EClock1,EClock2,EClock3,EClock4,EClock5,EClock6,EClock7,EClock8,EClock9,};
        inventory.setContents(GuiContainer);

        EnderSystem.getEClockPage(player);  //これはMySqlにデータが無い人のための
        UnlockPage(player,inventory,page);
        EnderSystem.ReadEnderPage(player,inventory,page);


        player.openInventory(inventory);
    }

    public static void openUnlockPage(Player player,int page){
        Inventory inventory = Bukkit.createInventory(null,9,page+"ページまで開放しますか？");
        ItemStack Check = GuiItem.Check();
        ItemMeta checkMeta = Check.getItemMeta();
        checkMeta.setDisplayName(ChatColor.GOLD+EnderSystem.CalculateCost(player,page).toString()+"E"+ChatColor.WHITE+"支払って解放する");
        Check.setItemMeta(checkMeta);
        ItemStack redNo = GuiItem.RedNo();
        ItemStack[] GuiContainer = new  ItemStack[]{Check,null,null,null,null,null,null,null,redNo};
        inventory.setContents(GuiContainer);

        player.openInventory(inventory);
    }

    public static void UnlockPage(Player player,Inventory inventory,int page){
        ItemStack item = inventory.getItem(26+page);
        if(item != null){
            if(!item.getType().equals(Material.AIR)){
                ItemMeta itemMeta = item.getItemMeta();
                String itemName = itemMeta.getDisplayName()+ ChatColor.YELLOW+"＜選択中＞";
                int CustomModel = itemMeta.getCustomModelData()-10;
                itemMeta.setCustomModelData(CustomModel);
                itemMeta.setDisplayName(itemName);
                item.setItemMeta(itemMeta);

                int Cost = 0;
                for (int i = 2 ; i <= 9;i++){
                    int LockPage = EnderSystem.getEClockPage(player);
                    if(LockPage < i){
                        ItemStack LockPlate = inventory.getItem(26+i);
                        if(LockPlate != null) {
                            if (!item.getType().equals(Material.AIR)) {
                                ItemMeta LockPlateMeta = LockPlate.getItemMeta();
                                String LockPlateName = LockPlateMeta.getDisplayName()+"§c＜未開放＞";
                                List<String> Lore = new ArrayList<>();
                                Cost += 1000* Math.pow (2,(i-1));
                                Lore.add(ChatColor.WHITE+"合計"+ChatColor.GOLD+Cost+ChatColor.WHITE+"で開放できます");
                                int LockCustomModel = LockPlateMeta.getCustomModelData()+10;
                                LockPlateMeta.setLore(Lore);
                                LockPlateMeta.setCustomModelData(LockCustomModel);
                                LockPlateMeta.setDisplayName(LockPlateName);
                                LockPlate.setItemMeta(LockPlateMeta);
                            }
                        }
                    }
                }
            }
        }

    }



}
