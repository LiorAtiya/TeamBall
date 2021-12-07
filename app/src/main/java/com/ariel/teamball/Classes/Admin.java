package com.ariel.teamball.Classes;

public class Admin implements User {

    // player attributes
    private String fullName;
    private String nickName;
    private String email;
    private String password;
    private String phone;
    private String city;
    private String gender;
    private static int id = 1;

    // admin attributes
    private String plan; // free/premium
    private String roomName;
    private String categoryGroup;


    public Admin(String _fullName, String _Email, String _Phone, String _group,String _Category) {
        this.categoryGroup = _Category;
        this.roomName = _group;
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

    public int getRoomID() {
        return this.getId();
    }

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