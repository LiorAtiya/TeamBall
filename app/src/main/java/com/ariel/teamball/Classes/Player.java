package com.ariel.teamball.Classes;

import java.util.Date;
import java.util.HashMap;
import java.util.Set;

public class Player {

    private String fullName;
    private String nickName;
    private String email;
    private String password;
    private String phone;
    private String city;
    private String gender;

    /* Constructor */
    public Player(String _FullName, String  _nickname,String _Email, String _Password, String _Phone , String _city) {
        this.fullName = _FullName;
        this.nickName = _nickname;
        this.email = _Email;
        this.password = _Password;
        this.phone = _Phone;
        this.city = "";
    }

    public Player(String _FirstName, String _Email, String _Phone, String _group, String _Category) {}

    public Player() {}

    public void JoinGame() {

    }

    public void LeaveGame() {

    }

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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String _FullName) {
        this.fullName = _FullName;
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

    public void setGender(String _Gender){this.gender = _Gender;}

    public String getGender(){return this.gender;}

}
