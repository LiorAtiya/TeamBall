package com.ariel.teamball;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.ariel.teamball.Classes.GameManagement;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class CreateRoom extends AppCompatActivity {
    TextInputEditText mGroupName, mCurtName;
    // for start game picker
    Button mPickTimeBtn, mDoneDefine;
    TextView showCurrentTime;

    Spinner CitySpinner, playersCapacitySpinner; // for spinners pick

    //for setting hours and minute
    int currentHr;
    int currentMin;
    Calendar calendar; // calendar object

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), GameCenter.class);
        //Get date from previous page
        String category = getIntent().getExtras().get("category").toString();
        i.putExtra("category", category);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        getSupportActionBar().hide();

        //catch the design by id - Link to layout
        mGroupName = findViewById(R.id.RoomName);
        mCurtName = findViewById(R.id.courtName);
        mPickTimeBtn = (Button) findViewById(R.id.timePicker);
        showCurrentTime = findViewById(R.id.TimeText);
        mDoneDefine = findViewById(R.id.donedef);

        calendar = Calendar.getInstance();

        currentHr = calendar.get(Calendar.HOUR);
        currentHr = calendar.get(Calendar.MINUTE);

        //when we click the time picker
        mPickTimeBtn.setOnClickListener(view -> {
            TimePickerDialog dialog = new TimePickerDialog(CreateRoom.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    showCurrentTime.setText(hourOfDay + ":" + minute);
                }
            }, currentHr, currentMin, false);

            dialog.show();
        });

        /* For Choose City */
        CitySpinner = findViewById(R.id.cityGameSpinner);
        /* For Choose Capacity */
        playersCapacitySpinner = findViewById(R.id.playersCapacity);

        ArrayAdapter<String> allCities = new ArrayAdapter<String>(CreateRoom.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        //for drop down list:
        allCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CitySpinner.setAdapter(allCities);

        ArrayAdapter<String> capacityAdapter = new ArrayAdapter<String>(CreateRoom.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.capacity));
        //for drop down list:
        capacityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playersCapacitySpinner.setAdapter(capacityAdapter);


        /* when we click on done button then what will happen */
        mDoneDefine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // catch details
                String RoomN = mGroupName.getText().toString();
                String CurtN = mCurtName.getText().toString();
                String chosenCity = CitySpinner.getSelectedItem().toString();
                String chosenTime = calendar.getTime().toString();
                String chosenCapacity = playersCapacitySpinner.getSelectedItem().toString();
                int capacityInteger = 0;
                if (!chosenCapacity.contains("N")) {
                    capacityInteger = Integer.parseInt(chosenCapacity); //convert for Room constructor
                }



                /* if the user click on "done button and left one of the field empty or not choose option */
                if (TextUtils.isEmpty(RoomN)) {
                    mGroupName.setError("Group name is Required");
                    return;
                }

                if (TextUtils.isEmpty(CurtN)) {
                    mCurtName.setError("Curt name is Required");
                    return;
                }
                if(chosenCity.contains("ty")){
                    ((TextView)CitySpinner.getSelectedView()).setError("choose city");
                    return;
                }
                if(chosenCapacity.contains("N")){
                    ((TextView)playersCapacitySpinner.getSelectedView()).setError("choose number of players");
                    return;
                }

                String category = getIntent().getExtras().get("category").toString();
                // creates game management object
                GameManagement gm = GameManagement.getInstance();
                /* creates a new room, updates its admin, updates the database,
                   uses the room's key that received to add the room to the user's room list
                */
                String roomKey = gm.createRoom(RoomN, capacityInteger, CurtN, chosenCity, chosenTime, "date", category);

//                PlayerDAO playerDAO = new PlayerDAO();
//                RoomDAO roomDAO = new RoomDAO();
//                // Room details storage in database
//                String admin = playerDAO.playerID();
//                Room newRoom = new Room(RoomN, capacityInteger, CurtN, chosenCity, chosenTime, "date", admin);
//                newRoom.addUser(admin);
//                newRoom.addLastUser(admin);
//                String roomKey = roomDAO.createRoom(category, newRoom);

                //-----------------------------------------

//                //Add room to list of private rooms user
//                playerDAO.addRoom(category, roomKey);

//                //User enters the room - add to current in room
//                RoomDAO.newUserInRoom(category,RoomN);

                // move user back to game center
                openGameCenter(category);
            }
        });
    }

    /* function that moves the user (Creator of the group)
       from the group setting room back to game center */
    public void openGameCenter(String category) {
        Intent intent = new Intent(this, MyRooms.class);
        intent.putExtra("category", category);
        startActivity(intent);
        finish();
    }
}