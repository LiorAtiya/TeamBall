package com.ariel.teamball.Classes.DAO;

import android.content.Context;

import com.ariel.teamball.Classes.Admin;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Set;

// Data Access Object class that synchronizes the Admin objects with the data base
public class AdminDAO implements UserDAO<Admin> {


    public static final String TAG = "TAG";
    private static FirebaseAuth fAuth;
    private static FirebaseFirestore fStore;
    private static Context context;
    private static DatabaseReference reference;

    public AdminDAO(Context context){
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        this.context = context;
    }


    @Override
    public Admin getUser() {
        return null;
    }

    @Override
    public Set<Admin> getAllUsers() {
        return null;
    }

    @Override
    public Admin getUserByUserEmailAndPassword() {
        return null;
    }

    @Override
    public boolean insertUser() {
        return false;
    }

    @Override
    public boolean updateUser() {
        return false;
    }

    @Override
    public boolean deleteUser() {
        return false;
    }
}
