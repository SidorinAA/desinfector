package org.example.rest;

import org.example.ApplicationRunner;
import org.example.context.AppplicationContext;
import org.example.interfaces.Policeman;
import org.example.police.PolicemanImpl;
import org.example.rest.params.ApplicationParameters;
import org.example.rest.server.Server;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws Exception {
        AppplicationContext context = ApplicationRunner.run("org.example", new HashMap<>(Map.of(Policeman.class, PolicemanImpl.class)));
        ApplicationParameters.getInstance().setFileDirectory(args);
        var server = new Server(4221, context);
        server.start();
    }
}
