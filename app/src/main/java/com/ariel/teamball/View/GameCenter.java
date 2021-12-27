package com.ariel.teamball.View;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.ariel.teamball.Controller.Adapters.ListAdapter;
import com.ariel.teamball.Controller.GameManagement;
import com.ariel.teamball.Controller.SwitchActivities;
import com.ariel.teamball.Model.Classes.Room;
import com.ariel.teamball.Model.DAL.RoomDAL;
import com.ariel.teamball.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Set;

public class GameCenter extends AppCompatActivity {

    TextView nameCategory;
    ListView listView;
    Button createRoomBtn,filterBtn;
    Spinner citySpinner;
    ArrayAdapter<Room> adapter;
    String category;

    BottomNavigationView bottomNavigationView; //Switch between gameCenter to MyRoom

    RoomDAL roomDAL = new RoomDAL(this);

    // creates game management object
    GameManagement gm = GameManagement.getInstance();

    @Override
    public void onBackPressed() {
        SwitchActivities.SportMenu(getApplicationContext());
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_center);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        //catch the design by id - Link to layout
        nameCategory = findViewById(R.id.nameCategory);
        listView = findViewById(R.id.listView);
        createRoomBtn = findViewById(R.id.CR_btn);
        filterBtn = findViewById(R.id.filterBtn);

        /* For Choose City */
        citySpinner = findViewById(R.id.citySpinner);

        ArrayAdapter allCities = ArrayAdapter.createFromResource(this
                ,R.array.names,R.layout.color_spinner);
        allCities.setDropDownViewResource(R.layout.spinner_dropdown);
        citySpinner.setAdapter(allCities);

        //Get date from previous page
        category = getIntent().getExtras().get("category").toString();
        nameCategory.setText(category);

        //---------------------------------------------------

        //Move to MyRoom Activity from navigator bar
        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.all_rooms);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.my_rooms:
                        SwitchActivities.MyRoom(getApplicationContext(), category);
                        finish();
                        overridePendingTransition(0, 0);
                        return true;
                    case R.id.all_rooms:
                        return true;
                }
                return false;
            }
        });

        //---------------------------------------------------

        //Custom design for listView
        ArrayList<Room> list = new ArrayList<>();
        adapter = new ListAdapter(this, R.layout.list_rooms, list);
        listView.setAdapter(adapter);

        //---------------------------------------------------

        //Show the list of all rooms
//        gm.displayRoomsList(category, list, adapter);
        Set<String> myRoomsList = roomDAL.getMyListRooms(category);
        roomDAL.setRoomsOnListview(myRoomsList, category, list, adapter, false);

        //---------------------------------------------------

        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chosenCity = citySpinner.getSelectedItem().toString();
                roomDAL.setListViewByFilter(myRoomsList,category,list,adapter,chosenCity);
            }
        });

        //---------------------------------------------------



        //Click on some room
        //createRoomChannel();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int room, long l) {

                String roomName = adapter.getItem(room).getName();
                String roomID = adapter.getItem(room).getRoomID();

                // add the player to the given room if it's not full already
                gm.joinRoom(category, roomID, roomName, GameCenter.this);

            }
        });

        //---------------------------------------------------

        createRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivities.createRoom(GameCenter.this, category);
            }
        });
    }
}