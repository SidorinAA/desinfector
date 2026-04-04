package org.example.interfaces;

import org.example.context.AppplicationContext;

import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;

public interface ObjectConfigurator {

    void configure(Object t, AppplicationContext context) throws Exception;
}
