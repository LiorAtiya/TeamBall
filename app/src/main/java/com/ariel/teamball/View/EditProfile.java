package com.ariel.teamball.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ariel.teamball.Model.DAL.PlayerDAL;
import com.ariel.teamball.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class EditProfile extends AppCompatActivity {

    EditText profileFullName,profilePhone,profileNickname,profileCity,profileGender,profileAge;
    ImageView profileImageView;
    Button saveBtn;

    PlayerDAL playerDAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        getSupportActionBar().hide();

        Intent data = getIntent();
        String fullName = data.getStringExtra("fName");
        String nickname = data.getStringExtra("nickName");
        String gender = data.getStringExtra("gender");
        String phone = data.getStringExtra("phone");
        String age = data.getStringExtra("age");
        String city = data.getStringExtra("city");
        //        String email = data.getStringExtra("email");


        profilePhone = findViewById(R.id.editProfilePhone);
        profileFullName = findViewById(R.id.editProfileName);
        profileImageView = findViewById(R.id.editProfileImage);
        profileNickname = findViewById(R.id.editProfileNickname);
        profileGender = findViewById(R.id.editProfileGender);
        profileAge = findViewById(R.id.editProfileAge);

//        profileCity = findViewById(R.id.editProfileCity);

        saveBtn = findViewById(R.id.saveBtn);

        playerDAL = new PlayerDAL(this);

        //Access to profile picture of the player
        playerDAL.setProfilePicture(profileImageView,playerDAL.getPlayerID());


        //Edit profile picture of the player
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent,1000);
            }
        });

        profileFullName.setText(fullName);
        profileNickname.setText(nickname);
        profilePhone.setText(phone);
        profileAge.setText(age);
        profileGender.setText(gender);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check empty editText
                if(profilePhone.getText().toString().isEmpty() || profileFullName.getText().toString().isEmpty()
                   || profileNickname.getText().toString().isEmpty() || profileAge.getText().toString().isEmpty()
                    || profileAge.getText().toString().isEmpty()){
                    Toast.makeText(EditProfile.this,"One or many fields are empty",Toast.LENGTH_SHORT).show();
                    return;
                }

//                final String email = profileEmail.getText().toString();
                String fullName = profileFullName.getText().toString();
                String nickname = profileNickname.getText().toString();
                String phone = profilePhone.getText().toString();
                String age = profileAge.getText().toString();
                String gender = profileGender.getText().toString();

                playerDAL.editPlayerDetails(fullName,nickname,phone,age,gender);

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri imageUri = data.getData();

                try {
                    playerDAL.uploadImage(imageUri,profileImageView);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}