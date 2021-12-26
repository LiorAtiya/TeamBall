package com.ariel.teamball.View;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import com.ariel.teamball.Controller.GameManagement;
import com.ariel.teamball.Controller.SwitchActivities;
import com.ariel.teamball.Model.DAL.RoomDAL;
import com.ariel.teamball.R;

public class GameRoom extends AppCompatActivity {
    /* Page objects */
    public static final String TAG = "TAG";

    Button participants, chat, editRoom, leaveRoom, notificationBtn;
    TextView roomName, capacity, city, field, admin, timeText;
    String category, room_name, admin_name, my_name, adminID, roomID, time;

    /* logic objects */
    RoomDAL roomDAL;

    // creates game management object
    GameManagement gm;
    //permission
    boolean isAdmin;

    //notification
    public static final String CHANNEL_ID = "channel";
    private NotificationManagerCompat notificationManager;

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
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        //catch the design by id - Link to layout
        roomName = findViewById(R.id.room_name);
        capacity = findViewById(R.id.capacity);
        city = findViewById(R.id.city);
        field = findViewById(R.id.field);
        timeText = findViewById(R.id.time);
        admin = findViewById(R.id.admin);
        notificationBtn = findViewById(R.id.notification);

        participants = findViewById(R.id.participants_btn);
        chat = findViewById(R.id.chat);
        editRoom = findViewById(R.id.edit_room);
        leaveRoom = findViewById(R.id.leaveRoom);

        //Access to firebase
        roomDAL = new RoomDAL(this);

        gm = GameManagement.getInstance();

        /*-----  Information from the previous page ------*/
        my_name = getIntent().getExtras().get("user_name").toString();
        category = getIntent().getExtras().get("category").toString();
        roomID = getIntent().getExtras().get("roomID").toString();
        room_name = getIntent().getExtras().get("room_name").toString();

        //---------------------------------------------------

        //Show button of "edit room" only for admin
        gm.permissionForAdmin(category, roomID, editRoom);
        //Show button of "Call players" only for admin
        gm.permissionForAdmin(category,roomID,notificationBtn);

        /*----- notification -----*/
        notificationManager = NotificationManagerCompat.from(this);
        createNotificationChannel();


        //---------------------------------------------------

        //Fill the details of room in TextView
        gm.setDetailsRoom(GameRoom.this, category, roomID, roomName, capacity, city, field, timeText, admin);


        //---------------------------------------------------

        //Show the list of players of room
        participants.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                SwitchActivities.Participants(GameRoom.this, roomID, category);
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
                SwitchActivities.EditRoom(v.getContext(), roomName.getText().toString(), timeText.getText().toString(),
                        city.getText().toString(), field.getText().toString(), category, roomID, my_name);
            }
        });

        //---------------------------------------------------

        //Leave room - player | Remove room - admin
        leaveRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                roomDAL.leaveOrRemoveRoom(roomID, category);
            }
        });
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("This is Channel");

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }

    public void sendNotification(View v) {
        Bitmap largeIcon = BitmapFactory.decodeResource(getResources(),R.drawable.teamball_logo);
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_logo)
                .setLargeIcon(largeIcon)
                .setContentTitle("TeamBall")
                .setContentText("lets start and play!")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }
}