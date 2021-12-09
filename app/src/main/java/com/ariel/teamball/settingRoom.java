package com.ariel.teamball;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Calendar;

public class settingRoom extends AppCompatActivity {
    TextInputEditText mGroupName , mCurtName;
    // for start game picker
    Button mPickTimeBtn , mDoneDefine;
    TextView showCurrentTime;

    Spinner CitySpinner , playersCapacitySpinner; // for spinners pick

    //for setting hours and minute
    int currentHr;
    int currentMin;
    Calendar calendar; // calendar object

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_room);

        //catch the design by id - Link to layout
        mGroupName = findViewById(R.id.FullName);
        mCurtName = findViewById(R.id.tvNickName);
        mPickTimeBtn = (Button) findViewById(R.id.timePicker);
        showCurrentTime = findViewById(R.id.TimeText);
        mDoneDefine = findViewById(R.id.donedef);

        calendar = Calendar.getInstance();

        currentHr = calendar.get(Calendar.HOUR);
        currentHr = calendar.get(Calendar.MINUTE);

        //when we click the time picker
        mPickTimeBtn.setOnClickListener(view -> {
            TimePickerDialog dialog = new TimePickerDialog(settingRoom.this,new TimePickerDialog.OnTimeSetListener(){
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                    showCurrentTime.setText(hourOfDay + ":" + minute);
                }
            },currentHr,currentMin,false);

            dialog.show();
        });

        /* For Choose City */
        CitySpinner = findViewById(R.id.cityGameSpinner);
        /* For Choose Capacity */
        playersCapacitySpinner = findViewById(R.id.genderSpinner);

        ArrayAdapter<String> allCities = new ArrayAdapter<String>(settingRoom.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.names));
        //for drop down list:
        allCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        CitySpinner.setAdapter(allCities);

        ArrayAdapter<String> genders = new ArrayAdapter<String>(settingRoom.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.capacity));
        //for drop down list:
        genders.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        playersCapacitySpinner.setAdapter(genders);


        /* when we click on done button then what will happen */
        mDoneDefine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String GroupN = mGroupName.getText().toString().trim();
                String CurtN = mCurtName.getText().toString();

                /* if the user click on "done button and left one of the field empty */
                if(TextUtils.isEmpty(GroupN)){
                    mGroupName.setError("Group name is Required");
                    return;
                }

                if(TextUtils.isEmpty(CurtN)){
                    mCurtName.setError("Curt name is Required");
                    return;
                }

                //TODO: need to connect the page to the group message page
            }
        });
    }
}