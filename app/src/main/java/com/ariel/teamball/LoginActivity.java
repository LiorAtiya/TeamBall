package com.ariel.teamball;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void onBtnClick(View view) {
        TextView txtFirstName = findViewById(R.id.textViewFirstName);
        TextView txtLastName = findViewById(R.id.textViewLastName);
        TextView txtEmail = findViewById(R.id.textViewEmail);


        EditText editTxtFirst = findViewById(R.id.editTextFirstName);
        EditText editTxtLast = findViewById(R.id.editTextLastName);
        EditText editTxtEmail = findViewById(R.id.editTextTextEmailAddress);


        txtFirstName.setText("First Name: " + editTxtFirst.getText().toString());
        txtLastName.setText("Last Name: " + editTxtLast.getText().toString());
        txtEmail.setText("Email: " + editTxtEmail.getText().toString());
    }
}