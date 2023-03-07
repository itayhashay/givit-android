package com.example.fragmenttest;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fragmenttest.model.Item;
import com.example.fragmenttest.model.Model;

import java.util.LinkedList;
import java.util.List;

public class FeedFragmentViewModel extends ViewModel {
    private LiveData<List<Item>> itemList;

    public FeedFragmentViewModel() {
        itemList = Model.getInstance().getAllItems();
    }

    public LiveData<List<Item>> getData() {
        return itemList;
    }
}
