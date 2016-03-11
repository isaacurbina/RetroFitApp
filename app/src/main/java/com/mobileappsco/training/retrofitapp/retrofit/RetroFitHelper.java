package com.mobileappsco.training.retrofitapp.retrofit;

import android.util.Log;

import com.mobileappsco.training.retrofitapp.BuildConfig;
import com.mobileappsco.training.retrofitapp.entities.RelatedTopic;
import com.mobileappsco.training.retrofitapp.entities.Result;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 3/11/2016.
 */
public class RetroFitHelper {

    public static void main(String[] args) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.duckduckgo.com/?")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetroFitInterface rfInterface = retrofit.create(RetroFitInterface.class);

        Call<Result> listCall = rfInterface.listCharacters("simpsons characters");

        Result results = null;

        try {
            results = listCall.execute().body();
            for (RelatedTopic result : results.getRelatedTopics()){
                System.out.println(result.getText());
            }
        } catch (Exception e) {
            //Log.e("MYAPP", "Error: " + e.toString());
        }

        //return results;

    }
}
