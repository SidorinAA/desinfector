package org.example.annoncer;

import org.example.annotation.InjectProperty;
import org.example.interfaces.ObjectConfigurator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

public class InjectPropertyAnnotationObjectConfigurator implements ObjectConfigurator {

    private Map<String, String> map;

    public InjectPropertyAnnotationObjectConfigurator() throws FileNotFoundException {
        String path = ClassLoader.getSystemClassLoader().getResource("apllication.properties").getPath();
        Stream<String> lines = new BufferedReader(new FileReader(path)).lines();
        this.map = lines.map(line -> line.split("=")).collect(toMap(arr -> arr[0], arr -> arr[1]));
    }

    @Override
    public void configure(Object t) throws IllegalAccessException {
        Class type = t.getClass();
        for (Field field : type.getDeclaredFields()) {
            InjectProperty annotation = field.getAnnotation(InjectProperty.class);
            if (annotation != null) {
                String value = annotation.value();
                String propertyValue = map.get(value);
                field.setAccessible(true);
                field.set(t, propertyValue);
            }
        }
    }


}
