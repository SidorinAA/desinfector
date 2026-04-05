package org.example.database.interfaces;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface StatementConnectConfigure {

     PreparedStatement getPrepareStatement(String sql) throws SQLException;
}
