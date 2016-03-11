package com.mobileappsco.training.retrofitapp.retrofit;

import com.mobileappsco.training.retrofitapp.entities.Result;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RetroFitInterface {
    @GET("?format=json")
    Call<Result> listCharacters(@Query("q") String q);
}
