package com.ariel.teamball.Classes.DAO;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import com.ariel.teamball.Classes.Player;
import com.ariel.teamball.Classes.Room;
import com.ariel.teamball.GameRoom;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

/*
Data Access Object class that synchronizes the Room objects with the database.
The methods in that class add, update and remove the data from the Rooms table in the database.
 */
public class RoomDAO {

    private static DatabaseReference reference;
    private static FirebaseDatabase database;
    private static Context context;
    private static PlayerDAO playerDAO;
    private static FirebaseFirestore fStore; // access Firesotre Database

    public RoomDAO(Context context) {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        fStore = FirebaseFirestore.getInstance();
        this.context = context;
        this.playerDAO = new PlayerDAO(context);
    }

    public RoomDAO() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        fStore = FirebaseFirestore.getInstance();
    }

    public static DatabaseReference getPathReference(String path) {
        return FirebaseDatabase.getInstance().getReference(path);
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
//        //Add new player to usersList
//        DatabaseReference usersList = reference.child("usersList");
//        HashMap<String,Object> newUser = new HashMap<>();
//
//        String playerName = p.getFullName();
//        newUser.put(playerID,playerName);
//        usersList.updateChildren(newUser);

        DatabaseReference reference = getPathReference("Rooms/" + category + "/" + roomKey + "/usersList");
        reference.child(userID).removeValue();
    }

    // The function gets the key and the category of the room and removes it from the Rooms table in the DB
    public void removeRoom(String roomKey, String category) {
        DatabaseReference reference = getPathReference("Rooms/" + category);
        reference.child(roomKey).removeValue();
    }

    public static void addNewUser(String category, String roomID,String playerID){

        //Access to the list of rooms category
        DatabaseReference reference = getPathReference("Rooms/" + category + "/" + roomID);

        //Get the name of player
        DocumentReference docRef = fStore.collection("users").document(playerID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Player p = documentSnapshot.toObject(Player.class);

                //Add new player to usersList
                DatabaseReference usersList = reference.child("usersList");
                HashMap<String,Object> newUser = new HashMap<>();

                String playerName = p.getFullName();
                newUser.put(playerID,playerName);
                usersList.updateChildren(newUser);
            }
        });

        //Increase the number of users in room
        reference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Room room = mutableData.getValue(Room.class);
                if (room == null) {
                    return Transaction.success(mutableData);
                }

                //Update number of players
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

    public static void checkLimitOfRoom_And_JoinToRoom(String category, String roomID,String namePlayer,String nameRoom){

        //Access to the list of rooms category
        DatabaseReference reference = getPathReference("Rooms/" + category + "/" + roomID);

        reference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Room room = mutableData.getValue(Room.class);
                if (room == null) {
                    return Transaction.success(mutableData);
                }

                ((Activity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //Check the number of players in the room
                        if(room.getNumOfPlayers() == room.getCapacity()){
                            Toast.makeText(context, "The room is full", Toast.LENGTH_SHORT).show();
                        }else{
                            JoinToRoom(category,roomID,namePlayer,nameRoom);
                        }
                    }
                });

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

    private static void JoinToRoom(String category,String roomID,String name,String roomName){

        final AlertDialog.Builder EnterGroupDialog = new AlertDialog.Builder(context);
        EnterGroupDialog.setTitle("Want to join the room?");
        EnterGroupDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Add player to room
                addNewUser(category,roomID,playerDAO.playerID());

                //Add room to list of private rooms user
                playerDAO.addRoom(category,roomID);

                //----------------------------------------

                //Go to a GameRoom page
                Intent intent = new Intent(context, GameRoom.class);
                intent.putExtra("room_name", roomName);
                intent.putExtra("user_name", name);
                intent.putExtra("category", category);
                intent.putExtra("roomID",roomID);
                context.startActivity(intent);
            }
        });
        EnterGroupDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        EnterGroupDialog.show();
    }

    public static void editRoomDetails(String category, String roomID, String roomName,String fieldName, String city, String time){

        //Access to the list of rooms category
        DatabaseReference reference = getPathReference("Rooms/" + category + "/" + roomID);

        reference.child("name").setValue(roomName);
        reference.child("field").setValue(fieldName);
        reference.child("city").setValue(city);
        reference.child("date").setValue(time);

    }

}
