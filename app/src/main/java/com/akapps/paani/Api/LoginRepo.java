package com.akapps.paani.Api;

import com.akapps.paani.Callback.LoginRegisterResultListener;
import com.akapps.paani.Model.User;
import com.akapps.paani.Utils.RequestHandler;
import com.akapps.paani.Utils.SharedPref;

public class LoginRepo {
    private SharedPref sharedPref;
    private RequestHandler requestHandler;
    public LoginRepo(SharedPref sharedPref, RequestHandler requestHandler){
        this.sharedPref = sharedPref;
        this.requestHandler = requestHandler;
    }
    public boolean isLoggedIn(){
        return sharedPref.isLoggedIn();
    }

    public void login(User user, LoginRegisterResultListener listener){
        requestHandler.login(user, listener);
    }
    public void register(User user, LoginRegisterResultListener listener){
        requestHandler.register(user, listener);
    }

    public void saveLogin(User user){
        sharedPref.saveLogin(user);
    }
}
