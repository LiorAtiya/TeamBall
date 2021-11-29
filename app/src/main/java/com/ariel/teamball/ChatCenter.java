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

import com.ariel.teamball.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChatCenter extends AppCompatActivity {

    public static final String TAG = "TAG";
    DatabaseReference reference;

    TextView nameCategory;
    ListView l1;
    Button createGroupBtn;
    ArrayAdapter<String> adapter;
    String name;
    EditText ee;
    String category;

    FirebaseFirestore fStore;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_center);
        getSupportActionBar().hide();

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        nameCategory = findViewById(R.id.nameCategory);
        l1 = findViewById(R.id.listView);
        createGroupBtn = findViewById(R.id.button);
        ArrayList<String> list = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1, list);
        l1.setAdapter(adapter);

        category = getIntent().getExtras().get("Category").toString();

        nameCategory.setText(category);

        reference = FirebaseDatabase.getInstance().getReference(category);
//        reference = FirebaseDatabase.getInstance().getReference().getRoot();

        String userID = fAuth.getCurrentUser().getUid();
        DocumentReference docRef = fStore.collection("users").document(userID);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    name = document.getString("fName");
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });

//        request_username();

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

        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent intent = new Intent(ChatCenter.this, Chatroom.class);
                String selectedFromList = (String) (l1.getItemAtPosition(i));
                intent.putExtra("room_name", selectedFromList);
                intent.putExtra("user_name", name);
                startActivity(intent);

            }
        });

        createGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder newGroupDialog = new AlertDialog.Builder(v.getContext());
                newGroupDialog.setTitle("Enter name group");
                ee = new EditText(v.getContext());
                newGroupDialog.setView(ee);

                newGroupDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Map<String,Object> map = new HashMap<>();
                        map.put(ee.getText().toString(), "");
                        reference.updateChildren(map);
                    }
                });

                newGroupDialog.show();
            }
        });
    }

    public void request_username()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your name?");
        ee = new EditText(this);
        builder.setView(ee);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                name = ee.getText().toString();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                request_username();
            }
        });
        builder.show();
    }
}