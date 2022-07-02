package com.github.majisyou.enderchestplus.Event;

import com.github.majisyou.enderchestplus.EnderChestPlus;
import com.github.majisyou.enderchestplus.System.EnderSystem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;

public class CloseEnderChest implements Listener {
    private static final EnderChestPlus plugin = EnderChestPlus.getInstance();
    public CloseEnderChest(EnderChestPlus plugin){plugin.getServer().getPluginManager().registerEvents(this,plugin);}

    @EventHandler
    public static void CloseEC(InventoryCloseEvent event){
        switch (event.getView().getTitle()){
            case "エンダーチェストPage1"->{
                EnderSystem.RecordEnderPage((Player) event.getPlayer(),event.getInventory(),1);
            }
            case "エンダーチェストPage2"->{
                EnderSystem.RecordEnderPage((Player) event.getPlayer(),event.getInventory(),2);
            }
            case "エンダーチェストPage3"->{
                EnderSystem.RecordEnderPage((Player) event.getPlayer(),event.getInventory(),3);
            }
            case "エンダーチェストPage4"->{
                EnderSystem.RecordEnderPage((Player) event.getPlayer(),event.getInventory(),4);
            }
            case "エンダーチェストPage5"->{
                EnderSystem.RecordEnderPage((Player) event.getPlayer(),event.getInventory(),5);
            }
            case "エンダーチェストPage6"->{
                EnderSystem.RecordEnderPage((Player) event.getPlayer(),event.getInventory(),6);
            }
            case "エンダーチェストPage7"->{
                EnderSystem.RecordEnderPage((Player) event.getPlayer(),event.getInventory(),7);
            }
            case "エンダーチェストPage8"->{
                EnderSystem.RecordEnderPage((Player) event.getPlayer(),event.getInventory(),8);
            }
            case "エンダーチェストPage9"->{
                EnderSystem.RecordEnderPage((Player) event.getPlayer(),event.getInventory(),9);
            }
        }
    }
}
