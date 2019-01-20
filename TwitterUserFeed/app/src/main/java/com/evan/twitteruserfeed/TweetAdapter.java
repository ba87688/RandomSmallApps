package com.evan.twitteruserfeed;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;


public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private ArrayList<String> names;
    private ArrayList<HashMap<String,String>> hashMaps = new ArrayList<>();

    public TweetAdapter(Context c, ArrayList<HashMap<String,String>> names){
        inflater = LayoutInflater.from(c);
        this.hashMaps= names;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate the custom view
        View v = inflater.inflate(R.layout.custom_view, parent,false);
        //pass the custom view created to the view holder
        MyViewHolder holder = new MyViewHolder(v);

        //this will be used in onBindviewHolder
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //this is where you bind data you have to the view created, row by row
        //using position as a guide
        holder.author.setText(hashMaps.get(position).get("title"));
        holder.title.setText(hashMaps.get(position).get("dc:creator"));
        holder.dateCreated.setText(hashMaps.get(position).get("pubDate"));

    }

    @Override
    public int getItemCount() {
        return hashMaps.size();

//        return names.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, dateCreated,author;


        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.titleOfTweet);
            dateCreated = itemView.findViewById(R.id.dateCreated);
            author= itemView.findViewById(R.id.autorOfTweet);
        }
    }
}
