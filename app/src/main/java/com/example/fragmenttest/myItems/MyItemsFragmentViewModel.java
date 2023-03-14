package com.example.fragmenttest.myItems;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.fragmenttest.model.Item;
import com.example.fragmenttest.model.Model;

import java.util.LinkedList;
import java.util.List;

public class MyItemsFragmentViewModel extends ViewModel {
    LiveData<List<Item>> itemList = Model.getInstance().getAllItems();

    public LiveData<List<Item>> getData() {
        return itemList;
    }
}
