package com.example.fragmenttest.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
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
public class Item implements Serializable {
    @PrimaryKey
    @NonNull
    public String id;
    public String name;
    public String description;
    public String address;
    public String userId;
    public String imageUrl;
    public long lastUpdated;
    public Boolean isDeleted = false;

    public final static String LAST_UPDATED = "lastUpdated";

    public Item(String name, String description, String address, String userId, String imageUrl) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.address = address;
        this.userId = userId;
        this.imageUrl=imageUrl;
    }

    public static long getLocalLastUpdate() {
        SharedPreferences sharedPref = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE);
        return sharedPref.getLong("ITEMS_LOCAL_LAST_UPDATED", 0);
    }

    public static void setLocalLastUpdate(long time) {
        SharedPreferences.Editor ed = MyApplication.getMyContext().getSharedPreferences("TAG", Context.MODE_PRIVATE).edit();
        ed.putLong("ITEMS_LOCAL_LAST_UPDATED",time);
        ed.commit();
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

    public void setLastUpdated(long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public long getLastUpdated() {
        return lastUpdated;
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

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("id", this.getId());
        json.put("name", this.getName());
        json.put("address", this.getAddress());
        json.put("description", this.getDescription());
        json.put("userId", this.getUserId());
        json.put("imageUrl", this.getImageUrl());
        json.put("lastUpdated", FieldValue.serverTimestamp());
        json.put("isDeleted", this.getDeleted());
        return json;
    }

    public static Item fromJson(Map<String, Object> json) {
        String id = (String)json.get("id");
        String name = (String)json.get("name");
        String address = (String)json.get("address");
        String description = (String)json.get("description");
        String userId = (String)json.get("userId");
        String imageUrl = (String)json.get("imageUrl");
        Boolean isDeleted = (Boolean)json.get("isDeleted");
        Item i = new Item(name,description,address,userId, imageUrl);
        i.setId(id);
        i.setDeleted(isDeleted);
        try {
            Timestamp lastUpdated = (Timestamp)json.get(LAST_UPDATED);
            i.setLastUpdated(lastUpdated.getSeconds());
        } catch (Exception e) {
            Log.w("TAG", e.getMessage());
        }
        return i;
    }

}
