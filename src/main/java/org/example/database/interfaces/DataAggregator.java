package org.example.database.interfaces;

import org.example.database.pojo.Student;

import java.sql.PreparedStatement;
import java.util.UUID;

public interface DataAggregator {

    Student getData(UUID id, String sql );
    Student getResultSet(PreparedStatement preparedStatement, UUID id) throws Exception;
}
