package com.ariel.teamball;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ariel.teamball.Classes.DAO.PlayerDAO;
import com.ariel.teamball.Classes.DAO.RoomDAO;
import com.ariel.teamball.Classes.Room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Arrays;

public class GameRoom extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextView roomName, capacity, city, field, admin;
    Button players, chat, editRoom;
    String category,room_name,admin_name,my_name,adminID;

    RoomDAO roomDAO;
    PlayerDAO playerDAO;

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(),MyRooms.class);
        i.putExtra("category",category);
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
        admin = findViewById(R.id.admin);

        players = findViewById(R.id.players);
        chat = findViewById(R.id.chat);
        editRoom = findViewById(R.id.edit_room);

        //Access to firebase
        roomDAO = new RoomDAO(this);
        playerDAO = new PlayerDAO(this);

        //Get date from previous page
        my_name = getIntent().getExtras().get("user_name").toString();
        category = getIntent().getExtras().get("category").toString();
        room_name = getIntent().getExtras().get("room_name").toString();

        //---------------------------------------------------

        //Check for editRoom button
        DatabaseReference roomRef = roomDAO.getPathReference("Rooms/"+category+"/"+room_name);

        // Attach a listener to read the data at our rooms reference
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Room room = dataSnapshot.getValue(Room.class);
                adminID = room.getAdmin();

                //Show button of edit room only for admin
                if(playerDAO.playerID().equals(adminID)){
                    editRoom.setVisibility(View.VISIBLE);
                }else{
                    editRoom.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //---------------------------------------------------

        //Fill the details of room in TextView
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Room room = dataSnapshot.getValue(Room.class);
                roomName.setText(room.getName());
                capacity.setText("Capacity: "+room.getCurrentInRoom()+"/"+room.getCapacity());
                city.setText("City: "+room.getCity());
                field.setText("Field: "+room.getField());

                DocumentReference adminRef = playerDAO.getCollection("users",room.getAdmin());

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

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        //---------------------------------------------------

        //Show the list of players of room
        players.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                final AlertDialog.Builder playersDialog = new AlertDialog.Builder(v.getContext());
                playersDialog.setTitle("Team players");

//                // Attach a listener to read the data at our rooms reference
//                roomRef.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Room room = dataSnapshot.getValue(Room.class);
//                        room.getPlayersList();
//                        playersDialog.setMessage(room.getPlayersList().toString());
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        System.out.println("The read failed: " + databaseError.getCode());
//                    }
//                });


                playersDialog.setMessage("Lior\nLioz\nOfir");

                playersDialog.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                playersDialog.create().show();
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
                startActivity(intent);
                finish();
            }
        });

        //---------------------------------------------------

        editRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(v.getContext(),EditRoom.class);
                i.putExtra("fName",roomName.getText().toString());
                i.putExtra("email",capacity.getText().toString());
                i.putExtra("phone",city.getText().toString());
                i.putExtra("phone",field.getText().toString());

                startActivity(i);
            }
        });
    }

}