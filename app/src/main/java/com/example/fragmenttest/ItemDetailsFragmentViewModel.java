package com.example.fragmenttest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fragmenttest.model.Model;
import com.example.fragmenttest.model.User;

import java.util.List;

public class ItemDetailsFragmentViewModel extends ViewModel {
    private LiveData<List<User>> usersList = Model.getInstance().getAllUsers();

    public LiveData<List<User>> getUsersList() { return this.usersList;}
}
