package com.example.finalyearproject.Models;

public class UserModel {
    String ID;
    String name;
    String username;
    String email;

    public UserModel(String name, String username, String email, String ID) {
        this.ID = ID;
        this.name = name;
        this.username = username;
        this.email = email;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
