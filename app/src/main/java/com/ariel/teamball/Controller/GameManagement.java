package com.ariel.teamball.Controller;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ariel.teamball.Model.Classes.Player;
import com.ariel.teamball.Model.Classes.Room;
import com.ariel.teamball.Model.DAL.PlayerDAL;
import com.ariel.teamball.Model.DAL.RoomDAL;

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
    private android.content.Context context;

    private GameManagement() {
        this.roomsCapacity = 1000;
        this.playerDAL = new PlayerDAL();
        this.roomDAL = new RoomDAL();
    }

    public GameManagement(android.content.Context _context) {
        this.roomsCapacity = 1000;
        this.playerDAL = new PlayerDAL(_context);
        this.roomDAL = new RoomDAL(_context);
        this.context = _context;
    }

    public static GameManagement getInstance() {
        // creates object if it's not already created
        if (gmObject == null) {
            gmObject = new GameManagement();
        }
        return gmObject;
    }

    // functions
    //--------------------Player Management ------------------------//

    public void playerRegister(Player p, ProgressBar progressBar){
        playerDAL.playerRegister(p,progressBar);
    }

    public boolean playerConnected(){
        return playerDAL.playerConnected();
    }

    public void playerLogin(String email, String password, ProgressBar pb){
        playerDAL.playerLogin(email,password,pb);
    }

    public void resetPassword(View v){
        playerDAL.resetPassword(v);
    }

    public void playerSignOut(){
        playerDAL.playerSignOut();
    }


    //--------------------Room Management ------------------------//

    public void setRoomsOnListview(String category, ArrayList<Room> list, ArrayAdapter<Room> adapter, boolean myRooms){
        Set<String> myRoomsList = roomDAL.getMyListRooms(category);
        roomDAL.setRoomsOnListview(myRoomsList,category,list,adapter,myRooms);
    }

    public void permissionForAdmin(String category, String roomID, Button editRoom){
        roomDAL.permissionForAdmin(category,roomID,editRoom);
    }

    public void setDetailsRoom(Context context, String category, String roomID, TextView roomName,
                               TextView capacity, TextView city, TextView field, TextView timeText
            , TextView admin){

        roomDAL.setDetailsRoom(context, category,roomID, roomName, capacity, city,field, timeText, admin);
    }

    public void showPlayersList(Context context,String category,String roomID){
        roomDAL.showPlayersList(context, category, roomID);
    }

    public void leaveOrRemoveRoom(String roomID, String category){
        roomDAL.leaveOrRemoveRoom(roomID,category);
    }

    /*
    The function creates a new room, updates its admin, updates the database,
    returns the room's key and add the room to the user's room list
     */
    public String createRoom(String RoomN, int capacityInteger, String CurtN, String
            chosenCity, String chosenTime, String date, String category) {
        // Room details storage in database
        String adminID = this.playerDAL.getPlayerID();
        Room newRoom = new Room(RoomN, capacityInteger, CurtN, chosenCity, chosenTime, date,
                adminID, category);
        String roomKey = this.roomDAL.addRoom(category, newRoom);
        // adds the admin to the playerList
        roomDAL.addNewUser(category, roomKey, adminID);
        // add the room to the user's rooms list
        this.playerDAL.addRoom(category, roomKey);

        return roomKey;
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
