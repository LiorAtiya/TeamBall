package com.ariel.teamball.Model.Classes;

import java.util.HashMap;
import java.util.Map;


public class Room {

    private boolean isFull; // lock/unlock
    private String name, field,city;
    private int capacity, imageID;
    private String admin;
    private String roomID;
    private String dayGame;
    private String startGame;
    private Map<String,String> usersList; // list of IDs of all the group's users
    private int numOfPlayers;
    private String category;

//    Time expiredTime;
//    ArrayList<Player> teams;
//    String fieldLocation;
//    Chat chat;
//    private ArrayList<Player> banList;


    public Room(String _name, int _capacity,String _field,String _city, String _startGame, String _dayGame, String _admin,String _category) {
        this.name = _name;
        this.capacity = _capacity;
        this.field = _field;
        this.city = _city;
        this.startGame = _startGame;
        this.dayGame = _dayGame;
        this.usersList = new HashMap<>();
        this.isFull = false;
        this.admin = _admin;
        this.numOfPlayers = 0;
        this.category = _category;
    }

    public Room() {}


    //-------------Getters & Setters---------------------


    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public Map<String, String> getUsersList() {
        return usersList;
    }

    public void setUsersList(Map<String, String> usersList) {
        this.usersList = usersList;
    }

    public int getNumOfPlayers() {
        return numOfPlayers;
    }

    public void setNumOfPlayers(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;

    }

    public boolean isFull() {
        return isFull;
    }

    public void setFull(boolean full) {
        this.isFull = full;
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

    public String getDayGame() {
        return dayGame;
    }

    public void setDayGame(String dayGame) {
        this.dayGame = dayGame;
    }

    public String getTime() {
        return startGame;
    }

    public void setTime(String time) {
        this.startGame = time;
    }

}
