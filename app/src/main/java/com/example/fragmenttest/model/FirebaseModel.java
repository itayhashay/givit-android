package com.example.fragmenttest.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FirebaseModel {
    FirebaseFirestore db;

    FirebaseModel() {
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new
                FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
    }

    public void getAllItems(Model.GetAllItemsListener callback) {
        db.collection("items").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Item> items = new LinkedList<>();
                if(task.isSuccessful()){
//                    items.addAll(tesk)
                    QuerySnapshot jsonList = task.getResult();
                    for(DocumentSnapshot json: jsonList) {
                        items.add(Item.fromJson(json.getData()));
                    }
                }

                callback.onComplete(items);
            }
        });
    }

    public void addItem(Item item, Model.AddItemListener callback) {
        db.collection("items").document(item.getId()).set(item.toJson()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("TAG", "addItem: " + item.toJson().toString());
                callback.onComplete();
            }
        });

    }

    public void deleteItem(Item item, Model.DeleteItemListener callback) {
        db.collection("items").document(item.getId())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                        callback.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });
    }

    public void editItem(String itemId, String itemName, String itemDescription, String itemAddress, Model.EditItemListener callback) {
        DocumentReference docRef = db.collection("items").document(itemId);
        Map<String,Object> updates = new HashMap<>();
        updates.put("name", itemName);
        updates.put("address", itemAddress);
        updates.put("description", itemDescription);

        docRef.update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully updated!");
                        callback.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error updating document", e);
                    }
                });
        }


    public void getAllUsers(Model.GetAllUsersListener callback) {
        db.collection("users").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<User> users = new LinkedList<>();
                if(task.isSuccessful()){
//                    items.addAll(tesk)
                    QuerySnapshot jsonList = task.getResult();
                    for(DocumentSnapshot json: jsonList) {
                        users.add(User.fromJson(json.getData()));
                    }
                }
                callback.onComplete(users);
            }
        });
    }

    public void addUser(User user, Model.AddUserListener callback) {
        db.collection("users").document(user.getId()).set(user.toJson()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("TAG", "addItem: " + user.toJson().toString());
                callback.onComplete();
            }
        });

    }

    public void editUser(String userId, String username, String userPhone, String firstName,String lastName, Model.EditUserListener callback) {
        DocumentReference docRef = db.collection("users").document(userId);
        Map<String,Object> updates = new HashMap<>();
        updates.put("username", username);
        updates.put("phone", userPhone);
        updates.put("firstName", firstName);
        updates.put("lastName", lastName);

        docRef.update(updates).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully updated!");
                        callback.onComplete();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error updating document", e);
                    }
                });
    }

    public void getUserById(String userId,Model.GetUserByIdListener callback) {
        DocumentReference docRef = db.collection("users").document(userId);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d("TAG", "DocumentSnapshot data: " + document.getData());
                        callback.onComplete(User.fromJson(document.getData()));
                    } else {
                        Log.d("TAG", "No such document");
                        callback.onComplete(null);
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });
    }
}
