package com.ariel.teamball;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ChatCenter extends AppCompatActivity {

    DatabaseReference reference;

    EditText e1;
    ListView l1;
    Button createGroupBtn;
    ArrayAdapter<String> adapter;
    String name;
    EditText ee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_center);
        getSupportActionBar().hide();

        e1 = findViewById(R.id.editText);
        l1 = findViewById(R.id.listView);
        createGroupBtn = findViewById(R.id.button);
        ArrayList<String> list = new ArrayList<>();

        adapter = new ArrayAdapter<String>(this, android.R.layout.activity_list_item, android.R.id.text1, list);
        l1.setAdapter(adapter);

        reference = FirebaseDatabase.getInstance().getReference().getRoot();
        request_username();

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
                Map<String,Object> map = new HashMap<>();
                map.put(e1.getText().toString(), "");
                reference.updateChildren(map);
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

//    public void insert_data(View v)
//    {
//
//        Map<String,Object> map = new HashMap<>();
//        map.put(e1.getText().toString(), "");
//        reference.updateChildren(map);
//
//    }

//    public void create_chatroom(View v){
//        HashMap<String,Object> HashMap = new HashMap<>();
//        HashMap.put(e1.getText().toString(),"Chatroom");
//        reference.updateChildren(HashMap);
//
//        list.add(e1.getText().toString());
//        adapter.notifyDataSetChanged();
//    }

//    DatabaseReference reference;
//    ArrayList<String> arrayList;
//
//    EditText e1;
//    ListView l1;
//    ArrayAdapter<String> adapter;
//    String name;
//    EditText ee;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        e1 = findViewById(R.id.editText);
//        l1 = findViewById(R.id.listView);
//        arrayList = new ArrayList<>();
//
//        adapter = new ArrayAdapter<String>(this,
//                android.R.layout.activity_list_item, android.R.id.text1, arrayList);
//        l1.setAdapter(adapter);
//
//        reference = FirebaseDatabase.getInstance().getReference().getRoot();
//        request_username();
//
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//
//                Set<String> set = new HashSet<String>();
//
//
//                Iterator i = dataSnapshot.getChildren().iterator();
//                while (i.hasNext()) {
//                    set.add(((DataSnapshot) i.next()).getKey());
//
//                }
//
//                arrayList.clear();
//                arrayList.addAll(set);
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Toast.makeText(ChatCenter.this, "No network connectivity", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//
//        l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//
//                Intent intent = new Intent(ChatCenter.this, Chatroom.class);
//                intent.putExtra("room_name", ((TextView) view).getText().toString());
//                intent.putExtra("user_name", name);
//                startActivity(intent);
//
//            }
//        });
//
//
//
//    }
//
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
//
//
//            }
//        });
//
//        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                dialogInterface.cancel();
//                request_username();
//
//
//            }
//        });
//        builder.show();
//
//    }
//
//    public void insert_data(View v)
//    {
//
//        Map<String,Object> map = new HashMap<>();
//        map.put(e1.getText().toString(), "");
//        reference.updateChildren(map);
//
//    }
}