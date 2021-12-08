package com.ariel.teamball.Classes;

/*
A singleton class that creates and manages rooms, players, and admins
 */
public class GameManagement {
    private static GameManagement gmObject;
    private final int roomsCapacity = 1000; // the limit of the active rooms in the app
    private int roomsCount = 0; // how many active rooms there are in the app right now

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
        boolean ans = false;
        if(roomsCount < roomsCapacity) {
            ans = true;
        }
        return ans;
    }

    // The function gets a player id and checks if it can be an admin
    public boolean canBeAdmin(int player) {
        return true;
    }

    // The function gets a player id, creates a new Admin, and returns its ID
    public int createAdmin(int playerID) {
        return 0;
    }

    // The function creates a new room, updates its admin, and returns the room ID
    public int createRoom(String _name, int _capacity, Admin _admin) {
        Room myRoom = new Room(_name, _capacity, _admin);
        roomsCount++;
        return myRoom.getId();
    }

    // The function gets a room id and an admin and update the room attribute in the admin class
    public void updateAdminRoom(int roomID, Admin _admin) {
        _admin.getRoomID();
    }


}
