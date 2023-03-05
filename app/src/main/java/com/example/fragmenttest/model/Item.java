package com.example.fragmenttest.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
public class Item implements Serializable {
    @PrimaryKey
    @NonNull
    public String id;
    public String name;
    public String description;
    public String address;
    public String userId;
    public String imageUrl;

    public Item(String name, String description, String address, String userId, String imageUrl) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.address = address;
        this.userId = userId;
        this.imageUrl=imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", this.getId());
        json.put("name", this.getName());
        json.put("address", this.getAddress());
        json.put("description", this.getDescription());
        json.put("userId", this.getUserId());
        json.put("imageUrl", this.getImageUrl());
        return json;
    }

    public static Item fromJson(Map<String, Object> json) {
        String id = (String)json.get("id");
        String name = (String)json.get("name");
        String address = (String)json.get("address");
        String description = (String)json.get("description");
        String userId = (String)json.get("userId");
        String imageUrl = (String)json.get("imageUrl");
        Item i = new Item(name,description,address,userId, imageUrl);
        i.setId(id);
        return i;
    }
}
