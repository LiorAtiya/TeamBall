package com.ariel.teamball.Classes;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Player {

    // player attributes
    private String fullName;
    //    private String firstName;
//    private String lastName;
    private String nickName;
    private String email;
    private String password;
    private String phone;
    private String city;
    private String gender;
    private List roomsList; // this is a list of all the rooms (IDs) in which the player is an admin
//    private int age;
    private static int id = 1;

    // admin attributes
    private String plan; // free/premium

    /* Constructor */
//    public Player(String _firstName, String _lastName, String _nickname, String _Email, String _Password, String _Phone , String _city, int _age) {
    public Player(String _fullName, String _nickname, String _Email, String _Password, String _Phone, String _city) {
//        this.firstName = _firstName;
//        this.lastName = _lastName;
        this.fullName = _fullName;
        this.nickName = _nickname;
        this.email = _Email;
        this.password = _Password;
        this.phone = _Phone;
        this.city = "";
        this.roomsList = new ArrayList<Integer>();
//        this.age = _age;
        this.id += 1;
    }

    public Player(String _FirstName, String _Email, String _Phone, String _group, String _Category) {
    }

    public Player() {
    }

    //========================================Player-Methods========================================

    public void addRoomToManage(int roomID) {
        this.roomsList.add(roomID);
    }

    public void removeRoomToManage(int roomID) {
        this.roomsList.remove(roomID);
    }

    public boolean isAdmin(int roomID) {
        return this.roomsList.contains(roomID);
    }

    public void JoinGame() {

    }

    public void LeaveGame() {

    }

    public boolean VerifyLogin() {

        return true;
    }

    public void GetLocation() {

    }

    public void AddFriend() {

    }

    public void CreateClan() {

    }

    public void AddToClan() {

    }

    public void ShareExternalLink() {

    }

    //--------Getters & Setters---------

    public int getId() {
        return this.id;
    }

//    public int getAge() {
//        return this.age;
//    }

    public String getFullName() {
        return this.fullName;
    }

    public String getFirstName() {
        return null;
    }

    public String getLastName() {
        return null;
    }

    public String getNickName() {
        return this.nickName;
    }

    public String getGender() {
        return this.gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String _city) {
        this.city = _city;
    }

    public void setGender(String _Gender) {
        this.gender = _Gender;
    }

    public void setAge(int age) {

    }

    public void setFirstName(String _firstName) {

    }

    public void setLastName(String _lastName) {

    }

    public void setNickName(String _nickName) {
        this.nickName = _nickName;
    }


    //========================================Admin-Methods========================================
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

    public String getPlan() {
        return this.plan;
    }

    public void setPlan(String _plan) {
        this.plan = _plan;
    }

}
