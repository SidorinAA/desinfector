package org.example.inject;

import org.example.annotation.InjectByType;
import org.example.context.AppplicationContext;
import org.example.interfaces.ObjectConfigurator;

import java.lang.reflect.Field;

public class InjectByTypeAnnotattionObjectConfigurator implements ObjectConfigurator {

    @Override
    public void configure(Object t, AppplicationContext context) throws Exception {
        for (Field declareField : t.getClass().getDeclaredFields()) {
            if (declareField.isAnnotationPresent(InjectByType.class)) {
                Object object = context.getObject(declareField.getType());
                declareField.setAccessible(true);
                declareField.set(t, object);
            }
        }

    }
}
