package com.ermile.khadijehapp.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ermile.khadijehapp.Activity.Splash;
import com.ermile.khadijehapp.Item.item_Language;
import com.ermile.khadijehapp.R;
import com.ermile.khadijehapp.utility.SaveManager;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Locale;

public class LanguageAdaptor extends RecyclerView.Adapter<LanguageAdaptor.MyViewHolder> {

    private List<item_Language> itemList;
    private Context mContext;

    public LanguageAdaptor(List<item_Language> itemList, Context mContext) {
        this.itemList = itemList;
        this.mContext = mContext;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NotNull ViewGroup parent, int viewType) {

        View aView = LayoutInflater.from(mContext).inflate(R.layout.item_language, parent, false);
        return new MyViewHolder(aView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        item_Language aItem = itemList.get(position);

        holder.titel.setText(aItem.getTitle());
        holder.titel.setTag(aItem.getTag());
/*
        holder.checkLanguage.setVisibility(aItem.getChBoxVisibel());
*/
        holder.linrLnaguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveManager.get(mContext).change_appLanguage(holder.titel.getTag().toString());
                SaveManager.get(mContext).change_LanguageByUser(false);
                setLocale(holder.titel.getTag().toString());
                Intent refresh = new Intent(mContext, Splash.class);
                ((Activity)mContext).finish();
                mContext.startActivity(refresh);
            }
        });

    }
    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = mContext.getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView titel;
/*
        ImageView checkLanguage;
*/
        CardView linrLnaguage;


        public MyViewHolder(View itemView) {
            super(itemView);
            linrLnaguage = itemView.findViewById(R.id.linrLnaguage);
            titel = itemView.findViewById(R.id.titleLanguage);
/*
            checkLanguage = itemView.findViewById(R.id.checkLanguage);
*/
        }
    }

}