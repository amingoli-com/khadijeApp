package com.ermile.khadijehapp.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ermile.khadijehapp.Adaptor.Adaptor_del;
import com.ermile.khadijehapp.Item.item_del;
import com.ermile.khadijehapp.R;
import com.ermile.khadijehapp.api.apiV6;
import com.ermile.khadijehapp.utility.Database;
import com.ermile.khadijehapp.utility.SaveManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nex3z.togglebuttongroup.button.OnCheckedChangeListener;
import com.nex3z.togglebuttongroup.button.ToggleButton;

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
    boolean men = false;
    String sex = "famale";

    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delneveshte);

        progressBar = findViewById(R.id.progress_del);


        final LinearLayout send_del_acdel = findViewById(R.id.send_del_acdel);

        send_del_acdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDelneveshte();
            }
        });


        itemDels = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview_del);
        adaptorDel = new Adaptor_del(this,itemDels);
        LayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setAdapter(adaptorDel);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(10);

        String url = getString(R.string.url_del);
        String apikey = SaveManager.get(this).getstring_appINFO().get(SaveManager.apiKey);

        apiV6.del(url,apikey,new apiV6.delListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void result(String respone) {
                try {
                    JSONArray array = new JSONArray(respone);
                    for (int i = 0; i < array.length(); i++) {
                        String plus = "0";
                        String name = null;
                        JSONObject object = array.getJSONObject(i);
                        String id = object.getString("id");
                        String sex = object.getString("gender");
                        String content = object.getString("content");
                        if (!object.isNull("plus")){
                            plus = object.getString("plus");
                        }
                        if (!object.isNull("name")){
                            name = object.getString("name");
                        }

                        SQLiteDatabase databases = new Database(getApplicationContext()).getReadableDatabase();
                        @SuppressLint("Recycle") Cursor checkID_del = databases.rawQuery(Database.select_del(id),null);

                        if (checkID_del.getCount() == 0){
                            itemDels.add(new item_del(name,content,null,plus,sex,id,false));
                            recyclerView.setLayoutManager(LayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                        }else {
                            itemDels.add(new item_del(name,content,null,plus,sex,id,true));
                            recyclerView.setLayoutManager(LayoutManager);
                            recyclerView.setItemAnimator(new DefaultItemAnimator());
                        }
                        databases.close();
                        checkID_del.close();



                    }
                    send_del_acdel.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void error(String error) {
                finish();
                startActivity(new Intent(getApplicationContext(),errorNet.class));

            }
        });


    }


    private void sendDelneveshte() {

        LayoutInflater aInflater = getLayoutInflater();
        View alertLayout = aInflater.inflate(R.layout.add_del, null);

        final EditText name = alertLayout.findViewById(R.id.addel_edt_name);
        final EditText number = alertLayout.findViewById(R.id.addel_edt_number);
        final EditText text = alertLayout.findViewById(R.id.addel_edt_text);

        final ToggleButton a = alertLayout.findViewById(R.id.choice_a);
        final ToggleButton b = alertLayout.findViewById(R.id.choice_b);
        a.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public <T extends View & Checkable> void onCheckedChanged(T view, boolean isChecked) {

                if (isChecked){
                    men = false;
                    b.setChecked(false);
                }
            }
        });

        b.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public <T extends View & Checkable> void onCheckedChanged(T view, boolean isChecked) {

                if (isChecked){
                    men = true;
                    a.setChecked(false);
                }
            }
        });

        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setView(alertLayout)
                .setPositiveButton(getString(R.string.send), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (men){
                            sex = "male";
                        }else {
                            sex = "famale";
                        }

                        String names = name.getText().toString();
                        String numbers = number.getText().toString();
                        String texts = text.getText().toString();
                        add_del(names,numbers,texts,sex);

                    }
                })

                .create()
                .show();

    }

    private void add_del(String name,String number,String text,String sexs){
        String url = getString(R.string.url_add_del);
        String apikey = SaveManager.get(this).getstring_appINFO().get(SaveManager.apiKey);
        apiV6.sendDel(url, apikey,name, text, number, sexs, new apiV6.sendelListener() {
            @Override
            public void result(String respone) {
                try {
                    JSONArray array = new JSONArray(respone);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.getJSONObject(i);
                        String text = object.getString("text");
                        Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT).show();
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
