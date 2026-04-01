package org.example.config;

import org.example.interfaces.Config;
import org.reflections.Reflections;

import java.util.Map;
import java.util.Set;

public class JavaConfig implements Config {

    private Reflections scanner;
    private Map<Class, Class> ifc2ImplClass;


    public JavaConfig(Reflections scanner, Map<Class, Class> ifc2ImplClass) {
        this.scanner = scanner;
        this.ifc2ImplClass = ifc2ImplClass;
    }

    @Override
    public <T> Class<? extends T> getImplClass(Class<T> type) throws IllegalAccessException {
        return ifc2ImplClass.computeIfAbsent(type, aClass -> {
            Set<Class<? extends T>> set = scanner.getSubTypesOf(type);
            if (set.size() != 1) {
                try {
                    throw new IllegalAccessException(type + " has 0 or more than one impl");
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            return set.iterator().next();
        });
    }
}
