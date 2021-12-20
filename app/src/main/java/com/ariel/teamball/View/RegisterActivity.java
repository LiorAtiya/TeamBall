package com.ariel.teamball.View;

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

import androidx.appcompat.app.AppCompatActivity;

import com.ariel.teamball.Model.DAL.PlayerDAL;
import com.ariel.teamball.Model.Classes.Player;
import com.ariel.teamball.R;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    TextInputEditText mFullName, mEmail, mPassword, mPhone,mNickName;
    Spinner citySpinner, genderSpinner , ageSpinner;
    Button mRegisterBtn;
    TextView AlreadyRegisterBtn;
    ProgressBar progressBar;

    PlayerDAL playerDAL;

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

        //Link to layout
        mFullName = findViewById(R.id.FullName);
        mNickName = findViewById(R.id.tvNickName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mPhone = findViewById(R.id.Phone);

        /* For Choose City */
        citySpinner = findViewById(R.id.citySpinner);
        /* For Choose gender */
        genderSpinner = findViewById(R.id.genderSpinner);
        /* For Choose age */
        ageSpinner = findViewById(R.id.ageSpinner);

        /* store and connect our cities names with spinner */
        ArrayAdapter<String> allCities = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.names));
        //for drop down list:
        allCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(allCities);

        /* store and connect  gender options with spinner */
        ArrayAdapter<String> genders = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.genders));
        //for drop down list:
        genders.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genders);

        ArrayAdapter<String> ageAdapter = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.ages));
        //for drop down list:
        ageAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ageSpinner.setAdapter(ageAdapter);

        mRegisterBtn = findViewById(R.id.RegisterBtn);
        AlreadyRegisterBtn = findViewById(R.id.LoginFromRegister);
        progressBar = findViewById(R.id.simpleProgressBar);

        playerDAL = new PlayerDAL(this);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String fullName = mFullName.getText().toString();
                String phone = mPhone.getText().toString();
                String nickName = mNickName.getText().toString();
                String chosenGender = genderSpinner.getSelectedItem().toString();
                String chosenCity = citySpinner.getSelectedItem().toString();
                String chosenAge = ageSpinner.getSelectedItem().toString();


                /* -- Character insertion check -- */
                if(TextUtils.isEmpty(fullName)){
                    mFullName.setError("Fullname is Required");
                    return;
                }

                if(TextUtils.isEmpty(nickName) || nickName.contains(" ")){
                    mNickName.setError("Nickname not valid");
                    return;
                }

                if(TextUtils.isEmpty(password) || password.contains(" ")){
                    mPassword.setError("Password not valid");
                    return;
                }

                if(TextUtils.isEmpty(email) || !email.contains("@") || !email.contains(".")){
                    mEmail.setError("Email not valid");
                    return;
                }

                if(TextUtils.isEmpty(phone) || phone.contains(" ")){
                    mPhone.setError("Phone nubmer not valid");
                    return;
                }

                /* Not chosen item in Spinners */
                if(chosenGender.contains("G")){ //gender not chosen
                    ((TextView)genderSpinner.getSelectedView()).setError("choose gender");
                    return;
                }

                if(chosenCity.contains("ty")){ //city not chosen
                    ((TextView)citySpinner.getSelectedView()).setError("choose city");
                    return;
                }

                if(chosenAge.contains("A")){ //age not chosen
                    ((TextView)ageSpinner.getSelectedView()).setError("choose age");
                    return;
                }

                //Charging circle while waiting to connect
                progressBar.setVisibility(View.VISIBLE);

                //Create new player
                Player p = new Player(fullName ,nickName,email,password, phone,chosenCity,chosenGender,chosenAge);
                playerDAL.playerRegister(p,progressBar);

            }
        });


        AlreadyRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }

}