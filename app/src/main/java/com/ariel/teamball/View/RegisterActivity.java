package com.ariel.teamball.View;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ariel.teamball.Controller.GameManagement;
import com.ariel.teamball.Controller.SwitchActivities;
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

    GameManagement gm;

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Don't go out :(", Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        //Controller between view and model
        gm = new GameManagement(this);

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
        ArrayAdapter allCities = ArrayAdapter.createFromResource(this
                ,R.array.names,R.layout.color_spinner);
        allCities.setDropDownViewResource(R.layout.spinner_dropdown);
        citySpinner.setAdapter(allCities);

        /* store and connect  gender options with spinner */
        ArrayAdapter genders = ArrayAdapter.createFromResource(this
                ,R.array.genders,R.layout.color_spinner);
        genders.setDropDownViewResource(R.layout.spinner_dropdown);
        genderSpinner.setAdapter(genders);

        ArrayAdapter ageAdapter = ArrayAdapter.createFromResource(this
                ,R.array.ages,R.layout.color_spinner);
        ageAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
        ageSpinner.setAdapter(ageAdapter);

        mRegisterBtn = findViewById(R.id.RegisterBtn);
        AlreadyRegisterBtn = findViewById(R.id.LoginFromRegister);
        progressBar = findViewById(R.id.simpleProgressBar);


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

                //Added player to firebase
                gm.playerRegister(p,progressBar);

            }
        });


        AlreadyRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivities.LoginActivity(getApplicationContext());
//                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }
}