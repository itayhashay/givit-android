package com.example.fragmenttest.model;

import java.io.Serializable;

public class User implements Serializable {
    public String id;
    public String username;
    public String phone;
    public String firstName;
    public String lastName;
    public String email;


    public User(String id, String username, String phone, String firstName, String lastName, String email) {
        this.id=id;
        this.username = username;
        this.phone = phone;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return this.username + " " + this.email + " " + this.phone + " " + this.firstName + " " + this.lastName;
    }
}
