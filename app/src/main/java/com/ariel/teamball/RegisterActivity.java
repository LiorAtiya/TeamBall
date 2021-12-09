package com.ariel.teamball;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ariel.teamball.Classes.DAO.PlayerDAO;
import com.ariel.teamball.Classes.Firebase;
import com.ariel.teamball.Classes.Player;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextInputEditText mFullName, mEmail, mPassword, mPhone,mNickName;
    Spinner mySpinner, genderSpinner;
    Button mRegisterBtn;
    TextView AlreadyRegisterBtn;
    ProgressBar progressBar;

    PlayerDAO playerDAO;

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Don't go out :(", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        // TODO: have to update first, last name and age
        //Link to layout
        mFullName = findViewById(R.id.FullName);
        mNickName = findViewById(R.id.tvNickName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mPhone = findViewById(R.id.Phone);

        /* For Choose City */
        mySpinner = findViewById(R.id.citySpinner);
        /* For Choose gender */
        genderSpinner = findViewById(R.id.genderSpinner);

        /* store and connect our cities names with spinner */
        ArrayAdapter<String> allCities = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.names));
        //for drop down list:
        allCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(allCities);

        /* store and connect our cities names with spinner */
        ArrayAdapter<String> genders = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.genders));
        //for drop down list:
        genders.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genders);

        mRegisterBtn = findViewById(R.id.RegisterBtn);
        AlreadyRegisterBtn = findViewById(R.id.LoginFromRegister);
        progressBar = findViewById(R.id.simpleProgressBar);

//        mFirstName = findViewById(R.id.firstName);
//        mLastName = findViewById(R.id.lastName);
//        mAge = findViewById(R.id.age);
//        mCity = findViewById(R.id.city);


        playerDAO = new PlayerDAO(this);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String fullName = mFullName.getText().toString();
                String phone = mPhone.getText().toString();
                String nickName = mNickName.getText().toString();

//                String firstName = mFirstName.getText().toString().trim();
//                String lastName = mLastName.getText().toString().trim();
//                int age = Integer.valueOf(mAge.getText().toString());

                //Create new player
                Player p = new Player(fullName ,nickName,email,password, phone," ");

                //Character insertion check
                if(TextUtils.isEmpty(p.getEmail())){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(p.getPassword())){
                    mPassword.setError("Password is Required.");
                    return;
                }

                if(TextUtils.isEmpty(p.getPhone())){
                    mPhone.setError("Phone is Required");
                    return;
                }

                if(TextUtils.isEmpty(p.getNickName())){
                    mNickName.setError("Nickname is Required");
                    return;
                }

                if(TextUtils.isEmpty(p.getFullName())){
                    mFullName.setError("Fullname is Required");
                    return;
                }

                //Charging circle while waiting to connect
                progressBar.setVisibility(View.VISIBLE);

                playerDAO.playerRegister(p,progressBar);

            }
        });


        AlreadyRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

    }

}