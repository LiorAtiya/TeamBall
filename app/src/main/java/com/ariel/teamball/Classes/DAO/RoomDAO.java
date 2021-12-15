package com.ariel.teamball.Classes.DAO;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.ariel.teamball.Classes.Room;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

// Data Access Object class that synchronizes the Room objects with the database
public class RoomDAO {

    //    public static final String TAG = "TAG";
//    private static FirebaseAuth fAuth;
//    private static FirebaseFirestore fStore;
//    private static Context context;
    private static DatabaseReference reference;
    private static String adminID;
    private static int currentInRoom;

    public RoomDAO(Context context) {

//        fAuth = FirebaseAuth.getInstance();
//        fStore = FirebaseFirestore.getInstance();
//
//        this.context = context;
    }

    public RoomDAO() {

    }

    public static String getAdminOfRoom(String category, String roomName) {
        DatabaseReference roomRef = getPathReference("Rooms/" + category + "/" + roomName);

        // Attach a listener to read the data at our rooms reference
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Room room = dataSnapshot.getValue(Room.class);
                adminID = room.getAdmin();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return adminID;
    }

//<<<<<<< HEAD
//    public static void addUser(String userID,String category,String roomID){
//        reference = FirebaseDatabase.getInstance().getReference("Rooms").child(category).child(roomID).child("playerList");
//        Map<String,Object> user = new HashMap<>();
//        user.put(userID,userID);
//        reference.updateChildren(user);
//    }

    public static String createRoom(String category, Room newRoom){

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        DatabaseReference roomsRef = ref.child("Rooms").child(category);
        Map<String, Object> room = new HashMap<>();

        //Get and set unique key for room
        reference = FirebaseDatabase.getInstance().getReference("Rooms").child(category);
        String temp_key = reference.push().getKey();
        newRoom.setRoomID(temp_key);

        room.put(temp_key, newRoom);

        roomsRef.updateChildren(room);
        return temp_key;
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

                room.setCurrentInRoom(room.getCurrentInRoom()+1);

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

//    public static boolean isAdminOfRoom(String playerID,String category,String room_name){
//
//        DatabaseReference roomRef = getPathReference("Rooms/"+category+"/"+room_name);
//
//        // Attach a listener to read the data at our rooms reference
//        roomRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Room room = dataSnapshot.getValue(Room.class);
//                adminID = room.getAdmin();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        if(playerID == adminID){
//            return true;
//        }
//
//        return false;
//    }
}
