package com.ariel.teamball.View;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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

import com.ariel.teamball.Model.DAL.PlayerDAL;
import com.ariel.teamball.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class MyProfile extends AppCompatActivity {

    TextView fullName, email, phone,verifyMsg, myCity,myNickName,myGender,myAge;
    Button logoutBtn, resendCode,resetPassword,changeProfile;
    String userID;
    FirebaseUser user;
    ImageView profileImage;

    PlayerDAL playerDAL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        getSupportActionBar().hide();

        profileImage = findViewById(R.id.profileImage);
        changeProfile = findViewById(R.id.changeProfile);
        logoutBtn = findViewById(R.id.profileLogoutBtn);

        fullName = findViewById(R.id.ProfileName);
        email = findViewById(R.id.ProfileEmail);
        phone = findViewById(R.id.ProfilePhone);
        myCity = findViewById(R.id.City);
        myNickName = findViewById(R.id.MyNickName);
        myGender = findViewById(R.id.Gender);
        myAge = findViewById(R.id.age);

        resendCode = findViewById(R.id.resendCode);
        verifyMsg = findViewById(R.id.verifyMsg);
        resetPassword = findViewById(R.id.resetPasswordLocal);

        playerDAL = new PlayerDAL(this);

        //Access to profile picture of the player
        StorageReference profileRef = playerDAL.getStorage("users/"+ playerDAL.playerID()+"/profile.jpg");

        profileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).into(profileImage);
            }
        });


        //Player verify
        userID = playerDAL.playerID();
        user = playerDAL.getUser();

        if(!user.isEmailVerified()){
            resendCode.setVisibility(View.VISIBLE);
            verifyMsg.setVisibility(View.VISIBLE);

            resendCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(v.getContext(),"Verification Email Has Been Sent", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d("TAG","onFailure: Email not sent " + e.getMessage());
                        }
                    });
                }
            });
        }

        //Access from firebase to details of player
        DocumentReference documentReference = playerDAL.getCollection("users",userID);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                phone.setText(value.getString("phone"));
                fullName.setText(value.getString("fullName"));
                email.setText(value.getString("email"));
                myCity.setText(value.getString("city"));
                myGender.setText(value.getString("gender"));
                myNickName.setText(value.getString("nickName"));
                myAge.setText(value.getString("age"));
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText resetPassword = new EditText(v.getContext());
                final AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(v.getContext());
                passwordResetDialog.setTitle("Reset Password ?");
                passwordResetDialog.setMessage("Enter New Password");
                passwordResetDialog.setView(resetPassword);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        String newPassword = resetPassword.getText().toString();

                        playerDAL.updatePassword(newPassword);
                    }
                });

                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                passwordResetDialog.create().show();
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
                Intent i = new Intent(v.getContext(), EditProfile.class);
                i.putExtra("fName",fullName.getText().toString());
                i.putExtra("email",email.getText().toString());
                i.putExtra("phone",phone.getText().toString());
                i.putExtra("city",myCity.getText().toString());
                i.putExtra("gender",myGender.getText().toString());
                i.putExtra("nickName",myNickName.getText().toString());
                i.putExtra("age",myNickName.getText().toString());

                startActivity(i);
                finish();
            }
        });

    }

}