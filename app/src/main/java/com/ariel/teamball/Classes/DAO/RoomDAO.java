package com.ariel.teamball.Classes.DAO;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.ariel.teamball.Classes.Room;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/*
Data Access Object class that synchronizes the Room objects with the database.
The methods in that class add, update and remove the data from the Rooms table in the database.
 */
public class RoomDAO {

    private static DatabaseReference reference;
    private static FirebaseDatabase database;
    private static String adminID;

    public RoomDAO(Context context) {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

    public RoomDAO() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
    }

  public static String addRoom(String category, Room newRoom){

        DatabaseReference roomsRef = reference.child("Rooms").child(category);
        Map<String, Object> room = new HashMap<>();

        //Get and set unique key for room
        String temp_key = reference.push().getKey();
        newRoom.setRoomID(temp_key);

        room.put(temp_key, newRoom);

        roomsRef.updateChildren(room);
        return temp_key;
    }

    /*
    The function gets the key and the category of the room,
    and a user id and removes the given user from the Rooms table in the DB
     */
    public void removeUserFromRoom(String roomKey, String category, String userID) {
        DatabaseReference reference = getPathReference("Rooms/" + category + "/usersList");
        Log.d("TAG", "reference.child(userID): " + reference.child(userID));
        reference.child(userID).removeValue();
    }

    // The function gets the key and the category of the room and removes it from the Rooms table in the DB
    public void removeRoom(String roomKey, String category) {
        DatabaseReference reference = getPathReference("Rooms/" + category);
        reference.child(roomKey).removeValue();
    }

    public static DatabaseReference getPathReference(String path) {
        return FirebaseDatabase.getInstance().getReference(path);
    }

    public static void addNewUser(String category, String roomID,String playerID){

        //Access to the list of rooms category
        DatabaseReference reference = getPathReference("Rooms/" + category + "/" + roomID);

        //Add new player to usersList
        DatabaseReference usersList = reference.child("usersList");
        HashMap<String,Object> newUser = new HashMap<>();
        newUser.put(playerID,playerID);
        usersList.updateChildren(newUser);

        //Increase the number of users in room
        reference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Room room = mutableData.getValue(Room.class);
                if (room == null) {
                    return Transaction.success(mutableData);
                }

                room.setNumOfPlayers(room.getNumOfPlayers()+1);

                // Set value and report transaction success
                mutableData.setValue(room);
                return Transaction.success(mutableData);

            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean committed,
                                   DataSnapshot currentData) {
                // Transaction completed
                Log.d("TAG", "postTransaction:onComplete:" + databaseError);
            }
        });

    }

                //    // The function add a new player to the given room
//    public static void addPlayer(String category, String roomName){
//
//        final FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference ref = database.getReference();
//
//        DatabaseReference mDatabase = ref.child("Rooms/"+category+"/"+roomID);
//        String key = mDatabase.child("usersList").getKey();
//    }

}
