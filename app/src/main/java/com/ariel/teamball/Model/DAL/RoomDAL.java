package com.ariel.teamball.Model.DAL;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.ariel.teamball.Controller.GameManagement;
import com.ariel.teamball.Controller.SwitchActivities;
import com.ariel.teamball.Model.Classes.Player;
import com.ariel.teamball.Model.Classes.Room;
import com.ariel.teamball.View.GameRoom;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/*
Data Access Object class that synchronizes the Room objects with the database.
The methods in that class add, update and remove the data from the Rooms table in the database.
 */
public class RoomDAL {

    private static DatabaseReference reference;
    private static FirebaseDatabase database;
    private static Context context;
    private static PlayerDAL playerDAL;
    private static FirebaseFirestore fStore; // access Firesotre Database
    private static boolean isAdmin;

    public RoomDAL(Context context) {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        fStore = FirebaseFirestore.getInstance();
        this.context = context;
        this.playerDAL = new PlayerDAL(context);
    }

    public RoomDAL() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference();
        fStore = FirebaseFirestore.getInstance();
    }

    public static DatabaseReference getPathReference(String path) {
        return FirebaseDatabase.getInstance().getReference(path);
    }

    public static Set<String> getMyListRooms(String category){
        //Access to the list of my rooms category
        DatabaseReference myRoomsRef = getPathReference("userRooms/"+ playerDAL.getPlayerID()+"/"+category);

        Set<String> myRoomsName = new HashSet<String>();

        //Put all the my rooms of the category to list
        myRoomsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Loop on each room
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    DataSnapshot childSnapshot = (DataSnapshot) i.next();
                    String room = childSnapshot.getValue(String.class);
                    myRoomsName.add(room);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "No network connectivity", Toast.LENGTH_SHORT).show();
            }
        });

        return myRoomsName;
    }

    public static void setRoomsOnListview(Set<String> myRoomsList, String category,ArrayList<Room> list, ArrayAdapter<Room> adapter){
        //Access to the list of room category
        DatabaseReference reference = getPathReference("Rooms/" + category);

        //Put all the rooms of the category to list from the firebase
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<Room> set = new HashSet<Room>();

                //Loop on each room
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    DataSnapshot childSnapshot = (DataSnapshot) i.next();
                    Room room = childSnapshot.getValue(Room.class);
                    //Add to the list all the rooms that the user does not enter.
                    if(!myRoomsList.contains(room.getRoomID())){
                        set.add(room);
                    }
                }

                list.clear();
                list.addAll(set);
                //Set the list on viewlist
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "No network connectivity", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void leaveOrRemoveRoom(String roomID, String category){
        final AlertDialog.Builder EnterGroupDialog;

        // creates game management object
        GameManagement gm = GameManagement.getInstance();

        if(isAdmin) { // the user is the admin of the group
            EnterGroupDialog = new AlertDialog.Builder(context);
            EnterGroupDialog.setMessage("If you leave this room, it will be deleted.\nAre you sure you want to leave?");
            EnterGroupDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() { // delete the room and go back
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    gm.removeRoom(roomID, category);
                    SwitchActivities.GameCenter(context,category);

                }
            });
            EnterGroupDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) { // do nothing
                    dialogInterface.cancel();
                }
            });
        }

        else { // the user is not the admin of the room
            EnterGroupDialog = new AlertDialog.Builder(context);
            EnterGroupDialog.setMessage("Are you sure you want to leave?");
            EnterGroupDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() { // leave and go back
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    gm.leaveRoom(roomID, category);
                    SwitchActivities.GameCenter(context,category);
                }
            });
            EnterGroupDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) { // do nothing
                    dialogInterface.cancel();
                }
            });
        }
        EnterGroupDialog.show();
    }

    public static void setRoomsOnListview(Set<String> myRoomsList, String category,ArrayList<Room> list, ArrayAdapter<Room> adapter,boolean myRooms){
        //Access to the list of room category
        DatabaseReference reference = getPathReference("Rooms/" + category);

        //Put all the rooms of the category to list from the firebase
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<Room> set = new HashSet<Room>();

                //Loop on each room
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    DataSnapshot childSnapshot = (DataSnapshot) i.next();
                    Room room = childSnapshot.getValue(Room.class);
                    //Add to the list all the rooms that the user does not enter.
                    if(myRoomsList.contains(room.getRoomID()) == myRooms){
                        set.add(room);
                    }
                }

                list.clear();
                list.addAll(set);
                //Set the list on viewlist
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(context, "No network connectivity", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void permissionForAdmin(String category, String roomID, Button editRoom){

        //Check for editRoom button
        DatabaseReference roomRef = getPathReference("Rooms/"+category+"/"+roomID);

        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Room room = dataSnapshot.getValue(Room.class);

                if(room != null){
                    String adminID = room.getAdmin();
                    //Show button of edit room only for admin
                    if(playerDAL.getPlayerID().equals(adminID)){
                        editRoom.setVisibility(View.VISIBLE);
                        isAdmin = true;
                    }else{
                        editRoom.setVisibility(View.INVISIBLE);
                        isAdmin = false;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


//        roomRef.runTransaction(new Transaction.Handler() {
//
//            public Transaction.Result doTransaction(MutableData mutableData) {
//                Room room = mutableData.getValue(Room.class);
//                if (room == null) {
//                    return Transaction.success(mutableData);
//                }
//
//                String adminID = room.getAdmin();
//                //Show button of edit room only for admin
//                if(playerDAL.getPlayerID().equals(adminID)){
//                    editRoom.setVisibility(View.VISIBLE);
//                    isAdmin = true;
//                }else{
//                    editRoom.setVisibility(View.INVISIBLE);
//                    isAdmin = false;
//                }
//
//                // Set value and report transaction success
//                mutableData.setValue(room);
//                return Transaction.success(mutableData);
//
//            }
//
//            @Override
//            public void onComplete(DatabaseError databaseError, boolean committed,
//                                   DataSnapshot currentData) {
//                // Transaction completed
//                Log.d("TAG", "postTransaction:onComplete:" + databaseError);
//            }
//        });
    }

    public static void setDetailsRoom(Context context, String category, String roomID, TextView roomName,
                                      TextView capacity,TextView city, TextView field, TextView timeText
                                      ,TextView admin){

        DatabaseReference roomRef = getPathReference("Rooms/"+category+"/"+roomID);

        // Attach a listener to read the data at our rooms reference
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Room room = dataSnapshot.getValue(Room.class);
                if(room != null) {

                    roomName.setText(room.getName());
                    capacity.setText("Capacity: "+room.getNumOfPlayers()+"/"+room.getCapacity());
                    city.setText("City: "+room.getCity());
                    field.setText("Field: "+room.getField());
                    timeText.setText("Start Game: "+room.getTime());

                    DocumentReference adminRef = playerDAL.getCollection("users",room.getAdmin());

                    adminRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()) {
                                DocumentSnapshot document = task.getResult();
                                String admin_name = document.getString("fullName");
                                admin.setText("Admin: " + admin_name);
                            } else {
                                Log.d("TAG", "get failed with ", task.getException());
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

//        DatabaseReference roomRef = getPathReference("Rooms/"+category+"/"+roomID);
//        roomRef.runTransaction(new Transaction.Handler() {
//            @Override
//            public Transaction.Result doTransaction(MutableData mutableData) {
//                Room room = mutableData.getValue(Room.class);
//                if (room == null) {
//                    return Transaction.success(mutableData);
//                }
//
//                roomName.setText(room.getName());
//                capacity.setText("Capacity: "+room.getNumOfPlayers()+"/"+room.getCapacity());
//                city.setText("City: "+room.getCity());
//                field.setText("Field: "+room.getField());
//                timeText.setText("Start Game: "+room.getTime());
//
//                DocumentReference adminRef = playerDAL.getCollection("users",room.getAdmin());
//
//                adminRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            String admin_name = document.getString("fullName");
//                            admin.setText("Admin: " + admin_name);
//                        } else {
//                            Log.d("TAG", "get failed with ", task.getException());
//                        }
//                    }
//                });
//
//                // Set value and report transaction success
//                mutableData.setValue(room);
//                return Transaction.success(mutableData);
//
//            }
//
//            @Override
//            public void onComplete(DatabaseError databaseError, boolean committed,
//                                   DataSnapshot currentData) {
//                // Transaction completed
//                Log.d("TAG", "postTransaction:onComplete:" + databaseError);
//            }
//        });
    }

    public static void showPlayersList(Context context,String category,String roomID){

        DatabaseReference roomRef = getPathReference("Rooms/"+category+"/"+roomID);

        // Attach a listener to read the data at our rooms reference
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Room room = dataSnapshot.getValue(Room.class);
                if(room != null) {
                    Map<String, String> players = room.getUsersList();
                    String playersName = "";
                    for (Map.Entry<String, String> player : players.entrySet()) {
                        playersName += player.getValue() + "\n";
                    }

                    final AlertDialog.Builder playersDialog = new AlertDialog.Builder(context);
                    playersDialog.setTitle("Team players");
                    playersDialog.setMessage(playersName);

                    playersDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    if(!((Activity) context).isFinishing())
                    {
                        playersDialog.create().show();
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }

    public static String addRoom(String category, Room newRoom) {

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
        DatabaseReference reference = getPathReference("Rooms/" + category + "/" + roomKey + "/usersList");
        reference.child(userID).removeValue();
        decreaseNumOfPlayers(roomKey, category); // numOfPlayers--
    }

    // The function gets a roomID and decreases the amount of current players in the given room
    private void decreaseNumOfPlayers(String roomKey, String category) {
        DatabaseReference reference = getPathReference("Rooms/" + category + "/" + roomKey);

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
                        int prevNum = room.getNumOfPlayers(); // get the current num of players in the room
                        int updateNum = prevNum - 1; // decreases the number by one
                        reference.child("numOfPlayers").setValue(updateNum); // update the new number in the DB
                    }
                });
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

    // The function gets the key and the category of the room and removes it from the Rooms table in the DB
    public void removeRoom(String roomKey, String category) {
        DatabaseReference reference = getPathReference("Rooms/" + category);
        reference.child(roomKey).removeValue();
    }

    public static void addNewUser(String category, String roomID, String playerID) {

        //Access to the list of rooms category
        DatabaseReference reference = getPathReference("Rooms/" + category + "/" + roomID);

        //Get the name of player
        DocumentReference docRef = fStore.collection("users").document(playerID);
        docRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Player p = documentSnapshot.toObject(Player.class);
                String playerName = p.getFullName();

                //Add new player to usersList
                DatabaseReference usersList = reference.child("usersList");
                HashMap<String, Object> newUser = new HashMap<>();

                newUser.put(playerID, playerName);
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
                room.setNumOfPlayers(room.getNumOfPlayers() + 1);

                // Set value and report transaction success
                mutableData.setValue(room);
                return Transaction.success(mutableData);

            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean committed,
                                   DataSnapshot currentData) {
                // Transaction completed
//                Log.d("TAG", "postTransaction:onComplete:" + databaseError);
            }
        });

    }

    public static void checkLimitOfRoom_And_JoinToRoom(String category, String roomID, String nameRoom) {

        //Access to the list of rooms category
        DatabaseReference reference = getPathReference("Rooms/" + category + "/" + roomID);

        reference.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Room room = mutableData.getValue(Room.class);
                if (room == null) {
                    return Transaction.success(mutableData);
                }

                //Check the number of players in the room
                if (room.getNumOfPlayers() == room.getCapacity()) {
                    Toast.makeText(context, "The room is full", Toast.LENGTH_SHORT).show();
                } else {
                    JoinToRoom(category, roomID, nameRoom);
                }

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

    private static void JoinToRoom(String category, String roomID, String roomName) {

        final AlertDialog.Builder EnterGroupDialog = new AlertDialog.Builder(context);
        EnterGroupDialog.setTitle("Want to join the room?");
        EnterGroupDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //Add player to room
                addNewUser(category, roomID, playerDAL.getPlayerID());

                //Add room to list of private rooms user
                playerDAL.addRoom(category, roomID);

                //----------------------------------------

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

    // The function update the room values in the DB in the Rooms table
    public static void updateRoom(String category, String roomID, String roomName,
                                  String fieldName, String city, String time, String date) {

        //Access to the list of rooms category
        DatabaseReference reference = getPathReference("Rooms/" + category + "/" + roomID);

        //make changes if player fill the details properly

        if(!roomName.isEmpty()) {
            reference.child("name").setValue(roomName);
        }

        if(!fieldName.isEmpty()) {
            reference.child("field").setValue(fieldName);
        }

        if(!city.contains("ty")) {
            reference.child("city").setValue(city);
        }

        if(!time.contains("Game")) {
            reference.child("time").setValue(time);
        }

        if(!date.contains("Date")) {
            reference.child("dayGame").setValue(date);
        }

    }

}
