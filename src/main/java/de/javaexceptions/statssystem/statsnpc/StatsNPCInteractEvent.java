package de.javaexceptions.statssystem.statsnpc;

import de.javaexceptions.statssystem.commands.StatsCommand;
import net.citizensnpcs.api.event.NPCRightClickEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class StatsNPCInteractEvent implements Listener {

    private final String NPC_NAME = "§3§lStats";
    private final StatsCommand statsCommand = new StatsCommand();

    @EventHandler
    public void onStatsNPCRightClick(NPCRightClickEvent event) {
        if (!(event.getNPC().getName().equalsIgnoreCase(NPC_NAME))) {
            return;
        }

        final Player player = event.getClicker();
        statsCommand.openStatsGUI(player);
    }
}