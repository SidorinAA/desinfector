package org.example;

import org.example.annotation.InjectByType;
import org.example.database.interfaces.Aggregator;
import org.example.database.pojo.Student;
import org.example.interfaces.Announcer;
import org.example.interfaces.Policeman;
import org.example.room.Room;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.UUID;

public class CoronaDesinfector {

    //dont call us we call you
    @InjectByType
    private Announcer announcer;

    @InjectByType
    private Policeman policeman;

    @InjectByType
    private Aggregator aggregator;

    public CoronaDesinfector() throws Exception {
    }

    public void start(Room room) {
        Student student = aggregator.getStudentById(UUID.fromString("123e4567-e89b-12d3-a456-426614174000"), "SELECT * FROM student WHERE id = ?");
        System.out.println("Здесь люди: " + student);
        announcer.announce("Всем покинуть");
        policeman.makePeopleLeaveRoom();
        desinfect(room);
        announcer.announce("Можете вернуться");
    }

    private void desinfect(Room room) {
        System.out.println("Идет дизенфекция");
    }
}
