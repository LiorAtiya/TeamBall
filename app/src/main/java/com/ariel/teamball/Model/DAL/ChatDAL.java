package com.ariel.teamball.Model.DAL;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ChatDAL {

    private static DatabaseReference reference;
    private static Context context;

    public ChatDAL(Context _context){
        this.context = _context;
    }

    public static void addNewMessage(String category, String roomID,String user_name,String msg){
        reference = FirebaseDatabase.getInstance().getReference("Rooms").child(category).child(roomID).child("chat");

        Map<String,Object> map = new HashMap<String,Object>();
        String temp_key = reference.push().getKey();
        reference.updateChildren(map);

        DatabaseReference child_ref = reference.child(temp_key);
        Map<String,Object> map2 = new HashMap<>();
        map2.put("name",user_name);
        map2.put("msg", msg);
        child_ref.updateChildren(map2).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

//    public void append_chat(DataSnapshot ss, TextView message)
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
}
