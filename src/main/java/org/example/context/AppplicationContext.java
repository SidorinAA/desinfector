package org.example.context;

import lombok.SneakyThrows;
import org.example.annotation.Singleton;
import org.example.annotation.betta.DriverManager;
import org.example.config.JavaConfig;
import org.example.factory.ObjectFactory;
import org.reflections.Reflections;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AppplicationContext {
    private Map<Class, Object> cache = new ConcurrentHashMap<>();
    private Reflections scanner;
    private final JavaConfig javaConfig;
    private ObjectFactory factory;

    public AppplicationContext(String packageRoScan, Map<Class, Class> ifc2Impl) throws Exception {
        this.scanner = new Reflections(packageRoScan);
        this.javaConfig = new JavaConfig(scanner, ifc2Impl);
        this.factory = new ObjectFactory(this);
    }

    private <T> Class<T> resolveImpl(Class<T> type) throws IllegalAccessException {
        //как тут быть с реализацией абстрактрого метода - нужна другая конфигурация?

        if (type.isAnnotationPresent(DriverManager.class)) {
            DriverManager implementation = type.getAnnotation(DriverManager.class);
            return (Class<T>) implementation.value();
        }

        if (type.isInterface()) {
            type = (Class<T>) javaConfig.getImplClass(type);
        }
        return type;
    }

    @SneakyThrows
    public <T> T getObject(Class<T> type) throws Exception {
        if (cache.containsKey(type)) {
            return (T) cache.get(type);
        }
        Class<T> implClass = resolveImpl(type);
        T t = factory.createObject(implClass);

        if (implClass.isAnnotationPresent(Singleton.class)) {
            cache.put(type, t);
        }
        return t;
    }

    public Reflections getScanner() {
        return scanner;
    }
}
