package com.ariel.teamball.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ariel.teamball.Controller.SwitchActivities;
import com.ariel.teamball.Model.DAL.PlayerDAL;
import com.ariel.teamball.R;

public class SportsMenu extends AppCompatActivity {

    Button mLogoutBtn, myProfile;
    ImageButton SoccerBtn , BasketBallBtn , TennisBtn, TableTennisBtn,
            HandBallBtn, VolleyBallBtn, DogeBallBtn,footballBtn;
    PlayerDAL playerDAL;

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Don't go out :(", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_menu);
        getSupportActionBar().hide();

        //Link to layout
        mLogoutBtn = findViewById(R.id.LogoutBtn);
        myProfile = findViewById(R.id.myProfileBtn);

        SoccerBtn = findViewById(R.id.SoccerBtn);
        BasketBallBtn = findViewById(R.id.BasketBallBtn);
        TennisBtn = findViewById(R.id.TennisBtn);
        TableTennisBtn = findViewById(R.id.TableTennisBtn);
        HandBallBtn = findViewById(R.id.HandBallBtn);
        VolleyBallBtn = findViewById(R.id.VolleyBallBtn);
        DogeBallBtn = findViewById(R.id.DogeBallBtn);
        footballBtn = findViewById(R.id.football);

        playerDAL = new PlayerDAL(this);

        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerDAL.playerSignOut();
                SwitchActivities.LoginActivity(getApplicationContext());
                finish();
            }
        });

        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MyProfile.class));
            }
        });

        SoccerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivities.Categories(getApplicationContext(),"Soccer");
            }
        });

        BasketBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivities.Categories(getApplicationContext(),"Basketball");
            }
        });

        TennisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivities.Categories(getApplicationContext(),"Tennis");
            }
        });

        TableTennisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivities.Categories(getApplicationContext(),"Table Tennis");
            }
        });

        HandBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivities.Categories(getApplicationContext(),"Table Tennis");
            }
        });

        VolleyBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivities.Categories(getApplicationContext(),"Volleyball");
            }
        });

        DogeBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivities.Categories(getApplicationContext(),"Dodgeball");
            }
        });

        footballBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SwitchActivities.Categories(getApplicationContext(),"American Football");
            }
        });
    }


}