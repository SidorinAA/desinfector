package org.example;

import org.example.context.AppplicationContext;

import java.util.Map;

public class ApplicationRunner {
    public static AppplicationContext run(String packageToscan, Map<Class, Class> ifcToImplClass) throws Exception {
        AppplicationContext context = new AppplicationContext(packageToscan, ifcToImplClass);
        return context;
    }
}
