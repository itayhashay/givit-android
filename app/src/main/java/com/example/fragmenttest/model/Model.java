package com.example.fragmenttest.model;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;

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

    List<Item> itemsList = new LinkedList<>();

    public interface GetAllItemsListener{
        void onComplete(List<Item> data);
    }
    public void getAllItems(GetAllItemsListener callback) {
        firebaseModel.getAllItems(callback);
//        executor.execute(() -> {
//            List<Item> data = localDb.itemDao().getAll();
//            mainHandler.post(() -> {
//                itemsList = data;
//                callback.onComplete(data);
//            });
//        });
    }

    public interface AddItemListener{
        void onComplete();
    }
    public void addItem(Item item, AddItemListener callback) {
        firebaseModel.addItem(item,callback);
//        executor.execute(() -> {
//            localDb.itemDao().insertAll(item);
//            mainHandler.post(() -> {
//                callback.onComplete();
//            });
//        });
    }

    public interface DeleteItemListener{
        void onComplete();
    }
    public void deleteItem(Item item, DeleteItemListener callback) {
//        executor.execute(() -> {
//            localDb.itemDao().delete(item);
//            mainHandler.post(() -> {
//                callback.onComplete();
//            });
//        });
        firebaseModel.deleteItem(item,callback);
    }

    public interface EditItemListener{
        void onComplete();
    }
    public void editItem(String itemId, String itemName, String itemDescription, String itemAddress, EditItemListener callback) {
       firebaseModel.editItem(itemId,itemName,itemDescription,itemAddress,callback);


//        executor.execute(() -> {
//            localDb.itemDao().editItem(itemId, itemName, itemDescription, itemAddress);
//            mainHandler.post(() -> {
//                callback.onComplete();
//            });
//        });
    }

    public interface GetAllUsersListener{
        void onComplete(List<User> data);
    }
    public void getAllUsers(GetAllUsersListener callback) {
        firebaseModel.getAllUsers(callback);
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
    public void editUser(String userId, String username, String userPhone, String firstName,String lastName, String email, EditUserListener callback) {
        firebaseModel.editUser(userId, username, userPhone, firstName,lastName,email, callback);
    }

    public interface GetUserByIdListener{
        void onComplete(User user);
    }
    public void getUserById(String userId, GetUserByIdListener callback) {
        firebaseModel.getUserById(userId, callback);
    }
}

