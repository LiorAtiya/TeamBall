package com.ariel.teamball.Classes;

import com.ariel.teamball.Classes.Chat;

import java.sql.Time;
import java.util.ArrayList;

public class Group {

    boolean status;
    String name;
    int capacity;
    ArrayList<Player> players;
//    int id;
    Admin admin;
//    Time expiredTime;
//    ArrayList<Player> teams;
//    String fieldLocation;
//    Chat chat;

    /* Constructor */
    public Group(String _name, int _capacity){
        this.name = _name;
        this.capacity = _capacity;
        status = true;
        players = new ArrayList<>();
        players.add(new Player("a","b","c", "d","1"," "));
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }
}
