package com.ariel.teamball.Classes;

import java.util.ArrayList;
import java.util.List;

public class Room {

    private boolean status; // lock/unlock
    private String name, field,city;
    private int capacity, imageID;
    private static int id = 1;
    private List adminsList; // this is a list of all the admins (IDs) associated with this room


//    private ArrayList<Player> playersList;
//    Time expiredTime;
//    ArrayList<Player> teams;
//    String fieldLocation;
//    Chat chat;
//    private ArrayList<Player> banList;

    public Room(String _name, int _capacity,String _field,String _city, int _adminID) {
        this.name = _name;
        this.capacity = _capacity;
        this.field = _field;
        this.city = _city;
        this.status = true;
        this.adminsList = new ArrayList<Integer>();
        this.adminsList.add(_adminID);
        this.id += 1;
    }

    // TODO: temporary instructor
    public Room(String _name, int _capacity,String _field,String _city) {
        this.name = _name;
        this.capacity = _capacity;
        this.field = _field;
        this.city = _city;
        this.status = true;
        this.adminsList = new ArrayList<Integer>();
        this.id += 1;
    }

    public Room() {}

    // sets/adds

    public void addAdmin(int adminID) {
        this.adminsList.add(adminID);
    }

    public List getAdminsList() {
        return this.adminsList;
    }

    // removes the admin permission to the given user, only if there is another admin in the room
    public void removeAdmin(int adminID) {
        if(this.adminsList.size() > 1) {
            this.adminsList.remove(adminID);
        }
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

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // updates the room's capacity
    public void setCapacity(int updateCapacity) {
        this.capacity = updateCapacity;
    }

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

    //gets

    public boolean isOpen() {
        return status;
    }

    public int getCapacity() {
        return capacity;
    }

//    // returns the amount of players in the group
//    public int getParticipants() {
//        return this.playersList.size();
//    }
}
