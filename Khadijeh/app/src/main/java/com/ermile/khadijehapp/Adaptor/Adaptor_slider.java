package com.ermile.khadijehapp.Adaptor;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ermile.khadijehapp.Item.item_slider;
import com.ermile.khadijehapp.R;

import java.util.List;

public class Adaptor_slider extends RecyclerView.Adapter<Adaptor_slider.ViewHolder>{

    private List<item_slider> itemSliderList;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    Context context;

    // data is passed into the constructor
    public Adaptor_slider(Context context, List<item_slider> itemSliderList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.itemSliderList = itemSliderList;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_slider, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String image = itemSliderList.get(position).getImage();
        String title = itemSliderList.get(position).getTitle();
        String content = itemSliderList.get(position).getTitle();
        if (image != null){
            Glide.with(context).load(image).into(holder.imageViews);
        }
        holder.title.setText(title);
        holder.content.setText(content);
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return itemSliderList.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageViews;
        TextView title,content;

        ViewHolder(View itemView) {
            super(itemView);
            imageViews = itemView.findViewById(R.id.slider_image);
            title = itemView.findViewById(R.id.slider_title);
            content = itemView.findViewById(R.id.slider_desc);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    item_slider getItem(int id) {
        return itemSliderList.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}