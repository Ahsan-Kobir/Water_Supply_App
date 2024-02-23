package com.akapps.paani.Api;

import com.akapps.paani.Callback.LoadListener;
import com.akapps.paani.Model.RequestModels.ApiRequestBody;
import com.akapps.paani.Utils.RequestHandler;
import com.akapps.paani.Utils.SharedPref;

public class ApiRepo {
    private SharedPref sharedPref;
    private RequestHandler requestHandler;
    public ApiRepo(SharedPref sharedPref, RequestHandler requestHandler){
        this.sharedPref = sharedPref;
        this.requestHandler = requestHandler;
    }

    public void postRequest(ApiRequestBody apiRequestBody, LoadListener listener){
        requestHandler.postRequest(apiRequestBody, listener);
    }
}
