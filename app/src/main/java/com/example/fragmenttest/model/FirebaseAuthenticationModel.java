package com.example.fragmenttest.model;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.concurrent.Executor;

public class FirebaseAuthenticationModel {
    private final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    public FirebaseAuthenticationModel() {}


    public String getCurrentUserUID() {
        return firebaseAuth.getUid();
    }

    public boolean isUserSignedIn() {
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        return currentUser != null;
    }

    public void signIn(String email, String password, Model.FirebaseUserOnCompleteListener callback) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Log.d("TAG", "signInWithEmail:success");
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                        callback.onComplete(user, task.getException());
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("TAG", "signInWithEmail:failure", task.getException());
                        callback.onComplete(null, task.getException());
                    }
                });
    }

    public void register(String email, String password, Model.FirebaseUserOnCompleteListener listener) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> onAuthenticationComplete(task, listener));
    }

    public void signOut(Model.EmptyOnCompleteListener listener) {
        firebaseAuth.signOut();
        listener.onComplete();
    }

    private void onAuthenticationComplete(Task<AuthResult> task, Model.FirebaseUserOnCompleteListener listener) {
        FirebaseUser user;
        Exception ex = null;
        if (task.isSuccessful()) {
            user = firebaseAuth.getCurrentUser();
        } else {
            user = null;
            ex = task.getException();
        }
        listener.onComplete(user, ex);
    }
}
