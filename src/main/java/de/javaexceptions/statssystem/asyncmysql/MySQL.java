package de.javaexceptions.statssystem.asyncmysql;

import java.sql.*;

public class MySQL {

    private final String host, username, password, database;
    private final int port;

    public Connection connection;

    public MySQL(String host, int port, String username, String password, String database) throws Exception {
        this.host = host;
        this.port = port;
        this.username = username;
        this.password = password;
        this.database = database;

        this.openConnection();
    }

    public void queryUpdate(String query) {
        checkConnection();
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            queryUpdate(statement);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void queryUpdate(PreparedStatement statement) {
        checkConnection();
        try {
            statement.executeUpdate();
        } catch (Exception exception) {
            exception.printStackTrace();
        } finally {
            try {
                statement.close();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    public ResultSet query(String query) {
        checkConnection();
        try {
            return query(connection.prepareStatement(query));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public ResultSet query(PreparedStatement statement) {
        checkConnection();
        try {
            return statement.executeQuery();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public Connection getConnection() {
        checkConnection();
        return this.connection;
    }

    private void checkConnection() {
        try {
            if (this.connection == null || !this.connection.isValid(10) || this.connection.isClosed()) openConnection();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    public void openConnection() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database + "?autoReconnect=true", this.username, this.password);
    }

    public void closeConnection() {
        try {
            this.connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            this.connection = null;
        }
    }
}