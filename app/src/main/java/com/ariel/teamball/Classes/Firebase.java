package com.ariel.teamball.Classes;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ariel.teamball.SportsMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Firebase {

    public static final String TAG = "TAG";
    private static FirebaseAuth fAuth;
    private static FirebaseFirestore fStore;
    private static Context context;
    private static DatabaseReference reference;

    public Firebase(Context context){
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        this.context = context;
    }

    public static void userSignOut(){
        FirebaseAuth.getInstance().signOut();
    }

    public static boolean userExist(){
        return fAuth.getCurrentUser() != null;
    }


    public static void userRegister(Player p){

        //Register the user in firebase
        fAuth.createUserWithEmailAndPassword(p.getEmail(),p.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //Send verification link
                    FirebaseUser fuser = fAuth.getCurrentUser();
                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context,"Verification Email Has Been Sent", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG,"onFailure: Email not sent " + e.getMessage());
                        }
                    });

                    Toast.makeText(context,"User Created", Toast.LENGTH_SHORT).show();

                    //Storing user information in firestore

                    //Create collection
//
//                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
//                    DatabaseReference ref = database.getReference();
//
//                    DatabaseReference usersRef = ref.child("Users");
//                    Map<String, Object> users = new HashMap<>();
//                    users.put(userID,p);
//
//                    usersRef.updateChildren(users);

                    String userID = fuser.getUid();
                    DocumentReference documentReference = fStore.collection("users").document(userID);

//                    Map<String, Object> user = new HashMap<>();
//                    user.put(userID, p);
//                    user.put("fName", p.getFirstName());
//                    user.put("email", p.getEmail());
//                    user.put("phone", p.getPhone());

                    //Store in the collection
                    documentReference.set(p).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG,"onSuccess: user Profile is created for "+userID);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG,"onFailure: "+e.toString());
                        }
                    });

                    Intent myIntent = new Intent(context, SportsMenu.class);
                    context.startActivity(myIntent);

                }else{
                    Toast.makeText(context,"Error! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void userLogin(String email,String password){

        //Authenticate the user
        fAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(context,"Logged in Successfully", Toast.LENGTH_SHORT).show();

                    Intent myIntent = new Intent(context, SportsMenu.class);
                    context.startActivity(myIntent);

                }else{
                    Toast.makeText(context,"Error! " + task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void resetPassword(String mail){

        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Reset Link Sent to Your Email",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error! Reset Link Is Not Sent " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



}
