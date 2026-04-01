package org.example;

public class CoronaDesinfector {

    private Announcer announcer = new ConsoleAnnouncer();

    private Policeman policement = new PolicemanImpl();

    public void start(Room room) {
        announcer.announce("Всем покинуть");
        policement.makePeopleLeaveRoom();
        desinfect(room);
        announcer.announce("Можете вернуться");
    }

    private void desinfect(Room room) {
        System.out.println("Идет дизенфекция");
    }
}
