package com.evan.viewpagerapp;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ImagesAndNamesAdapter extends RecyclerView.Adapter<ImagesAndNamesAdapter.ViewHolder> {

    private String[] captions;
    private int[] imageIds;

    public ImagesAndNamesAdapter(String[] captions, int[] imageIds){
        this.captions = captions;
        this.imageIds = imageIds;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //this is where you return the viewgroup which is created here.
        // in our case, it is the carview
        CardView cv = (CardView) LayoutInflater.from(parent.getContext()).
                inflate(R.layout.card_image_name,parent,false);
        return new ViewHolder(cv);
    }

    @Override
    public void onBindViewHolder( ViewHolder holder, int position) {
        //this is where the data is added.
        CardView cardView = holder.cardView;

        ImageView imageView = (ImageView)cardView.findViewById(R.id.image_info);
        Drawable drawable =
                ContextCompat.getDrawable(cardView.getContext(), imageIds[position]);
        imageView.setImageDrawable(drawable);

        TextView textView = cardView.findViewById(R.id.text_info);
        textView.setText(captions[position]);
    }

    @Override
    public int getItemCount() {
        return captions.length;
    }

    //inner class of viewholder defined.
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private CardView cardView;

        //this is where you tell the viewholder what type of data you're populating
        //in this case, it is a cardview
        public ViewHolder(CardView itemView) {
            super(itemView);
            cardView = itemView;
        }
    }


}
