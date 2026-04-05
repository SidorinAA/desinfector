package org.example.factory;

import lombok.SneakyThrows;
import org.example.context.AppplicationContext;
import org.example.interfaces.ObjectConfigurator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ObjectFactory {

    private List<ObjectConfigurator> configurators = new ArrayList<>();
    private AppplicationContext context;


    public ObjectFactory(AppplicationContext context) throws Exception {
        this.context = context;
        Set<Class<? extends ObjectConfigurator>> classes = context.getScanner().getSubTypesOf(ObjectConfigurator.class);
        for (Class<? extends ObjectConfigurator> aClass : classes) {
            configurators.add(aClass.getDeclaredConstructor().newInstance());
        }
    }


    //sneaky throws not work need search bag
    @SneakyThrows
    public <T> T createObject(Class<T> type) throws Exception {
        T t = type.getDeclaredConstructor().newInstance();
        configure(t);
        //instatiation.isDriverAllowed(type, ClassLoader.getSystemClassLoader());
        return t;
    }

    private <T> void configure(T t) {
        configurators.forEach(c -> {
            try {
                c.configure(t, context);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


}
