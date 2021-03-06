//package com.ariel.teamball.View;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.ariel.teamball.Controller.SwitchActivities;
//import com.ariel.teamball.Model.DAL.ChatDAL;
//import com.ariel.teamball.R;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//public class Chatroom extends AppCompatActivity {
//
//    EditText text2send;
//    TextView message;
//    private String user_name,room_name,category,roomID;
//    Button send;
//    DatabaseReference reference;
//    String temp_key;
//
//    ChatDAL chatDAL;
//
//    @Override
//    public void onBackPressed() {
//        SwitchActivities.MyRoom(getApplicationContext(),category);
//        overridePendingTransition(0,0);
//        finish();
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_chatroom);
//
//        text2send = findViewById(R.id.textToSend);
//        message = findViewById(R.id.message);
//        send = findViewById(R.id.send);
//
//        chatDAL = new ChatDAL(this);
//
//        if(getSupportActionBar()!=null)
//        {
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }
//
//        user_name = getIntent().getExtras().get("user_name").toString();
//        room_name = getIntent().getExtras().get("room_name").toString();
//        category = getIntent().getExtras().get("category").toString();
//        roomID = getIntent().getExtras().get("roomID").toString();
//        setTitle(" Room - "+room_name);
//
//        reference = FirebaseDatabase.getInstance().getReference("Rooms").child(category).child(roomID).child("chat");
//        reference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                append_chat(dataSnapshot);
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                append_chat(dataSnapshot);
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//                append_chat(dataSnapshot);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//        });
//
//        send.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Map<String,Object> map = new HashMap<String,Object>();
//                temp_key = reference.push().getKey();
//                reference.updateChildren(map);
//
//                DatabaseReference child_ref = reference.child(temp_key);
//                Map<String,Object> map2 = new HashMap<>();
//                map2.put("name",user_name);
//                map2.put("msg", text2send.getText().toString());
//                child_ref.updateChildren(map2).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(Exception e) {
//                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                });
//                text2send.setText("");
//            }
//        });
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == android.R.id.home)
//            finish();
//
//        return super.onOptionsItemSelected(item);
//    }
//
////    public void send(View v)
////    {
////        Map<String,Object> map = new HashMap<String,Object>();
////        temp_key = reference.push().getKey();
////        reference.updateChildren(map);
////
////        DatabaseReference child_ref = reference.child(temp_key);
////        Map<String,Object> map2 = new HashMap<>();
////        map2.put("name",user_name);
////        map2.put("msg", text2send.getText().toString());
////        child_ref.updateChildren(map2).addOnFailureListener(new OnFailureListener() {
////            @Override
////            public void onFailure(Exception e) {
////                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
////            }
////        });
////        text2send.setText("");
////    }
//
//    public void append_chat(DataSnapshot ss)
//    {
//        String chat_msg,chat_username;
//        Iterator i = ss.getChildren().iterator();
//
//        while(i.hasNext())
//        {
//            chat_msg = ((DataSnapshot)i.next()).getValue().toString();
//            chat_username = ((DataSnapshot)i.next()).getValue().toString();
//            message.append(chat_username + ": " +chat_msg + " \n");
//        }
//    }
//}


package com.ariel.teamball.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ariel.teamball.Controller.GameManagement;
import com.ariel.teamball.Controller.SwitchActivities;
import com.ariel.teamball.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class Chatroom extends AppCompatActivity {
    /* Page objects */
    EditText text2send;
    TextView message;
    FrameLayout send;
    private String user_name,room_name,category,roomID;

    GameManagement gm;

    @Override
    public void onBackPressed() {
        /*-----  Information from the previous page ------*/
        SwitchActivities.MyRoom(getApplicationContext(),category);
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);

        text2send = findViewById(R.id.textToSend);
        message = findViewById(R.id.message);
        send = findViewById(R.id.send);

        // creates game management object
        gm = GameManagement.getInstance();

        if(getSupportActionBar()!=null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        user_name = getIntent().getExtras().get("user_name").toString();
        room_name = getIntent().getExtras().get("room_name").toString();
        category = getIntent().getExtras().get("category").toString();
        roomID = getIntent().getExtras().get("roomID").toString();
        setTitle(" Room - "+room_name);

        //Set all the messages of the chat room
        gm.showMessageOfChat(category,roomID,message);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gm.addNewMessage(category,roomID,user_name,text2send.getText().toString());
                text2send.setText("");
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

}