package de.javaexceptions.statssystem.asyncmysql;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class AsyncMySQL {

    private ExecutorService executorService;
    private Plugin plugin;
    private MySQL mySQL;

    public AsyncMySQL(Plugin owner, String host, int port, String username, String password, String database) {
        try {
            mySQL = new MySQL(host, port, username, password, database);
            executorService = Executors.newCachedThreadPool();
            plugin = owner;
            Bukkit.getConsoleSender().sendMessage("Es wurde eine Datenbank gefunden. MySQL ist nun Verbunden.");
        } catch (Exception exception) {
            Bukkit.getConsoleSender().sendMessage("Die MySQL Daten stimmen nicht, trage in die config.yml die Daten ein.");
        }
    }

    public void update(PreparedStatement statement) {
        executorService.execute(() -> mySQL.queryUpdate(statement));
    }

    public void update(String statement) {
        executorService.execute(() -> mySQL.queryUpdate(statement));
    }

    public void query(PreparedStatement statement, Consumer<ResultSet> consumer) {
        executorService.execute(() -> {
            ResultSet result = mySQL.query(statement);
            Bukkit.getScheduler().runTask(plugin, () -> consumer.accept(result));
        });
    }

    public void query(String statement, Consumer<ResultSet> consumer) {
        executorService.execute(() -> {
            ResultSet result = mySQL.query(statement);
            Bukkit.getScheduler().runTask(plugin, () -> consumer.accept(result));
        });
    }

    public PreparedStatement prepare(String query) {
        try {
            return mySQL.getConnection().prepareStatement(query);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public MySQL getMySQL() {
        return mySQL;
    }
}