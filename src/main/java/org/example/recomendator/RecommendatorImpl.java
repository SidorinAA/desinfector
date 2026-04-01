package org.example.recomendator;

import org.example.annotation.InjectProperty;
import org.example.interfaces.Recommendator;

public class RecommendatorImpl implements Recommendator {

    @InjectProperty("alhocol")
    private String drinkName;

    @Override
    public void recommend() {
        System.out.println("пей: " + drinkName);
    }
}
