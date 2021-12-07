package com.ariel.teamball.Classes;

/*
A singleton class that creates and manages rooms, players, and admins
 */
public class GameManagement {
    private static GameManagement gmObject;

    private GameManagement() {}

    public static GameManagement getInstance() {
        // creates object if it's not already created
        if (gmObject == null) {
            gmObject = new GameManagement();
        }
        return gmObject;
    }

    // functions

    // The function checks if we can create a new room
    public boolean roomsAvailability() {
        return true;
    }

    // The function gets a player and checks if it can be an admin
    public boolean canBeAdmin(Player _player) {
        return true;
    }

    // The function gets a player, creates a new Admin, and returns its ID
    public int createAdmin(Player _player) {
        return 0;
    }

    // The function creates a new room, updates its admin, and returns the room ID
    public int createRoom(int adminID) {
        return 0;
    }


}
