package com.ariel.teamball.Classes;

public class Admin extends Player {

    private String plan;
    private String group_name;
    private String category_group;

    public Admin(String _FirstName, String _Email, String _Phone, String _group,String _Category) {
        super(_FirstName, _Email, _Phone, _group, _Category);

        this.category_group = _Category;
        this.group_name = _group;
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

    public String getCategory_group() {
        return category_group;
    }

    public void setCategory_group(String category_group) {
        this.category_group = category_group;
    }

    public String getPlan() {
        return this.plan;
    }

    public void setPlan(String _plan) {
        this.plan = _plan;
    }

}