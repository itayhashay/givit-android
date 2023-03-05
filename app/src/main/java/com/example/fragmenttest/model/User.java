package com.example.fragmenttest.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
public class User implements Serializable {
    @PrimaryKey
    @NonNull
    public String id;
    public String username;
    public String phone;
    public String firstName;
    public String lastName;
    public String email;
    public String imageUrl;


    public User(String id, String username, String phone, String firstName, String lastName, String email, String imageUrl) {
        this.id= id;
        this.username = username;
        this.phone = phone;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String toString() {
        return this.username + " " + this.email + " " + this.phone + " " + this.firstName + " " + this.lastName;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", this.getId());
        json.put("username", this.getUsername());
        json.put("phone", this.getPhone());
        json.put("firstName", this.getFirstName());
        json.put("lastName", this.getLastName());
        json.put("email", this.getEmail());
        json.put("imageUrl", this.getImageUrl());
        return json;
    }

    public static User fromJson(Map<String, Object> json) {
        String id = (String)json.get("id");
        String username = (String)json.get("username");
        String phone = (String)json.get("phone");
        String firstName = (String)json.get("firstName");
        String lastName = (String)json.get("lastName");
        String email = (String)json.get("email");
        String imageUrl = (String)json.get("imageUrl");
        User user = new User(id,username,phone,firstName,lastName,email,imageUrl);
        return user;
    }
}
