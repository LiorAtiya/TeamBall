package com.ariel.teamball.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ariel.teamball.Controller.GameManagement;
import com.ariel.teamball.Controller.SwitchActivities;
import com.ariel.teamball.Model.DAL.PlayerDAL;
import com.ariel.teamball.R;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText mEmail, mPassword;
    Button mLoginBtn;
    TextView mCreateBtn, mForgotPassBtn;
    ProgressBar progressBar;

    GameManagement gm;

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Don't go out :(", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

        //Link to layout
        mEmail = findViewById(R.id.LoginEmail);
        mPassword = findViewById(R.id.LoginPassword);
        progressBar = findViewById(R.id.simpleProgressBar2);
        mLoginBtn = findViewById(R.id.LoginBtn);
        mCreateBtn = findViewById(R.id.RegisterFromLogin);
        mForgotPassBtn = findViewById(R.id.ForgotPassBtn);

        //Controller between view and model
        gm = new GameManagement(this);

        //Automatic connection
        if(gm.playerConnected()){
            SwitchActivities.SportMenu(getApplicationContext());
            finish();
        }

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();

                //Character insertion check
                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required.");
                }

                //Charging circle while waiting to connect
                progressBar.setVisibility(View.VISIBLE);

                gm.playerLogin(email,password,progressBar);

            }
        });

        //Go to the registration page
        mCreateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivities.RegisterActivity(getApplicationContext());
                finish();
            }
        });


        mForgotPassBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gm.resetPassword(v);
            }
        });
    }
}