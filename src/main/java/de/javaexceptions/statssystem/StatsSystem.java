package de.javaexceptions.statssystem;

import de.javaexceptions.statssystem.asyncmysql.AsyncMySQL;
import de.javaexceptions.statssystem.commands.StatsCommand;
import de.javaexceptions.statssystem.listener.StatsDeathEvent;
import de.javaexceptions.statssystem.listener.StatsInventoryClickEvent;
import de.javaexceptions.statssystem.statsnpc.StatsNPCInteractEvent;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class StatsSystem extends JavaPlugin {

    private static StatsSystem statsSystem;
    private static AsyncMySQL asyncMySQL;

    @Override
    public void onLoad() {
        statsSystem = this;
    }

    @Override
    public void onEnable() {
        printOutWatermark();
        loadConfig();
        connectMySQL();
        registerEvents();

        Bukkit.getConsoleSender().sendMessage("Das StatsSystem wurde Aktiviert.");
    }

    @Override
    public void onDisable() {
        Bukkit.getConsoleSender().sendMessage("Das StatsSystem wurde Deaktiviert.");
    }

    private void printOutWatermark() {
        Bukkit.getConsoleSender().sendMessage("   _____ _        _        _____           _                 \n" +
                "  / ____| |      | |      / ____|         | |                \n" +
                " | (___ | |_ __ _| |_ ___| (___  _   _ ___| |_ ___ _ __ ___  \n" +
                "  \\___ \\| __/ _` | __/ __|\\___ \\| | | / __| __/ _ \\ '_ ` _ \\ \n" +
                "  ____) | || (_| | |_\\__ \\____) | |_| \\__ \\ ||  __/ | | | | |\n" +
                " |_____/ \\__\\__,_|\\__|___/_____/ \\__, |___/\\__\\___|_| |_| |_|\n" +
                "                                  __/ |                      \n" +
                "                                 |___/                       ");
    }

    private void loadConfig() {
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    private void connectMySQL() {
        int port = getConfig().getInt("MySQL.Port");
        String host = getConfig().getString("MySQL.Host"),
               database = getConfig().getString("MySQL.Database"),
               username = getConfig().getString("MySQL.Username"),
               password = getConfig().getString("MySQL.Password");

        asyncMySQL = new AsyncMySQL(statsSystem, host, port, username, password, database);
        asyncMySQL.update("CREATE TABLE IF NOT EXISTS StatsSystem (PlayerUUID VARCHAR(64), Kills INT(100), Deaths  INT(100), Elo INT(100))");
    }

    private void registerEvents() {
        final PluginManager pluginManager = Bukkit.getPluginManager();

        // Listener
        pluginManager.registerEvents(new StatsInventoryClickEvent(), statsSystem);
        pluginManager.registerEvents(new StatsDeathEvent(), statsSystem);
        pluginManager.registerEvents(new StatsNPCInteractEvent(), statsSystem);
        // Commands
        getCommand("stats").setExecutor(new StatsCommand());
    }

    public static StatsSystem getStatsSystem() {
        return statsSystem;
    }

    public static AsyncMySQL getAsyncMySQL() {
        return asyncMySQL;
    }
}