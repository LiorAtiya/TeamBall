package com.ariel.teamball;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.ariel.teamball.Classes.Adapters.ListAdapter;
import com.ariel.teamball.Classes.DAO.PlayerDAO;
import com.ariel.teamball.Classes.DAO.RoomDAO;
import com.ariel.teamball.Classes.Room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

public class MyRooms extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextView nameCategory;
    ListView listView;
    Button createRoomBtn;
    ArrayAdapter<Room> adapter;
    String name,category, adminID;
    EditText room_name;

    BottomNavigationView bottomNavigationView;

    PlayerDAO playerDAO;
    RoomDAO roomDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rooms);
        getSupportActionBar().hide();

        nameCategory = findViewById(R.id.nameCategory);
        listView = findViewById(R.id.listView);
        createRoomBtn = findViewById(R.id.CR_btn);

        category = getIntent().getExtras().get("category").toString();
        nameCategory.setText(category);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.my_rooms);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.my_rooms:
                        return true;
                    case R.id.all_rooms:
                        Intent i = new Intent(getApplicationContext(),GameCenter.class);
                        i.putExtra("category",category);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        finish();                        return true;

                }

                return false;
            }
        });

        playerDAO = new PlayerDAO(this);
        roomDAO = new RoomDAO(this);

        ArrayList<Room> list = new ArrayList<>();
        adapter = new ListAdapter(this, R.layout.list_group, list);
        listView.setAdapter(adapter);

        //Access to user collection to take my name
        String userID = playerDAO.playerID();
        DocumentReference docRef = playerDAO.getCollection("users", userID);

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

        //--------------------------------------------------------------

        //Access to the list of my rooms category
        DatabaseReference myRoomsRef = roomDAO.getPathReference("userRooms/"+playerDAO.playerID()+"/"+category);

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
                Toast.makeText(MyRooms.this, "No network connectivity", Toast.LENGTH_SHORT).show();
            }
        });

        //--------------------------------------------------------------

        //Access to the list of rooms category
        DatabaseReference reference = roomDAO.getPathReference("Rooms/" + category);

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
                    //Add to list just the my rooms
                    if(myRoomsName.contains(room.getName())){
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
                Toast.makeText(MyRooms.this, "No network connectivity", Toast.LENGTH_SHORT).show();
            }
        });

        //--------------------------------------------------------------

        //Click on some room
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int room, long l) {

                String roomName = adapter.getItem(room).getName();

                DatabaseReference roomRef = roomDAO.getPathReference("Rooms/"+category+"/"+roomName);

                // Attach a listener to read the data at our rooms reference
                roomRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Room room = dataSnapshot.getValue(Room.class);
                        adminID = room.getAdmin();

                        //Go to a GameRoom page
                        Intent intent = new Intent(MyRooms.this, Chatroom.class);
                        intent.putExtra("room_name", roomName);
                        intent.putExtra("user_name", name);
                        intent.putExtra("category", category);
                        intent.putExtra("adminID", adminID);
                        startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });


        createRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder EnterGroupDialog = new AlertDialog.Builder(v.getContext());
                EnterGroupDialog.setTitle("Want to open new Room?");
                EnterGroupDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openSettingRoom(); //function to next page --> "settingRoom"
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
    }

    /* function that moves the user(Admin of the room from now)
       from the "GameCenter" page to the "settingRoom" page to set the room */
    public void openSettingRoom() {
        Intent intentSettingRoom = new Intent(MyRooms.this, CreateRoom.class);
        intentSettingRoom.putExtra("category", category);
        startActivity(intentSettingRoom);
    }

}