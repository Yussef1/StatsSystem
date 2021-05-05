package de.javaexceptions.statssystem.manager;

import de.javaexceptions.statssystem.StatsSystem;
import de.javaexceptions.statssystem.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class StatsManager {

    public final String KILLS_TOP5_INVENTORY_NAME = "§a§lTop 5 Kills Ranking", DEATHS_TOP5_INVENTORY_NAME = "§4§lTop 5 Deaths Ranking", ELO_TOP5_INVENTORY_NAME = "§d§lTop 5 Elo Ranking";

    public boolean isInDatabase(String playerUUID) {
        try {
            ResultSet resultSet = StatsSystem.getAsyncMySQL().prepare("SELECT * FROM StatsSystem WHERE PlayerUUID='"+playerUUID+"'").executeQuery();
            return resultSet.next();
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public void insertToDatabase(String playerUUID) {
        StatsSystem.getAsyncMySQL().update("INSERT INTO StatsSystem (PlayerUUID, Kills, Deaths, Elo) VALUES ('"+playerUUID+"', '0', '0', '0')");
    }

    public Integer getKills(String playerUUID) {
        try {
            if(isInDatabase(playerUUID)) {
                ResultSet resultSet = StatsSystem.getAsyncMySQL().prepare("SELECT * FROM StatsSystem WHERE PlayerUUID='"+playerUUID+"'").executeQuery();

                while(resultSet.next()) {
                    return resultSet.getInt("Kills");
                }
            } else {
                insertToDatabase(playerUUID);
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public Integer getDeaths(String playerUUID) {
        try {
            if(isInDatabase(playerUUID)) {
                ResultSet resultSet = StatsSystem.getAsyncMySQL().prepare("SELECT * FROM StatsSystem WHERE PlayerUUID='"+playerUUID+"'").executeQuery();

                while(resultSet.next()) {
                    return resultSet.getInt("Deaths");
                }
            } else {
                insertToDatabase(playerUUID);
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public Integer getElo(String playerUUID) {
        try {
            if(isInDatabase(playerUUID)) {
                ResultSet resultSet = StatsSystem.getAsyncMySQL().prepare("SELECT * FROM StatsSystem WHERE PlayerUUID='"+playerUUID+"'").executeQuery();

                while(resultSet.next()) {
                    return resultSet.getInt("Elo");
                }
            } else {
                insertToDatabase(playerUUID);
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    public void addKills(String playerUUID, int killsAmount) {
        if(isInDatabase(playerUUID)) {
            if(killsAmount < 0) {
                killsAmount = 0;
            }

            final int totalAmount = getKills(playerUUID) + killsAmount;
            StatsSystem.getAsyncMySQL().update("UPDATE StatsSystem SET Kills='"+totalAmount+"' WHERE PlayerUUID='"+playerUUID+"'");
        } else {
            insertToDatabase(playerUUID);
        }
    }

    public void addDeaths(String playerUUID, int deathsAmount) {
        if(isInDatabase(playerUUID)) {
            if(deathsAmount < 0) {
                deathsAmount = 0;
            }

            final int totalAmount = getDeaths(playerUUID) + deathsAmount;
            StatsSystem.getAsyncMySQL().update("UPDATE StatsSystem SET Deaths='"+totalAmount+"' WHERE PlayerUUID='"+playerUUID+"'");
        } else {
            insertToDatabase(playerUUID);
        }
    }

    public void addElo(String playerUUID, int eloAmount) {
        if(isInDatabase(playerUUID)) {
            if(eloAmount < 0) {
                eloAmount = 0;
            }

            final int totalAmount = getElo(playerUUID) + eloAmount;
            StatsSystem.getAsyncMySQL().update("UPDATE StatsSystem SET Elo='"+totalAmount+"' WHERE PlayerUUID='"+playerUUID+"'");
        } else {
            insertToDatabase(playerUUID);
        }
    }

    public void removeKills(String playerUUID, int killsAmount) {
        if(isInDatabase(playerUUID)) {
            if(killsAmount < 0) {
                killsAmount = 0;
            }

            final int totalAmount = getKills(playerUUID) - killsAmount;
            StatsSystem.getAsyncMySQL().update("UPDATE StatsSystem SET Kills='"+totalAmount+"' WHERE PlayerUUID='"+playerUUID+"'");
        } else {
            insertToDatabase(playerUUID);
        }
    }

    public void removeDeaths(String playerUUID, int deathsAmount) {
        if(isInDatabase(playerUUID)) {
            if(deathsAmount < 0) {
                deathsAmount = 0;
            }

            final int totalAmount = getDeaths(playerUUID) - deathsAmount;
            StatsSystem.getAsyncMySQL().update("UPDATE StatsSystem SET Deaths='"+totalAmount+"' WHERE PlayerUUID='"+playerUUID+"'");
        } else {
            insertToDatabase(playerUUID);
        }
    }

    public void removeElo(String playerUUID, int eloAmount) {
        if(isInDatabase(playerUUID)) {
            if(eloAmount < 0) {
                eloAmount = 0;
            }

            final int totalAmount = getElo(playerUUID) - eloAmount;
            StatsSystem.getAsyncMySQL().update("UPDATE StatsSystem SET Elo='"+totalAmount+"' WHERE PlayerUUID='"+playerUUID+"'");
        } else {
            insertToDatabase(playerUUID);
        }
    }

    public void setKills(String playerUUID, int killsAmount) {
        if(isInDatabase(playerUUID)) {
            if(killsAmount < 0) {
                killsAmount = 0;
            }

            StatsSystem.getAsyncMySQL().update("UPDATE StatsSystem SET Kills='"+killsAmount+"' WHERE PlayerUUID='"+playerUUID+"'");
        } else {
            insertToDatabase(playerUUID);
        }
    }

    public void setDeaths(String playerUUID, int deathsAmount) {
        if(isInDatabase(playerUUID)) {
            if(deathsAmount < 0) {
                deathsAmount = 0;
            }

            StatsSystem.getAsyncMySQL().update("UPDATE StatsSystem SET Deaths='"+deathsAmount+"' WHERE PlayerUUID='"+playerUUID+"'");
        } else {
            insertToDatabase(playerUUID);
        }
    }

    public void setElo(String playerUUID, int eloAmount) {
        if(isInDatabase(playerUUID)) {
            if(eloAmount < 0) {
                eloAmount = 0;
            }

            StatsSystem.getAsyncMySQL().update("UPDATE StatsSystem SET Elo='"+eloAmount+"' WHERE PlayerUUID='"+playerUUID+"'");
        } else {
            insertToDatabase(playerUUID);
        }
    }

    public void clearStatsList(String playerUUID) {
        StatsSystem.getAsyncMySQL().update("TRUNCATE TABLE StatsSystem");
    }

    public void deleteFromDatabase(String playerUUID) {
        StatsSystem.getAsyncMySQL().update("DELETE FROM StatsSystem WHERE PlayerUUID='"+playerUUID+"'");
    }

    public void openTop5KillsInventory(Player player) {
        try {
            ResultSet resultSet = StatsSystem.getAsyncMySQL().prepare("SELECT * FROM StatsSystem ORDER BY Kills DESC LIMIT 5").executeQuery();
            Inventory top5KillsInventory = Bukkit.createInventory(null, 9, KILLS_TOP5_INVENTORY_NAME);

            while(resultSet.next()) {
                final String topPlayer = Bukkit.getOfflinePlayer(UUID.fromString(resultSet.getString("PlayerUUID"))).getName();

                ArrayList<String> top5KillsDescription = new ArrayList<String>();
                ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta playerHeadMeta = (SkullMeta) playerHead.getItemMeta();
                playerHeadMeta.setOwner(topPlayer);
                playerHeadMeta.setDisplayName("§3" + topPlayer);
                playerHead.setAmount(1);
                top5KillsDescription.add("§8❯ §7Hat §a§l" + String.format("%,d", resultSet.getInt("Kills")) + " §7Kills");
                playerHeadMeta.setLore(top5KillsDescription);
                playerHead.setItemMeta(playerHeadMeta);

                top5KillsInventory.setItem(0, new ItemBuilder("§c§lZurück", Material.LECTERN, 1).build());
                top5KillsInventory.setItem(1, new ItemBuilder(" ", Material.BLACK_STAINED_GLASS_PANE, 1).build());

                top5KillsInventory.setItem(7, new ItemBuilder(" ", Material.BLACK_STAINED_GLASS_PANE, 1).build());
                top5KillsInventory.setItem(8, new ItemBuilder(" ", Material.BLACK_STAINED_GLASS_PANE, 1).build());

                top5KillsInventory.addItem(playerHead);
                player.openInventory(top5KillsInventory);
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void openTop5DeathsInventory(Player player) {
        try {
            ResultSet resultSet = StatsSystem.getAsyncMySQL().prepare("SELECT * FROM StatsSystem ORDER BY Deaths DESC LIMIT 5").executeQuery();
            Inventory top5DeathsInventory = Bukkit.createInventory(null, 9, DEATHS_TOP5_INVENTORY_NAME);

            while(resultSet.next()) {
                final String topPlayer = Bukkit.getOfflinePlayer(UUID.fromString(resultSet.getString("PlayerUUID"))).getName();

                ArrayList<String> top5KillsDescription = new ArrayList<String>();
                ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta playerHeadMeta = (SkullMeta) playerHead.getItemMeta();
                playerHeadMeta.setOwner(topPlayer);
                playerHeadMeta.setDisplayName("§3" + topPlayer);
                playerHead.setAmount(1);
                top5KillsDescription.add("§8❯ §7Hat §4§l" + String.format("%,d", resultSet.getInt("Deaths")) + " §7Tode.");
                playerHeadMeta.setLore(top5KillsDescription);
                playerHead.setItemMeta(playerHeadMeta);

                top5DeathsInventory.setItem(0, new ItemBuilder("§C§lZurück", Material.LECTERN, 1).build());
                top5DeathsInventory.setItem(1, new ItemBuilder(" ", Material.BLACK_STAINED_GLASS_PANE, 1).build());

                top5DeathsInventory.setItem(7, new ItemBuilder(" ", Material.BLACK_STAINED_GLASS_PANE, 1).build());
                top5DeathsInventory.setItem(8, new ItemBuilder(" ", Material.BLACK_STAINED_GLASS_PANE, 1).build());

                top5DeathsInventory.addItem(playerHead);
                player.openInventory(top5DeathsInventory);
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }

    public void openTop5EloInventory(Player player) {
        try {
            ResultSet resultSet = StatsSystem.getAsyncMySQL().prepare("SELECT * FROM StatsSystem ORDER BY Kills DESC LIMIT 5").executeQuery();
            Inventory top5EloInventory = Bukkit.createInventory(null, 9, ELO_TOP5_INVENTORY_NAME);

            while(resultSet.next()) {
                final String topPlayer = Bukkit.getOfflinePlayer(UUID.fromString(resultSet.getString("PlayerUUID"))).getName();

                ArrayList<String> top5KillsDescription = new ArrayList<String>();
                ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
                SkullMeta playerHeadMeta = (SkullMeta) playerHead.getItemMeta();
                playerHeadMeta.setOwner(topPlayer);
                playerHeadMeta.setDisplayName("§3" + topPlayer);
                playerHead.setAmount(1);
                top5KillsDescription.add("§8❯ §7Hat §d§l" + String.format("%,d", resultSet.getInt("Elo")) + " §7Elo.");
                playerHeadMeta.setLore(top5KillsDescription);
                playerHead.setItemMeta(playerHeadMeta);

                top5EloInventory.setItem(0, new ItemBuilder("§c§lZurück", Material.LECTERN, 1).build());
                top5EloInventory.setItem(1, new ItemBuilder(" ", Material.BLACK_STAINED_GLASS_PANE, 1).build());

                top5EloInventory.setItem(7, new ItemBuilder(" ", Material.BLACK_STAINED_GLASS_PANE, 1).build());
                top5EloInventory.setItem(8, new ItemBuilder(" ", Material.BLACK_STAINED_GLASS_PANE, 1).build());

                top5EloInventory.addItem(playerHead);
                player.openInventory(top5EloInventory);
            }
        } catch(SQLException exception) {
            exception.printStackTrace();
        }
    }
}