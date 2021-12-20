package com.ariel.teamball.View;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.ariel.teamball.Model.DAL.PlayerDAL;
import com.ariel.teamball.Model.DAL.RoomDAL;
import com.ariel.teamball.Controller.GameManagement;
import com.ariel.teamball.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class CreateRoom extends AppCompatActivity {
    /* Page objects */
    TextInputEditText mGroupName, mCurtName;
    Button mPickTimeBtn, mDoneDefine, dateButton;
    TextView showCurrentTime, timeTxt , tvDate;
    Spinner CitySpinner, playersCapacitySpinner;
    Calendar calendarTime;
    Calendar calendarDate;

    @Override
    public void onBackPressed() {
        /*-----  Information from the previous page ------*/
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
        mPickTimeBtn =findViewById(R.id.timePicker);
        showCurrentTime = findViewById(R.id.TimeText);
        mDoneDefine = findViewById(R.id.donedef);
        timeTxt = findViewById(R.id.TimeText);
        dateButton = findViewById(R.id.datePickerButton);
        tvDate = findViewById(R.id.datePicker_textView);


        String category = getIntent().getExtras().get("category").toString();
        PlayerDAL playerDAL = new PlayerDAL();
        RoomDAL roomDAL = new RoomDAL();

        /*----- Date picker -----*/

        dateButton.setOnClickListener(view ->{
            calendarDate = Calendar.getInstance();
            int day = calendarDate.get(Calendar.DAY_OF_MONTH);
            int month = calendarDate.get(Calendar.MONTH);
            int year = calendarDate.get(Calendar.YEAR);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month+1;
                String date = dayOfMonth +"-" + month + "-" + year;
                tvDate.setText(date);
                }
            }, year,month,day);
            datePickerDialog.show();
        });



        /*------- Time picker -------*/

        //for setting hours and minute
        calendarTime = Calendar.getInstance();
        int currentHr = calendarTime.get(Calendar.HOUR);
        int currentMin = calendarTime.get(Calendar.MINUTE);

        mPickTimeBtn.setOnClickListener(view -> {
            TimePickerDialog dialog = new TimePickerDialog(CreateRoom.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    showCurrentTime.setText(hourOfDay + ":" + minute);
                }
            }, currentHr, currentMin, false);

            dialog.show();
        });

        /*------- Spinners -------*/
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


        /*-------- Done button --------*/
        mDoneDefine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // catch details
                String RoomN = mGroupName.getText().toString();
                String CurtN = mCurtName.getText().toString();
                String chosenCity = CitySpinner.getSelectedItem().toString();
                String chosenTime = timeTxt.getText().toString();
                String chosenCapacity = playersCapacitySpinner.getSelectedItem().toString();
                int capacityInteger = 0;
                if (!chosenCapacity.contains("N")) {
                    capacityInteger = Integer.parseInt(chosenCapacity); //convert for Room constructor
                }



                /*-- if the user click on "done button and left one of the field empty or not choose option --*/

                if (TextUtils.isEmpty(RoomN)) {
                    mGroupName.setError("Group name is Required");
                    return;
                }

                if (TextUtils.isEmpty(CurtN)) {
                    mCurtName.setError("Curt name is Required");
                    return;
                }
                if (chosenCity.contains("ty")) {
                    ((TextView) CitySpinner.getSelectedView()).setError("choose city");
                    return;
                }
                if (chosenCapacity.contains("N")) {
                    ((TextView) playersCapacitySpinner.getSelectedView()).setError("choose number of players");
                    return;
                }

                if(chosenTime.contains("Time")){
                    timeTxt.setError("Choose time start game");
                    return;
                }

              
                String category = getIntent().getExtras().get("category").toString();
                // creates game management object
                GameManagement gm = GameManagement.getInstance();
                /* creates a new room, updates its admin, updates the database,
                   uses the room's key that received to add the room to the user's room list
                */
                String roomKey = gm.createRoom(RoomN, capacityInteger, CurtN, chosenCity, chosenTime, "date", category);

//                //Add admin to playerList
//                String admin = playerDAO.playerID();
//                roomDAO.addNewUser(category,roomKey,admin);

                // move user back to game center
                openGameCenter(category);

            }
        });
    }

    /* function that moves the user (Creator of the group)
       from the group setting room back to game center */
    private void openGameCenter(String category) {
        Intent intent = new Intent(this, MyRooms.class);
        intent.putExtra("category", category);
        startActivity(intent);
        finish();
    }


}