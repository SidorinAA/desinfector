package org.example.annotation.betta_02;

import org.example.annotation.betta.Driver;
import org.example.annotation.betta.DriverManager;

@Deprecated
public class DriverFactory {

    public static Driver createDriver(Class<? extends Driver> driverClass) {
        try {
            // Получаем класс реализации из аннотации @Implementation
            DriverManager driverManager = driverClass.getAnnotation(DriverManager.class);
            if (driverManager != null) {
                Class<? extends Driver> implClass = (Class<? extends Driver>) driverManager.value();
                // Создаем экземпляр с параметром

                return implClass.getDeclaredConstructor().newInstance();
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to create driver", e);
        }
        return null;
    }
}