package com.ermile.khadijehapp.Adaptor;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ermile.khadijehapp.Item.item_Main;
import com.ermile.khadijehapp.Item.item_link_2_4;
import com.ermile.khadijehapp.Item.item_slider;
import com.ermile.khadijehapp.R;
import com.ermile.khadijehapp.api.apiV6;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Adaptor_Main extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<item_Main> itemMains;
    private Context mContext;
    private int total_types;


    public static class holder_slide extends RecyclerView.ViewHolder {

        Context context;
        RecyclerViewPager recyclerViewPager;
        Adaptor_slider adaptorSlider;
        ArrayList<item_slider> itemSliderArrayList;

        holder_slide(View itemView) {
            super(itemView);
            this.recyclerViewPager = itemView.findViewById(R.id.recyclerViewPager);
        }
    }

    public static class holder_link1 extends RecyclerView.ViewHolder {

        ImageView imageView ;

        public holder_link1(View itemView) {
            super(itemView);

            this.imageView = itemView.findViewById(R.id.link1_imageView);
        }
    }

    public static class holder_link2 extends RecyclerView.ViewHolder {

        ImageView imageView_1,imageView_2 ;

        holder_link2(View itemView) {
            super(itemView);

            this.imageView_1 = itemView.findViewById(R.id.link2_imageView1);
            this.imageView_2 = itemView.findViewById(R.id.link2_imageView2);
        }
    }

    public static class holder_link3Desc extends RecyclerView.ViewHolder {

        ImageView imageView_1,imageView_2 ;
        TextView title,desc;

        holder_link3Desc(View itemView) {
            super(itemView);

            this.imageView_1 = itemView.findViewById(R.id.linkDesc_img);
            this.title = itemView.findViewById(R.id.linkDesc_title);
            this.desc = itemView.findViewById(R.id.linkDesc_desc);
            this.desc = itemView.findViewById(R.id.linkDesc_desc);
        }
    }

    public static class holder_link4 extends RecyclerView.ViewHolder {

        ImageView imageView_1,imageView_2,imageView_3,imageView_4 ;

        holder_link4(View itemView) {
            super(itemView);

            this.imageView_1 = itemView.findViewById(R.id.link4_imageView1);
            this.imageView_2 = itemView.findViewById(R.id.link4_imageView2);
            this.imageView_3 = itemView.findViewById(R.id.link4_imageView3);
            this.imageView_4 = itemView.findViewById(R.id.link4_imageView4);
        }
    }

    public static class holder_title_link extends RecyclerView.ViewHolder {

        TextView title,go;

        holder_title_link(View itemView) {
            super(itemView);

            this.title = itemView.findViewById(R.id.titleLink_title);
            this.go = itemView.findViewById(R.id.titleLink_go);
        }
    }


    public static class holder_title extends RecyclerView.ViewHolder {

        TextView title;

        holder_title(View itemView) {
            super(itemView);
            this.title = itemView.findViewById(R.id.titlenone_title);
        }
    }

    public static class holder_salavet extends RecyclerView.ViewHolder {

        TextView count,readText,salavet;

        holder_salavet(View itemView) {
            super(itemView);

            this.count = itemView.findViewById(R.id.salavat_cunt);
            this.readText = itemView.findViewById(R.id.salavat_readText);
            this.salavet = itemView.findViewById(R.id.salavat_textsalavat);
        }
    }

    public static class holder_hadith extends RecyclerView.ViewHolder {

        TextView title,hadith;

        holder_hadith(View itemView) {
            super(itemView);

            this.title = itemView.findViewById(R.id.hadith_title);
            this.hadith = itemView.findViewById(R.id.hadith_desc);
        }
    }

    public static class holder_news extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView title,desc;

        holder_news(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.news_img);
            this.title = itemView.findViewById(R.id.news_title);
            this.desc = itemView.findViewById(R.id.news_desc);

        }
    }

    public static class holder_hr extends RecyclerView.ViewHolder {

        ImageView imageView;

        holder_hr(View itemView) {
            super(itemView);

            this.imageView = itemView.findViewById(R.id.hr_img);
        }
    }

/*    public static class holder_ extends RecyclerView.ViewHolder {

        ImageView imageView_1;

        holder_(View itemView) {
            super(itemView);

            this.imageView_1 = itemView.findViewById(R.id.link4_imageView1);
        }
    }*/

    public Adaptor_Main(ArrayList<item_Main> data, Context context ) {
        this.itemMains = data;
        this.mContext = context;
        total_types = itemMains.size();

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case item_Main.SLIDE :
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_slide_viewpager, parent, false);
                return new holder_slide(view);
            case item_Main.LINK_1 :
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link1, parent, false);
                return new holder_link1(view);
            case item_Main.LINK_2:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link2, parent, false);
                return new Adaptor_Main.holder_link2(view);
            case item_Main.LINK_Desc:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link3_desc, parent, false);
                return new Adaptor_Main.holder_link3Desc(view);
            case item_Main.LINK_4:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link4, parent, false);
                return new Adaptor_Main.holder_link4(view);
            case item_Main.TITEL_link:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_title_link, parent, false);
                return new Adaptor_Main.holder_title_link(view);
            case item_Main.TITEL_NONE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_link_none, parent, false);
                return new Adaptor_Main.holder_title(view);
            case item_Main.SALAVAT:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_salavat, parent, false);
                return new Adaptor_Main.holder_salavet(view);
            case item_Main.HADITH:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hadith, parent, false);
                return new Adaptor_Main.holder_hadith(view);
            case item_Main.NEWS:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
                return new Adaptor_Main.holder_news(view);
            case item_Main.HR:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hr, parent, false);
                return new Adaptor_Main.holder_hr(view);

        }
        return null;


    }


    @Override
    public int getItemViewType(int position) {

        switch (itemMains.get(position).type) {
            case 100:
                return item_Main.SLIDE;
            case 1:
                return item_Main.LINK_1;
            case 2:
                return item_Main.LINK_2;
            case 3:
                return item_Main.LINK_Desc;
            case 4:
                return item_Main.LINK_4;
            case 10:
                return item_Main.TITEL_link;
            case 11:
                return item_Main.TITEL_NONE;
            case 20:
                return item_Main.SALAVAT;
            case 21:
                return item_Main.HADITH;
            case 30:
                return item_Main.NEWS;
            case 0:
                return item_Main.HR;
            default:
                return -1;
        }


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {

        final item_Main object = itemMains.get(listPosition);
        if (object != null) {
            switch (object.type) {
                case item_Main.SLIDE:
                    ((holder_slide)holder).context = mContext;
                    ((holder_slide)holder).itemSliderArrayList = new ArrayList<item_slider>();

                    ((holder_slide)holder).adaptorSlider = new Adaptor_slider(((holder_slide)holder).context,((holder_slide)holder).itemSliderArrayList);
                    ((holder_slide)holder).recyclerViewPager.setAdapter(((holder_slide)holder).adaptorSlider);

                    final LinearLayoutManager layout = new LinearLayoutManager(mContext,LinearLayoutManager.HORIZONTAL,false);

                    try {
                        JSONArray link4Array = new JSONArray(object.slide_title);

                        for (int i = 0; i < link4Array.length(); i++) {
                            JSONObject object_link4 = link4Array.getJSONObject(i);
                            JSONObject meta = object_link4.getJSONObject("meta");

                            String title = object_link4.getString("title");
                            String content = object_link4.getString("content");
                            String image = meta.getString("thumb");
                            String url = object_link4.getString("link");

                            ((holder_slide)holder).itemSliderArrayList.add(new item_slider(image,title,String.valueOf(Html.fromHtml(content)),url));
                            ((holder_slide)holder).recyclerViewPager.setLayoutManager(layout);
                            ((holder_slide)holder).recyclerViewPager.setItemAnimator(new DefaultItemAnimator());
                        }





                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    break;

                case item_Main.LINK_1:
                    Glide.with(mContext).load(object.link1_url).into(((holder_link1) holder).imageView);
                    ((holder_link1) holder).imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    break;

                case item_Main.LINK_2:
                    Glide.with(mContext).load(object.link2_url_1).into(((holder_link2) holder).imageView_2);
                    Glide.with(mContext).load(object.link2_url_2).into(((holder_link2) holder).imageView_1);


                    ((holder_link2) holder).imageView_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    ((holder_link2) holder).imageView_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });
                    break;
                case item_Main.LINK_Desc :
                    Glide.with(mContext).load(object.link3_url_img).into(((holder_link3Desc) holder).imageView_1);
                    ((holder_link3Desc) holder).title.setText(object.link3_title);
                    ((holder_link3Desc) holder).desc.setText(object.link3_desc);
                    break;
                    case item_Main.LINK_4:
                        Glide.with(mContext).load(object.link4_url_img1).into(((holder_link4) holder).imageView_1);
                        Glide.with(mContext).load(object.link4_url_img2).into(((holder_link4) holder).imageView_2);
                        Glide.with(mContext).load(object.link4_url_img3).into(((holder_link4) holder).imageView_3);
                        Glide.with(mContext).load(object.link4_url_img4).into(((holder_link4) holder).imageView_4);
                        break;
                case item_Main.TITEL_link:
                    ((holder_title_link) holder).title.setText(object.titleLink_title);
                    ((holder_title_link) holder).go.setText(object.titleLink_go);
                    ((holder_title_link) holder).go.setTag(object.titleLink_url);

                    break;
                case item_Main.TITEL_NONE:
                    ((holder_title) holder).title.setText(object.titleNONE_title);
                    break;
                case item_Main.SALAVAT:
                    ((holder_salavet) holder).count.setText(object.salavat_count);
                    ((holder_salavet) holder).readText.setText(object.salavat_readText);
                    ((holder_salavet) holder).salavet.setText(object.salavat_text);
                    break;
                case item_Main.HADITH:
                    ((holder_hadith) holder).title.setText(object.hadith_title);
                    ((holder_hadith) holder).hadith.setText(object.hadith_desc);
                    break;
                case item_Main.NEWS:
                    Glide.with(mContext).load(object.news_url_img).into(((holder_news) holder).imageView);
                    ((holder_news) holder).title.setText(object.news_title);
                    ((holder_news) holder).desc.setText(object.news_desc);
                    break;

                case item_Main.HR:
                    Glide.with(mContext).load(object.hr_url_img).into(((holder_hr) holder).imageView);
                    break;


            }
        }

    }

    @Override
    public int getItemCount() {
        return itemMains.size();
    }


}