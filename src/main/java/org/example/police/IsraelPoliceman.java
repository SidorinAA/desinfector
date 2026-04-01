package org.example.police;

import org.example.interfaces.Policeman;

public class IsraelPoliceman implements Policeman {

    @Override
    public void makePeopleLeaveRoom() {
        System.out.println("Израильская полиция - всем вон");
    }
}
