package com.ariel.teamball.Classes;

import java.util.Date;

public class Player {

    private String fullName;
    private String nickName;
    private String email;
    private String password;
    private String phone;
    private String city;
    private boolean GameStatus;
    private String gender;
    private String groupId;
    private String teamColor;
    private LocalDateTime RegisterDate;
    private String LoginStatus;

    /* Constructor */
    public Player(String _FullName, String  _nickname,String _Email, String _Password, String _Phone , String _city) {
        this.fullName = _FullName;
        this.nickName = _nickname;
        this.email = _Email;
        this.password = _Password;
        this.phone = _Phone;
        this.city = "";
        this.GameStatus = false;
        this.teamColor = "";
        this.groupId = "";
        this.gender = "";
        LoginStatus = "";
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

    public String getGroupId(){ return this.groupId;}

    public void setGroupId(String _GroupId){this.groupId = _GroupId;}

    public String getTeamColor(){return this.teamColor;}

    public void setTeamColor(String _TeamColor){this.teamColor = _TeamColor;}

    public void setGender(String _Gender){this.gender = _Gender;}

    public String getGender(){return this.gender;}

}
