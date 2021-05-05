package de.javaexceptions.statssystem.listener;

import de.javaexceptions.statssystem.manager.StatsManager;
import de.javaexceptions.statssystem.utils.ConfigMessages;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.concurrent.ThreadLocalRandom;

public class StatsDeathEvent implements Listener {

    private final StatsManager statsManager = new StatsManager();

    @EventHandler
    public void onDeath(PlayerDeathEvent event) {
        final Player player = event.getEntity();
        final Player killer = event.getEntity().getKiller();

        event.setDeathMessage(null);

        int deaths = statsManager.getDeaths(player.getUniqueId().toString()) + 1;

        if(killer == null) {
            statsManager.addDeaths(player.getUniqueId().toString(), 1);
            player.sendMessage(ConfigMessages.STATS_PREFIX + "§cDu bist gestorben.");
            player.sendMessage(ConfigMessages.STATS_PREFIX + "Du hast nun §4§l" + deaths + "§7 Tode.");
            return;
        }

        int maxElo = 100, randomElo = ThreadLocalRandom.current().nextInt(maxElo), killerElo = statsManager.getElo(killer.getUniqueId().toString()), kills = statsManager.getKills(killer.getUniqueId().toString()) + 1;

        statsManager.addKills(killer.getUniqueId().toString(), 1);
        statsManager.addElo(killer.getUniqueId().toString(), randomElo);

        player.sendMessage(ConfigMessages.STATS_PREFIX + "§7Du wurdest von §c" + killer.getName() + "§7 getötet.");
        killer.sendMessage(ConfigMessages.STATS_PREFIX + "Du hast §a" + player.getName() + "§7 getötet.");
        killer.sendMessage(ConfigMessages.STATS_PREFIX + "Du erhälst §d§l" + randomElo + "§7 Elo.");
    }
}