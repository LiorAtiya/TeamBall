package com.ariel.teamball.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ariel.teamball.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}