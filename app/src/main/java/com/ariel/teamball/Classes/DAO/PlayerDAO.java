package com.ariel.teamball.Classes.DAO;

import com.ariel.teamball.Classes.Player;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Set;


// Data Access Object class that synchronizes the Player objects with the data base
public class PlayerDAO implements UserDAO<Player>{
//    private DatabaseReference databaseReference;
//
//    public PlayerDAO() {
//        FirebaseDatabase db = FirebaseDatabase.getInstance();
//        databaseReference = db.getReference(Player.class.getSimpleName());
//    }
//
//
//    public void add(Player _player) {
//        databaseReference.push().setValue(_player);
//    }
//
//    public void update(String key, HashMap<String, Object> hashMap) {
//        databaseReference.child(key).updateChildren(hashMap);
//    }
//
//    public void remove(String key) {
//        databaseReference.child(key).removeValue();
//    }

    @Override
    public Player getUser() {
        return null;
    }

    @Override
    public Set<Player> getAllUsers() {
        return null;
    }

    @Override
    public Player getUserByUserEmailAndPassword() {
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
