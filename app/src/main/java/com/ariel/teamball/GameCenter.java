package com.ariel.teamball;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.ariel.teamball.Classes.Adapters.ListAdapter;
import com.ariel.teamball.Classes.Admin;
import com.ariel.teamball.Classes.GameManagement;
import com.ariel.teamball.Classes.Room;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class GameCenter extends AppCompatActivity {

    public static final String TAG = "TAG";
    DatabaseReference reference;

    TextView nameCategory;
    ListView listView;
    Button createRoomBtn;
    ArrayAdapter<Room> adapter;
    String name,category;
    EditText roomName;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_center);
        getSupportActionBar().hide();

        nameCategory = findViewById(R.id.nameCategory);
        listView = findViewById(R.id.listView);
        createRoomBtn = findViewById(R.id.button);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        ArrayList<Room> list = new ArrayList<>();
        adapter = new ListAdapter(this,R.layout.list_group,list);
        listView.setAdapter(adapter);

//        //For listview
//        ArrayList<String> list = new ArrayList<>();
//        adapter = new ArrayAdapter<String>(this, R.layout.list_group,R.id.txtName, list);
//        listView.setAdapter(adapter);

        category = getIntent().getExtras().get("Category").toString();
        nameCategory.setText(category);

        String userID = fAuth.getCurrentUser().getUid();
        DocumentReference docRef = fStore.collection("users").document(userID);

        //Access to user collection
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    name = document.getString("firstName");
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });


        //Access to the list of group category
        reference = FirebaseDatabase.getInstance().getReference("Rooms/"+category);

        //Put all the rooms of the category to list from the firebase
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

//                Set<String> set = new HashSet<String>();
//
//                Iterator i = dataSnapshot.getChildren().iterator();
//                while (i.hasNext()) {
//                    set.add(((DataSnapshot) i.next()).getKey());
//
//                }
//
//                list.clear();
//                list.addAll(set);

                Set<Room> set = new HashSet<Room>();

                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    DataSnapshot childSnapshot = (DataSnapshot) i.next();
                    Room room = childSnapshot.getValue(Room.class);
                    set.add(room);
                }

                list.clear();
                list.addAll(set);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(GameCenter.this, "No network connectivity", Toast.LENGTH_SHORT).show();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int room, long l) {

                final AlertDialog.Builder EnterGroupDialog = new AlertDialog.Builder(view.getContext());
                EnterGroupDialog.setTitle("Want to join the room?");
                EnterGroupDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

//                        String roomName = (String) (listView.getItemAtPosition(room));
                        String roomName = adapter.getItem(room).getName();

                        //Add group to list of private rooms user
                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference();

                        DatabaseReference usersRef = ref.child("userGroups/"+userID);
                        Map<String, Object> groups = new HashMap<>();
                        groups.put(category,roomName);

                        usersRef.updateChildren(groups);

                        Toast.makeText(GameCenter.this,"My rooms Updated", Toast.LENGTH_SHORT).show();

                        //----------------------------------------
                        Intent intent = new Intent(GameCenter.this, Chatroom.class);
                        intent.putExtra("room_name", roomName);
                        intent.putExtra("user_name", name);
                        intent.putExtra("category", category);
                        startActivity(intent);

                    }
                });
                EnterGroupDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                EnterGroupDialog.show();

            }
        });

        createRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder newRoomDialog = new AlertDialog.Builder(v.getContext());
                newRoomDialog.setTitle("Enter room name: ");
                roomName = new EditText(v.getContext());
                newRoomDialog.setView(roomName);

                newRoomDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String userID = fAuth.getCurrentUser().getUid();
                        DocumentReference docRef = fStore.collection("users").document(userID);

                        // makes player to be an admin on the group

                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    name = document.getString("fullName");
                                    String email = document.getString("email");
                                    String phone = document.getString("phone");


                                    Admin admin = new Admin(name,email,phone, roomName.getText().toString(),category);

//                                    // creates game management object
//                                    GameManagement gm = GameManagement.getInstance();
//                                    // checks if a room can be created with that admin
//                                    if(gm.roomsAvailability() && gm.canBeAdmin(0)) { // TODO: player id
//                                        // upgrade the player to be an admin
//                                        int adminID = gm.createAdmin(userID);
//                                        // creates a new room with the given admin
//                                        int roomID = gm.createRoom(adminID);
//                                        // updates the room in the admin object
//                                        gm.updateAdminRoom(roomID, adminID);
////                                    }

                                    DocumentReference docRefAdmin = fStore.collection("admins").document(userID);

                                    // Stores the admin in the collection
                                    docRefAdmin.set(admin).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d(TAG,"onSuccess: user Profile is created for "+userID);
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG,"onFailure: "+e.toString());
                                        }
                                    });

                                    // Group storage in database
                                    Room newRoom = new Room(roomName.getText().toString(), 20,"Neighborhood A","Tel-Aviv",admin);

                                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference ref = database.getReference();

                                    DatabaseReference roomsRef = ref.child("Rooms").child(category);
                                    Map<String, Object> room = new HashMap<>();
                                    room.put(roomName.getText().toString(),newRoom);

                                    roomsRef.updateChildren(room);

                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });

                    }
                });

                newRoomDialog.show();
            }
        });
    }

//    public void request_username()
//    {
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Enter your name?");
//        ee = new EditText(this);
//        builder.setView(ee);
//        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                name = ee.getText().toString();
//            }
//        });
//
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//                request_username();
//            }
//        });
//        builder.show();
//    }
}