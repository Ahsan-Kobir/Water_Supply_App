package com.akapps.paani.Interface;

import com.akapps.paani.Model.RequestModels.LoginResponse;
import com.akapps.paani.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;


public interface LoginRegisterApi {
    @POST("login")
    Call<LoginResponse> login(
            @Body User user
    );

    @POST("register")
    Call<LoginResponse> register(
            @Body User user
            );
}
