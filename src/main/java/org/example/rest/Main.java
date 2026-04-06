package org.example.rest;

import org.example.rest.params.ApplicationParameters;
import org.example.rest.server.Server;

public class Main {
    public static void main(String[] args) {
        ApplicationParameters.getInstance().setFileDirectory(args);

        var server = new Server(4221);
        server.start();
    }
}
