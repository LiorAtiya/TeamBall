package com.ariel.teamball.Controller;

import android.content.Context;
import android.content.Intent;

import com.ariel.teamball.View.SportsMenu;

public class SwitchActivities {

    public static void SportMenu(Context context){
        Intent myIntent = new Intent(context, SportsMenu.class);
        context.startActivity(myIntent);
    }


}
