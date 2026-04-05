package org.example.annotation.betta;

import org.example.annotation.InjectProperty;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class PostgresDriver extends Driver {
    private org.postgresql.Driver driver;

    @InjectProperty("url")
    private  String url;
    @InjectProperty("user")
    private  String user;
    @InjectProperty("password")
    private  String password;

    public PostgresDriver() {
        try {
            this.driver = new org.postgresql.Driver();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize PostgreSQL driver", e);
        }
    }


    @Override
    public Connection getConnection() {
        java.util.Properties info = new java.util.Properties();
        if (user != null) {
            info.put("user", user);
        }
        if (password != null) {
            info.put("password", password);
        }
        return connect(url, info);
    }

    public Connection connect(String url, Properties info) {
        try {
            return driver.connect(url, info);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}