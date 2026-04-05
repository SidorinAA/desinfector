package org.example.database.interfaces;

import org.example.database.pojo.Student;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@Deprecated
public interface Connector {
    List<Student> getConnection(String query) throws SQLException;
}
