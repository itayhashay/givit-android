package com.example.fragmenttest.model.interfaces;

import com.google.firebase.auth.FirebaseUser;

public interface FirebaseUserOnCompleteListener {
    void onComplete(FirebaseUser firebaseUser, Exception ex);
}
