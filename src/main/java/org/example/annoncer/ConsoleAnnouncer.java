package org.example.annoncer;

import org.example.factory.ObjectFactory;
import org.example.interfaces.Announcer;
import org.example.interfaces.Recommendator;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public class ConsoleAnnouncer implements Announcer {


    private Recommendator recommendator = ObjectFactory.getInstance().createObject(Recommendator.class);

    public ConsoleAnnouncer() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, FileNotFoundException {
    }

    @Override
    public void announce(String message) {
        System.out.println(message);
        recommendator.recommend();
    }
}
