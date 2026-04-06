package org.example.police;

import org.example.annotation.InjectByType;
import org.example.interfaces.Policeman;
import org.example.interfaces.Recommendator;

import javax.annotation.PostConstruct;

public class PolicemanImpl implements Policeman {

    @InjectByType
    private Recommendator recommendator;


    @PostConstruct
    public void init() {
        System.out.println(recommendator.getClass());
    }

    public PolicemanImpl() {
    }
    @Override
    public void makePeopleLeaveRoom() {
        System.out.println("Русская полиция - выходите");
    }
}
