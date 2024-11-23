package com.example.newsapp.Helper;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {

    @GET("v2/everything")
    Call<JsonObject> getEverything(
            @Query("q") String q,
            @Query("apikey") String apikey

    );

    @GET("v2/top-headlines")
    Call<JsonObject> getHeadlines(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
@GET("v2/top-headlines/sources")
    Call<JsonObject> getSource(
            @Query("apiKey") String apiKey

    );




}
