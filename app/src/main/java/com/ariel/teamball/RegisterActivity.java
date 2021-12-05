package com.ariel.teamball;

import androidx.appcompat.app.AppCompatActivity;

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

import com.ariel.teamball.Classes.Firebase;
import com.ariel.teamball.Classes.Player;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    TextInputEditText mFullName, mEmail, mPassword, mPhone,mNickName;
    Button mRegisterBtn;
    TextView mLoginBtn;
    ProgressBar progressBar;

//    FirebaseAuth fAuth;
//    FirebaseFirestore fStore;
//    String userID;

    Firebase FB;

    @Override
    public void onBackPressed() {
        Toast.makeText(this,"Don't go out :(", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        //Link to layout
        mFullName = findViewById(R.id.FullName);
        mNickName = findViewById(R.id.nickName);
        mEmail = findViewById(R.id.Email);
        mPassword = findViewById(R.id.Password);
        mPhone = findViewById(R.id.Phone);
       // mCity = findViewById(R.id.city);
        mRegisterBtn = findViewById(R.id.RegisterBtn);
        mLoginBtn = findViewById(R.id.LoginFromRegister);
        progressBar = findViewById(R.id.simpleProgressBar);


        FB = new Firebase(this);

//        fAuth = FirebaseAuth.getInstance();
//        fStore = FirebaseFirestore.getInstance();
//
//        //Automatic login - If the user is registered
//        if(fAuth.getCurrentUser() != null){
//            startActivity(new Intent(getApplicationContext(),GameOptions.class));
//            finish();
//        }


        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmail.getText().toString().trim();
                String password = mPassword.getText().toString().trim();
                String fullName = mFullName.getText().toString();
                String phone = mPhone.getText().toString();
                String nickName = mNickName.getText().toString();

                //Create new player
                Player p1 = new Player(fullName,nickName,email,password, phone," ");

                //Character insertion check
                if(TextUtils.isEmpty(p1.getEmail())){
                    mEmail.setError("Email is Required.");
                    return;
                }

                if(TextUtils.isEmpty(p1.getPassword())){
                    mPassword.setError("Password is Required.");
                    return;
                }

                //Charging circle while waiting to connect
                progressBar.setVisibility(View.VISIBLE);

                FB.userRegister(p1);

                progressBar.setVisibility(View.GONE);



//                //Register the user in firebase
//                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()){
//                            //Send verification link
//                            FirebaseUser fuser = fAuth.getCurrentUser();
//                            fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    Toast.makeText(RegisterActivity.this,"Verification Email Has Been Sent", Toast.LENGTH_SHORT).show();
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.d(TAG,"onFailure: Email not sent " + e.getMessage());
//                                }
//                            });
//
//                            Toast.makeText(RegisterActivity.this,"User Created", Toast.LENGTH_SHORT).show();
//
//                            //Storing user information in firestore
//                            userID = fAuth.getCurrentUser().getUid();
//                            DocumentReference documentReference = fStore.collection("users").document(userID);
//                            Map<String, Object> user = new HashMap<>();
//                            user.put("fName", fullName);
//                            user.put("email", email);
//                            user.put("phone", phone);
//
//                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
//                                @Override
//                                public void onSuccess(Void unused) {
//                                    Log.d(TAG,"onSuccess: user Profile is created for "+userID);
//                                }
//                            }).addOnFailureListener(new OnFailureListener() {
//                                @Override
//                                public void onFailure(@NonNull Exception e) {
//                                    Log.d(TAG,"onFailure: "+e.toString());
//                                }
//                            });
//
//                            startActivity(new Intent(getApplicationContext(),GameOptions.class));
//
//                        }else{
//                            Toast.makeText(RegisterActivity.this,"Error! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
//                            progressBar.setVisibility(View.GONE);
//                        }
//                    }
//                });

            }
        });



        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

        /* For Choose City */
        Spinner mySpinner = (Spinner) findViewById(R.id.citySpinner);
        /* store and connect our cities names with spinner */
        ArrayAdapter<String> allCities = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.names));
        //for drop down list:
        allCities.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(allCities);

        /* For Choose gender */
        Spinner genderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        /* store and connect our cities names with spinner */
        ArrayAdapter<String> genders = new ArrayAdapter<String>(RegisterActivity.this,
                android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.genders));
        //for drop down list:
        genders.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        genderSpinner.setAdapter(genders);
    }
    /* check the detail that user insert */
    public void checkValidDetails(Player p){
        //Character insertion check
        if(TextUtils.isEmpty(p.getEmail())){
            mEmail.setError("Email is Required.");
            return;
        }

        if(TextUtils.isEmpty(p.getPassword())){
            mPassword.setError("Password is Required.");
            return;
        }

        /*!!!!!!!!!!!!!!!!! phone error doesn't work !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!*/
//        if(TextUtils.isEmpty(p.getPhone())){
//            mPhone.setError("Phone is Required");
//        }
    }

}