package com.ariel.teamball.Classes;

import java.util.ArrayList;
import java.util.List;


public class Room {

    private boolean status; // lock/unlock
    private String name, field,city;
    private int capacity, imageID;
    private String admin;
    private String date;
    private String time;
    private List usersList; // list of IDs of all the group's users
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
        this.usersList = new ArrayList<String>();
        this.status = true;
        this.admin = _admin;

//        this.adminsList = new ArrayList<Integer>();
//        this.adminsList.add(_adminID);
    }

    public Room() {}

    //-------------Getters & Setters---------------------


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void addUser(String userID) {
        this.usersList.add(userID);
    }

    public String getDate() {
        return this.date;
    }

    public String getTime() {
        return this.time;
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

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getAdmin() {
        return admin;
    }


//    public void addAdmin(int adminID) {
//        this.adminsList.add(adminID);
//    }
//
//    public List getAdminsList() {
//        return this.adminsList;
//    }
//
//    // removes the admin permission to the given user, only if there is another admin in the room
//    public void removeAdmin(int adminID) {
//        if(this.adminsList.size() > 1) {
//            this.adminsList.remove(adminID);
//        }
//    }

//    // adds a player to the group if it is not full and if the player did not get ban
//    public boolean addPlayer(Player _player) {
//        boolean add = false;
//        if(this.playersList.size() < this.capacity && !banList.contains(_player)) {
//            playersList.add(_player);
//            add = true;
//        }
//        return add;
//    }

//    public ArrayList<Player> getPlayers() {
//        return players;
//    }
//
//    public void setPlayers(ArrayList<Player> players) {
//        this.players = players;
//    }


//    public boolean isOpen() {
//        return status;
//    }


//    // returns the amount of players in the group
//    public int getParticipants() {
//        return this.playersList.size();
//    }
}
