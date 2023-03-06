package com.example.fragmenttest;

import androidx.lifecycle.ViewModel;

import com.example.fragmenttest.model.Item;
import com.example.fragmenttest.model.User;

public class EditItemFragmentViewModel extends ViewModel {
    Item item;
    User user;

    public Item getItem(){
        return item;
    }

    public void setItem(Item i) {
        this.item = i;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User u) {
        this.user = u;
    }
}
