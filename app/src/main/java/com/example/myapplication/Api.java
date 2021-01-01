package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("/random")
    Call<Result> getdata();

}
