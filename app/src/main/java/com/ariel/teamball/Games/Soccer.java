package com.ariel.teamball.Games;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.ariel.teamball.R;

public class Soccer extends AppCompatActivity {
    /* Setting Buttons */
    Button c_g , j_g;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soccer);

        /* Catches them */
        c_g = findViewById(R.id.C_G_btn);
        j_g = findViewById(R.id.J_G_btn);

        /* What each button does */
        c_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        j_g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}