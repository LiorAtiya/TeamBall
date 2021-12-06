package com.ariel.teamball.Classes;

import java.util.ArrayList;

public class Room {

    private boolean status; // lock/unlock
    private String name;
    private int capacity;
//    private ArrayList<Player> playersList;
    private static int id = 1;
    private Admin admin;
    //    Time expiredTime;
//    ArrayList<Player> teams;
//    String fieldLocation;
//    Chat chat;
//    private ArrayList<Player> banList;

    /* Constructor */
    public Room(String _name, int _capacity, Admin _admin) {
        this.name = _name;
        this.capacity = _capacity;
        this.status = true;
        this.admin = _admin;
//        playersList = new ArrayList<>();
//        banList = new ArrayList<>();
        this.id += 1;
//        players.add(new Player("a","b","c", "d"," "));
    }

    // sets/adds

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
