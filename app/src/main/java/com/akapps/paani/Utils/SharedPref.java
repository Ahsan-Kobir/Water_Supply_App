package com.akapps.paani.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.akapps.paani.Model.User;

public class SharedPref {
    private SharedPreferences sharedPreferences;
    public static final String USER_NAME = "username";
    public static final String USER_ID = "uid";
    public static final String USER_PROFILE = "user_profile";

    public SharedPref(Context context){
        this.sharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
    }

    public boolean isLoggedIn(){
        if(sharedPreferences.getString(USER_NAME, null) == null ||
                sharedPreferences.getString(USER_ID, null) == null){
            return false;
        }
        return true;
    }

    public void saveLogin(User user) {
        sharedPreferences.edit()
                .putString(USER_NAME, user.getName())
                .putString(USER_ID, user.getUid())
                .putString(USER_PROFILE, user.getProfile())
                .commit();
    }

    public String getUid() {
        if(isLoggedIn()){
            return sharedPreferences.getString(USER_ID, "");
        }
        return "";
    }
}
