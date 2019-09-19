package com.ermile.khadijehapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.ermile.khadijehapp.R;
import com.ermile.khadijehapp.api.apiV6;
import com.ermile.khadijehapp.utility.SaveManager;

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

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Add_Delneveshte.this, getText(), Toast.LENGTH_SHORT).show();
                Toast.makeText(Add_Delneveshte.this, getNumber(), Toast.LENGTH_SHORT).show();
                Toast.makeText(Add_Delneveshte.this, getSex(), Toast.LENGTH_SHORT).show();

                apiV6.sendDel(url,apikey, getText(), getNumber(), getSex(), new apiV6.sendelListener() {
                    @Override
                    public void result(String respone) {
                        Toast.makeText(Add_Delneveshte.this, respone, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void error(String error) {
                        finish();
                        startActivity(new Intent(getApplicationContext(),errorNet.class));

                    }
                });
            }
        });



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
