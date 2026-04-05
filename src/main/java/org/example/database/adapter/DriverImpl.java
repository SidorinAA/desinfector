/*
package org.example.database.adapter;


import org.example.annotation.InjectByType;
import org.example.annotation.InjectProperty;
import org.example.annotation.betta.PostgresDriver;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

public abstract class DriverImpl implements Driver {

    @InjectByType
    private Driver driver;

    @InjectProperty("url")
    private  String url;
    @InjectProperty("user")
    private  String user;
    @InjectProperty("password")
    private  String password;

    public DriverImpl(Driver driver) {
        this.driver = driver;
    }


    public Connection connection() throws SQLException {
        java.util.Properties info = new java.util.Properties();
        if (user != null) {
            info.put("user", user);
        }
        if (password != null) {
            info.put("password", password);
        }
        return connect(url, info);
    }

    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        return driver.connect(url, info);
    }
}
*/
