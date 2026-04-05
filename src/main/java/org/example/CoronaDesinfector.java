package org.example;

import org.example.annotation.betta.Driver;
import org.example.annotation.InjectByType;
import org.example.database.pojo.Student;
import org.example.interfaces.Announcer;
import org.example.interfaces.Policeman;
import org.example.room.Room;

import java.sql.SQLException;
import java.util.List;

public class CoronaDesinfector {

    //dont call us we call you
    @InjectByType
    private Announcer announcer;

    @InjectByType
    private Policeman policeman;

    @InjectByType
    private Driver database;

    public CoronaDesinfector() throws Exception {
    }

    public void start(Room room) throws SQLException {
        System.out.println("Здесь люди: :");
        List<Student> student = database.findStudent("SELECT * FROM student");
        student.forEach(System.out::println);
        announcer.announce("Всем покинуть");
        policeman.makePeopleLeaveRoom();
        desinfect(room);
        announcer.announce("Можете вернуться");
    }

    private void desinfect(Room room) {
        System.out.println("Идет дизенфекция");
    }
}
