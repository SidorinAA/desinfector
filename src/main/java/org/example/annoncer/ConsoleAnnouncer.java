package org.example.annoncer;

import org.example.annotation.InjectByType;
import org.example.annotation.Singleton;
import org.example.interfaces.Announcer;
import org.example.interfaces.Recommendator;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
@Singleton
public class ConsoleAnnouncer implements Announcer {

    @InjectByType
    private Recommendator recommendator;

    public ConsoleAnnouncer() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, FileNotFoundException {
    }

    @Override
    public void announce(String message) {
        System.out.println(message);
        recommendator.recommend();
    }
}
