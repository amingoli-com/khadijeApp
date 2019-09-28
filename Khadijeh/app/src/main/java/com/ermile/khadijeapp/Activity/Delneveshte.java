package com.ermile.khadijeapp.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ermile.khadijeapp.Adaptor.Adaptor_del;
import com.ermile.khadijeapp.Item.item_del;
import com.ermile.khadijeapp.R;
import com.ermile.khadijeapp.api.apiV6;
import com.ermile.khadijeapp.utility.Dialog;
import com.ermile.khadijeapp.utility.SaveManager;
import com.nex3z.togglebuttongroup.button.OnCheckedChangeListener;
import com.nex3z.togglebuttongroup.button.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Delneveshte extends AppCompatActivity {

    RecyclerView recyclerView;
    Adaptor_del adaptorDel;
    LinearLayoutManager LayoutManager;
    List<item_del> itemDels;
    boolean men = false;
    String sex = "famale";
    ProgressBar progressBar;
    LinearLayout send_del_acdel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delneveshte);

        String url = getString(R.string.url_del);
        itemDels = new ArrayList<>();
        recyclerView = findViewById(R.id.recyclerview_del);
        adaptorDel = new Adaptor_del(this,itemDels);
        LayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setAdapter(adaptorDel);




        progressBar = findViewById(R.id.progress_del);
        send_del_acdel = findViewById(R.id.send_del_acdel);
        send_del_acdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendDelneveshte();
            }
        });

        String apikey = SaveManager.get(this).getstring_appINFO().get(SaveManager.apiKey);

        apiV6.del(url,apikey,new apiV6.delListener() {
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

                        itemDels.add(new item_del(name,content,null,plus,sex,id,false));
                        recyclerView.setLayoutManager(LayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());




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
                Intent getintent = getIntent();
                new Dialog(Delneveshte.this,getString(R.string.errorNet_title_snackBar),"",getString(R.string.errorNet_button_snackBar),false,getintent);

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
                Toast.makeText(Delneveshte.this, getString(R.string.errorNet_title_snackBar), Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
