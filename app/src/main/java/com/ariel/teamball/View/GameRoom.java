package com.ariel.teamball.View;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ariel.teamball.Model.DAL.PlayerDAL;
import com.ariel.teamball.Model.DAL.RoomDAL;
import com.ariel.teamball.Controller.GameManagement;
import com.ariel.teamball.Model.Classes.Room;
import com.ariel.teamball.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Map;

public class GameRoom extends AppCompatActivity {

    public static final String TAG = "TAG";
    Button players, chat, editRoom, leaveRoom;
    TextView roomName, capacity, city, field, admin,time;
    String category,room_name,admin_name,my_name,adminID,roomID;

    RoomDAL roomDAL;
    PlayerDAL playerDAL;

    // creates game management object
    GameManagement gm = GameManagement.getInstance();

    boolean isAdmin;


    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MyRooms.class);
        i.putExtra("category", category);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_room);
        getSupportActionBar().hide();

        roomName = findViewById(R.id.room_name);
        capacity = findViewById(R.id.capacity);
        city = findViewById(R.id.city);
        field = findViewById(R.id.field);
        time = findViewById(R.id.time);
        admin = findViewById(R.id.admin);

        players = findViewById(R.id.players);
        chat = findViewById(R.id.chat);
        editRoom = findViewById(R.id.edit_room);
        leaveRoom = findViewById(R.id.leaveRoom);

        //Access to firebase
        roomDAL = new RoomDAL(this);
        playerDAL = new PlayerDAL(this);

        //Get date from previous page
        my_name = getIntent().getExtras().get("user_name").toString();
        category = getIntent().getExtras().get("category").toString();
        room_name = getIntent().getExtras().get("room_name").toString();
        roomID = getIntent().getExtras().get("roomID").toString();

        //---------------------------------------------------

        //Check for editRoom button
        DatabaseReference roomRef = roomDAL.getPathReference("Rooms/"+category+"/"+roomID);

        roomRef.runTransaction(new Transaction.Handler() {
              
            public Transaction.Result doTransaction(MutableData mutableData) {
                Room room = mutableData.getValue(Room.class);
                if (room == null) {
                    return Transaction.success(mutableData);
                }

                adminID = room.getAdmin();
                //Show button of edit room only for admin
                if(playerDAL.getPlayerID().equals(adminID)){
                    editRoom.setVisibility(View.VISIBLE);
                    isAdmin = true;
                }else{
                    editRoom.setVisibility(View.INVISIBLE);
                    isAdmin = false;
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


        //---------------------------------------------------

        //Fill the details of room in TextView
        roomRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Room room = mutableData.getValue(Room.class);
                if (room == null) {
                    return Transaction.success(mutableData);
                }

                ((Activity) GameRoom.this).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        roomName.setText(room.getName());
                        capacity.setText("Capacity: "+room.getNumOfPlayers()+"/"+room.getCapacity());
                        city.setText("City: "+room.getCity());
                        field.setText("Field: "+room.getField());
//                time.setText("Start Game: "+room.getTime());

                        DocumentReference adminRef = playerDAL.getCollection("users",room.getAdmin());

                        adminRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    admin_name = document.getString("fullName");
                                    admin.setText("Admin: " + admin_name);
                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });
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

//        roomRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Room room = dataSnapshot.getValue(Room.class);
//                roomName.setText(room.getName());
//                capacity.setText("Capacity: "+room.getNumOfPlayers()+"/"+room.getCapacity());
//                city.setText("City: "+room.getCity());
//                field.setText("Field: "+room.getField());
////                time.setText("Start Game: "+room.getTime());
//
//                DocumentReference adminRef = playerDAO.getCollection("users",room.getAdmin());
//
//                adminRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            admin_name = document.getString("fullName");
//                            admin.setText("Admin: " + admin_name);
//                        } else {
//                            Log.d(TAG, "get failed with ", task.getException());
//                        }
//                    }
//                });
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                System.out.println("The read failed: " + databaseError.getCode());
//            }
//        });

        //---------------------------------------------------

        //Show the list of players of room
        players.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // Attach a listener to read the data at our rooms reference
                roomRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Room room = dataSnapshot.getValue(Room.class);
                        Map<String,String> players = room.getUsersList();
                        String playersName = "";
                        for (Map.Entry<String,String> player : players.entrySet()){
                            playersName += player.getValue()+"\n";
                        }

                        final AlertDialog.Builder playersDialog = new AlertDialog.Builder(v.getContext());
                        playersDialog.setTitle("Team players");
                        playersDialog.setMessage(playersName);

                        playersDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        if (!GameRoom.this.isFinishing()){
                            playersDialog.create().show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });
            }
        });

        //---------------------------------------------------

        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Go to a Chatroom page
                Intent intent = new Intent(GameRoom.this, Chatroom.class);
                intent.putExtra("room_name", room_name);
                intent.putExtra("user_name", my_name);
                intent.putExtra("category", category);
                intent.putExtra("roomID",roomID);
                startActivity(intent);
                finish();
            }
        });

        //---------------------------------------------------

        editRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(), EditRoom.class);
                i.putExtra("roomName",roomName.getText().toString());
                i.putExtra("time",time.getText().toString());
                i.putExtra("city",city.getText().toString());
                i.putExtra("fieldName",field.getText().toString());
                i.putExtra("category",category);
                i.putExtra("roomID",roomID);
                i.putExtra("user_name",my_name);

                startActivity(i);
            }
        });

        //---------------------------------------------------

        leaveRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isAdmin) { // the user is the admin of the group
                    final AlertDialog.Builder EnterGroupDialog = new AlertDialog.Builder(v.getContext());
                    EnterGroupDialog.setMessage("If you leave this room, it will be deleted.\nAre you sure you want to leave?");
                    EnterGroupDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() { // delete the room and go back
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            gm.removeRoom(roomID, category);
                            moveToGameCenterScreen(v);
                        }
                    });
                    EnterGroupDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) { // do nothing
                            dialogInterface.cancel();
                        }
                    });
                    EnterGroupDialog.show();
                }

                else { // the user is not the admin of the room
                    final AlertDialog.Builder EnterGroupDialog = new AlertDialog.Builder(v.getContext());
                    EnterGroupDialog.setMessage("Are you sure you want to leave?");
                    EnterGroupDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() { // leave and go back
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            gm.leaveRoom(roomID, category);
                            moveToGameCenterScreen(v);
                        }
                    });
                    EnterGroupDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) { // do nothing
                            dialogInterface.cancel();
                        }
                    });
                    EnterGroupDialog.show();
                }

            }
        });
    }

    // The function move to user back to the GameCenter activity
    private void moveToGameCenterScreen(View v) {
        Intent i = new Intent(v.getContext(), GameCenter.class);
        i.putExtra("category", category);
        startActivity(i);
    }

}