package com.example.fragmenttest.model;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

public class Model {
    static final Model _instance = new Model();

    public static Model getInstance() {
        return _instance;
    }

    private Model() {
        for (int i = 0; i < 10; i++) {
            User user = createUser();
            addItem(new Item("Table" +i, "Descriptionnnnnnn", "Yahud City", user));
        }
    }

    private User createUser() {
        return new User("1", "dauss", "0543453552");
    }

    List<Item> itemsList = new LinkedList<>();

    public List<Item> getAllItems() {
        return itemsList;
    }

    public void addItem(Item item) {
        itemsList.add(item);
    }

    public void deleteItem(int itemIndex) {
        try {
            itemsList.remove(itemIndex);
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
        }
    }

    public void editStudent(int itemIndex, Item updatedItem) {
        try {
            itemsList.set(itemIndex, updatedItem);
        } catch (Exception e) {
            Log.d("ERROR", e.getMessage());
        }
    }
}

