package com.ariel.teamball.Classes;

public class Admin implements User {

    // player attributes
    private String fullName;
    private String email;
    private String phone;
    private static int id = 1;

//    private String nickName;
//    private String password;
//    private String city;
//    private String gender;

    // admin attributes
    private String plan; // free/premium
    private String roomName;
    private String categoryGroup;


    public Admin(String _fullName, String _email, String _phone, String _roomName,String _category) {
        this.fullName = _fullName;
        this.email = _email;
        this.phone = _phone;
        this.roomName = _roomName;
        this.categoryGroup = _category;
        this.id += id;
    }

    //======================================Player-Methods==========================================
    @Override
    public void JoinGame() {

    }

    @Override
    public void LeaveGame() {

    }

    @Override
    public boolean VerifyLogin() {
        return false;
    }

    @Override
    public void GetLocation() {

    }

    @Override
    public void AddFriend() {

    }

    @Override
    public void CreateClan() {

    }

    @Override
    public void AddToClan() {

    }

    @Override
    public void ShareExternalLink() {

    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getFullName() {
        return null;
    }

    @Override
    public void setFullName(String _fullName) {

    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public void setEmail(String email) {

    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public void setPassword(String password) {

    }

    @Override
    public String getPhone() {
        return null;
    }

    @Override
    public void setPhone(String phone) {

    }

    @Override
    public String getCity() {
        return null;
    }

    @Override
    public void setCity(String _city) {

    }

    @Override
    public void setGender(String _gender) {

    }

    @Override
    public String getGender() {
        return null;
    }

    //======================================Admin-Methods==========================================

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
}