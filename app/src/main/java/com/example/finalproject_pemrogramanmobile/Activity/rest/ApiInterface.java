package com.example.finalproject_pemrogramanmobile.Activity.rest;

import com.example.finalproject_pemrogramanmobile.Activity.model.HomepageModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiInterface {

    @GET("homepage_api")
    Call<HomepageModel> getHomepageApi(@QueryMap Map<String, String> params);
}
