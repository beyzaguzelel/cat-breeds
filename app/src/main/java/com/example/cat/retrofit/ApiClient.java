package com.example.cat.retrofit;

import com.example.cat.model.Cat;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiClient
{

   //@Headers(x-api-key)
    @GET("/v1/breeds?x-api-key=c9249791-2a37-425a-a758-c6357478bc99")
    Call<List<Cat>> getBreeds();

    @GET("/v1/breeds/search?q={name}")
    Call<List<Cat>> searchBreed(@Path("name") String name);




}
