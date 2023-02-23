package com.example.fragmenttest.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Item implements Serializable {
    public String name;
    public String description;
    public String address;
    public User user;

    public Item(String name, String description, String address, User user) {
        this.name = name;
        this.description = description;
        this.address = address;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
