package com.github.majisyou.enderchestplus.Command;

import com.github.majisyou.enderchestplus.EnderChestPlus;
import com.github.majisyou.enderchestplus.Gui.GuiMaster;
import com.github.majisyou.enderchestplus.System.EnderChestMySql;
import com.github.majisyou.enderchestplus.System.EnderSystem;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Cmd_watchEnderChest implements CommandExecutor {
    EnderChestPlus plugin = EnderChestPlus.getInstance();
    public Cmd_watchEnderChest(EnderChestPlus plugin){plugin.getCommand("watchdog").setExecutor(this);}


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player player){
            if(args.length==3){
                if(args[0].equals("name")){
                    OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(args[1]);
                    if((args[2].matches("-?\\d+"))){
                        int page = Integer.parseInt(args[2]);
                        sender.sendMessage(offlinePlayer.getName()+"のエンダーチェストを確認する。");
                        Inventory inventory = Bukkit.createInventory(null,36,args[1]+"のエンダーチェストの"+page+"ページ");
                        EnderSystem.ReadEnderPage(offlinePlayer,inventory,page);
                        player.openInventory(inventory);
                        return true;
                    }
                }
                if(args[0].equals("uuid")){
                    OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(UUID.fromString(args[1]));
                    if((args[2].matches("-?\\d+"))){
                        int page = Integer.parseInt(args[2]);
                        sender.sendMessage(offlinePlayer.getName()+"のエンダーチェストを確認する。");
                        Inventory inventory = Bukkit.createInventory(null,36,offlinePlayer.getName()+"のエンダーチェストの"+page+"ページ");
                        EnderSystem.ReadEnderPage(offlinePlayer,inventory,page);
                        player.openInventory(inventory);
                        return true;
                    }
                }
            }
            sender.sendMessage("<Player> <page>で調べられる。");
        }
        return true;
    }
}
