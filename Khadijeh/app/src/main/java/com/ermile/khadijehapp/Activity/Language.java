package com.ermile.khadijehapp.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ermile.khadijehapp.Adaptor.LanguageAdaptor;
import com.ermile.khadijehapp.Item.item_Language;
import com.ermile.khadijehapp.R;
import com.ermile.khadijehapp.api.apiV6;
import com.ermile.khadijehapp.utility.Dialog;
import com.ermile.khadijehapp.utility.SaveManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public class Language extends AppCompatActivity {


    RecyclerView relv_Language;
    List<item_Language> mItem = new ArrayList<>();
    LanguageAdaptor mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);

        String url = getString(R.string.url_language);

        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        /*add RecyclerView and Adapter*/
        relv_Language = findViewById(R.id.lv_Language);
        mAdapter = new LanguageAdaptor(mItem, this);

        /*Set*/
        LinearLayoutManager sLayoutManager = new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        relv_Language.setLayoutManager(sLayoutManager);
        relv_Language.setItemAnimator(new DefaultItemAnimator());
        relv_Language.setHasFixedSize(true);
        relv_Language.setAdapter(mAdapter);
        GetLanguage(url);
    }

    /*Get Language*/
    void GetLanguage(String url) {
        apiV6.getLanguage(url,new apiV6.languageListener() {
            @Override
            public void result(String respone) {
                String appLanguage = SaveManager.get(getApplicationContext()).getstring_appINFO().get(SaveManager.appLanguage);
                try {
                    JSONObject jsonOffline = new JSONObject(respone);
                    boolean ok = jsonOffline.getBoolean("ok");
                    JSONObject result = jsonOffline.getJSONObject("result");
                    JSONObject lang_list = jsonOffline.getJSONObject("result");
                    Iterator<?> keys = lang_list.keys();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        JSONObject lang_key = lang_list.getJSONObject(key);
                        if (lang_list.get(key) instanceof JSONObject) {
                            if (appLanguage == lang_key.getString("name")) {
                                mItem.add(new item_Language(
                                        lang_key.getString("localname"),
                                        lang_key.getString("name"),
                                        View.VISIBLE));
                                mAdapter.notifyDataSetChanged();
                            } else {
                                mItem.add(new item_Language(
                                        lang_key.getString("localname"),
                                        lang_key.getString("name"),
                                        View.INVISIBLE));
                                mAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {
                Intent getintent = getIntent();
                new Dialog(Language.this,getString(R.string.errorNet_title_snackBar),"",getString(R.string.errorNet_button_snackBar),false,getintent);
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }
}
