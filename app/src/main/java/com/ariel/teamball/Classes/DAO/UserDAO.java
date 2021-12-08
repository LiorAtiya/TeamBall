package com.ariel.teamball.Classes.DAO;

import java.util.Set;

public interface UserDAO<T> {
    T getUser();
    Set<T> getAllUsers();
    T getUserByUserEmailAndPassword();
    boolean insertUser();
    boolean updateUser();
    boolean deleteUser();
}
