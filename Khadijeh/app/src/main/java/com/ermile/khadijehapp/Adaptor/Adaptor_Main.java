package com.ermile.khadijehapp.Adaptor;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ermile.khadijehapp.Activity.ApiView;
import com.ermile.khadijehapp.Activity.Delneveshte;
import com.ermile.khadijehapp.Activity.Language;
import com.ermile.khadijehapp.Activity.ListNews;
import com.ermile.khadijehapp.Activity.News;
import com.ermile.khadijehapp.Activity.Web_View;
import com.ermile.khadijehapp.Item.item_Main;
import com.ermile.khadijehapp.Item.item_link_2_4;
import com.ermile.khadijehapp.Item.item_slider;
import com.ermile.khadijehapp.R;
import com.ermile.khadijehapp.api.apiV6;
import com.ermile.khadijehapp.utility.SaveManager;
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


    public static class holder_baner extends RecyclerView.ViewHolder {

        ImageView baner;

        holder_baner(View itemView) {
            super(itemView);
            this.baner = itemView.findViewById(R.id.baner_imageView);
        }
    }

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

        ImageView imageView ;
        TextView title,desc;

        holder_link3Desc(View itemView) {
            super(itemView);

            this.imageView = itemView.findViewById(R.id.linkDesc_img);
            this.title = itemView.findViewById(R.id.linkDesc_title);
            this.desc = itemView.findViewById(R.id.linkDesc_desc);
            this.desc = itemView.findViewById(R.id.linkDesc_desc);
        }
    }

    public static class holder_link4 extends RecyclerView.ViewHolder {

        ImageView imageView_1,imageView_2,imageView_3,imageView_4 ;
        TextView textView_1,textView_2,textView_3,textView_4 ;
        holder_link4(View itemView) {
            super(itemView);
            this.imageView_1 = itemView.findViewById(R.id.link4_imageView1);
            this.imageView_2 = itemView.findViewById(R.id.link4_imageView2);
            this.imageView_3 = itemView.findViewById(R.id.link4_imageView3);
            this.imageView_4 = itemView.findViewById(R.id.link4_imageView4);

            this.textView_1 = itemView.findViewById(R.id.link4_text_1);
            this.textView_2 = itemView.findViewById(R.id.link4_text_2);
            this.textView_3 = itemView.findViewById(R.id.link4_text_3);
            this.textView_4 = itemView.findViewById(R.id.link4_text_4);
        }
    }

    public static class holder_title_link extends RecyclerView.ViewHolder {

        TextView title,go,space;

        holder_title_link(View itemView) {
            super(itemView);

            this.space = itemView.findViewById(R.id.titleLink_space);
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

        TextView count,readText;
        ImageView imgSalawat;

        holder_salavet(View itemView) {
            super(itemView);

            this.count = itemView.findViewById(R.id.salavat_cunt);
            this.readText = itemView.findViewById(R.id.salavat_readText);
            this.imgSalawat = itemView.findViewById(R.id.img_salawat);
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

    public static class holder_language extends RecyclerView.ViewHolder {

        ImageView imageView;
        LinearLayout linearLayout;
        TextView textView;

        holder_language(View itemView) {
            super(itemView);
            this.textView = itemView.findViewById(R.id.text_language);
            this.linearLayout = itemView.findViewById(R.id.line_language);
            this.imageView = itemView.findViewById(R.id.img_language);
        }
    }
    public static class holder_version extends RecyclerView.ViewHolder {
        holder_version(View itemView) {
            super(itemView);
        }
    }

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
            case item_Main.BANER :
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_baner, parent, false);
                return new holder_baner(view);
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
            case item_Main.LANGUAGE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_change_language, parent, false);
                return new Adaptor_Main.holder_language(view);
            case item_Main.VERSION:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_version, parent, false);
                return new Adaptor_Main.holder_version(view);

        }
        return null;


    }


    @Override
    public int getItemViewType(int position) {

        switch (itemMains.get(position).type) {
            case 500:
                return item_Main.BANER;
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
            case 2000:
                return item_Main.LANGUAGE;
            case 3000:
                return item_Main.VERSION;
            default:
                return -1;
        }


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition) {
        final Intent go = new Intent(mContext, Web_View.class);

        View.OnClickListener link = new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                switch (view.getTag().toString()){
                    case "news":
                        mContext.startActivity(new Intent(mContext, ListNews.class));
                        break;
                        default:
                            go.putExtra("url",view.getTag().toString());
                            mContext.startActivity(go);
                            break;
                }
            }
        };

        final String url_salawat = mContext.getString(R.string.url_salawat);

        final item_Main object = itemMains.get(listPosition);
        if (object != null) {
            switch (object.type) {
                case item_Main.BANER:
                    Glide.with(mContext).load(object.baner_img).into(((holder_baner) holder).baner);

                    ((holder_baner) holder).baner.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swiech(object.baner_url,null);
                        }
                    });
                    break;

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
                            String content_html = String.valueOf(Html.fromHtml(content));
                            String id = object_link4.getString("id");
                            String image = meta.getString("thumb");
                            String url = object_link4.getString("link");


                            ((holder_slide)holder).itemSliderArrayList.add(new item_slider(image,title,content_html.replace("\n"," "),url,id));
                            ((holder_slide)holder).recyclerViewPager.setLayoutManager(layout);
                            ((holder_slide)holder).recyclerViewPager.setItemAnimator(new DefaultItemAnimator());
                        }





                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    break;

                case item_Main.LINK_1:
                    Glide.with(mContext).load(object.link1_img).into(((holder_link1) holder).imageView);

                    ((holder_link1) holder).imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swiech(object.link1_url,null);
                        }
                    });
                    break;

                case item_Main.LINK_2:
                    Glide.with(mContext).load(object.link2_img_1).into(((holder_link2) holder).imageView_1);
                    Glide.with(mContext).load(object.link2_img_2).into(((holder_link2) holder).imageView_2);

                    ((holder_link2) holder).imageView_1.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swiech(object.link2_url_1,null);
                        }
                    });
                    ((holder_link2) holder).imageView_2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swiech(object.link2_url_2,null);

                        }
                    });
                    break;

                case item_Main.LINK_Desc :
                    Glide.with(mContext).load(object.link3_url_img).into(((holder_link3Desc) holder).imageView);
                    ((holder_link3Desc) holder).title.setText(object.link3_title);
                    ((holder_link3Desc) holder).desc.setText(object.link3_desc);

                    ((holder_link3Desc) holder).title.setTag(object.link3_link);
                    ((holder_link3Desc) holder).desc.setTag(object.link3_link);
                    ((holder_link3Desc)holder).imageView.setTag(object.link3_link);


                    ((holder_link3Desc) holder).title.setOnClickListener(link);
                    ((holder_link3Desc) holder).desc.setOnClickListener(link);
                    ((holder_link3Desc)holder).imageView.setOnClickListener(link);
                    break;
                    case item_Main.LINK_4:
                        Glide.with(mContext).load(object.link4_url_img1).into(((holder_link4) holder).imageView_1);
                        Glide.with(mContext).load(object.link4_url_img2).into(((holder_link4) holder).imageView_2);
                        Glide.with(mContext).load(object.link4_url_img3).into(((holder_link4) holder).imageView_3);
                        Glide.with(mContext).load(object.link4_url_img4).into(((holder_link4) holder).imageView_4);

                        ((holder_link4) holder).textView_1.setText(object.link4_text_1);
                        ((holder_link4) holder).textView_2.setText(object.link4_text_2);
                        ((holder_link4) holder).textView_3.setText(object.link4_text_3);
                        ((holder_link4) holder).textView_4.setText(object.link4_text_4);

                        ((holder_link4)holder).imageView_1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                swiech(object.l4_url_1,object.link4_type_1);
                            }
                        });
                        ((holder_link4)holder).imageView_2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                swiech(object.l4_url_2,object.link4_type_2);
                            }
                        });
                        ((holder_link4)holder).imageView_3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                swiech(object.l4_url_3,object.link4_type_3);
                            }
                        });
                        ((holder_link4)holder).imageView_4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                swiech(object.l4_url_4,object.link4_type_4);
                            }
                        });
                        break;



                case item_Main.TITEL_link:
                    ((holder_title_link) holder).title.setText(object.titleLink_title);
                    ((holder_title_link) holder).go.setText(object.titleLink_go);

                    ((holder_title_link) holder).space.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swiech(object.titleLink_url,null);
                        }
                    });
                    View.OnClickListener links = new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            swiech(object.titleLink_url,null);
                        }
                    };

                    ((holder_title_link) holder).title.setOnClickListener(links);
                    ((holder_title_link) holder).go.setOnClickListener(links);

                    break;

                case item_Main.TITEL_NONE:
                    ((holder_title) holder).title.setText(object.titleNONE_title);
                    break;

                case item_Main.SALAVAT:
                    ((holder_salavet) holder).count.setText(object.salavat_count);
                    ((holder_salavet) holder).readText.setText(mContext.getString(R.string.flag_salawat));

                    View.OnClickListener salawat = new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            String apikey = SaveManager.get(mContext).getstring_appINFO().get(SaveManager.apiKey);
                            apiV6.salawat(url_salawat,apikey, new apiV6.salawatListener() {
                                @Override
                                public void saveSalawat(String count, String msgArray) {
                                    ((holder_salavet) holder).count.setText(count);
                                    try {
                                        JSONArray msg = new JSONArray(msgArray);
                                        for (int i = 0; i < msg.length(); i++) {
                                            JSONObject object1 = msg.getJSONObject(i);
                                            String text = object1.getString("text");
                                            Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void errorSalawat(String error) {
                                }
                            });
                        }
                    };

                    ((holder_salavet) holder).count.setOnClickListener(salawat);
                    ((holder_salavet) holder).readText.setOnClickListener(salawat);
                    ((holder_salavet)holder).imgSalawat.setOnClickListener(salawat);
                    break;

                case item_Main.HADITH:
                    ((holder_hadith) holder).title.setText(object.hadith_title);
                    ((holder_hadith) holder).hadith.setText(object.hadith_desc);
                    break;

                case item_Main.NEWS:
                    Glide.with(mContext).load(object.news_url_img).into(((holder_news) holder).imageView);
                    ((holder_news) holder).title.setText(object.news_title);
                    ((holder_news) holder).desc.setText(object.news_desc);

                    ((holder_news) holder).title.setTag(object.news_id);

                    View.OnClickListener clickNews = new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent go_news = new Intent(mContext, News.class);
                            go_news.putExtra("id",((holder_news)holder).title.getTag().toString());
                            mContext.startActivity(go_news);
                        }
                    };

                    ((holder_news) holder).imageView.setOnClickListener(clickNews);
                    ((holder_news) holder).desc.setOnClickListener(clickNews);
                    ((holder_news) holder).desc.setOnClickListener(clickNews);

                    break;

                case item_Main.HR:
                    Glide.with(mContext).load(object.hr_url_img).into(((holder_hr) holder).imageView);
                    break;
                case item_Main.LANGUAGE:

                    View.OnClickListener langOnclick = new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((Activity)mContext).finish();
                            mContext.startActivity(new Intent(mContext, Language.class));
                        }
                    };

                    ((holder_language)holder).imageView.setOnClickListener(langOnclick);
                    ((holder_language)holder).linearLayout.setOnClickListener(langOnclick);
                    ((holder_language)holder).textView.setOnClickListener(langOnclick);
                    break;
                case item_Main.VERSION:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        return itemMains.size();
    }


    private void swiech(String url,String type){

        Intent goApiView = new Intent(mContext, ApiView.class);
        Intent goWebView = new Intent(mContext,Web_View.class);

        switch (url){
            case "mission":
            case "about":
            case "contact":
            case "vision":
                goApiView.putExtra("url",url);
                mContext.startActivity(goApiView);
                break;
            case "news":
                mContext.startActivity(new Intent(mContext,ListNews.class));
                break;
            case "lang":
                ((Activity)mContext).finish();
                mContext.startActivity(new Intent(mContext,Language.class));
                break;
            case "delneveshte":
                mContext.startActivity(new Intent(mContext, Delneveshte.class));
                break;

            default:
                if (type!=null && type.equals("browser")){
                    Toast.makeText(mContext, ""+type, Toast.LENGTH_SHORT).show();
                    Intent browser = new Intent(Intent.ACTION_VIEW);
                    browser.setData(Uri.parse(url));
                    mContext.startActivity(browser);
                    break;
                }else {
                    goWebView.putExtra("url",url);
                    mContext.startActivity(goWebView);
                    break;
                }
        }

    }


}