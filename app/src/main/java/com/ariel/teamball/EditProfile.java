package com.ariel.teamball;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class EditProfile extends AppCompatActivity {

    public static final String TAG = "TAG";
    EditText profileFullName,profileEmail,profilePhone;
    ImageView profileImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent data = getIntent();
        String fullName = data.getStringExtra("fullName");
        String email = data.getStringExtra("email");
        String phone = data.getStringExtra("phone");

        profileEmail = findViewById(R.id.editProfileEmail);
        profilePhone = findViewById(R.id.editProfilePhone);
        profileFullName = findViewById(R.id.editProfileName);
        profileImageView = findViewById(R.id.editProfileImage);

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(EditProfile.this,"Profile Image Clicked",Toast.LENGTH_SHORT).show();
            }
        });

        profileEmail.setText(email);
        profileFullName.setText(fullName);
        profilePhone.setText(phone);
    }
}