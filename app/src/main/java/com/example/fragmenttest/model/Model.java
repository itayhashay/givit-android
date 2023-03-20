package com.example.fragmenttest.model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.fragmenttest.model.firebase.FirebaseAuthenticationModel;
import com.example.fragmenttest.model.firebase.FirebaseModel;
import com.example.fragmenttest.model.interfaces.EmptyOnCompleteListener;
import com.example.fragmenttest.model.interfaces.FirebaseUserOnCompleteListener;
import com.example.fragmenttest.model.interfaces.ImageOnCompleteListener;
import com.example.fragmenttest.model.interfaces.UserOnCompleteListener;
import com.example.fragmenttest.model.interfaces.UsersOnCompleteListener;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Model {
    static final Model _instance = new Model();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();
    private Executor executor = Executors.newSingleThreadExecutor();
    private FirebaseModel firebaseModel  = new FirebaseModel();
    private FirebaseAuthenticationModel authentication = new FirebaseAuthenticationModel();
    public static Model getInstance() {
        return _instance;
    }

    private Model() {}

    public enum LoadingStatus{
        LOADING,
        NOT_LOADING
    }

   final public MutableLiveData<LoadingStatus> EventItemsListLoadingState = new MutableLiveData<>();

    public String getCurrentUserUID() {
        return authentication.getCurrentUserUID();
    }

    public boolean isSignedIn() {
        return authentication.isUserSignedIn();
    }

    public void signIn(String email, String password, FirebaseUserOnCompleteListener listener) {
        authentication.signIn(email, password, listener);
    }

    public void signUp(String email, String password, FirebaseUserOnCompleteListener listener) {
        authentication.register(email, password, listener);
    }

    public void signOut(EmptyOnCompleteListener listener) {
        authentication.signOut(listener);
    }

    LiveData<List<Item>> itemsList;
    public LiveData<List<Item>> getAllItems(){
        if(itemsList == null) {
            itemsList = localDb.itemDao().getAll();
            refreshAllUsers();
            refreshAllItems();
        }
        return itemsList;
    }

    public void refreshAllItems() {
        EventItemsListLoadingState.setValue(LoadingStatus.LOADING);

        long localLastUpdate = Item.getLocalLastUpdate();

        firebaseModel.getAllItemsSince(localLastUpdate, list ->{
            executor.execute(()->{
                Log.d("TAG", "getAllItems: " + list.size());
                long time = localLastUpdate;
                for(Item item:  list) {
                    localDb.itemDao().insertAll(item);
                    if(time< item.getLastUpdated()) {
                        time = item.getLastUpdated();
                    }
                }
                Item.setLocalLastUpdate(time);
                EventItemsListLoadingState.postValue(LoadingStatus.NOT_LOADING);
            });
        });

    }

    public void addItem(Item item, EmptyOnCompleteListener callback) {
        firebaseModel.addItem(item,() -> {
            refreshAllItems();
            callback.onComplete();
        });
    }

    public void deleteItem(Item item, EmptyOnCompleteListener callback) {
        executor.execute(() -> {
            item.setDeleted(true);
            localDb.itemDao().insertAll(item);
        });
        firebaseModel.deleteItem(item,() -> {
            refreshAllItems();
            callback.onComplete();
        });
    }

    public void editItem(String itemId, String itemName, String itemDescription, String itemAddress, String imageUrl,EmptyOnCompleteListener callback) {
        executor.execute(() -> {
            localDb.itemDao().editItem(itemId, itemName,itemDescription,itemAddress, imageUrl);
        });
       firebaseModel.editItem(itemId,itemName,itemDescription,itemAddress,imageUrl,() -> {
           refreshAllItems();
           callback.onComplete();
       });
    }

    LiveData<List<User>> usersList;
    public LiveData<List<User>> getAllUsers(){
        if(usersList == null) {
            usersList = localDb.userDao().getAll();
            refreshAllUsers();
        }
        return usersList;
    }

    public void refreshAllUsers() {
        EventItemsListLoadingState.setValue(LoadingStatus.LOADING);

        long localLastUpdate = Item.getLocalLastUpdate();

        firebaseModel.getAllUsersSince(localLastUpdate, list ->{
            executor.execute(()->{
                Log.d("TAG", "getAllItems: " + list.size());
                long time = localLastUpdate;
                for(User user: list) {
                    localDb.userDao().insertAll(user);
                    if(time< user.getLastUpdated()) {
                        time = user.getLastUpdated();
                    }
                }
                Item.setLocalLastUpdate(time);
                EventItemsListLoadingState.postValue(LoadingStatus.NOT_LOADING);
            });
        });
    }

    public void addUser(User user, EmptyOnCompleteListener callback) {
        executor.execute(() -> {
            localDb.userDao().insertAll(user);
        });
        firebaseModel.addUser(user, () -> {
            refreshAllUsers();
            callback.onComplete();
        });
    }

    public void editUser(String userId, String username, String userPhone, String firstName,String lastName, String imageUrl,EmptyOnCompleteListener callback) {
        executor.execute(() -> {
            localDb.userDao().editUser(userId, username,userPhone,firstName, lastName,imageUrl);
        });
        firebaseModel.editUser(userId, username, userPhone, firstName,lastName,imageUrl, () -> {
            refreshAllUsers();
            callback.onComplete();
        });
    }

    public void getUserById(String userId, UserOnCompleteListener callback) {
        firebaseModel.getUserById(userId, callback);
    }

    public void uploadImage(String fileName, Bitmap bitmap, ImageOnCompleteListener callback) {
        firebaseModel.uploadImage(fileName, bitmap, callback);
    }
}

