package com.github.majisyou.enderchestplus.Command;

import com.github.majisyou.enderchestplus.EnderChestPlus;
import com.github.majisyou.enderchestplus.System.EnderChestMySql;
import com.github.majisyou.enderchestplus.System.EnderSystem;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class Cmd_deleteEnderChest implements CommandExecutor {
    EnderChestPlus plugin = EnderChestPlus.getInstance();
    public Cmd_deleteEnderChest(EnderChestPlus plugin){plugin.getCommand("EnderChestDelete").setExecutor(this);}



    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 3){
            if(args[0].equals("page")){
                OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(args[1]);
                if((args[2].matches("-?\\d+"))){
                    int page = Integer.parseInt(args[2]);
                    EnderSystem.deleteEnderPage(offlinePlayer,page);
                    sender.sendMessage(offlinePlayer.getName()+"の"+page+"ページを消去しました");
                    return true;
                }
            }
        }
        if(args.length == 2){
            if(args[0].equals("all")){
                OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(args[1]);
                EnderSystem.deleteEnderChest(offlinePlayer);
                sender.sendMessage(offlinePlayer.getName()+"のエンダーチェストの中身を消去した");
                return true;
            }
        }
        return true;
    }
}
