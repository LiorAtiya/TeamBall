package com.ariel.teamball.Classes.DAO;

import com.ariel.teamball.Classes.Admin;

import java.util.Set;

// Data Access Object class that synchronizes the Admin objects with the data base
public class AdminDAO implements UserDAO<Admin> {
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
