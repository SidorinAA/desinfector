package org.example.database.interfaces;

import org.example.database.pojo.Student;

import java.util.UUID;

public interface Aggregator {

    Student getStudentById(UUID id, String sql);

}
