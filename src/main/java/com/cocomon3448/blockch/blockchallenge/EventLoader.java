package com.cocomon3448.blockch.blockchallenge;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Map;

public class EventLoader implements Listener {
    public String[] yellow = {"rammer3448","0"};
    public String[] orange = {"pumpkeenboy","orange_space"};
    public String[] green = {"_torch_light_","0"};

    public boolean consumeItem(Player player, int count, Material mat) {
        Map<Integer, ? extends ItemStack> ammo = player.getInventory().all(mat);

        int found = 0;
        for (ItemStack stack : ammo.values())
            found += stack.getAmount();
        if (count > found)
            return false;

        for (Integer index : ammo.keySet()) {
            ItemStack stack = ammo.get(index);

            int removed = Math.min(count, stack.getAmount());
            count -= removed;

            if (stack.getAmount() == removed)
                player.getInventory().setItem(index, null);
            else
                stack.setAmount(stack.getAmount() - removed);

            if (count <= 0)
                break;
        }

        player.updateInventory();
        return true;
    }
    @EventHandler
    public void PickupItem(PlayerPickupItemEvent e) {
        ItemStack item1 = new ItemStack(Material.DIAMOND);
        if (e.getItem().getItemStack().equals(item1)) {
            e.getPlayer().sendMessage(ChatColor.DARK_RED + "" + ChatColor.BOLD + "특수아이템 획득 DIAMOND! 우클릭시 사용");
        }
        ItemStack item2 = new ItemStack(Material.GOLD_INGOT,48);
        if (e.getItem().getItemStack().equals(item2)) {
            e.getPlayer().sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "특수아이템 획득 64 GOLD! 우클릭시 사용");
        }
    }
    @EventHandler
    public void onPlayerClicks(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action.equals(Action.RIGHT_CLICK_AIR)) {
            if (player.getItemInHand().getType() == Material.DIAMOND) {
                consumeItem(player, 1, Material.DIAMOND);
                player.sendMessage(ChatColor.GOLD + "" + ChatColor.BOLD + "특수아이템 사용됨");
                boolean cheakyellow = ArrayUtils.contains(yellow, player.getName().toString());
                boolean cheakgreen = ArrayUtils.contains(green, player.getName().toString());
                boolean cheakorange = ArrayUtils.contains(orange, player.getName().toString());
                ItemStack chooseGreen = new ItemStack(Material.GREEN_WOOL);
                ItemMeta chooseGreen_meta = chooseGreen.getItemMeta();
                chooseGreen_meta.setDisplayName("Green");
                chooseGreen.setItemMeta(chooseGreen_meta);

                ItemStack chooseOrange = new ItemStack(Material.ORANGE_WOOL);
                ItemMeta chooseOrange_meta = chooseOrange.getItemMeta();
                chooseOrange_meta.setDisplayName("Orange");
                chooseOrange.setItemMeta(chooseOrange_meta);

                ItemStack chooseYellow = new ItemStack(Material.YELLOW_WOOL);
                ItemMeta chooseYellow_meta = chooseOrange.getItemMeta();
                chooseYellow_meta.setDisplayName("Yellow");
                chooseYellow.setItemMeta(chooseYellow_meta);
                if (cheakyellow) {
                    Inventory SelectTeam_Yellow = Bukkit.createInventory(player, 9, ChatColor.YELLOW + "Select Team(Yellow)");
                    ItemStack[] menu_items_yellow = {chooseGreen, chooseOrange};
                    SelectTeam_Yellow.setContents(menu_items_yellow);
                    player.openInventory(SelectTeam_Yellow);

                }
                if (cheakgreen) {
                    Inventory SelectTeam_Green = Bukkit.createInventory(player, 9, ChatColor.GREEN + "Select Team(Green)");
                    ItemStack[] menu_items_green = {chooseYellow, chooseOrange};
                    SelectTeam_Green.setContents(menu_items_green);
                    player.openInventory(SelectTeam_Green);
                }
                if (cheakorange) {
                    Inventory SelectTeam_Orange = Bukkit.createInventory(player, 9, ChatColor.GOLD + "Select Team(Orange)");
                    ItemStack[] menu_items_orange = {chooseYellow, chooseGreen};
                    SelectTeam_Orange.setContents(menu_items_orange);
                    player.openInventory(SelectTeam_Orange);
                }
            }
            else if(player.getItemInHand().getType() == Material.GOLD_INGOT&&player.getItemInHand().getAmount() >= 48) {
                int count = 0;
                for (ItemStack stack : player.getInventory().getContents()) {
                    if (stack != null && stack.getType() == Material.BARRIER) {
                        count += stack.getAmount();
                    }
                }
                if(count >=1) {
                    consumeItem(player, 48, Material.GOLD_INGOT);
                    player.setHealth(20.0);
                    player.setFoodLevel(20);
                    consumeItem(player, 1, Material.BARRIER);
                }
            }
        }

    }
    @EventHandler
    public void OnInventoryClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();

        if(e.getCurrentItem().getType() == Material.BARRIER)
        {
            e.setCancelled(true);
        }
        if(e.getView().getTitle().equals(ChatColor.YELLOW+"Select Team(Yellow)")) {
            switch (e.getCurrentItem().getType()) {
                case ORANGE_WOOL:
                    player.closeInventory();
                    int countorange = 0;
                    Player orangep = Bukkit.getPlayer(orange[0].toString());
                    for (ItemStack stack : orangep.getInventory().getContents()) {
                        if (stack != null && stack.getType() == Material.BARRIER) {
                            countorange += stack.getAmount();
                        }
                    }
                    for(int i = 0; i<orange.length; i++) {
                        Player p = Bukkit.getPlayer(orange[i].toString());
                        p.getInventory().setItem(35 - countorange, new ItemStack(Material.BARRIER, 1));
                    }
                    player.getInventory().setItem(35 - countorange, new ItemStack(Material.BARRIER, 1));
                    break;
                case GREEN_WOOL:
                    player.closeInventory();
                    int countgreen = 0;
                    Player greenp = Bukkit.getPlayer(green[0].toString());
                    for (ItemStack stack : greenp.getInventory().getContents()) {
                        if (stack != null && stack.getType() == Material.BARRIER) {
                            countgreen += stack.getAmount();
                        }
                    }
                    for(int i = 0; i<green.length; i++) {
                        Player p = Bukkit.getPlayer(green[i].toString());
                        p.getInventory().setItem(35 - countgreen, new ItemStack(Material.BARRIER, 1));
                    }
                    break;
            }
            e.setCancelled(true);
        }
        if(e.getView().getTitle().equals(ChatColor.GOLD+"Select Team(Orange)")) {
            switch(e.getCurrentItem().getType()) {
                case YELLOW_WOOL:
                    player.closeInventory();
                    int countyellow = 0;
                    Player yellowp = Bukkit.getPlayer(yellow[0].toString());
                    for (ItemStack stack : yellowp.getInventory().getContents()) {
                        if (stack != null && stack.getType() == Material.BARRIER) {
                            countyellow += stack.getAmount();
                        }
                    }
                    for(int i = 0; i<yellow.length; i++) {
                        Player p = Bukkit.getPlayer(yellow[i].toString());
                        p.getInventory().setItem(35 - countyellow, new ItemStack(Material.BARRIER, 1));
                    }
                    break;
                case GREEN_WOOL:
                    player.closeInventory();
                    int countgreen = 0;
                    Player greenp = Bukkit.getPlayer(green[0].toString());
                    for (ItemStack stack : greenp.getInventory().getContents()) {
                        if (stack != null && stack.getType() == Material.BARRIER) {
                            countgreen += stack.getAmount();
                        }
                    }
                    for(int i = 0; i<green.length; i++) {
                        Player p = Bukkit.getPlayer(green[i].toString());
                        p.getInventory().setItem(35 - countgreen, new ItemStack(Material.BARRIER, 1));
                    }
                    break;
            }
            e.setCancelled(true);
        }
        if(e.getView().getTitle().equals(ChatColor.GREEN+"Select Team(Green)")) {
            switch(e.getCurrentItem().getType()) {
                case YELLOW_WOOL:
                    player.closeInventory();
                    int countyellow = 0;
                    Player yellowp = Bukkit.getPlayer(yellow[0].toString());
                    for (ItemStack stack : yellowp.getInventory().getContents()) {
                        if (stack != null && stack.getType() == Material.BARRIER) {
                            countyellow += stack.getAmount();
                        }
                    }
                    for(int i = 0; i<yellow.length; i++) {
                        Player p = Bukkit.getPlayer(yellow[i].toString());
                        p.getInventory().setItem(35 - countyellow, new ItemStack(Material.BARRIER, 1));
                    }
                    break;
                case ORANGE_WOOL:
                    player.closeInventory();
                    int countorange = 0;
                    Player orangep = Bukkit.getPlayer(orange[0].toString());
                    for (ItemStack stack : orangep.getInventory().getContents()) {
                        if (stack != null && stack.getType() == Material.BARRIER) {
                            countorange += stack.getAmount();
                        }
                    }
                    for(int i = 0; i<orange.length; i++) {
                        Player p = Bukkit.getPlayer(orange[i].toString());
                        p.getInventory().setItem(35 - countorange, new ItemStack(Material.BARRIER, 1));
                    }
                    break;
            }
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {

        if(e.getItemDrop().getItemStack().getType() == Material.BARRIER)
        {
            e.setCancelled(true);
        }

    }
    @EventHandler
    public void blockPlace(BlockPlaceEvent e) {
        if(e.getBlock().getType() == Material.BARRIER) {
            e.setCancelled(true);
        }
    }
}
