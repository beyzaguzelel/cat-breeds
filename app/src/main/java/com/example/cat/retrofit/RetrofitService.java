package com.example.cat.retrofit;

import com.google.gson.Gson;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitService {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create(new Gson()))
            .build();

    public Retrofit getRetrofit(){
        return retrofit;
    }


}
