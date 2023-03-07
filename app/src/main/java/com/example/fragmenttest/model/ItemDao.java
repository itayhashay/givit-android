package com.example.fragmenttest.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {
    @Query("select * from Item where isDeleted = 0 ORDER BY id DESC")
    LiveData<List<Item>> getAll();

    @Query("select * from Item where id = :itemId")
    Item getItemById(String itemId);

    @Query("UPDATE Item set name = :itemName, description = :itemDescription, address = :itemAddress, imageUrl = :imageUrl WHERE id = :itemId")
    void editItem(String itemId, String itemName, String itemDescription, String itemAddress, String imageUrl);

    @Query("select * from Item where userId = :userId")
    List<Item> getItemsByUserId(String userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Item ...items);

    @Delete
    void delete(Item item);
}
