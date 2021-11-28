package com.ariel.teamball.Classes;

import com.ariel.teamball.Classes.User;

import java.security.acl.Group;
public class Admin extends User {

    String Plan;
    Group group;

    /* Constructor */
    public Admin(){}

    public void deleteGroup(){}

    public void addUser(){}

    public void removeUser(){}

    public void editGroupName(){}

    public void editCapacity(){}

    public void editLevel(){}

    public void splitTeams(){}

    public void lockGroup(){}

    public void changeChatPermission(){}

    /* For those who pay an extension */
    public void extendTime(){}
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
}
