package org.example;

import org.example.factory.ObjectFactory;
import org.example.interfaces.Announcer;
import org.example.interfaces.Policeman;
import org.example.room.Room;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public class CoronaDesinfector {

    private Announcer announcer = ObjectFactory.getInstance().createObject(Announcer.class);
    private Policeman policeman = ObjectFactory.getInstance().createObject(Policeman.class);

    public CoronaDesinfector() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, FileNotFoundException {
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
