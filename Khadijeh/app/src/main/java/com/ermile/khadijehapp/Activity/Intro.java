package com.ermile.khadijehapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.ermile.khadijehapp.Adaptor.Adaptor_Intro;
import com.ermile.khadijehapp.Item.item_intro;
import com.ermile.khadijehapp.R;
import com.ermile.khadijehapp.api.GetAndroidDetail;
import com.ermile.khadijehapp.utility.SaveManager;
import com.lsjwzh.widget.recyclerviewpager.RecyclerViewPager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Intro extends AppCompatActivity {

    List<item_intro> itemIntroList;
    RecyclerViewPager recyclerViewPager;
    Adaptor_Intro adaptorIntro;

    Button nex, prav;

    int count = 0;

    String next,pravs,skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        nex = findViewById(R.id.btn_next);
        prav = findViewById(R.id.btn_prav);



        itemIntroList = new ArrayList<>();
        recyclerViewPager  = findViewById(R.id.recyclerViewPager_intro);
        adaptorIntro  = new Adaptor_Intro(this,itemIntroList);
        recyclerViewPager.setAdapter(adaptorIntro);
        final LinearLayoutManager layout = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);


        GetAndroidDetail.GetJson(new GetAndroidDetail.JsonLocalListener() {
            @Override
            public void onGetJson_Online(String ResponeOnline) {
                try {

                    JSONObject main_object = new JSONObject(ResponeOnline);
                    JSONObject result = main_object.getJSONObject("result");

                    JSONArray intro = result.getJSONArray("intro");

                    for (int i = 0; i < intro.length(); i++,count++) {
                        JSONObject object = intro.getJSONObject(i);
                        String image = object.getString("image");
                        String title = object.getString("title");
                        String desc = object.getString("desc");

                        itemIntroList.add(new item_intro(image,title,desc));
                        recyclerViewPager.setLayoutManager(layout);
                        recyclerViewPager.setItemAnimator(new DefaultItemAnimator());

                        if (!object.isNull("btn")){
                            JSONArray btnArray = object.getJSONArray("btn");
                            for (int j = 0; j < btnArray.length(); j++) {
                                JSONObject btnObject = btnArray.getJSONObject(i);
                                String action = btnObject.getString("action");
                                String titleBtn = btnObject.getString("title");

                                switch (action){
                                    case "next":
                                        next = titleBtn;
                                        break;
                                    case "prev":
                                        pravs = titleBtn;
                                        break;
                                    case "start":
                                        next = titleBtn;

                                        break;
                                }

                            }
                        }

                        if (i == 0){
                            nex.setText(next);
                            nex.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (recyclerViewPager.getCurrentPosition() == 0) {
                                        if (prav.getVisibility() != View.INVISIBLE) {
                                            prav.setVisibility(View.INVISIBLE);
                                        }
                                        recyclerViewPager.scrollToPosition(recyclerViewPager.getCurrentPosition() + 1);
                                    } else {
                                        if (prav.getVisibility() != View.VISIBLE) {
                                            prav.setVisibility(View.VISIBLE);
                                        }
                                        recyclerViewPager.scrollToPosition(recyclerViewPager.getCurrentPosition() + 1);
                                    }

                                    if (recyclerViewPager.getCurrentPosition() == count){
                                        nex.setText(skip);
                                        if (prav.getVisibility() != View.VISIBLE){
                                            prav.setVisibility(View.VISIBLE);
                                        }
                                        SaveManager.get(getApplicationContext()).change_intriOpen(true);
                                        finish();
                                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                    }
                                }
                            });
                            prav.setText(pravs);
                            prav.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (recyclerViewPager.getCurrentPosition() == 0) {
                                        if (prav.getVisibility() != View.INVISIBLE) {
                                            prav.setVisibility(View.INVISIBLE);
                                        }
                                    } else {
                                        if (prav.getVisibility() != View.VISIBLE) {
                                            prav.setVisibility(View.VISIBLE);
                                        }
                                        recyclerViewPager.scrollToPosition(recyclerViewPager.getCurrentPosition() - 1);
                                    }
                                }
                            });
                        }

                    }





                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onGetJson_error(String error) {
                finish();
                startActivity(new Intent(getApplicationContext(),errorNet.class));
            }

        });
    }


}