package com.ariel.teamball.View;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ariel.teamball.Controller.Adapters.ListAdapter;
import com.ariel.teamball.Controller.SwitchActivities;
import com.ariel.teamball.Model.DAL.PlayerDAL;
import com.ariel.teamball.Model.DAL.RoomDAL;
import com.ariel.teamball.Model.Classes.Room;
import com.ariel.teamball.R;
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

    TextView nameCategory;
    ListView listView;
    Button createRoomBtn;
    ArrayAdapter<Room> adapter;
    String category;

    BottomNavigationView bottomNavigationView;

    PlayerDAL playerDAL;
    RoomDAL roomDAL;

    @Override
    public void onBackPressed() {
        SwitchActivities.SportMenu(getApplicationContext());
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rooms);
        getSupportActionBar().hide();

        nameCategory = findViewById(R.id.nameCategory);
        listView = findViewById(R.id.listView);
        createRoomBtn = findViewById(R.id.CR_btn);

        //Get date from previous page
        category = getIntent().getExtras().get("category").toString();
        nameCategory.setText(category);

        //---------------------------------------------------

        //Move to GameCenter Activity from navigator bar
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.my_rooms);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.my_rooms:
                        return true;
                    case R.id.all_rooms:
                        SwitchActivities.GameCenter(getApplicationContext(),category);
                        overridePendingTransition(0,0);
                        finish();
                        return true;
                }

                return false;
            }
        });

        //---------------------------------------------------

        playerDAL = new PlayerDAL(this);
        roomDAL = new RoomDAL(this);

        //Custom design for listView
        ArrayList<Room> list = new ArrayList<>();
        adapter = new ListAdapter(this, R.layout.list_group, list);
        listView.setAdapter(adapter);

        //---------------------------------------------------

        //Show the list of my rooms
        Set<String> myRoomsList = roomDAL.getMyListRooms(category);
        roomDAL.setRoomsOnListview(myRoomsList,category,list,adapter,true);

        //---------------------------------------------------

        //Click on some room
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int room, long l) {

                String roomName = adapter.getItem(room).getName();
                String roomID =  adapter.getItem(room).getRoomID();

                //Go to a GameRoom page
                SwitchActivities.GameRoom(MyRooms.this,roomName, category, roomID);

            }
        });

        //---------------------------------------------------

        createRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivities.createRoom(MyRooms.this,category);
            }
        });
    }

}