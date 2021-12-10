package com.ariel.teamball;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ariel.teamball.Classes.Adapters.ListAdapter;
import com.ariel.teamball.Classes.DAO.PlayerDAO;
import com.ariel.teamball.Classes.DAO.RoomDAO;
import com.ariel.teamball.Classes.GameManagement;
import com.ariel.teamball.Classes.Room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class GameCenter extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextView nameCategory;
    ListView listView;
    Button createRoomBtn;
    ArrayAdapter<Room> adapter;
    String name,category;
    EditText roomName;

    PlayerDAO playerDAO;
    RoomDAO roomDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_center);
        getSupportActionBar().hide();

        nameCategory = findViewById(R.id.nameCategory);
        listView = findViewById(R.id.listView);
        createRoomBtn = findViewById(R.id.button);

        playerDAO = new PlayerDAO(this);
        roomDAO = new RoomDAO(this);

        ArrayList<Room> list = new ArrayList<>();
        adapter = new ListAdapter(this,R.layout.list_group,list);
        listView.setAdapter(adapter);

        category = getIntent().getExtras().get("Category").toString();
        nameCategory.setText(category);


        //Access to user collection
        String userID = playerDAO.playerID();
        DocumentReference docRef = playerDAO.getCollection("users",userID);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    name = document.getString("fullName");
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        //Access to the list of group category
        DatabaseReference reference = roomDAO.getPathReference("Rooms/"+category);

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
                    set.add(room);
                }

                list.clear();
                list.addAll(set);
                //Set the list on viewlist
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(GameCenter.this, "No network connectivity", Toast.LENGTH_SHORT).show();
            }
        });

        //Click on some room
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int room, long l) {

                final AlertDialog.Builder EnterGroupDialog = new AlertDialog.Builder(view.getContext());
                EnterGroupDialog.setTitle("Want to join the room?");
                EnterGroupDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String roomName = adapter.getItem(room).getName();

                        //Add room to list of private rooms user
                        playerDAO.addRoom(category,roomName);

                        Toast.makeText(GameCenter.this,"My rooms updated", Toast.LENGTH_SHORT).show();

                        //----------------------------------------

                        //Go to a Chatroom page
                        Intent intent = new Intent(GameCenter.this, Chatroom.class);
                        intent.putExtra("room_name", roomName);
                        intent.putExtra("user_name", name);
                        intent.putExtra("category", category);
                        startActivity(intent);

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
        });


        createRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder newRoomDialog = new AlertDialog.Builder(v.getContext());
                newRoomDialog.setTitle("Enter room name: ");
                roomName = new EditText(v.getContext());
                newRoomDialog.setView(roomName);

                newRoomDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        // makes player to be an admin on the group
                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    // creates game management object
                                    GameManagement gm = GameManagement.getInstance();
                                    // checks if a room can be created with that admin
                                    if(gm.roomsAvailability() && gm.canBeAdmin(playerDAO.playerID())) {
                                        int capacity = 10; // TODO: have to fix capacity
                                        String field = "Neighborhood A"; // TODO: have to fix field
                                        String city = "TLV"; // TODO: have to fix city
                                        String time = "20:00"; // TODO:
                                        String date = "01/01/2022"; // TODO:
                                        // creates a new room with the given admin
                                        gm.createRoom(roomName.getText().toString(), capacity, field, city, time, date, category, userID);
                                    }

                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });

                    }
                });

                newRoomDialog.show();
            }
        });
    }

}