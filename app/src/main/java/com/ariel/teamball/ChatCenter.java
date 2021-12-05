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

import com.ariel.teamball.Classes.Admin;
import com.ariel.teamball.Classes.Group;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChatCenter extends AppCompatActivity {

    public static final String TAG = "TAG";
    DatabaseReference reference;

    TextView nameCategory;
    ListView listView;
    Button createGroupBtn;
    ArrayAdapter<String> adapter;
    String name,category;
    EditText groupName;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_center);
        getSupportActionBar().hide();

        nameCategory = findViewById(R.id.nameCategory);
        listView = findViewById(R.id.listView);
        createGroupBtn = findViewById(R.id.button);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //For listview
        ArrayList<String> list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.list_row,R.id.txtName, list);
        listView.setAdapter(adapter);


        category = getIntent().getExtras().get("Category").toString();
        nameCategory.setText(category);

        //Access to the list of group category
        reference = FirebaseDatabase.getInstance().getReference("Group/"+category);

        String userID = fAuth.getCurrentUser().getUid();
        DocumentReference docRef = fStore.collection("users").document(userID);

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

//        request_username();

        //Put all the group of the category to list from the firebase
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<String>();

                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    set.add(((DataSnapshot) i.next()).getKey());

                }

                list.clear();
                list.addAll(set);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(ChatCenter.this, "No network connectivity", Toast.LENGTH_SHORT).show();
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int room, long l) {

                final AlertDialog.Builder EnterGroupDialog = new AlertDialog.Builder(view.getContext());
                EnterGroupDialog.setTitle("Want to join the group?");
                EnterGroupDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String roomName = (String) (listView.getItemAtPosition(room));

                        Map<String,Object> groups = new HashMap<>();
                        Map<String,Object> all_category = new HashMap<>();
                        all_category.put(category,roomName);
                        groups.put("My Groups",all_category);

                        docRef
                                .update(groups)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ChatCenter.this,"My Groups Updated", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ChatCenter.this, Chatroom.class);
                                intent.putExtra("room_name", roomName);
                                intent.putExtra("user_name", name);
                                intent.putExtra("category", category);
                                startActivity(intent);
                            }
                        });

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

        createGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder newGroupDialog = new AlertDialog.Builder(v.getContext());
                newGroupDialog.setTitle("Enter group name: ");
                groupName = new EditText(v.getContext());
                newGroupDialog.setView(groupName);

                newGroupDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String userID = fAuth.getCurrentUser().getUid();
                        DocumentReference docRef = fStore.collection("users").document(userID);

                        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    name = document.getString("firstName");

                                    Admin admin = new Admin();

                                } else {
                                    Log.d(TAG, "get failed with ", task.getException());
                                }
                            }
                        });


                        //Group storage in database
                        Group newGroup = new Group(groupName.getText().toString(), 20);

                        final FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference ref = database.getReference();

                        DatabaseReference usersRef = ref.child("Group").child(category);
                        Map<String, Object> group = new HashMap<>();
                        group.put(groupName.getText().toString(),newGroup);

                        usersRef.updateChildren(group);

//                        Map<String,Object> map = new HashMap<>();
//                        map.put(groupName.getText().toString(), "");
//                        reference.updateChildren(map);
                    }
                });

                newGroupDialog.show();
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