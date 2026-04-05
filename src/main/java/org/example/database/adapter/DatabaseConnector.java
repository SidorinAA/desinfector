/*
package org.example.database.adapter;

import org.example.annotation.InjectByType;
import org.example.annotation.InjectProperty;

import java.sql.*;
import java.util.*;

import org.example.database.interfaces.Connector;
import org.example.database.pojo.Student;


public class DatabaseConnector implements Connector {


    @InjectByType
    private DriverImpl driver;

    @Override
    public List<Student> getConnection(String query) throws SQLException {
        List<Student> students = new ArrayList<>();

        Connection connection = driver.connection();

        PreparedStatement preparedStatement = connection.prepareStatement(query);
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            Student student = new Student();
            student.setId((UUID) resultSet.getObject("id"));
            student.setName(resultSet.getString("name"));
            student.setPhone(resultSet.getString("phone"));
            student.setAddress(resultSet.getString("address"));
            student.setEmail(resultSet.getString("email"));
            students.add(student);
        }
        return students;
    }
}*/
