package com.ariel.teamball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class GameOptions extends AppCompatActivity {
    /*..  Setting Buttons ..*/
    Button mLogoutBtn, SoccerBtn, BasketBallBtn, TennisBtn, TableTennisBtn, HandBallBtn, VolleyBallBtn, DogeBallBtn,myProfile;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_options);
        getSupportActionBar().hide();

        /* Catches them */
        mLogoutBtn = findViewById(R.id.LogoutBtn);
        myProfile = findViewById(R.id.myProfileBtn);

        SoccerBtn = findViewById(R.id.SoccerBtn);
        BasketBallBtn = findViewById(R.id.BasketBallBtn);
        TennisBtn = findViewById(R.id.TennisBtn);
        TableTennisBtn = findViewById(R.id.TableTennisBtn);
        HandBallBtn = findViewById(R.id.HandBallBtn);
        VolleyBallBtn = findViewById(R.id.VolleyBallBtn);
        DogeBallBtn = findViewById(R.id.DogeBallBtn);

        /* What each button does */
        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });

        myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),MyProfile.class));
            }
        });

        SoccerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatCenter.class);
                intent.putExtra("Category", "Soccer");
                startActivity(intent);
            }
        });

        BasketBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatCenter.class);
                intent.putExtra("Category", "BasketBall");
                startActivity(intent);
            }
        });

        TennisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatCenter.class);
                intent.putExtra("Category", "Tennis");
                startActivity(intent);
            }
        });

        TableTennisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatCenter.class);
                intent.putExtra("Category", "TableTennis");
                startActivity(intent);
            }
        });

        HandBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatCenter.class);
                intent.putExtra("Category", "HandBall");
                startActivity(intent);
            }
        });

        VolleyBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatCenter.class);
                intent.putExtra("Category", "VolleyBall");
                startActivity(intent);
            }
        });
//
        DogeBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ChatCenter.class);
                intent.putExtra("Category", "DogeBall");
                startActivity(intent);
            }
        });

    }


}