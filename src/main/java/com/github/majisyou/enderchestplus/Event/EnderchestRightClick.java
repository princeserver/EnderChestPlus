package com.github.majisyou.enderchestplus.Event;

import com.github.majisyou.enderchestplus.EnderChestPlus;
import com.github.majisyou.enderchestplus.Gui.GuiMaster;
import com.github.majisyou.enderchestplus.System.SoundManager;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import static org.bukkit.event.block.Action.RIGHT_CLICK_AIR;
import static org.bukkit.event.block.Action.RIGHT_CLICK_BLOCK;

public class EnderchestRightClick implements Listener {
    private static final EnderChestPlus plugin = EnderChestPlus.getInstance();
    public EnderchestRightClick(EnderChestPlus plugin){plugin.getServer().getPluginManager().registerEvents(this,plugin);}


    @EventHandler
    public static void RightClick(PlayerInteractEvent event){
        if(event.getAction() == RIGHT_CLICK_AIR || event.getAction() == RIGHT_CLICK_BLOCK) {
            if(event.getAction() == RIGHT_CLICK_BLOCK){
                if(event.getClickedBlock().getType().equals(Material.ENDER_CHEST)){
                    event.setCancelled(true);
                    SoundManager.OpenEnder(event.getPlayer());
                    GuiMaster.openEnderChest(event.getPlayer(),1);
                }
                if(event.getClickedBlock().getType().equals(Material.ANVIL)||event.getClickedBlock().getType().equals(Material.DAMAGED_ANVIL)||event.getClickedBlock().getType().equals(Material.CHIPPED_ANVIL)||event.getClickedBlock().getType().equals(Material.GRINDSTONE)){
                    return;
                }
            }

            if(event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.ENDER_CHEST)){
                if(!event.getPlayer().isSneaking()){
                    event.setCancelled(true);
                    SoundManager.OpenEnder(event.getPlayer());
                    GuiMaster.openEnderChest(event.getPlayer(),1);
                }
            }
        }

    }


}
