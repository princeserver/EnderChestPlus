package com.github.majisyou.enderchestplus.Gui;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GuiItem {

    public static ItemStack EClock1(){
        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE,1);
        ItemMeta itemMeta = item.getItemMeta();
        String name = "1ページ";
        itemMeta.setDisplayName(name);
        itemMeta.setCustomModelData(3011);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack EClock2(){
        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE,1);
        ItemMeta itemMeta = item.getItemMeta();
        String name = "2ページ";
        itemMeta.setDisplayName(name);
        itemMeta.setCustomModelData(3012);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack EClock3(){
        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE,1);
        ItemMeta itemMeta = item.getItemMeta();
        String name = "3ページ";
        itemMeta.setDisplayName(name);
        itemMeta.setCustomModelData(3013);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack EClock4(){
        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE,1);
        ItemMeta itemMeta = item.getItemMeta();
        String name = "4ページ";
        itemMeta.setDisplayName(name);
        itemMeta.setCustomModelData(3014);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack EClock5(){
        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE,1);
        ItemMeta itemMeta = item.getItemMeta();
        String name = "5ページ";
        itemMeta.setDisplayName(name);
        itemMeta.setCustomModelData(3015);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack EClock6(){
        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE,1);
        ItemMeta itemMeta = item.getItemMeta();
        String name = "6ページ";
        itemMeta.setDisplayName(name);
        itemMeta.setCustomModelData(3016);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack EClock7(){
        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE,1);
        ItemMeta itemMeta = item.getItemMeta();
        String name = "7ページ";
        itemMeta.setDisplayName(name);
        itemMeta.setCustomModelData(3017);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack EClock8(){
        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE,1);
        ItemMeta itemMeta = item.getItemMeta();
        String name = "8ページ";
        itemMeta.setDisplayName(name);
        itemMeta.setCustomModelData(3018);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack EClock9(){
        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE,1);
        ItemMeta itemMeta = item.getItemMeta();
        String name = "9ページ";
        itemMeta.setDisplayName(name);
        itemMeta.setCustomModelData(3019);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack Check(){
        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE,1);
        ItemMeta itemMeta = item.getItemMeta();
        String name = "";
        itemMeta.setDisplayName(name);
        itemMeta.setCustomModelData(3400);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack RedNo(){
        ItemStack item = new ItemStack(Material.WHITE_STAINED_GLASS_PANE,1);
        ItemMeta itemMeta = item.getItemMeta();
        String name = "キャンセル";
        itemMeta.setDisplayName(name);
        itemMeta.setCustomModelData(3401);
        item.setItemMeta(itemMeta);
        return item;
    }


}
