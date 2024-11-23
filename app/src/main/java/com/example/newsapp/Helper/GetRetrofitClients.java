package com.example.newsapp.Helper;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetRetrofitClients {

    public static ApiServices getClients(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServices apiServices = retrofit.create(ApiServices.class);

        return apiServices;
    }
}
