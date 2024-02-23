package com.akapps.paani.Callback;

import com.akapps.paani.Model.User;

public interface LoginRegisterResultListener {
    void onLoginFailed(String message);
    void onLoginSuccess(User user);
}
