package org.example;

import org.example.context.AppplicationContext;
import org.example.interfaces.Policeman;
import org.example.police.PolicemanImpl;
import org.example.room.Room;

import java.util.HashMap;
import java.util.Map;

public class Main {


    public static void main(String[] args) throws Exception {
        AppplicationContext context = ApplicationRunner.run("org.example", new HashMap<>(Map.of(Policeman.class, PolicemanImpl.class)));
        context.getObject(CoronaDesinfector.class).start(new Room());

    }
}
