package com.ariel.teamball;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ariel.teamball.Classes.DAO.PlayerDAO;

public class SportsMenu extends AppCompatActivity {

    Button mLogoutBtn, myProfile;
    ImageButton SoccerBtn , BasketBallBtn , TennisBtn, TableTennisBtn,
            HandBallBtn, VolleyBallBtn, DogeBallBtn,footballBtn;
    PlayerDAO playerDAO;

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

        playerDAO = new PlayerDAO(this);

        mLogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                playerDAO.playerSignOut();
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
                Intent intent = new Intent(getApplicationContext(), GameCenter.class);
                intent.putExtra("category", "Soccer");
                startActivity(intent);
            }
        });

        BasketBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameCenter.class);
                intent.putExtra("category", "Basketball");
                startActivity(intent);
            }
        });

        TennisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameCenter.class);
                intent.putExtra("category", "Tennis");
                startActivity(intent);
            }
        });

        TableTennisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameCenter.class);
                intent.putExtra("category", "Table Tennis");
                startActivity(intent);
            }
        });

        HandBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameCenter.class);
                intent.putExtra("category", "Handball");
                startActivity(intent);
            }
        });

        VolleyBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameCenter.class);
                intent.putExtra("category", "Volleyball");
                startActivity(intent);
            }
        });

        DogeBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameCenter.class);
                intent.putExtra("category", "Dodgeball");
                startActivity(intent);
            }
        });

        footballBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GameCenter.class);
                intent.putExtra("category", "American Football");
                startActivity(intent);
            }
        });
    }


}