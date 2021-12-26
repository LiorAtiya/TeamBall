package com.ariel.teamball.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.ariel.teamball.Controller.SwitchActivities;
import com.ariel.teamball.Model.DAL.PlayerDAL;
import com.ariel.teamball.Model.DAL.RoomDAL;
import com.ariel.teamball.Controller.GameManagement;
import com.ariel.teamball.R;

public class GameRoom extends AppCompatActivity {
    /* Page objects */
    public static final String TAG = "TAG";
    Button players, chat, editRoom, leaveRoom;
    TextView roomName, capacity, city, field, admin,timeText;
    String category,room_name,admin_name,my_name,adminID,roomID,time;

    /* logic objects */
    RoomDAL roomDAL;
//    PlayerDAL playerDAL;
    // creates game management object
    GameManagement gm;
    //permission
    boolean isAdmin;


    @Override
    public void onBackPressed() {
        /*-----  Information from the previous page ------*/
        Intent i = new Intent(getApplicationContext(), MyRooms.class);
        i.putExtra("category", category);
        startActivity(i);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_room);
//        getSupportActionBar().hide();

        //catch the design by id - Link to layout
        roomName = findViewById(R.id.room_name);
        capacity = findViewById(R.id.capacity);
        city = findViewById(R.id.city);
        field = findViewById(R.id.field);
        timeText = findViewById(R.id.time);
        admin = findViewById(R.id.admin);

        players = findViewById(R.id.players);
        chat = findViewById(R.id.chat);
        editRoom = findViewById(R.id.edit_room);
        leaveRoom = findViewById(R.id.leaveRoom);

        //Access to firebase
        roomDAL = new RoomDAL(this);
//        playerDAL = new PlayerDAL(this);

        gm = GameManagement.getInstance();

        /*-----  Information from the previous page ------*/
        my_name = getIntent().getExtras().get("user_name").toString();
        category = getIntent().getExtras().get("category").toString();
        roomID = getIntent().getExtras().get("roomID").toString();
        room_name = getIntent().getExtras().get("room_name").toString();

        //---------------------------------------------------

        //Show button of edit room only for admin
        gm.permissionForAdmin(category,roomID,editRoom);

        //        roomDAL.permissionForAdmin(category,roomID,editRoom);

        //---------------------------------------------------

        //Fill the details of room in TextView
        gm.setDetailsRoom(GameRoom.this, category,roomID, roomName, capacity, city,field, timeText, admin);

//        roomDAL.setDetailsRoom(GameRoom.this, category,roomID, roomName, capacity, city,field, timeText, admin);

        //---------------------------------------------------

        //Show the list of players of room
        players.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                gm.showPlayersList(GameRoom.this, category, roomID);

//                roomDAL.showPlayersList(GameRoom.this, category, roomID);
            }
        });

        //---------------------------------------------------

        //Button chat
        chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Go to a Chatroom page
                SwitchActivities.Chatroom(GameRoom.this, room_name, my_name, category, roomID);
                finish();
            }
        });

        //---------------------------------------------------

        //Button editRoom - Only for admin
        editRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivities.EditRoom(v.getContext(), roomName.getText().toString(),timeText.getText().toString(),
                        city.getText().toString(),field.getText().toString(), category, roomID, my_name);
            }
        });

        //---------------------------------------------------

        //Leave room - player | Remove room - admin
        leaveRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                roomDAL.leaveOrRemoveRoom(roomID,category);
            }
        });
    }
}