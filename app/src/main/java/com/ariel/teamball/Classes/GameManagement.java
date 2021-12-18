package com.ariel.teamball.Classes;

import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ariel.teamball.Classes.DAO.PlayerDAO;
import com.ariel.teamball.Classes.DAO.RoomDAO;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/*
A singleton class that use to manage the app.
The purpose of this class is to connect the front-end to the database.
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

//    // The function checks if the user is the admin of the given room
//    public boolean isAdmin(String roomID, String category) {
//        final boolean[] isAdmin = {false};
//        DatabaseReference roomRef = this.roomDAO.getPathReference("Rooms/" + category + "/" + roomID);
//        // Attach a listener to read the data at our rooms reference
//        roomRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Room room = dataSnapshot.getValue(Room.class);
//                String adminID = room.getAdmin();
//
//                //Show button of edit room only for admin
//                if(playerDAO.playerID().equals(adminID)){
//                    isAdmin[0] = true;
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        return isAdmin[0];
//    }

    /*
    The function creates a new room, updates its admin, updates the database,
    returns the room's key and add the room to the user's room list
     */
    public String createRoom(String RoomN, int capacityInteger, String CurtN, String
            chosenCity, String chosenTime, String date, String category) {
        // Room details storage in database
        String adminID = this.playerDAO.playerID();
        Room newRoom = new Room(RoomN, capacityInteger, CurtN, chosenCity, chosenTime, "date", adminID, category);
        String roomKey = this.roomDAO.addRoom(category, newRoom);
        // adds the admin to the playerList
        roomDAO.addNewUser(category, roomKey, adminID);
        // add the room to the user's rooms list
        this.playerDAO.addRoom(category, roomKey);

        return roomKey;
    }

    /*
    The function gets the room's key and category and exit from the room
    (removes the room from the userRooms and removes the user from the usersList)
     */
    public void leaveRoom(String roomID, String category) {
        String userID = this.playerDAO.playerID();
        this.playerDAO.removeRoomFromUserRooms(roomID, category, userID);
        this.roomDAO.removeUserFromRoom(roomID, category, userID);
    }

    /*
    The function gets the key and the category of the room and removes it from the DB:
    1. removes the room from the Rooms table
    2. removes the room from the userRooms table
     */
    public void removeRoom(String roomKey, String category) {
        this.playerDAO.removeRoomFromUserRooms(roomKey, category);
        this.roomDAO.removeRoom(roomKey, category);
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
