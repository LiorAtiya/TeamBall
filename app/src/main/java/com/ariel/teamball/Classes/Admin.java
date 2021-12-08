package com.ariel.teamball.Classes;

public class Admin extends Player{

    // player attributes
    private String fullName;
    private String email;
    private String phone;
//    private static int id = 1;

//    private String nickName;
//    private String password;
//    private String city;
//    private String gender;

    // admin attributes
    private String plan; // free/premium
    private String roomName;
    private int roomID;
    private String categoryGroup;

    // TODO: have to split full name to first and last
    public Admin(String _fullName, String _email, String _phone, String _roomName,String _category) {
        super(_fullName, _email, _phone, _roomName, _category);
        this.fullName = _fullName;
        this.email = _email;
        this.phone = _phone;
        this.roomName = _roomName;
        this.categoryGroup = _category;
//        this.id += id;
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

//    public int getRoomID() {
//        return this.roomID;
//    }

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