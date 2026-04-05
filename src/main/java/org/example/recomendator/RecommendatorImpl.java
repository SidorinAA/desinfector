package org.example.recomendator;

import org.example.annoncer.ConsoleAnnouncer;
import org.example.annotation.InjectByType;
import org.example.annotation.InjectProperty;
import org.example.annotation.Singleton;
import org.example.interfaces.Recommendator;
import org.example.interfaces.Instatiation;

@Singleton
public class RecommendatorImpl implements Recommendator {

    @InjectProperty("alhocol")
    private String drinkName;

    @InjectByType
    private Instatiation instatiation;


    public RecommendatorImpl() {
        System.out.println("recomendatorImpl was created");
    }
    @Override
    public void recommend() {
        System.out.print("2 " );
        instatiation.initalize(ConsoleAnnouncer.class, ClassLoader.getSystemClassLoader()
);
        System.out.println("пей: " + drinkName);
    }
}
