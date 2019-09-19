package com.ermile.khadijehapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.ermile.khadijehapp.Adaptor.Adaptor_del;
import com.ermile.khadijehapp.Item.item_del;
import com.ermile.khadijehapp.R;
import com.ermile.khadijehapp.api.apiV6;
import com.ermile.khadijehapp.utility.SaveManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Delneveshte extends AppCompatActivity {

    List<item_del> itemDels;
    RecyclerView recyclerView;
    Adaptor_del adaptorDel;
    LinearLayoutManager LayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delneveshte);

        itemDels = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview_del);
        adaptorDel = new Adaptor_del(this,itemDels);
        LayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        recyclerView.setAdapter(adaptorDel);

        String apikey = SaveManager.get(this).getstring_appINFO().get(SaveManager.apiKey);

        apiV6.del(apikey,new apiV6.delListener() {
            @Override
            public void result(String respone) {
                try {
                    JSONArray array = new JSONArray(respone);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String id = object.getString("id");
                        String content = object.getString("content");
                        String plus = object.getString("plus");

                        itemDels.add(new item_del(content,"70"+i,plus,"",id));
                        recyclerView.setLayoutManager(LayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {

            }
        });


    }
}
