package de.javaexceptions.statssystem.listener;

import de.javaexceptions.statssystem.commands.StatsCommand;
import de.javaexceptions.statssystem.manager.StatsManager;
import de.javaexceptions.statssystem.utils.ConfigMessages;
import de.javaexceptions.statssystem.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public class StatsInventoryClickEvent implements Listener {

    private final StatsManager statsManager = new StatsManager();
    private final StatsCommand statsCommand = new StatsCommand();

     final int TOP5_CHOOSE_INVENTORY_SIZE = 9, STATS_RESET_CONFIRM_INVENTORY_SIZE = 9*3;
     final String TOP5_CHOOSE_INVENTORY_NAME = "§e§lWähle eine Katergorie", STATS_RESET_CONFIRM_INVENTORY_NAME = "§4§lStats Löschen";

    private void openTop5ChooseInventory(Player player) {
        final Inventory top5ChooseInventory = Bukkit.createInventory(null, TOP5_CHOOSE_INVENTORY_SIZE, TOP5_CHOOSE_INVENTORY_NAME);

        for(int i = 0; i < top5ChooseInventory.getSize(); i++) {
            top5ChooseInventory.setItem(i, new ItemBuilder(" ", Material.BLACK_STAINED_GLASS_PANE, 1).build());
        }

        top5ChooseInventory.setItem(0, new ItemBuilder("§c§lZurück", Material.LECTERN, 1).removeItemFlags().build());
        top5ChooseInventory.setItem(3, new ItemBuilder("§a§lKills", Material.IRON_SWORD, 1).removeItemFlags().build());
        top5ChooseInventory.setItem(4, new ItemBuilder("§4§lDeaths", Material.DIAMOND_CHESTPLATE, 1).removeItemFlags().build());
        top5ChooseInventory.setItem(5, new ItemBuilder("§d§lElo", Material.GOLD_INGOT, 1).build());

        player.openInventory(top5ChooseInventory);
    }

    private void openStatsResetConfirmInventory(Player player) {
        final Inventory statsResetInventory = Bukkit.createInventory(null, STATS_RESET_CONFIRM_INVENTORY_SIZE, STATS_RESET_CONFIRM_INVENTORY_NAME);

        for(int i = 0; i < statsResetInventory.getSize(); i++) {
            statsResetInventory.setItem(i, new ItemBuilder(" ", Material.BLACK_STAINED_GLASS_PANE, 1).build());
        }

        statsResetInventory.setItem(4, new ItemBuilder("§c§lBist du dir Sicher?", Material.BOOK, 1).setLores("§8➥ §7Willst du deine Stats wirklich reseten?").build());
        statsResetInventory.setItem(12, new ItemBuilder("§a§lBestätigen", Material.GREEN_WOOL, 1).build());
        statsResetInventory.setItem(14, new ItemBuilder("§4§lAbbrechen", Material.RED_WOOL, 1).build());

        player.openInventory(statsResetInventory);
    }

    @EventHandler
    public void onClickOnMainMenu(InventoryClickEvent event) {
        if(!(event.getView().getTitle().equalsIgnoreCase(StatsCommand.INVENTORY_NAME))) {
            return;
        }

        if(event.getClickedInventory() == null) {
            return;
        }

        if(event.getCurrentItem() == null) {
            return;
        }

        final Player player = (Player) event.getWhoClicked();
        final Material material = event.getCurrentItem().getType();

        switch (material) {
            default:
                event.setCancelled(true);
                break;
            case NAME_TAG:
                event.setCancelled(true);
                openTop5ChooseInventory(player);
                break;
            case BARRIER:
                event.setCancelled(true);
                openStatsResetConfirmInventory(player);
                break;
        }
    }

    @EventHandler
    public void onClickOnStatsConfirmInventory(InventoryClickEvent event) {
        if(!(event.getView().getTitle().equalsIgnoreCase(STATS_RESET_CONFIRM_INVENTORY_NAME))) {
            return;
        }

        if(event.getClickedInventory() == null) {
            return;
        }

        if(event.getCurrentItem() == null) {
            return;
        }

        final Player player = (Player) event.getWhoClicked();
        final Material material = event.getCurrentItem().getType();

        switch (material) {
            default:
                event.setCancelled(true);
                break;
            case GREEN_WOOL:
                event.setCancelled(true);
                player.closeInventory();

                final int kills = statsManager.getKills(player.getUniqueId().toString()), deaths = statsManager.getDeaths(player.getUniqueId().toString()), elo = statsManager.getElo(player.getUniqueId().toString());

                if(kills == 0 && deaths == 0 && elo == 0) {
                    player.sendMessage(ConfigMessages.STATS_PREFIX + "§cDu kannst deine Stats nicht reseten, da deine Stats bereits auf 0 sind.");
                } else {
                    statsManager.setDeaths(player.getUniqueId().toString(), 0);
                    statsManager.setKills(player.getUniqueId().toString(), 0);
                    statsManager.setElo(player.getUniqueId().toString(), 0);

                    player.sendMessage(ConfigMessages.STATS_PREFIX + "§aDeine Stats wurden erfolgreich reseted.");
                }
                break;
            case RED_WOOL:
                event.setCancelled(true);
                statsCommand.openStatsGUI(player);
                break;
        }
    }

    @EventHandler
    public void onClickOnTop5ChooseCategory(InventoryClickEvent event) {
        if(!(event.getView().getTitle().equalsIgnoreCase(TOP5_CHOOSE_INVENTORY_NAME))) {
            return;
        }

        if(event.getClickedInventory() == null) {
            return;
        }

        if(event.getCurrentItem() == null) {
            return;
        }

        final Player player = (Player) event.getWhoClicked();
        final Material material = event.getCurrentItem().getType();

        switch (material) {
            default:
                event.setCancelled(true);
                break;
            case IRON_SWORD:
                event.setCancelled(true);
                statsManager.openTop5KillsInventory(player);
                break;
            case DIAMOND_CHESTPLATE:
                event.setCancelled(true);
                statsManager.openTop5DeathsInventory(player);
                break;
            case GOLD_INGOT:
                event.setCancelled(true);
                statsManager.openTop5EloInventory(player);
                break;
            case LECTERN:
                event.setCancelled(true);
                statsCommand.openStatsGUI(player);
                break;
        }
    }

    @EventHandler
    public void onClickOnTop5(InventoryClickEvent event) {
        if(!(event.getView().getTitle().equalsIgnoreCase(statsManager.KILLS_TOP5_INVENTORY_NAME) || event.getView().getTitle().equalsIgnoreCase(statsManager.DEATHS_TOP5_INVENTORY_NAME) || event.getView().getTitle().equalsIgnoreCase(statsManager.ELO_TOP5_INVENTORY_NAME))) {
            return;
        }

        if(event.getClickedInventory() == null) {
            return;
        }

        if(event.getCurrentItem() == null) {
            return;
        }

        final Player player = (Player) event.getWhoClicked();
        final Material material = event.getCurrentItem().getType();

        switch (material) {
            default:
                event.setCancelled(true);
                break;
            case LECTERN:
                event.setCancelled(true);
                openTop5ChooseInventory(player);
                break;
        }
    }
}