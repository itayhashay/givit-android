package com.example.fragmenttest.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.fragmenttest.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

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
    public long lastUpdated;

    final static String LAST_UPDATED = "lastUpdated";

    public User(String id, String username, String phone, String firstName, String lastName, String email, String imageUrl) {
        this.id= id;
        this.username = username;
        this.phone = phone;
        this.lastName = lastName;
        this.firstName = firstName;
        this.email = email;
        this.imageUrl = imageUrl;
    }

    public static long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong("USERS_LOCAL_LAST_UPDATED", 0);
    }

    public static void setLocalLastUpdate(long time) {
        SharedPreferences.Editor ed = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
        ed.putLong("USERS_LOCAL_LAST_UPDATED",time);
        ed.commit();
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public long getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
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
        json.put("lastUpdated", FieldValue.serverTimestamp());
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
        try {
            Timestamp lastUpdated = (Timestamp)json.get("lastUpdated");
            user.setLastUpdated(lastUpdated.getSeconds());
        } catch (Exception e) {
            Log.w("TAG", e.getMessage());
        }
        return user;
    }
}
