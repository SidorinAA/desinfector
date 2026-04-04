package org.example.database.adapter;

import org.example.annotation.InjectProperty;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.example.database.interfaces.Connector;


public class DatabaseConnector implements Connector {

    @InjectProperty("url")
    private  String url;
    @InjectProperty("user")
    private  String user;
    @InjectProperty("password")
    private  String password;

    @Override
    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("PostgreSQL JDBC Driver not found", e);
        }
    }
}