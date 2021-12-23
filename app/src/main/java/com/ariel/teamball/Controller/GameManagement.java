package com.ariel.teamball.Controller;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.ariel.teamball.Model.DAL.PlayerDAL;
import com.ariel.teamball.Model.Classes.Room;
import com.ariel.teamball.Model.DAL.RoomDAL;
import com.ariel.teamball.View.GameRoom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Set;

/*
A singleton class that use to manage the app.
The purpose of this class is to connect the front-end to the database.
 */
public class GameManagement {
    private static GameManagement gmObject;
    private int roomsCapacity; // the limit of the active rooms in the app
    private PlayerDAL playerDAL;
    private RoomDAL roomDAL;

    private GameManagement() {
        this.roomsCapacity = 1000;
        this.playerDAL = new PlayerDAL();
        this.roomDAL = new RoomDAL();
    }

    public static GameManagement getInstance() {
        // creates object if it's not already created
        if (gmObject == null) {
            gmObject = new GameManagement();
        }
        return gmObject;
    }

    // functions

    //--------------------Room Management ------------------------//

    /*
    The function creates a new room, updates its admin, updates the database,
    returns the room's key and add the room to the user's room list
     */
    public String createRoom(String RoomN, int capacityInteger, String CurtN, String
            chosenCity, String chosenTime, String date, String category) {
        // Room details storage in database
        String adminID = this.playerDAL.getPlayerID();
        Room newRoom = new Room(RoomN, capacityInteger, CurtN, chosenCity, chosenTime, date, adminID, category);
        String roomKey = this.roomDAL.addRoom(category, newRoom);
        // adds the admin to the playerList
        roomDAL.addNewUser(category, roomKey, adminID);
        // add the room to the user's rooms list
        this.playerDAL.addRoom(category, roomKey);

        return roomKey;
    }

    // The function edit the details of the given room
    public void editRoom(String category, String roomID, String roomName, String fieldName, String city, String time, String date) {
        roomDAL.updateRoom(category, roomID, roomName, fieldName, city, time, "date");
    }

    /*
    The function gets the room's key and category and exit from the room
    (removes the room from the userRooms and removes the user from the usersList)
     */
    public void leaveRoom(String roomID, String category) {
        String userID = this.playerDAL.getPlayerID();
        this.playerDAL.removeRoomFromUserRooms(roomID, category, userID);
        this.roomDAL.removeUserFromRoom(roomID, category, userID);
    }

    /*
    The function gets the key and the category of the room and removes it from the DB:
    1. removes the room from the Rooms table
    2. removes the room from the userRooms table
     */
    public void removeRoom(String roomKey, String category) {
        this.playerDAL.removeRoomFromUserRooms(roomKey, category);
        this.roomDAL.removeRoom(roomKey, category);
    }

    // The function updates 'all rooms' and 'my rooms'
    public void displayRoomsList(String category, ArrayList<Room> list, ArrayAdapter<Room> adapter) {
        Set<String> myRoomsList = roomDAL.getMyListRooms(category);
        roomDAL.setRoomsOnListview(myRoomsList, category, list, adapter, false);
    }

    /*
    The function adds the player to the given room if it's not full already
    and if the confirm that he wants to join
     */
    public void joinRoom(String category, String roomID, String roomName) {
        // checks if have a room for another player in the room
        boolean isFull = roomDAL.isTheRoomFull(category, roomID, roomName);
        if (!isFull) {
            // run only if the user confirmed the message
                Runnable runIfConfirmed = new Runnable() {
                    @Override
                    public void run() {
                        // adds the player to the room
                        RoomDAL.addNewUser(category, roomID, playerDAL.getPlayerID());
                        Context context = RoomDAL.getContext();
                        // switches screen to GameRoom
                        moveToGameRoomActivity(category, roomID, roomName, context);
                        // updates the player's rooms list
                        playerDAL.addRoom(category, roomID);
                    }
                };
            // ask the user to confirm that he wants to join the room
            doubleCheck(category, roomID, roomName, runIfConfirmed);
        }
    }

    // The function checks if the user sure that he wants to join the given room
    private void doubleCheck(String category, String roomID, String roomName, final Runnable runIfConfirmed) {
        Context context = RoomDAL.getContext();
        final AlertDialog.Builder EnterGroupDialog = new AlertDialog.Builder(context);
        EnterGroupDialog.setTitle("Would you like to join this room?");
        EnterGroupDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Join
                runIfConfirmed.run();
            }
        });
        EnterGroupDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        ((Activity) context).runOnUiThread(new Runnable() {
            public void run() {
                EnterGroupDialog.show();
            }
        });

    }

    // The function takes the player to the GameRoom screen
    private void moveToGameRoomActivity(String category, String roomID, String roomName, Context context) {
        DocumentReference docRef = playerDAL.getCollection("users", playerDAL.getPlayerID());

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    String name = document.getString("fullName");

                    //Go to a GameRoom page
                    Intent intent = new Intent(context, GameRoom.class);
                    intent.putExtra("room_name", roomName);
                    intent.putExtra("user_name", name);
                    intent.putExtra("category", category);
                    intent.putExtra("roomID", roomID);
                    context.startActivity(intent);

                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }

    // The function checks if we can create a new room
    public boolean roomsAvailability() {
        return true;
    }

    // The function gets a player id and checks if it can be an admin
    public boolean canBeAdmin(String playerID) {
        return true;
    }

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

}
