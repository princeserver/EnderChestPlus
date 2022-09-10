package com.github.majisyou.enderchestplus.Event;

import com.github.majisyou.enderchestplus.EnderChestPlus;
import com.github.majisyou.enderchestplus.System.EnderSystem;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class JoinEvent implements Listener {
    private static EnderChestPlus plugin = EnderChestPlus.getInstance();
    public JoinEvent(EnderChestPlus plugin){plugin.getServer().getPluginManager().registerEvents(this,plugin);}

    @EventHandler
    public static void LoginEvent(PlayerLoginEvent event){
        EnderSystem.getEClockPage(event.getPlayer());
    }
}
