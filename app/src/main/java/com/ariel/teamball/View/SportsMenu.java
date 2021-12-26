package com.ariel.teamball.View;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.ariel.teamball.Controller.GameManagement;
import com.ariel.teamball.Controller.SwitchActivities;
import com.ariel.teamball.R;
import com.google.android.material.navigation.NavigationView;

public class SportsMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    CardView SoccerBtn , BasketBallBtn , TennisBtn, TableTennisBtn,
            HandBallBtn, VolleyBallBtn, DogeBallBtn,footballBtn;

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    GameManagement gm;

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sports_menu);

        drawerLayout = findViewById(R.id.drawerlayout);
        navigationView = findViewById(R.id.navigationview);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        // ----------Navigation Drawer Menu -----------------------//
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

//        //Link to layout
        SoccerBtn = findViewById(R.id.SoccerBtn);
        BasketBallBtn = findViewById(R.id.BasketBallBtn);
        TennisBtn = findViewById(R.id.TennisBtn);
        TableTennisBtn = findViewById(R.id.TableTennisBtn);
        HandBallBtn = findViewById(R.id.HandBallBtn);
        VolleyBallBtn = findViewById(R.id.VolleyBallBtn);
        DogeBallBtn = findViewById(R.id.DodgeballBtn);
        footballBtn = findViewById(R.id.FootballBtn);

//        playerDAL = new PlayerDAL(this);

        //Controller between view and model
        gm = new GameManagement(this);


        SoccerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivities.GameCenter(getApplicationContext(),"Soccer");
            }
        });

        BasketBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivities.GameCenter(getApplicationContext(),"Basketball");
            }
        });

        TennisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivities.GameCenter(getApplicationContext(),"Tennis");
            }
        });

        TableTennisBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivities.GameCenter(getApplicationContext(),"Table Tennis");
            }
        });

        HandBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivities.GameCenter(getApplicationContext(),"Table Tennis");
            }
        });

        VolleyBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivities.GameCenter(getApplicationContext(),"Volleyball");
            }
        });

        DogeBallBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivities.GameCenter(getApplicationContext(),"Dodgeball");
            }
        });

        footballBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                SwitchActivities.GameCenter(getApplicationContext(),"Football");
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_myprofile:
                SwitchActivities.MyProfile(this);
                break;
            case R.id.nav_logout:
                gm.playerSignOut();
                SwitchActivities.LoginActivity(getApplicationContext());
                finish();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}