package org.example.annoncer;

import org.example.annotation.InjectByType;
import org.example.annotation.Singleton;
import org.example.interfaces.Announcer;
import org.example.interfaces.Recommendator;
import org.example.interfaces.Instatiation;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
@Singleton
public class ConsoleAnnouncer implements Announcer {

    @InjectByType
    private Recommendator recommendator;

    @InjectByType
    private Instatiation instatiation;

    public ConsoleAnnouncer() throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, FileNotFoundException {
    }

    @Override
    public void announce(String message) {
        System.out.print("1 " );
        instatiation.isInitalize(ConsoleAnnouncer.class, ClassLoader.getSystemClassLoader());
        System.out.println(message);
        recommendator.recommend();
    }
}
