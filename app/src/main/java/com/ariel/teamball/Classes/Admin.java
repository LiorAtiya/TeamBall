package com.ariel.teamball.Classes;

public class Admin {

    // player attributes
    private String fullName;
    private String nickName;
    private String email;
    private String password;
    private String phone;
    private String city;
    private String gender;
    private static int id = 1;

    // admin attributes
    private String plan; // free/premium
    private String roomName;
    private int roomID;
    private String categoryGroup;


    public Admin(String _fullName, String _Email, String _Phone, String _group,String _Category) {
        this.categoryGroup = _Category;
        this.roomName = _group;
        this.id += id;
    }


    public void deleteGroup() {
    }

    public void addUser() {
    }

    public void removeUser() {
    }

    public void editGroupName() {
    }

    public void editCapacity() {
    }

    public void editLevel() {
    }

    public void splitTeams() {
    }

    public void lockGroup() {
    }

    public void changeChatPermission() {
    }

    /* Get and Set */

    public int getRoomID() {
        return this.roomID;
    }

    public String getCategoryGroup() {
        return categoryGroup;
    }

    public void setCategoryGroup(String categoryGroup) {
        this.categoryGroup = categoryGroup;
    }

    public String getPlan() {
        return this.plan;
    }

    public void setPlan(String _plan) {
        this.plan = _plan;
    }

    public void setRoomID(int id) {
        this.roomID = id;
    }
}