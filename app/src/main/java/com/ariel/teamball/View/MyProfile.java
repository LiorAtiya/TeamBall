package com.ariel.teamball.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ariel.teamball.Controller.SwitchActivities;
import com.ariel.teamball.Model.DAL.PlayerDAL;
import com.ariel.teamball.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MyProfile extends AppCompatActivity {

    TextView fullName, email, phone,verifyMsg, myCity,myNickName,myGender,myAge;
    Button logoutBtn, verifyNowBtn,resetPassword,changeProfile;
    String userID;
    FirebaseUser user;
    ImageView profileImage;

    PlayerDAL playerDAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getSupportActionBar().hide();

        changeProfile = findViewById(R.id.changeProfile);
        logoutBtn = findViewById(R.id.profileLogoutBtn);
        profileImage = findViewById(R.id.profileImage);

        myNickName = findViewById(R.id.MyNickName);
        fullName = findViewById(R.id.ProfileName);
        myAge = findViewById(R.id.age);
        email = findViewById(R.id.ProfileEmail);
        myGender = findViewById(R.id.Gender);
        phone = findViewById(R.id.ProfilePhone);
        myCity = findViewById(R.id.City);

        verifyNowBtn = findViewById(R.id.verifyBtn);
        verifyMsg = findViewById(R.id.verifyMsg);
        resetPassword = findViewById(R.id.resetPasswordLocal);

        playerDAL = new PlayerDAL(this);

        //Access to profile picture of the player
        playerDAL.setProfilePicture(profileImage,playerDAL.getPlayerID());


        //Player verify
        user = playerDAL.getUser();
        if(!user.isEmailVerified()){
            verifyNowBtn.setVisibility(View.VISIBLE);
            verifyMsg.setVisibility(View.VISIBLE);

            verifyNowBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    playerDAL.verifyEmail(v);
                }
            });
        }

        //Access from firebase to details of player
        userID = playerDAL.getPlayerID();
        DocumentReference documentReference = playerDAL.getCollection("users",userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                myNickName.setText(value.getString("nickName"));
                fullName.setText(value.getString("fullName"));
                myAge.setText(value.getString("age"));
                email.setText(value.getString("email"));
                myGender.setText(value.getString("gender"));
                phone.setText(value.getString("phone"));
                myCity.setText(value.getString("city"));
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playerDAL.updatePassword(v);
            }
        });

//        profileImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
////                startActivityForResult(openGalleryIntent,1000);
//            }
//        });

        changeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SwitchActivities.EditProfile(v.getContext(),fullName,email, phone, myCity, myGender, myNickName, myAge);
                finish();
            }
        });

    }

}