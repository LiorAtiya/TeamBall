package com.ariel.teamball.Classes;

import java.util.HashMap;
import java.util.Map;

public class Room {

    private boolean status; // lock/unlock
    private String name, field,city;
    private int capacity, imageID;
    private int currentInRoom;
    private String admin;

    private String date;
    private String time;
    private Map<Integer, String> usersList; // list of IDs of all the group's users

//    Time expiredTime;
//    ArrayList<Player> teams;
//    String fieldLocation;
//    Chat chat;
//    private ArrayList<Player> banList;


    public Room(String _name, int _capacity,String _field,String _city, String _time, String _date, String _admin) {
        this.name = _name;
        this.capacity = _capacity;
        this.field = _field;
        this.city = _city;
        this.time = _time;
        this.date = _date;
        this.usersList = new HashMap<Integer, String>();
        this.status = true;
        this.admin = _admin;
        this.currentInRoom = 0;
//        this.adminsList = new ArrayList<Integer>();
//        this.adminsList.add(_adminID);
    }

    public Room() {}

    //-------------Getters & Setters---------------------


    public int getCurrentInRoom() {
        return currentInRoom;
    }

    public void setCurrentInRoom(int currentInRoom) {
        this.currentInRoom = currentInRoom;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

//    public  getUsersList() {
//        return usersList.toString();
//    }

//    public void setUsersList(List usersList) {
//        this.usersList = usersList;
//    }

    public void addUser(String userID) {
        this.usersList.put(currentInRoom, userID);
    }


}
