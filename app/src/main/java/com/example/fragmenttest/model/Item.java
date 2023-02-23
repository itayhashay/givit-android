package com.example.fragmenttest.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Item {
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
}
