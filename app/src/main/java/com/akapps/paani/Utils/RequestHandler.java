package com.akapps.paani.Utils;

import android.util.Log;

import com.akapps.paani.Callback.LoadListener;
import com.akapps.paani.Callback.LoginRegisterResultListener;
import com.akapps.paani.Constants.Constants;
import com.akapps.paani.Interface.ApiCall;
import com.akapps.paani.Interface.LoginRegisterApi;
import com.akapps.paani.Model.RequestModels.ApiRequestBody;
import com.akapps.paani.Model.RequestModels.ApiResponse;
import com.akapps.paani.Model.RequestModels.LoginResponse;
import com.akapps.paani.Model.User;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestHandler {
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_USER_OBJECT = "user";
    private static final String TAG_MESSAGE = "message";
    Retrofit retrofit;
    Gson gson = new Gson();
    private static RequestHandler instance;
    public synchronized static RequestHandler getInstance(){
        if(instance==null){
            instance = new RequestHandler();
        }
        return instance;
    }

    private RequestHandler(){
        retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
    public void login(User user, LoginRegisterResultListener listener){
        LoginRegisterApi loginApi = retrofit.create(LoginRegisterApi.class);
        loginApi.login(user).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.isSuccess()){
                        listener.onLoginSuccess(loginResponse.getUser());
                    } else {
                        listener.onLoginFailed(loginResponse.getMessage());
                    }
                } else {
                    listener.onLoginFailed("Error response code: " + response.body() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                listener.onLoginFailed(t.getLocalizedMessage());
            }
        });
    }

    public void register(User user, LoginRegisterResultListener listener){
        LoginRegisterApi registerApi = retrofit.create(LoginRegisterApi.class);

        Call<LoginResponse> registerRequest = registerApi.register(user);
        Log.d("REQUEST SAMPLE", registerRequest.request().toString());
        registerRequest.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()){
                    LoginResponse loginResponse = response.body();
                    if (loginResponse.isSuccess()){
                        listener.onLoginSuccess(loginResponse.getUser());
                    } else {
                        listener.onLoginFailed(loginResponse.getMessage());
                    }

                } else {
                    listener.onLoginFailed("Error response code: " + response.body() + " " + response.message());
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                t.printStackTrace();
                listener.onLoginFailed(t.getLocalizedMessage());
            }
        });
    }

//    public void loadBanner(LoadListener<String> loadListener){
//
//    }

    public void postRequest(ApiRequestBody apiRequestBody, LoadListener listener){
        ApiCall apiCall = retrofit.create(ApiCall.class);

        Call<ApiResponse> apiCallRequest = apiCall.postRequest(apiRequestBody);
        apiCallRequest.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if(response.isSuccessful() && response.code()==200){
                    ApiResponse apiResponse = response.body();
                    if(apiResponse.isSuccess()){
                        listener.onLoadSuccess(apiResponse.getResult());
                    } else {
                        listener.onLoadFailed(apiResponse.getMessage());
                    }
                } else {
                    listener.onLoadFailed(response.message());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                listener.onLoadFailed(t.getLocalizedMessage());
            }
        });
    }

}
