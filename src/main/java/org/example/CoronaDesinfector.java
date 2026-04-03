package org.example;

import org.example.annotation.InjectByType;
import org.example.interfaces.Announcer;
import org.example.interfaces.Policeman;
import org.example.room.Room;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public class CoronaDesinfector {

    //dont call us we call you
    @InjectByType
    private Announcer announcer;

    @InjectByType
    private Policeman policeman;

    public CoronaDesinfector() throws Exception {
    }

    public void start(Room room) {
        announcer.announce("Всем покинуть");
        policeman.makePeopleLeaveRoom();
        desinfect(room);
        announcer.announce("Можете вернуться");
    }

    private void desinfect(Room room) {
        System.out.println("Идет дизенфекция");
    }
}
