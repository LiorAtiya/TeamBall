package com.ariel.teamball.Controller;

import android.content.Context;
import android.content.Intent;
import android.provider.MediaStore;
import android.widget.TextView;

import com.ariel.teamball.View.EditProfile;
import com.ariel.teamball.View.GameCenter;
import com.ariel.teamball.View.LoginActivity;
import com.ariel.teamball.View.RegisterActivity;
import com.ariel.teamball.View.SportsMenu;

public class SwitchActivities {

    public static void SportMenu(Context context){
        Intent myIntent = new Intent(context, SportsMenu.class);
        myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myIntent);
    }

    public static void RegisterActivity(Context context){
        Intent myIntent = new Intent(context, RegisterActivity.class);
        context.startActivity(myIntent);
    }

    public static void LoginActivity(Context context){
        Intent myIntent = new Intent(context, LoginActivity.class);
        context.startActivity(myIntent);
    }

    public static void Categories(Context context,String nameCategory){
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

}
