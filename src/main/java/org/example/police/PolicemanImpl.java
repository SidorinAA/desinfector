package org.example.police;

import org.example.interfaces.Policeman;

public class PolicemanImpl implements Policeman {
    @Override
    public void makePeopleLeaveRoom() {
        System.out.println("Русская полиция - выходите");
    }
}
