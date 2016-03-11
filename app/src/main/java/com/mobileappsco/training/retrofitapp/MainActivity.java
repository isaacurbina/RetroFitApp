package com.mobileappsco.training.retrofitapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileappsco.training.retrofitapp.entities.RelatedTopic;
import com.mobileappsco.training.retrofitapp.entities.Result;
import com.mobileappsco.training.retrofitapp.retrofit.RetroFitInterface;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView tvResults;
    EditText etName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResults = (TextView) findViewById(R.id.tv_results);
        etName = (EditText) findViewById(R.id.et_name);
    }

    public void requestRetroFit(View view) {
        MyAsyncTask mAT = new MyAsyncTask();
        mAT.execute(etName.getText().toString());
    }

    private class MyAsyncTask extends AsyncTask<String, Integer, Result> {

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvResults.setText("Loading request for " + etName.getText().toString());
        }

        @Override
        protected void onPostExecute(Result results) {
            super.onPostExecute(results);
            tvResults.setText(tvResults.getText()+"\n Finished");
            try {
                if (results==null) {
                    Toast.makeText(MainActivity.this, "No results found", Toast.LENGTH_SHORT).show();
                } else {
                    for (RelatedTopic result : results.getRelatedTopics()) {
                        tvResults.setText(tvResults.getText() + result.getText() + "\n");
                    }
                }
            } catch (Exception e) {
                Log.e("MYTAG", e.getMessage());
            }
        }

        @Override
        protected Result doInBackground(String... params) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.duckduckgo.com/?")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            RetroFitInterface rfInterface = retrofit.create(RetroFitInterface.class);

            Call<Result> listCall = rfInterface.listCharacters(params[0]);

            Result results = null;

            try {
                results = listCall.execute().body();
            } catch (Exception e) {
                Log.e("MYAPP", "Error: " + e.toString());
            }

            return results;

        }
    }
}
