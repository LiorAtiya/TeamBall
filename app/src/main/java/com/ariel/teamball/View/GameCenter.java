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

public class GameCenter extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextView nameCategory;
    ListView listView;
    Button createRoomBtn;
    ArrayAdapter<Room> adapter;
    String name,category;
//    EditText room_name;

    BottomNavigationView bottomNavigationView; //Switch between gameCenter to MyRoom

    //Access to firebase
    PlayerDAL playerDAL;
    RoomDAL roomDAL;

    @Override
    public void onBackPressed() {
        SwitchActivities.SportMenu(getApplicationContext());
        finish();

//        Intent i = new Intent(getApplicationContext(), SportsMenu.class);
//        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_center);
        getSupportActionBar().hide();

        nameCategory = findViewById(R.id.nameCategory);
        listView = findViewById(R.id.listView);
        createRoomBtn = findViewById(R.id.CR_btn);

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

                switch (item.getItemId()){
                    case R.id.my_rooms:

                        SwitchActivities.MyRoom(getApplicationContext(),category);

//                        Intent i = new Intent(getApplicationContext(), MyRooms.class);
//                        i.putExtra("category",category);
//                        startActivity(i);
                        finish();
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.all_rooms:
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

//        //Access to the list of my rooms category
//        DatabaseReference myRoomsRef = roomDAL.getPathReference("userRooms/"+ playerDAL.getPlayerID()+"/"+category);
//
//        Set<String> myRoomsName = new HashSet<String>();
//
//        //Put all the my rooms of the category to list
//        myRoomsRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                //Loop on each room
//                Iterator i = dataSnapshot.getChildren().iterator();
//                while (i.hasNext()) {
//                    DataSnapshot childSnapshot = (DataSnapshot) i.next();
//                    String room = childSnapshot.getValue(String.class);
//                    myRoomsName.add(room);
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(GameCenter.this, "No network connectivity", Toast.LENGTH_SHORT).show();
//            }
//        });

        Set<String> myRoomsList = roomDAL.getMyListRooms(category);

        //--------------------------------------------------------------

//        roomDAL.setRoomsOnListview(myRoomsList,category,adapter);

        //Access to the list of room category
        DatabaseReference reference = roomDAL.getPathReference("Rooms/" + category);

        //Put all the rooms of the category to list from the firebase
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<Room> set = new HashSet<Room>();

                //Loop on each room
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    DataSnapshot childSnapshot = (DataSnapshot) i.next();
                    Room room = childSnapshot.getValue(Room.class);
                    //Add to the list all the rooms that the user does not enter.
                    if(!myRoomsList.contains(room.getRoomID())){
                        set.add(room);
                    }
                }

                list.clear();
                list.addAll(set);
                //Set the list on viewlist
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(GameCenter.this, "No network connectivity", Toast.LENGTH_SHORT).show();
            }
        });

        //---------------------------------------------------

//        //Access to user collection to take my name
//        String userID = playerDAL.getPlayerID();
//        DocumentReference docRef = playerDAL.getCollection("users", userID);
//
//        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    name = document.getString("fullName");
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });

        //---------------------------------------------------

        //Click on some room
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int room, long l) {

                String roomName = adapter.getItem(room).getName();
                String roomID =  adapter.getItem(room).getRoomID();

                roomDAL.checkLimitOfRoom_And_JoinToRoom(category,roomID,roomName);

            }
        });

        //---------------------------------------------------

        createRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SwitchActivities.createRoom(GameCenter.this,category);

//                final AlertDialog.Builder EnterGroupDialog = new AlertDialog.Builder(v.getContext());
//                EnterGroupDialog.setTitle("Want to open new Room?");
//                EnterGroupDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
////                        openSettingRoom(); //function to next page --> "settingRoom"
//                        SwitchActivities.createRoom(GameCenter.this,category);
//                    }
//                });
//                EnterGroupDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.cancel();
//                    }
//                });
//                EnterGroupDialog.show();
            }
        });
    }

//    /* function that moves the user(Admin of the room from now)
//       from the "GameCenter" page to the "settingRoom" page to set the room */
//    public void openSettingRoom() {
//        Intent intentSettingRoom = new Intent(GameCenter.this, CreateRoom.class);
//        intentSettingRoom.putExtra("category", category);
//        startActivity(intentSettingRoom);
//    }

}