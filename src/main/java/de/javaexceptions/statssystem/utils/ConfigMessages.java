package de.javaexceptions.statssystem.utils;

import de.javaexceptions.statssystem.StatsSystem;

public class ConfigMessages {

    public final static String STATS_PREFIX = StatsSystem.getStatsSystem().getConfig().getString("General.Prefix").replace("&", "§"),
                               NO_PERMISSION_MESSAGE = StatsSystem.getStatsSystem().getConfig().getString("General.NoPermissionMessage").replace("%prefix%", STATS_PREFIX).replace("&", "§"),
                               SENDER_IS_CONSOLE = StatsSystem.getStatsSystem().getConfig().getString("General.SenderIsConsole"),
                               COMMAND_PERMISSION = StatsSystem.getStatsSystem().getConfig().getString("General.Permission");
}