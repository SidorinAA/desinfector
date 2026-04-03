package org.example.factory;

import lombok.SneakyThrows;
import org.example.annotation.InjectProperty;
import org.example.config.JavaConfig;
import org.example.interfaces.Config;
import org.example.interfaces.Policeman;
import org.example.police.PolicemanImpl;
import org.example.props.PropertyService;
import org.example.props.PropertyServiceImpl;
import org.reflections.Reflections;

import java.io.FileNotFoundException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class ObjectFactory {

    private Config config;

    private PropertyService propertyService;

    //If we remove it when constructor create impl of this interface
    //we lose what?
    //private PropertyService propertyService;

    //volitile? while-volitile???
    private static ObjectFactory instance = new ObjectFactory();

    private ObjectFactory() {
        propertyService = new PropertyServiceImpl();
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

    //sneaky throws not work need search bag??? maybe i am wrong??
    @SneakyThrows
    public <T> T createObject(Class<T> type) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException, InstantiationException, FileNotFoundException {
        if (type.isInterface()) {
            type = (Class<T>) config.getImplClass(type);
        }
        T t = type.getDeclaredConstructor().newInstance();

        //stream
        Map<String, String> map = propertyService.getApplicationProperyMaps();

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
