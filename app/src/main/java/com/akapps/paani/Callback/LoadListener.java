package com.akapps.paani.Callback;

public interface LoadListener {
//    void onLoadStarted();
    void onLoadSuccess(Object result);
    void onLoadFailed(String error);
}
