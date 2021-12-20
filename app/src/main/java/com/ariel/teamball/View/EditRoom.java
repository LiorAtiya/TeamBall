package com.ariel.teamball.View;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ariel.teamball.Model.DAL.RoomDAL;
import com.ariel.teamball.R;

import java.util.Calendar;

public class EditRoom extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText editRoomName, editFieldName;
    Spinner editCity;
    TextView timeText;
    ImageView profileImageView;
    Button saveBtn,editTime;
    RoomDAL roomDAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room);
        getSupportActionBar().hide();

        Intent data = getIntent();
        String roomName = data.getStringExtra("roomName");
        String fieldName = data.getStringExtra("fieldName").substring(7);
        String myCity = data.getStringExtra("city");
        String time = data.getStringExtra("time");

        profileImageView = findViewById(R.id.editProfileImage);
        editRoomName = findViewById(R.id.editRoomName);
        editFieldName = findViewById(R.id.editFieldName);
        editCity = findViewById(R.id.cityGameSpinner);

        ArrayAdapter<String> allCities = new ArrayAdapter<String>(EditRoom.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        //for drop down list:
        allCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editCity.setAdapter(allCities);

        timeText = findViewById(R.id.TimeText);
        editTime = findViewById(R.id.timePicker);
        saveBtn = findViewById(R.id.saveBtn);


        editRoomName.setText(roomName);
        editFieldName.setText(fieldName);
        timeText.setText(time);

        //for setting hours and minute
        int currentHr;
        int currentMin;
        Calendar calendar; // calendar object

        calendar = Calendar.getInstance();
        currentHr = calendar.get(Calendar.HOUR);
        currentMin = calendar.get(Calendar.MINUTE);


        //when we click the time picker
        editTime.setOnClickListener(view -> {
            TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    timeText.setText(hourOfDay + ":" + minute);
                }
            }, currentHr, currentMin, false);

            dialog.show();
        });

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
                String city = editCity.getSelectedItem().toString();
                String time = timeText.getText().toString();

                String category = getIntent().getExtras().get("category").toString();
                String roomID = getIntent().getExtras().get("roomID").toString();
                roomDAL.editRoomDetails(category,roomID,roomName,fieldName,city,time);

                String name = getIntent().getExtras().get("user_name").toString();
                //Go to a GameRoom page
                Intent intent = new Intent(EditRoom.this, GameRoom.class);
                intent.putExtra("room_name", roomName);
                intent.putExtra("user_name", name);
                intent.putExtra("category", category);
                intent.putExtra("roomID",roomID);
                startActivity(intent);

            }
        });

    }
}