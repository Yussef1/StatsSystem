package de.javaexceptions.statssystem.commands;

import de.javaexceptions.statssystem.manager.StatsManager;
import de.javaexceptions.statssystem.utils.ConfigMessages;
import de.javaexceptions.statssystem.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class StatsCommand implements CommandExecutor {

    private final StatsManager statsManager = new StatsManager();

    private final int INVENTORY_SIZE = 9*4;
    public static final String INVENTORY_NAME = "§e§lDeine Stats";

    public void openStatsGUI(Player player) {
        final Inventory statsInventory = Bukkit.createInventory(null, INVENTORY_SIZE, INVENTORY_NAME);

        for(int i = 0; i < statsInventory.getSize(); i++) {
            statsInventory.setItem(i, new ItemBuilder(" ", Material.BLACK_STAINED_GLASS_PANE, 1).build());
        }

        int kills = statsManager.getKills(player.getUniqueId().toString()), deaths = statsManager.getDeaths(player.getUniqueId().toString());
        double kdAmount = (double) kills / (double) deaths;
        final DecimalFormat decimalFormat = new DecimalFormat("0.00");

        ArrayList<String> kdDescription = new ArrayList<String>();
        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta playerHeadMeta = (SkullMeta) playerHead.getItemMeta();
        playerHeadMeta.setOwner(player.getName());
        playerHeadMeta.setDisplayName("§3§l"+player.getName());

        if(kills == 0 && deaths == 0) {
            kdDescription.add("§8➥ §7Deine K/D §6§l0,00");
        } else {
            kdDescription.add("§8➥ §7Deine K/D §6§l" + decimalFormat.format(kdAmount));
        }

        playerHeadMeta.setLore(kdDescription);
        playerHead.setItemMeta(playerHeadMeta);

        statsInventory.setItem(4, playerHead);
        statsInventory.setItem(10, new ItemBuilder("§a§lKills", Material.IRON_SWORD, 1).setLores("§8➥ §7Du hast zurzeit §a§l" + String.format("%,d", statsManager.getKills(player.getUniqueId().toString())) + "§7 Kills.").removeItemFlags().build());
        statsInventory.setItem(12, new ItemBuilder("§4§lDeaths", Material.DIAMOND_CHESTPLATE, 1).setLores("§8➥ §7Du hast zurzeit §4§l" + String.format("%,d", statsManager.getDeaths(player.getUniqueId().toString())) + "§7 Tode.").removeItemFlags().build());
        statsInventory.setItem(14, new ItemBuilder("§6§lElo", Material.GOLD_INGOT, 1).setLores("§8➥ §7Du hast zurzeit §6§l" + String.format("%,d", statsManager.getElo(player.getUniqueId().toString())) + "§7 Elo.").removeItemFlags().build());
        statsInventory.setItem(16, new ItemBuilder("§e§lTop 5", Material.NAME_TAG, 1).setLores( "§8➥ §7Klicke §ehier§7, um die Top 5 auflisten zu lassen.").build());


        statsInventory.setItem(31, new ItemBuilder("§4§lStats Löschen", Material.BARRIER, 1).setLores( "§8➥ §7Klicke §4hier§7, um deine Stats zu Löschen.").build());
        player.openInventory(statsInventory);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        if(!(commandSender instanceof Player)) {
            Bukkit.getConsoleSender().sendMessage(ConfigMessages.SENDER_IS_CONSOLE);
            return true;
        }

        final Player player = (Player) commandSender;

        if(!(player.hasPermission(ConfigMessages.COMMAND_PERMISSION))) {
            player.sendMessage(ConfigMessages.NO_PERMISSION_MESSAGE);
            return true;
        }

        openStatsGUI(player);
        return false;
    }
}
