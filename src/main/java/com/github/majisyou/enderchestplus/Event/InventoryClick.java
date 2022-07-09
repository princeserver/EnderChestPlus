package com.github.majisyou.enderchestplus.Event;

import com.github.majisyou.enderchestplus.EnderChestPlus;
import com.github.majisyou.enderchestplus.Gui.GuiMaster;
import com.github.majisyou.enderchestplus.System.EnderSystem;
import com.github.majisyou.enderchestplus.System.SoundManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;

public class InventoryClick implements Listener {

    private static final EnderChestPlus plugin = EnderChestPlus.getInstance();
    public InventoryClick(EnderChestPlus plugin){plugin.getServer().getPluginManager().registerEvents(this,plugin);}


    @EventHandler
    public static void PageClick(InventoryClickEvent event){
        if(event.getView().getTitle().equals("エンダーチェストPage1")||event.getView().getTitle().equals("エンダーチェストPage2")||event.getView().getTitle().equals("エンダーチェストPage3")||event.getView().getTitle().equals("エンダーチェストPage4")||event.getView().getTitle().equals("エンダーチェストPage5")||event.getView().getTitle().equals("エンダーチェストPage6")||event.getView().getTitle().equals("エンダーチェストPage7")||event.getView().getTitle().equals("エンダーチェストPage8")||event.getView().getTitle().equals("エンダーチェストPage9")){
            if(event.getCurrentItem() != null){
                if(!event.getCurrentItem().getType().equals(Material.AIR)){
                    if(event.getCurrentItem().getType().equals(Material.WHITE_STAINED_GLASS_PANE)){
                        if(event.getCurrentItem().getItemMeta().hasCustomModelData()){
                            event.setCancelled(true);
                            SoundManager.OpenPage((Player) event.getWhoClicked());
                            if(!event.getInventory().getType().equals(InventoryType.PLAYER)){
                                switch (event.getCurrentItem().getItemMeta().getDisplayName()){
                                    case "1ページ" ->{
                                        GuiMaster.openEnderChest((Player) event.getWhoClicked(),1);
                                    }
                                    case "2ページ" ->{
                                        GuiMaster.openEnderChest((Player) event.getWhoClicked(),2);
                                    }
                                    case "3ページ" ->{
                                        GuiMaster.openEnderChest((Player) event.getWhoClicked(),3);
                                    }
                                    case "4ページ" ->{
                                        GuiMaster.openEnderChest((Player) event.getWhoClicked(),4);
                                    }
                                    case "5ページ" ->{
                                        GuiMaster.openEnderChest((Player) event.getWhoClicked(),5);
                                    }
                                    case "6ページ" ->{
                                        GuiMaster.openEnderChest((Player) event.getWhoClicked(),6);
                                    }
                                    case "7ページ" ->{
                                        GuiMaster.openEnderChest((Player) event.getWhoClicked(),7);
                                    }
                                    case "8ページ" ->{
                                        GuiMaster.openEnderChest((Player) event.getWhoClicked(),8);
                                    }
                                    case "9ページ" ->{
                                        GuiMaster.openEnderChest((Player) event.getWhoClicked(),9);
                                    }

                                    case "2ページ"+"§c＜未開放＞" ->{
                                        if(EnderSystem.JudgeCost2((Player) event.getWhoClicked(),2)){
                                            GuiMaster.openUnlockPage((Player) event.getWhoClicked(),2);
                                        }
                                    }
                                    case "3ページ"+"§c＜未開放＞" ->{
                                        if(EnderSystem.JudgeCost2((Player) event.getWhoClicked(),3)){
                                            GuiMaster.openUnlockPage((Player) event.getWhoClicked(),3);
                                        }
                                    }
                                    case "4ページ"+"§c＜未開放＞" ->{
                                        if(EnderSystem.JudgeCost2((Player) event.getWhoClicked(),4)){
                                            GuiMaster.openUnlockPage((Player) event.getWhoClicked(),4);
                                        }
                                    }
                                    case "5ページ"+"§c＜未開放＞" ->{
                                        if(EnderSystem.JudgeCost2((Player) event.getWhoClicked(),5)){
                                            GuiMaster.openUnlockPage((Player) event.getWhoClicked(),5);
                                        }
                                    }
                                    case "6ページ"+"§c＜未開放＞" ->{
                                        if(EnderSystem.JudgeCost2((Player) event.getWhoClicked(),6)){
                                            GuiMaster.openUnlockPage((Player) event.getWhoClicked(),6);
                                        }
                                    }
                                    case "7ページ"+"§c＜未開放＞" ->{
                                        if(EnderSystem.JudgeCost2((Player) event.getWhoClicked(),7)){
                                            GuiMaster.openUnlockPage((Player) event.getWhoClicked(),7);
                                        }
                                    }
                                    case "8ページ"+"§c＜未開放＞" ->{
                                        if(EnderSystem.JudgeCost2((Player) event.getWhoClicked(),8)){
                                            GuiMaster.openUnlockPage((Player) event.getWhoClicked(),8);
                                        }
                                    }
                                    case "9ページ"+"§c＜未開放＞" ->{
                                        if(EnderSystem.JudgeCost2((Player) event.getWhoClicked(),9)){
                                            GuiMaster.openUnlockPage((Player) event.getWhoClicked(),9);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        if(event.getView().getTitle().equals("2ページまで開放しますか？")||event.getView().getTitle().equals("3ページまで開放しますか？")||event.getView().getTitle().equals("4ページまで開放しますか？")||event.getView().getTitle().equals("5ページまで開放しますか？")||event.getView().getTitle().equals("6ページまで開放しますか？")||event.getView().getTitle().equals("7ページまで開放しますか？")||event.getView().getTitle().equals("8ページまで開放しますか？")||event.getView().getTitle().equals("9ページまで開放しますか？")){
           event.setCancelled(true);
           if(event.getClickedInventory() != null){
               if(!event.getClickedInventory().getType().equals(InventoryType.PLAYER)){
                   if(event.getSlot()==0){
                       switch (event.getView().getTitle()){
                           case "2ページまで開放しますか？" ->{
                               if(EnderSystem.removeBank((Player) event.getWhoClicked(),EnderSystem.CalculateCost((Player) event.getWhoClicked(),2))){
                                   EnderSystem.setEClockPage((Player) event.getWhoClicked(),2);
                                   plugin.getLogger().info("(ECP)"+event.getWhoClicked().getName()+"がエンダーチェストの2ページを解放した");
                                   SoundManager.sendSuccess((Player) event.getWhoClicked());
                               }
                           }
                           case "3ページまで開放しますか？" ->{
                               if(EnderSystem.removeBank((Player) event.getWhoClicked(),EnderSystem.CalculateCost((Player) event.getWhoClicked(),3))){
                                   EnderSystem.setEClockPage((Player) event.getWhoClicked(),3);
                                   plugin.getLogger().info("(ECP)"+event.getWhoClicked().getName()+"がエンダーチェストの3ページを解放した");
                                   SoundManager.sendSuccess((Player) event.getWhoClicked());
                               }
                           }
                           case "4ページまで開放しますか？" ->{
                               if(EnderSystem.removeBank((Player) event.getWhoClicked(),EnderSystem.CalculateCost((Player) event.getWhoClicked(),4))){
                                   EnderSystem.setEClockPage((Player) event.getWhoClicked(),4);
                                   plugin.getLogger().info("(ECP)"+event.getWhoClicked().getName()+"がエンダーチェストの4ページを解放した");
                                   SoundManager.sendSuccess((Player) event.getWhoClicked());
                               }
                           }
                           case "5ページまで開放しますか？" ->{
                               if(EnderSystem.removeBank((Player) event.getWhoClicked(),EnderSystem.CalculateCost((Player) event.getWhoClicked(),5))){
                                   EnderSystem.setEClockPage((Player) event.getWhoClicked(),5);
                                   plugin.getLogger().info("(ECP)"+event.getWhoClicked().getName()+"がエンダーチェストの5ページを解放した");
                                   SoundManager.sendSuccess((Player) event.getWhoClicked());
                               }
                           }
                           case "6ページまで開放しますか？" ->{
                               if(EnderSystem.removeBank((Player) event.getWhoClicked(),EnderSystem.CalculateCost((Player) event.getWhoClicked(),6))){
                                   EnderSystem.setEClockPage((Player) event.getWhoClicked(),6);
                                   plugin.getLogger().info("(ECP)"+event.getWhoClicked().getName()+"がエンダーチェストの6ページを解放した");
                                   SoundManager.sendSuccess((Player) event.getWhoClicked());
                               }
                           }
                           case "7ページまで開放しますか？" ->{
                               if(EnderSystem.removeBank((Player) event.getWhoClicked(),EnderSystem.CalculateCost((Player) event.getWhoClicked(),7))){
                                   EnderSystem.setEClockPage((Player) event.getWhoClicked(),7);
                                   plugin.getLogger().info("(ECP)"+event.getWhoClicked().getName()+"がエンダーチェストの7ページを解放した");
                                   SoundManager.sendSuccess((Player) event.getWhoClicked());
                               }
                           }
                           case "8ページまで開放しますか？" ->{
                               if(EnderSystem.removeBank((Player) event.getWhoClicked(),EnderSystem.CalculateCost((Player) event.getWhoClicked(),8))){
                                   EnderSystem.setEClockPage((Player) event.getWhoClicked(),8);
                                   plugin.getLogger().info("(ECP)"+event.getWhoClicked().getName()+"がエンダーチェストの8ページを解放した");
                                   SoundManager.sendSuccess((Player) event.getWhoClicked());
                               }
                           }
                           case "9ページまで開放しますか？" ->{
                               if(EnderSystem.removeBank((Player) event.getWhoClicked(),EnderSystem.CalculateCost((Player) event.getWhoClicked(),9))){
                                   EnderSystem.setEClockPage((Player) event.getWhoClicked(),9);
                                   plugin.getLogger().info("(ECP)"+event.getWhoClicked().getName()+"がエンダーチェストの9ページを解放した");
                                   SoundManager.sendSuccess((Player) event.getWhoClicked());
                               }
                           }
                       }
                       event.getWhoClicked().closeInventory();
                   }
                   if(event.getSlot()==8){
                       event.getWhoClicked().closeInventory();
                   }
               }
           }
        }
    }
}
