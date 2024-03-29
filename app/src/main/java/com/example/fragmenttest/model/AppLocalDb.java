package com.example.fragmenttest.model;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.fragmenttest.MyApplication;
import com.example.fragmenttest.model.doas.ItemDao;
import com.example.fragmenttest.model.doas.UserDao;

@Database(entities = {User.class, Item.class}, version = 33)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ItemDao itemDao();
}
public class AppLocalDb{
    static public AppLocalDbRepository getAppDb() {
           return Room.databaseBuilder(MyApplication.getMyContext(),
                            AppLocalDbRepository.class,
                            "dbGivit.db")
                    .fallbackToDestructiveMigration()
                    .build();
    }

    private AppLocalDb(){}
}