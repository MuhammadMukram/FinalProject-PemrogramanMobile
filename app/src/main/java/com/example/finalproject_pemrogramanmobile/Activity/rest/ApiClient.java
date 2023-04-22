package com.example.finalproject_pemrogramanmobile.Activity.rest;
import androidx.appcompat.app.AppCompatActivity;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient extends AppCompatActivity{
    //Local Host for emulators
    //10.0.2.2 default host for android emulator in android studio
    public static final String BASE_URL ="http://10.0.2.2/wordpress/wp-json/api/";

    //For Real Devices Users
    // 1- Connect your Laptop And Mobile On The Same Wifi
    // 2- Find Local IP address of your laptop
    // 3- Add that local IP address in your BASE_URL variable

    //You need to update this IP Address everytime your pc is connected to internet


    //Getting The Retrofit Api Client
    private static Retrofit retrofit = null;

    public static Retrofit getApiClient(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

}
