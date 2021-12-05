package com.ariel.teamball.Classes;

import java.security.acl.Group;

public class Admin extends Player {

    private String plan;
    private Group group;

    /* Constructor */
    public Admin(String _FullName,String _nickName, String _Email, String _Password, String _Phone, String _city) {
        super(_FullName,_nickName, _Email, _Password, _Phone, _city);
    }

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

    public String getPlan() {
        return this.plan;
    }

    public Group getGroup() {
        return this.group;
    }

    public void setPlan(String _plan) {
        this.plan = _plan;
    }

    public void setGroup(Group _group) {
        this.group = _group;
    }

    /* For those who pay an extension */
    public void extendTime() {
    }
}
