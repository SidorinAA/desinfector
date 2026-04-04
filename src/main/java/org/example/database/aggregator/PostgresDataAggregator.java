package org.example.database.aggregator;

import org.example.annotation.InjectByType;
import org.example.database.interfaces.DataAggregator;
import org.example.database.interfaces.StatementConnectConfigure;
import org.example.database.pojo.Student;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PostgresDataAggregator implements DataAggregator {

    @InjectByType
    private StatementConnectConfigure connectionConfigure;

    @Override
    public Student getData(UUID id, String sql) {
        try (PreparedStatement prepareStatement = connectionConfigure.getPrepareStatement(sql)) {
            return getResultSet(prepareStatement, id);
        } catch (SQLException e) {
            System.err.println("Database error: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    @Override
    public Student getResultSet(PreparedStatement preparedStatement,UUID id) throws Exception {
        Student student = null;
        preparedStatement.setObject(1, id);
        // ВАЖНО: executeQuery() вызывается БЕЗ параметров для PreparedStatement
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            if (resultSet.next()) {
                student = new Student();
                student.setId((UUID) resultSet.getObject("id"));
                student.setName(resultSet.getString("name"));
                student.setPhone(resultSet.getString("phone"));
                student.setAddress(resultSet.getString("address"));
                student.setEmail(resultSet.getString("email"));
            }
        }
        return student;
    }
}
