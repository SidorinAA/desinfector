package org.example.annotation.betta;

import org.example.database.pojo.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@DriverManager(PostgresDriver.class)
public abstract class Driver {


    public Driver() {
    }

    public List<Student> findStudent(String sql) throws SQLException {
        List<Student> students = new ArrayList<>();
        PreparedStatement preparedStatement = getConnection().prepareStatement(sql);
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

    public abstract Connection getConnection();
}
