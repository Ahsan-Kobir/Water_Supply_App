package com.akapps.paani.Api;

import com.akapps.paani.Callback.LoginRegisterResultListener;
import com.akapps.paani.Firebase.FirebaseAuthClient;
import com.akapps.paani.Model.User;
import com.akapps.paani.Utils.RequestHandler;
import com.akapps.paani.Utils.SharedPref;

public class LoginRepo {
    private SharedPref sharedPref;
    private RequestHandler requestHandler;
    private FirebaseAuthClient firebaseAuthClient;
    public LoginRepo(SharedPref sharedPref, RequestHandler requestHandler, FirebaseAuthClient firebaseAuthClient){
        this.sharedPref = sharedPref;
        this.requestHandler = requestHandler;
        this.firebaseAuthClient = firebaseAuthClient;
    }
    public boolean isLoggedIn(){
        return firebaseAuthClient.isLoggedIn();
        //return sharedPref.isLoggedIn(); old backend
    }

    public void login(User user, LoginRegisterResultListener listener){
        //requestHandler.login(user, listener); this is old backend
        firebaseAuthClient.loginWithEmail(user, listener);

    }
    public void register(User user, LoginRegisterResultListener listener){
       // requestHandler.register(user, listener);  this is old backend
        firebaseAuthClient.registerWithEmail(user, listener);
    }

    public void saveLogin(User user){
        sharedPref.saveLogin(user);
    }
}
