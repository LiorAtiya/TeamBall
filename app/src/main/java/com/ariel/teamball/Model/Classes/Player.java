package com.ariel.teamball.Model.Classes;

public class Player {

    private String fullName;
    private String nickName;
    private String email;
    private String password;
    private String phone;
    private String city;
    private String gender;
    private String age;
    private static int id = 1;

    /* Constructor */
    public Player(String _fullName, String _nickname, String _Email,
                  String _Password, String _Phone, String _city,String _gender, String _age) {
        this.fullName = _fullName;
        this.nickName = _nickname;
        this.email = _Email;
        this.password = _Password;
        this.phone = _Phone;
        this.city = _city;
        this.age = _age;
        this.gender = _gender;
        this.id += 1;
    }

    public Player() {
    }

    //========================================Player-Methods========================================

//    public void addRoomToManage(int roomID) {
//        this.roomsList.add(roomID);
//    }
//
//    public void removeRoomToManage(int roomID) {
//        this.roomsList.remove(roomID);
//    }
//
//    public boolean isAdmin(int roomID) {
//        return this.roomsList.contains(roomID);
//    }

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


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

//    public List getRoomsList() {
//        return roomsList;
//    }
//
//    public void setRoomsList(List roomsList) {
//        this.roomsList = roomsList;
//    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public static int getId() {
        return id;
    }

    public static void setId(int id) {
        Player.id = id;
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

}
