package com.ermile.khadijeapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ermile.khadijeapp.R;

public class status extends AppCompatActivity {

    String status,amount = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);

        finish();
       /* Uri data = getIntent().getData();
        Uri uri = Uri.parse(String.valueOf(data));
        if (uri.getQueryParameter("status") != null){
            status = uri.getQueryParameter("status");
            amount = uri.getQueryParameter("amount");
            Toast.makeText(this, status+"|"+amount, Toast.LENGTH_SHORT).show();
        }*/
    }
}
