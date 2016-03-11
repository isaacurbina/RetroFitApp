package com.mobileappsco.training.retrofitapp;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.Adapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mobileappsco.training.retrofitapp.entities.RelatedTopic;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RelatedResultViewHolder> {

    public List<RelatedTopic> relatedTopicsList;
    public Context mContext;

    public RecyclerViewAdapter(Context mContext, List<RelatedTopic> relatedTopicsList){
        this.relatedTopicsList = relatedTopicsList;
        this.mContext = mContext;
    }

    public void addRelatedTopic(RelatedTopic relatedTopic) {
        relatedTopicsList.add(relatedTopic);
        notifyItemInserted(getItemCount()-1);
    }
    public void removeDummy() {
        if (getItemCount()>0 && relatedTopicsList.get(0) == null) {
            relatedTopicsList.remove(0);
        }
    }

    public void updateList(List<RelatedTopic> relatedTopicsList) {
        this.relatedTopicsList = relatedTopicsList;
        notifyDataSetChanged();
    }

    @Override
    public RelatedResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        RelatedResultViewHolder rrvh = new RelatedResultViewHolder(v, mContext);
        return rrvh;
    }

    @Override
    public void onBindViewHolder(RelatedResultViewHolder holder, int position) {
        try {
            //Log.d("MYTAG", "Text: " + relatedTopicsList.get(position).getText().toString());
            //Log.d("MYTAG", "Image: " + relatedTopicsList.get(position).getFirstURL());
            holder.resultText.setText(relatedTopicsList.get(position).getText().toString());
            Picasso.with(mContext)
                    .load("https://cdn1.iconfinder.com/data/icons/ios-7-style-metro-ui-icons/512/MetroUI_OS_Android.png")
                    //.load(relatedTopicsList.get(position).getFirstURL().toString())
                    .placeholder(android.R.drawable.ic_menu_gallery)
                    .into(holder.resultIcon);
        } catch (Exception e) {
            Log.d("MYTAG", "Error onBindViewHolder: " + e.getMessage());
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void removeItem(View v) {

    }

    @Override
    public int getItemCount() {
        return relatedTopicsList.size();
    }

    public static class RelatedResultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView resultText;
        ImageView resultIcon;
        Context mContext2;

        public RelatedResultViewHolder(View itemView, Context mContext2) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.card_view);
            resultText = (TextView)itemView.findViewById(R.id.result_text);
            resultIcon = (ImageView)itemView.findViewById(R.id.result_icon);
            this.mContext2 = mContext2;
            //itemView.setClickable(true);
            //itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(mContext2, "The Item Clicked is: " + getPosition(), Toast.LENGTH_SHORT).show();
        }

    }

}
