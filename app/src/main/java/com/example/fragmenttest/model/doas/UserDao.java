package com.example.fragmenttest.model.doas;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.fragmenttest.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("select * from User")
    LiveData<List<User>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User ...users);

    @Query("UPDATE User set username = :username, phone = :userPhone,firstName = :firstName, lastName = :lastName, imageUrl = :imageUrl WHERE id = :userId")
    void editUser(String userId, String username, String userPhone, String firstName, String lastName, String imageUrl);

}
