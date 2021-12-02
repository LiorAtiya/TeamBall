package com.ariel.teamball.Classes;

public class Player extends User {

    private String firstName;
    private String email;
    private String password;
    private String phone;

    boolean GameStatus;

    /* Constructor */
    public Player(String _FirstName, String _Email, String _Password, String _Phone) {
        this.firstName = _FirstName;
        this.email = _Email;
        this.password = _Password;
        this.phone = _Phone;

        GameStatus = false;
    }

    public void JoinGame() {
    }

    public void LeaveGame() {
    }

    @Override
    boolean VerifyLogin() {
        return false;
    }

    @Override
    void GetLocation() {

    }

    @Override
    void AddFriend() {

    }

    @Override
    void CreateClan() {

    }

    @Override
    void AddToClan() {

    }

    @Override
    void ShareExternalLink() {

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

}
