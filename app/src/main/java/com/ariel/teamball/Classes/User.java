package com.ariel.teamball.Classes;

public interface User {

    public void JoinGame();

    public void LeaveGame();

    public boolean VerifyLogin();

    public void GetLocation();

    public void AddFriend();

    public void CreateClan();

    public void AddToClan();

    public void ShareExternalLink();


    //--------Getters & Setters---------

    public int getId();

    public String getFullName();

    public void setFullName(String _fullName);

    public String getEmail();

    public void setEmail(String email);

    public String getPassword();

    public void setPassword(String password);

    public String getPhone();

    public void setPhone(String phone);

    public String getCity();

    public void setCity(String _city);

    public void setGender(String _gender);

    public String getGender();
}
