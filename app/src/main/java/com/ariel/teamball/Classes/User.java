package com.ariel.teamball.Classes;

import java.util.Date;

abstract class User {
    private String FirstName;
    private String LastName;
    private String NickName;
    private String Email;
    private String Password;
    private String Gender;
    private Date RegisterDate;
    private String LoginStatus;
    private int GroupId;
    private String TeamColor;
    private String Location;

     abstract boolean VerifyLogin();
     abstract void GetLocation();
     abstract void AddFriend();
     abstract void CreateClan();
     abstract void AddToClan();
     abstract void ShareExternalLink();


 }
