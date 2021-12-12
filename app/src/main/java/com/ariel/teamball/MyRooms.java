package com.ariel.teamball;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MyRooms extends AppCompatActivity {

    String category;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_rooms);

        category = getIntent().getExtras().get("category").toString();

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setSelectedItemId(R.id.my_rooms);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.my_rooms:
                        return true;
                    case R.id.all_rooms:
                        Intent i = new Intent(getApplicationContext(),GameCenter.class);
                        i.putExtra("Category",category);
                        startActivity(i);
                        overridePendingTransition(0,0);
                        return true;

                }

                return false;
            }
        });
    }
}