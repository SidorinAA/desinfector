/*
package org.example.database.configure;

import org.example.annotation.InjectByType;
import org.example.database.adapter.DatabaseConnector;
import org.example.database.interfaces.StatementConnectConfigure;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PrepareStatementConnectConfigure implements StatementConnectConfigure {

    @InjectByType
    private DatabaseConnector connector;

    @Override
    public PreparedStatement getPrepareStatement(String sql) throws SQLException {
        return (PreparedStatement) connector.getConnection(sql);

    }
}
*/
