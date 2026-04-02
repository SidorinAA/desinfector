package org.example.factory;

import lombok.SneakyThrows;
import org.example.annoncer.InjectPropertyAnnotationObjectConfigurator;
import org.example.config.JavaConfig;
import org.example.interfaces.Config;
import org.example.interfaces.ObjectConfigurator;
import org.example.interfaces.Policeman;
import org.example.police.PolicemanImpl;
import org.reflections.Reflections;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectFactory {

    private Config config;
    private List<ObjectConfigurator> configurator;

    //volitile? while-volitile???
    private static ObjectFactory instance;

    static {
        try {
            instance = new ObjectFactory();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private ObjectFactory() throws FileNotFoundException {
        config = new JavaConfig(
                new Reflections("org.example"),
                resolveMap(new HashMap<>()));
        configurator = List.of(new InjectPropertyAnnotationObjectConfigurator());
    }

    public Map<Class, Class> resolveMap(Map<Class, Class> mapClass) {
        mapClass = new HashMap<>();
        mapClass.put(Policeman.class, PolicemanImpl.class);
        //если закинуть данный объект то будет так или иначе возвращаться одна реализация -
        //вторая будет игнорироваться
        //баг ли?
        //mapClass.put(Policeman.class, IsraelPoliceman.class);

        return mapClass;
    }

    public static ObjectFactory getInstance() {
        return instance;
    }

    //sneaky throws not work need search bag
    @SneakyThrows
    public <T> T createObject(Class<T> type) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException, FileNotFoundException {
        type = resolveImpl(type);
        T t = type.getDeclaredConstructor().newInstance();

        System.out.println("======= " + t);
        configure(t);

        return t;
    }

    private <T> void configure(T t) {
        configurator.forEach(c -> {
            try {
                c.configure(t);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private <T> Class<T> resolveImpl(Class<T> type) throws IllegalAccessException {
        if (type.isInterface()) {
            type = (Class<T>) config.getImplClass(type);
        }
        return type;
    }
}
