package com.mobileappsco.training.retrofitapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileappsco.training.retrofitapp.entities.RelatedTopic;
import com.mobileappsco.training.retrofitapp.entities.Result;
import com.mobileappsco.training.retrofitapp.retrofit.RetroFitInterface;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView tvResults;
    EditText etName;
    RecyclerView recyclerView;
    LinearLayoutManager llmanager;
    List<RelatedTopic> relatedTopicsList;
    RecyclerViewAdapter rvAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvResults = (TextView) findViewById(R.id.tv_results);
        etName = (EditText) findViewById(R.id.et_name);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        llmanager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llmanager);
        relatedTopicsList = new ArrayList<>();
        relatedTopicsList.add(new RelatedTopic());
        rvAdapter = new RecyclerViewAdapter(MainActivity.this, relatedTopicsList);
        recyclerView.setAdapter(rvAdapter);
    }

    public void removeItem(View v) {

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
                    for (RelatedTopic relatedTopic : results.getRelatedTopics()) {
                        //tvResults.setText(tvResults.getText() + relatedTopic.getText() + "\n");
                        //relatedTopicsList.add(relatedTopic);
                        rvAdapter.addRelatedTopic(relatedTopic);
                        rvAdapter.notifyItemInserted(rvAdapter.getItemCount()-1);
                    }
                    rvAdapter.removeDummy();
                    relatedTopicsList.remove(0);

                    //rvAdapter = new RecyclerViewAdapter(MainActivity.this, relatedTopicsList);
                    //recyclerView.setAdapter(rvAdapter);
                    //rvAdapter.updateList(relatedTopicsList);
                    rvAdapter.notifyDataSetChanged();
                    recyclerView.addOnItemTouchListener(
                            new RecyclerItemClickListener(MainActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                                @Override
                                public void onItemClick(View view, int position) {
                                    // TODO Handle item click
                                    Toast.makeText(MainActivity.this, "click en :"+position, Toast.LENGTH_SHORT).show();
                                    relatedTopicsList.remove(position);
                                    rvAdapter.notifyItemRemoved(position);
                                }
                            })
                    );
                }
            } catch (Exception e) {
                Log.e("MYTAG", "ERROR for: " +e.getMessage());
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
