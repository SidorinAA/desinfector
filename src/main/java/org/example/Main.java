package org.example;

import org.example.room.Room;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public class Main {



    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException, InstantiationException, FileNotFoundException {
        CoronaDesinfector coronaDesinfector = new CoronaDesinfector();
        coronaDesinfector.start(new Room());
    }
}
