package com.example.fragmenttest.model.doas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.fragmenttest.model.Item;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("select * from Item where isDeleted = 0 ORDER BY id DESC")
    LiveData<List<Item>> getAll();

    @Query("UPDATE Item set name = :itemName, description = :itemDescription, address = :itemAddress, imageUrl = :imageUrl WHERE id = :itemId")
    void editItem(String itemId, String itemName, String itemDescription, String itemAddress, String imageUrl);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Item ...items);

}
