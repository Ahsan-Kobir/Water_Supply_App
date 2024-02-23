package com.akapps.paani.Model.RequestModels;

import java.util.HashMap;

public class ApiRequestBody {
    private String type;
    private HashMap<String, String> requestParams;

    public ApiRequestBody(String type, HashMap<String, String> requestParams) {
        this.type = type;
        this.requestParams = requestParams;
    }
}
