package com.example.fragmenttest;

import androidx.lifecycle.ViewModel;

import com.example.fragmenttest.model.User;

public class PersonalInfoFragmentViewModel extends ViewModel {

    User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
