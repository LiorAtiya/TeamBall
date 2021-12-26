package com.ariel.teamball.Controller;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.ariel.teamball.Model.DAL.PlayerDAL;
import com.ariel.teamball.View.Chatroom;
import com.ariel.teamball.View.CreateRoom;
import com.ariel.teamball.View.EditProfile;
import com.ariel.teamball.View.EditRoom;
import com.ariel.teamball.View.GameCenter;
import com.ariel.teamball.View.GameRoom;
import com.ariel.teamball.View.LoginActivity;
import com.ariel.teamball.View.MyProfile;
import com.ariel.teamball.View.MyRooms;
import com.ariel.teamball.View.Participants;
import com.ariel.teamball.View.RegisterActivity;
import com.ariel.teamball.View.SportsMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

public class SwitchActivities {

    public static void Participants(Context context, String roomID, String category){
        Log.d("TAG", "switching to participants");

        Intent i = new Intent(context, Participants.class);
        i.putExtra("roomID",roomID);
        i.putExtra("category",category);

        context.startActivity(i);
    }

    public static void SportMenu(Context context){
        Intent myIntent = new Intent(context, SportsMenu.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }

    public static void MyProfile(Context context){
        Intent myIntent = new Intent(context, MyProfile.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }

    public static void RegisterActivity(Context context){
        Intent myIntent = new Intent(context, RegisterActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }

    public static void LoginActivity(Context context){
        Intent myIntent = new Intent(context, LoginActivity.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }

    public static void GameCenter(Context context, String nameCategory){
        Intent intent = new Intent(context, GameCenter.class);
        intent.putExtra("category", nameCategory);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void EditProfile(Context context, TextView fullName, TextView email, TextView phone,
                                   TextView myCity, TextView myGender, TextView myNickName, TextView myAge){

        Intent i = new Intent(context, EditProfile.class);
        i.putExtra("fName",fullName.getText().toString());
        i.putExtra("email",email.getText().toString());
        i.putExtra("phone",phone.getText().toString());
        i.putExtra("city",myCity.getText().toString());
        i.putExtra("gender",myGender.getText().toString());
        i.putExtra("nickName",myNickName.getText().toString());
        i.putExtra("age",myAge.getText().toString());

        context.startActivity(i);
    }

    public static void MyRoom(Context context,String category){
        Intent i = new Intent(context, MyRooms.class);
        i.putExtra("category",category);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public static void createRoom(Context context, String category){

        final AlertDialog.Builder EnterGroupDialog = new AlertDialog.Builder(context);
        EnterGroupDialog.setTitle("Want to open new Room?");
        EnterGroupDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                        openSettingRoom(); //function to next page --> "settingRoom"
                Intent intentSettingRoom = new Intent(context, CreateRoom.class);
                intentSettingRoom.putExtra("category", category);
                intentSettingRoom.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentSettingRoom);
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

    public static void GameRoom(Context context,String roomName, String category, String roomID) {

        //Access to user collection to take my name
        String userID = PlayerDAL.getPlayerID();
        DocumentReference docRef = PlayerDAL.getCollection("users", userID);

        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {


                    DocumentSnapshot document = task.getResult();
                    String name = document.getString("fullName");
                    Intent intent = new Intent(context, GameRoom.class);
                    intent.putExtra("room_name", roomName);
                    intent.putExtra("user_name", name);
                    intent.putExtra("category", category);
                    intent.putExtra("roomID",roomID);
                    context.startActivity(intent);

                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }

    public static void Chatroom(Context context, String room_name, String my_name, String category, String roomID){
        Intent intent = new Intent(context, Chatroom.class);
        intent.putExtra("room_name", room_name);
        intent.putExtra("user_name", my_name);
        intent.putExtra("category", category);
        intent.putExtra("roomID",roomID);
        context.startActivity(intent);
    }

    public static void EditRoom(Context context, String roomName, String timeText, String city,String field,
                                String category, String roomID, String my_name){
        Intent i = new Intent(context, EditRoom.class);
        i.putExtra("roomName",roomName);
        i.putExtra("time",timeText);
        i.putExtra("city",city);
        i.putExtra("fieldName",field);
        i.putExtra("category",category);
        i.putExtra("roomID",roomID);
        i.putExtra("user_name",my_name);

        context.startActivity(i);
    }
}
