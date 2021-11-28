package com.ariel.teamball.Classes;

public class Player extends User {
    boolean GameStatus;

    /* Constructor */
    public Player() {
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
}
