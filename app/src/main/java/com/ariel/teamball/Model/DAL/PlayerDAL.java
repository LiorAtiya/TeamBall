package com.ariel.teamball.Model.DAL;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.ariel.teamball.Controller.SwitchActivities;
import com.ariel.teamball.Model.Classes.Player;
import com.ariel.teamball.View.MyProfile;
import com.ariel.teamball.View.SportsMenu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/*
Data Access Object class that synchronizes the Player objects with the database.
The methods in that class add, update and remove the data from the userRooms table in the database.
 */
public class PlayerDAL {

    public static final String TAG = "TAG";
    private static FirebaseAuth fAuth; // access Authentication
    private static FirebaseFirestore fStore; // access Firesotre Database
    private static Context context; // the current activity
    private static StorageReference storageReference; // access Realtime Database
    private static FirebaseUser user; // access the user who logged in
    private static String value; //Take value from details of player

    public PlayerDAL(Context context) {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        user = fAuth.getCurrentUser();

        this.context = context;
    }

    public PlayerDAL() {
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        user = fAuth.getCurrentUser();
    }

    public static String getValueFromPlayer(String key) {
        //Access to user collection to take my name
        DocumentReference docRef = getCollection("users", playerID());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    value = document.getString(key);
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
        return value;
    }

    public static FirebaseUser getUser() {
        return user;
    }

    public static void setUser(FirebaseUser user) {
        PlayerDAL.user = user;
    }

    // Add room to list of private rooms user
    public static void addRoom(String category, String roomKey) {

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference();

        DatabaseReference usersRef = ref.child("userRooms/" + playerID() + "/" + category);
        Map<String, Object> groups = new HashMap<>();
        groups.put(roomKey, roomKey);

        usersRef.updateChildren(groups);
    }

    // The function gets the key and the category of the room and removes the room from each of the users
//    public void removeRoomFromUserRooms(String roomKey, String category) {
//        DatabaseReference userRoomsReference = getPathReference("UserRooms/");
//        userRoomsReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                // iterates over the userRooms
//                Iterator i = dataSnapshot.getChildren().iterator();
//                while (i.hasNext()) {
//                    DataSnapshot childSnapshot = (DataSnapshot) i.next();
//                    // checks if its the correct room's category
//                    if(childSnapshot.child(category).equals(category)) {
//                        userRoomsReference.child(roomKey).removeValue();
//                        childSnapshot.child(category).child(roomKey);
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
//    }

    /*
  The function gets the key, the category of the room and a userID and removes
  the room from the given user in the userRooms table in the DB
   */
    public void removeRoomFromUserRooms(String roomKey, String category, String userID) {
        DatabaseReference userRoomsReference = getPathReference("userRooms/");
        userRoomsReference.child(userID).child(category).child(roomKey).removeValue();

    }

    /*
    The function gets the key and the category of the room and removes
    the room from each of the users in the userRooms table in the DB
     */
    public void removeRoomFromUserRooms(String roomKey, String category) {
        DatabaseReference userRoomsReference = getPathReference("userRooms/");
        ValueEventListener valueEventListener = userRoomsReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("TAG", "enter to onDataChange");
                // iterates over all the users
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()) {
                    DataSnapshot childSnapshot = (DataSnapshot) i.next(); // current user
                    String userID = childSnapshot.getKey();
                    Log.d("TAG", "userID: " + userID);
                    userRoomsReference.child(userID).child(category).child(roomKey).removeValue();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public static DatabaseReference getPathReference(String path) {
        return FirebaseDatabase.getInstance().getReference(path);
    }

    public static String playerID() {
        return fAuth.getCurrentUser().getUid();
    }

    public static DocumentReference getCollection(String nameCollection, String playerID) {
        return fStore.collection(nameCollection).document(playerID);
    }

    public static StorageReference getStorage(String path) {
        return storageReference.child(path);
    }

    public static void playerSignOut() {
        FirebaseAuth.getInstance().signOut();
    }

    public static boolean playerConnected() {
        return fAuth.getCurrentUser() != null;
    }

    public static void playerRegister(Player p, ProgressBar pb) {

        //Register the user in firebase
        fAuth.createUserWithEmailAndPassword(p.getEmail(), p.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser fuser = fAuth.getCurrentUser();
                    //Send verification link
                    fuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(context, "Verification Email Has Been Sent", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: The email could not be sent " + e.getMessage());
                        }
                    });

                    Toast.makeText(context, "User Created Successfully", Toast.LENGTH_SHORT).show();


                    //Create collection
                    DocumentReference documentReference = fStore.collection("users").document(playerID());

                    //Store player in the collection
                    documentReference.set(p).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Log.d(TAG, "onSuccess: user Profile is created for " + playerID());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.d(TAG, "onFailure: " + e.toString());
                        }
                    });

                    //Go to a SportsMenu page
                    SwitchActivities.SportMenu(context);

//                    Intent myIntent = new Intent(context, SportsMenu.class);
//                    context.startActivity(myIntent);

                    pb.setVisibility(View.GONE);

                } else {
                    Toast.makeText(context, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void playerLogin(String email, String password, ProgressBar pb) {

        //Authenticate the user
        fAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "Logged in Successfully", Toast.LENGTH_SHORT).show();

                    Intent myIntent = new Intent(context, SportsMenu.class);
                    context.startActivity(myIntent);

                    pb.setVisibility(View.GONE);

                } else {
                    Toast.makeText(context, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public static void resetPassword(String mail) {

        fAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Reset Link Sent to Your Email", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Error! Reset Link Was Not Sent " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void updatePassword(String newPassword) {

        user.updatePassword(newPassword).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Password Updated Successfully", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Password Reset Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void editPlayerDetails(String fullName,String nickname, String phone,String age,String gender) {

        DocumentReference docRef = fStore.collection("users").document(user.getUid());
        Map<String, Object> edited = new HashMap<>();
        edited.put("fullName", fullName);
        edited.put("nickName", nickname);
        edited.put("phone", phone);
        edited.put("age", age);
        edited.put("gender", gender);

        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show();
                context.startActivity(new Intent(context, MyProfile.class));

            }
        });

//        user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
//            @Override
//            public void onSuccess(Void unused) {
//
//                DocumentReference docRef = fStore.collection("users").document(user.getUid());
//                Map<String, Object> edited = new HashMap<>();
//                edited.put("email", email);
//                edited.put("fullName", fullName);
//                edited.put("phone", phone);
//                docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(context, "Profile Updated", Toast.LENGTH_SHORT).show();
//                        context.startActivity(new Intent(context, MyProfile.class));
//
//                    }
//                });
//                Toast.makeText(context, "Email is changed", Toast.LENGTH_SHORT).show();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    //Upload image to firebase storage
    public static void uploadImage(Uri imageUri, ImageView profileImageView) {
        StorageReference fileRef = getStorage("users/" + playerID() + "/profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(context, "Image Uploaded", Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.get().load(uri).into(profileImageView);
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
