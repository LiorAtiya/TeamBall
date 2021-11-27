package com.ariel.teamball;

import java.sql.Time;
import java.util.ArrayList;

public class Group {
    int id;
    boolean status;
    String name;
    ArrayList<User> players;
    Admin admin;
    Time expiredTime;
    int capacity;
    ArrayList<ArrayList<User>> teams;
    String fieldLocation;
    Chat chat;

    /* Constructor */
    public Group(){}
}
