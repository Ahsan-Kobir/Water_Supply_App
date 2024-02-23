package com.akapps.paani.Interface;

import com.akapps.paani.Model.RequestModels.ApiRequestBody;
import com.akapps.paani.Model.RequestModels.ApiResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiCall {
    @POST("api")
    Call<ApiResponse> postRequest(@Body ApiRequestBody apiRequestBody);
}
