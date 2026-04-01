package org.example.factory;

import lombok.SneakyThrows;
import org.example.annotation.InjectProperty;
import org.example.config.JavaConfig;
import org.example.interfaces.Config;
import org.example.interfaces.Policeman;
import org.example.police.PolicemanImpl;
import org.reflections.Reflections;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class ObjectFactory {

    private Config config;

    //volitile? while-volitile???
    private static ObjectFactory instance = new ObjectFactory();

    private ObjectFactory() {
        config = new JavaConfig(
                new Reflections("org.example"),
                resolveMap(new HashMap<>()));
    }

    public static ObjectFactory getInstance() {
        return instance;
    }

    public Map<Class, Class> resolveMap(Map<Class, Class> mapClass) {
        mapClass = new HashMap<>();
        mapClass.put(Policeman.class, PolicemanImpl.class);
        //mapClass.put(Policeman.class, IsraelPoliceman.class);

        return mapClass;
    }

    //sneaky throws not work need search bag
    @SneakyThrows
    public <T> T createObject(Class<T> type) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException, FileNotFoundException {
        if (type.isInterface()) {
            type = (Class<T>) config.getImplClass(type);
        }
        T t = type.getDeclaredConstructor().newInstance();


        //stream
        String path = ClassLoader.getSystemClassLoader().getResource("apllication.properties").getPath();
        Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
        Map<String, String> map = lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));

        //config object
        for (Field field : type.getDeclaredFields()) {
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);
            if (annotation != null) {
                String value = annotation.value();
                String propertyValue = map.get(value);
                field.setAccessible(true);
                field.set(t, propertyValue);

            }
        }
        return t;
    }
}
