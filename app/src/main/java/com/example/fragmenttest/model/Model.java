package com.example.fragmenttest.model;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Model {
    static final Model _instance = new Model();
    AppLocalDbRepository localDb = AppLocalDb.getAppDb();
    private Executor executor = Executors.newSingleThreadExecutor();
    private Handler mainHandler = HandlerCompat.createAsync(Looper.getMainLooper());
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

    public interface FirebaseUserOnCompleteListener {
        void onComplete(FirebaseUser firebaseUser, Exception ex);
    }
    public void signIn(String email, String password, FirebaseUserOnCompleteListener listener) {
        authentication.signIn(email, password, listener);
    }

    public void signUp(String email, String password, FirebaseUserOnCompleteListener listener) {
        authentication.register(email, password, listener);
    }

    public interface EmptyOnCompleteListener {
        void onComplete();
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

    public interface GetAllItemsListener{
        void onComplete(List<Item> data);
    }
    public void refreshAllItems() {
        EventItemsListLoadingState.setValue(LoadingStatus.LOADING);

        long localLastUpdate = Item.getLocalLastUpdate();

        firebaseModel.getAllItemsSince(localLastUpdate, list ->{
            executor.execute(()->{
                Log.d("TAG", "getAllItems: " + list.size());
                long time = localLastUpdate;
                for(Item item: list) {
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

    public interface AddItemListener{
        void onComplete();
    }
    public void addItem(Item item, AddItemListener callback) {
        firebaseModel.addItem(item,() -> {
            refreshAllItems();
            callback.onComplete();
        });
    }

    public interface DeleteItemListener{
        void onComplete();
    }
    public void deleteItem(Item item, DeleteItemListener callback) {
        executor.execute(() -> {
            localDb.itemDao().delete(item);
        });
        firebaseModel.deleteItem(item,() -> {
            refreshAllItems();
            callback.onComplete();
        });
    }

    public interface EditItemListener{
        void onComplete();
    }
    public void editItem(String itemId, String itemName, String itemDescription, String itemAddress, String imageUrl,EditItemListener callback) {
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

    public interface GetAllUsersListener{
        void onComplete(List<User> data);
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

    public interface AddUserListener{
        void onComplete();
    }
    public void addUser(User user, AddUserListener callback) {
        firebaseModel.addUser(user, callback);
    }

    public interface EditUserListener{
        void onComplete();
    }
    public void editUser(String userId, String username, String userPhone, String firstName,String lastName, String imageUrl,EditUserListener callback) {
        executor.execute(() -> {
            localDb.userDao().editUser(userId, username,userPhone,firstName, lastName,imageUrl);
        });
        firebaseModel.editUser(userId, username, userPhone, firstName,lastName,imageUrl, () -> {
            refreshAllUsers();
            callback.onComplete();
        });
    }

    public interface GetUserByIdListener{
        void onComplete(User user);
    }
    public void getUserById(String userId, GetUserByIdListener callback) {
        firebaseModel.getUserById(userId, callback);
    }

    public interface UploadImageListener{
        void onComplete(String url);
    }
    public void uploadImage(String fileName, Bitmap bitmap, UploadImageListener callback) {
        firebaseModel.uploadImage(fileName, bitmap, callback);
    }
}

