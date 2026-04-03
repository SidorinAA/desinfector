package org.example.recomendator;

import org.example.annotation.InjectProperty;
import org.example.annotation.Singleton;
import org.example.interfaces.Recommendator;
@Singleton
public class RecommendatorImpl implements Recommendator {

    @InjectProperty("alhocol")
    private String drinkName;


    public RecommendatorImpl() {
        System.out.println("recomendatorImpl was created");
    }
    @Override
    public void recommend() {
        System.out.println("пей: " + drinkName);
    }
}
