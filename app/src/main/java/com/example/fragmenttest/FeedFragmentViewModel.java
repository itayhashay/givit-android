package com.example.fragmenttest;

import androidx.lifecycle.ViewModel;

import com.example.fragmenttest.model.Item;
import java.util.LinkedList;
import java.util.List;

public class FeedFragmentViewModel extends ViewModel {
    List<Item> itemList = new LinkedList<>();

    public List<Item> getData() {
        return itemList;
    }

    public void setData(List<Item> lst) {
        this.itemList = lst;
    }

}
