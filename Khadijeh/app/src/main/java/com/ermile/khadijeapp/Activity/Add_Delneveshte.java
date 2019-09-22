package com.ermile.khadijeapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ToggleButton;

import com.ermile.khadijeapp.R;
import com.ermile.khadijeapp.utility.SaveManager;

public class Add_Delneveshte extends AppCompatActivity {
    EditText text,number,name;
    ToggleButton sex;
    Button send;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delneveshte);

        final String url = getString(R.string.url_add_del);
        final String apikey = SaveManager.get(this).getstring_appINFO().get(SaveManager.apiKey);

        text=findViewById(R.id.edt_del);
        number = findViewById(R.id.number_del);
        sex = findViewById(R.id.sex_del);
        send = findViewById(R.id.send_del);


    }


    private String getText(){
        return text.getText().toString();
    }

    private String getNumber(){
        return number.getText().toString();
    }
    private String getSex(){
        return sex.getText().toString();
    }

}
