package com.ariel.teamball.View;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ariel.teamball.Controller.SwitchActivities;
import com.ariel.teamball.Model.DAL.RoomDAL;
import com.ariel.teamball.R;

import java.util.Calendar;

public class EditRoom extends AppCompatActivity {
    /* Page objects */
    public static final String TAG = "TAG";
    EditText editRoomName, editFieldName;
    Spinner editCitySpinner;
    TextView timeText , tvDate;
    ImageView profileImageView;
    Button saveBtn,editTime , editDateButton;
    Calendar calendarTime; // calendar object
    Calendar calendarDate;

    RoomDAL roomDAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);
        getSupportActionBar().hide();

        /*-----  Information from the previous page ------*/
        Intent data = getIntent();
        String roomName = data.getStringExtra("roomName");
        String fieldName = data.getStringExtra("fieldName");
        String time = data.getStringExtra("time");

        //catch the design by id - Link to layout
        profileImageView = findViewById(R.id.editProfileImage);
        editRoomName = findViewById(R.id.editRoomName);
        editFieldName = findViewById(R.id.editFieldName);
        editCitySpinner = findViewById(R.id.cityGameSpinner);
        timeText = findViewById(R.id.TimeText);
        editTime = findViewById(R.id.timePicker);
        saveBtn = findViewById(R.id.saveBtn);
        tvDate = findViewById(R.id.datePicker_textView);
        editDateButton = findViewById(R.id.datePickerButton);


        /*----- Spinners -----*/
        ArrayAdapter<String> allCities = new ArrayAdapter<String>(EditRoom.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        //for drop down list:
        allCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editCitySpinner.setAdapter(allCities);

        editRoomName.setText(roomName);
        editFieldName.setText(fieldName);
        timeText.setText(time);

        /* -----Edit Time Picker -----*/

        //for setting hours and minute
        int currentHr = 0;
        int currentMin = 0;
        
        //when we click the time picker
        editTime.setOnClickListener(view -> {
            TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String hour_with_zero = Integer.toString(hourOfDay);
                    String minute_with_zero = Integer.toString(minute);

                    if(hourOfDay < 10) hour_with_zero = "0" + hour_with_zero;
                    if(minute < 10) minute_with_zero = "0" + minute_with_zero;

                    timeText.setText(hour_with_zero + ":" + minute_with_zero);
                }
            }, currentHr, currentMin, false);

            dialog.show();
        });


        /*----- EditDate Picker -----*/
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


        /*----- Save button -----*/
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check empty editText
                if(editRoomName.getText().toString().isEmpty() || editFieldName.getText().toString().isEmpty()){
                    Toast.makeText(EditRoom.this,"One or many fields are empty",Toast.LENGTH_SHORT).show();
                    return;
                }

                //Update new details of room
                String roomName = editRoomName.getText().toString();
                String fieldName = editFieldName.getText().toString();
                String city = editCitySpinner.getSelectedItem().toString();
                String time = timeText.getText().toString();
                String category = getIntent().getExtras().get("category").toString();
                String roomID = getIntent().getExtras().get("roomID").toString();
                String editDate = tvDate.getText().toString();

                roomDAL.editRoomDetails(category,roomID,roomName,fieldName,city,time,editDate);

                String name = getIntent().getExtras().get("user_name").toString();
                //Go to a GameRoom page
                SwitchActivities.GameRoom(EditRoom.this,roomName,category, roomID);

            }
        });

    }
}