package com.ariel.teamball.Classes;

import android.content.Context;

import com.ariel.teamball.Classes.DAO.PlayerDAO;
import com.ariel.teamball.Classes.DAO.RoomDAO;

/*
A singleton class that creates and manages rooms, players, and admins
 */
public class GameManagement {
    private static GameManagement gmObject;
    private int roomsCapacity; // the limit of the active rooms in the app
    private PlayerDAO playerDAO;
    private RoomDAO roomDAO;

    private GameManagement() {
        this.roomsCapacity = 1000;
        this.playerDAO = new PlayerDAO();
        this.roomDAO = new RoomDAO();
    }

    public static GameManagement getInstance() {
        // creates object if it's not already created
        if (gmObject == null) {
            gmObject = new GameManagement();
        }
        return gmObject;
    }

    // functions

    // The function creates a new room and updates its admin and updates the database
    public void createRoom(String _name, int _capacity, String field, String city, String category, String playerID) {
        Room newRoom = new Room(_name, _capacity, field, city, playerID);
        this.roomDAO.createRoom(category,newRoom);
    }

    // The function checks if we can create a new room
    public boolean roomsAvailability() {
        return true;
    }

    // The function gets a player id and checks if it can be an admin
    public boolean canBeAdmin(String playerID) {
        return true;
    }
}
