package com.boot.model;

public class ChatName {

    private String username;

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "ChatName [user=" + this.username + "]";
    }
}
