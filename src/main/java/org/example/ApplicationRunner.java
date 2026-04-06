package org.example;

import org.example.context.AppplicationContext;
import org.example.rest.server.Server;

import java.util.Map;

public class ApplicationRunner {
    private static Server server = new Server(4421);

    public static AppplicationContext run(String packageToscan, Map<Class, Class> ifcToImplClass) throws Exception {
        AppplicationContext context = new AppplicationContext(packageToscan, ifcToImplClass);
        server.start();
        return context;
    }
}
