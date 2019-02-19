package com.example.jeremy.artgenerator.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Config {
    @SerializedName("api_url")
    @Expose
    private String apiUrl;

    public Config(String apiUrl) {
        this.apiUrl = apiUrl;
    }

    public Config() {
    }

    public String getApiUrl() {
        return apiUrl;
    }

    public void setApiUrl(String apiUrl) {
        this.apiUrl = apiUrl;
    }
}