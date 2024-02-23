package com.akapps.paani.Firebase;

import com.akapps.paani.Callback.LoginRegisterResultListener;
import com.akapps.paani.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseAuthClient {

    private FirebaseAuth auth;
    private FirebaseFirestore db;

    public FirebaseAuthClient(FirebaseAuth instance, FirebaseFirestore db) {
        this.auth = instance;
        this.db = db;
    }

    public void loginWithEmail(User user, LoginRegisterResultListener listener){
        auth.signInWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        listener.onLoginSuccess(user);
                    } else {
                        listener.onLoginFailed(task.getException().getLocalizedMessage());
                    }
                });
    }

    public void registerWithEmail(User user, LoginRegisterResultListener listener){
        auth.createUserWithEmailAndPassword(user.getEmail(), user.getPassword())
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        String newUserUid = task.getResult().getUser().getUid();
                        user.setUid(newUserUid);

                        db.collection("users")
                                .document(newUserUid)
                                .set(user)
                                .addOnCompleteListener(saveTask -> {
                                    listener.onLoginSuccess(user);
                                });
                    } else {
                        listener.onLoginFailed(task.getException().getLocalizedMessage());
                    }
                });
    }

    public boolean isLoggedIn() {
        return auth.getUid() != null;
    }
}
