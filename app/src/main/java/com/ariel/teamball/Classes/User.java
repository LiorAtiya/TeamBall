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

    public int getAge();

    public String getFirstName();

    public String getLastName();

    public String getNickName();

    public String getPhone();

    public String getEmail();

    public String getPassword();

    public String getCity();

    public String getGender();

    public void setAge(int age);

    public void setFirstName(String _firstName);

    public void setLastName(String _lastName);

    public void setNickName(String _nickName);

    public void setPhone(String phone);

    public void setEmail(String email);

    public void setPassword(String password);

    public void setCity(String _city);

    public void setGender(String _gender);

}
