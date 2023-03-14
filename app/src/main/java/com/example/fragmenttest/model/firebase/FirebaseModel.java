package com.example.fragmenttest.model.firebase;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.fragmenttest.model.Item;
import com.example.fragmenttest.model.Model;
import com.example.fragmenttest.model.User;
import com.example.fragmenttest.model.interfaces.EmptyOnCompleteListener;
import com.example.fragmenttest.model.interfaces.ImageOnCompleteListener;
import com.example.fragmenttest.model.interfaces.ItemsOnCompleteListener;
import com.example.fragmenttest.model.interfaces.UserOnCompleteListener;
import com.example.fragmenttest.model.interfaces.UsersOnCompleteListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FirebaseModel {
    FirebaseFirestore db;
    FirebaseStorage storage;
    public FirebaseModel() {
        storage = FirebaseStorage.getInstance();
        db = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new
                FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
    }

    public void getAllItemsSince(long since, ItemsOnCompleteListener callback) {
        db.collection("items")
                .whereGreaterThanOrEqualTo(Item.LAST_UPDATED, new Timestamp(since,0))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                List<Item> items = new LinkedList<>();
                if(task.isSuccessful()){
                    QuerySnapshot jsonList = task.getResult();
                    for(DocumentSnapshot json: jsonList) {
                        items.add(Item.fromJson(json.getData()));
                    }
                }

                callback.onComplete(items);
            }
        });
    }

    public void addItem(Item item, EmptyOnCompleteListener callback) {
        db.collection("items").document(item.getId()).set(item.toJson()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("TAG", "addItem: " + item.toJson().toString());
                callback.onComplete();
            }
        });

    }

    public void deleteItem(Item item, EmptyOnCompleteListener callback) {

        DocumentReference docRef = db.collection("items").document(item.getId());
        item.setDeleted(true);

        docRef.update(item.toJson()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully Deleted!");
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

    public void editItem(String itemId, String itemName, String itemDescription, String itemAddress, String imageUrl,EmptyOnCompleteListener callback) {
        DocumentReference docRef = db.collection("items").document(itemId);
        Map<String,Object> updates = new HashMap<>();
        updates.put("name", itemName);
        updates.put("address", itemAddress);
        updates.put("description", itemDescription);
        updates.put("imageUrl", imageUrl);
        updates.put("lastUpdated", FieldValue.serverTimestamp());
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


    public void getAllUsersSince(long since, UsersOnCompleteListener callback) {
        db.collection("users").whereGreaterThanOrEqualTo(Item.LAST_UPDATED, new Timestamp(since,0))
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
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

    public void addUser(User user, EmptyOnCompleteListener callback) {
        db.collection("users").document(user.getId()).set(user.toJson()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("TAG", "addItem: " + user.toJson().toString());
                callback.onComplete();
            }
        });

    }

    public void editUser(String userId, String username, String userPhone, String firstName,String lastName, String imageUrl,EmptyOnCompleteListener callback) {
        DocumentReference docRef = db.collection("users").document(userId);
        Map<String,Object> updates = new HashMap<>();
        updates.put("username", username);
        updates.put("phone", userPhone);
        updates.put("firstName", firstName);
        updates.put("lastName", lastName);
        updates.put("imageUrl", imageUrl);
        updates.put("lastUpdated", FieldValue.serverTimestamp());

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

    public void getUserById(String userId, UserOnCompleteListener callback) {
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
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("TAG", "onFailure: " + e.getMessage());
            }
        });
    }

    public void uploadImage(String fileName, Bitmap bitmap, ImageOnCompleteListener callback) {
        // Create a storage reference from our app
        StorageReference storageRef = storage.getReference();
        StorageReference imagesRef = storageRef.child("images/"+fileName+".jpg");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = imagesRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                callback.onComplete(null);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        callback.onComplete(uri.toString());
                    }
                });
            }
        });

    }
}
