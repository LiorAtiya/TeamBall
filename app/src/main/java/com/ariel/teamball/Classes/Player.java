package com.ariel.teamball.Classes;

import java.util.Date;

public class Player {

    private String firstName;
    private String LastName;
    private String NickName;
    private String email;
    private String password;
    private String phone;
    private String city;
    private boolean GameStatus;
    private String gender;
    private LocalDateTime RegisterDate;
    private String LoginStatus;
    private String GroupId;
    private String TeamColor;

    /* Constructor */
    public Player(String _FirstName, String _Email, String _Password, String _Phone , String _city) {
        this.firstName = _FirstName;
        this.email = _Email;
        this.password = _Password;
        this.phone = _Phone;
        this.city = "";
        GameStatus = false;
    }

    public Player() {}

    public void JoinGame() {

    }

    public void LeaveGame() {

    public boolean VerifyLogin(){

        return true;
    }
     public void GetLocation(){

     }
    public void AddFriend(){

    }
    public void CreateClan(){

    }
    public void AddToClan(){

    }
    public void ShareExternalLink(){

    }

    //--------Getters & Setters---------

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
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

    public String getCity(){return this.city;}

    public void setCity(String _city){ this.city = _city;}

}
