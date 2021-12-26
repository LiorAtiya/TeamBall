package com.ariel.teamball.View;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
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

import com.ariel.teamball.Controller.GameManagement;
import com.ariel.teamball.Controller.SwitchActivities;
import com.ariel.teamball.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class CreateRoom extends AppCompatActivity {

    TextInputEditText mGroupName, mCurtName;
    // for start game picker
    Button mPickTimeBtn, mDoneDefine , editDateButton;
    TextView showCurrentTime, timeTxt ,tvDate;
    Spinner CitySpinner, playersCapacitySpinner; // for spinners pick
    Calendar calendarDate;

    //for setting hours and minute
    int currentHr;
    int currentMin;
    Calendar calendar; // calendar object


    String category;

    @Override
    public void onBackPressed() {
        SwitchActivities.GameCenter(getApplicationContext(),category);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_room);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        category = getIntent().getExtras().get("category").toString();

        //catch the design by id - Link to layout
        mGroupName = findViewById(R.id.RoomName);
        mCurtName = findViewById(R.id.courtName);
        mPickTimeBtn = findViewById(R.id.timePicker);
        showCurrentTime = findViewById(R.id.TimeText);
        mDoneDefine = findViewById(R.id.donedef);
        CitySpinner = findViewById(R.id.cityGameSpinner);
        playersCapacitySpinner = findViewById(R.id.playersCapacity);
        timeTxt = findViewById(R.id.TimeText);
        tvDate = findViewById(R.id.datePicker_textView);
        editDateButton = findViewById(R.id.datePickerButton);


        //when we click the time picker
        mPickTimeBtn.setOnClickListener(view -> {
            TimePickerDialog dialog = new TimePickerDialog(CreateRoom.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String hour_with_zero = Integer.toString(hourOfDay);
                    String minute_with_zero = Integer.toString(minute);

                    if(hourOfDay < 10) hour_with_zero = "0" + hour_with_zero;
                    if(minute < 10) minute_with_zero = "0" + minute_with_zero;

                    showCurrentTime.setText(hour_with_zero + ":" + minute_with_zero);
                }
            }, currentHr, currentMin, false);

            dialog.show();
        });


        /*----- Date Picker -----*/
        editDateButton.setOnClickListener(view ->{
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

        /*------- Spinners -------*/

        /* store and connect our cities names with spinner */
        ArrayAdapter allCities = ArrayAdapter.createFromResource(this
                ,R.array.names,R.layout.color_spinner);
        allCities.setDropDownViewResource(R.layout.spinner_dropdown);
        CitySpinner.setAdapter(allCities);

        /* store and connect our cities names with spinner */
        ArrayAdapter capacityAdapter = ArrayAdapter.createFromResource(this
                ,R.array.capacity,R.layout.color_spinner);
        capacityAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        playersCapacitySpinner.setAdapter(capacityAdapter);

        /*-------- Done button --------*/
        mDoneDefine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // catch what player pick/fill
                String RoomN = mGroupName.getText().toString();
                String CurtN = mCurtName.getText().toString();
                String chosenCity = CitySpinner.getSelectedItem().toString();
                String chosenTime = timeTxt.getText().toString();
                String chosenCapacity = playersCapacitySpinner.getSelectedItem().toString();
                String chosenDate = tvDate.getText().toString();
                int capacityInteger = 0;

                //Check valid details
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

                if(chosenDate.contains("Date")){
                    tvDate.setError("Choose date game");
                    return;
                }

                // ---------------------------------------------------

                // creates game management object
                GameManagement gm = GameManagement.getInstance();
                /* creates a new room, updates its admin, updates the database,
                   uses the room's key that received to add the room to the user's room list
                */
                gm.createRoom(RoomN, capacityInteger, CurtN, chosenCity, chosenTime, chosenDate, category);

                SwitchActivities.MyRoom(CreateRoom.this,category);
                finish();

            }
        });
    }
}