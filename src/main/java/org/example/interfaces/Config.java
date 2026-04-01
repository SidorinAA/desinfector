package org.example.interfaces;

public interface Config {
    //maybe Cofig write from file, bd, stream
    <T> Class<? extends T> getImplClass(Class<T> type) throws IllegalAccessException;
}
