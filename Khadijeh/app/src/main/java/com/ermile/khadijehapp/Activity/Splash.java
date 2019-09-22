package com.ermile.khadijehapp.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.ermile.khadijehapp.R;
import com.ermile.khadijehapp.api.GetAndroidDetail;
import com.ermile.khadijehapp.api.SingUpUser;
import com.ermile.khadijehapp.api.Token;
import com.ermile.khadijehapp.utility.CheckVersion;
import com.ermile.khadijehapp.utility.Dialog;
import com.ermile.khadijehapp.utility.SaveManager;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;
import java.util.Locale;

import static com.ermile.khadijehapp.utility.SaveManager.appLanguage;


public class Splash extends AppCompatActivity {

    LinearLayout linearLayout;

    final Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            finish();
            startActivity(getIntent());

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setAppLanguage();
        linearLayout = findViewById(R.id.linear_splash);
    }


    private void setAppLanguage(){
        String AppLanguage = SaveManager.get(this).getstring_appINFO().get(appLanguage);
        if (AppLanguage == null){
            setFirstLanguages();
        }
        else {
            setLocale(AppLanguage);
            setSettingApp();
        }

    }
    private void setFirstLanguages(){
        String language_device = Locale.getDefault().getLanguage();
        switch (language_device){
            case "fa":
            case "ar":
                SaveManager.get(this).change_appLanguage(language_device);
                SaveManager.get(this).change_LanguageByUser(false);
                setLocale(language_device);
                setSettingApp();
                break;
            default:
                SaveManager.get(this).change_appLanguage("en");
                SaveManager.get(this).change_LanguageByUser(true);
                setSettingApp();
                break;
        }
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }
    private void setSettingApp(){
        GetAndroidDetail.GetJson(new GetAndroidDetail.JsonLocalListener() {
            @Override
            public void onGetJson_Online(String ResponeOnline) {
                choseLanguage(ResponeOnline);
            }

            @Override
            public void onGetJson_error(String error) {
                SnackBar();
            }

        });
    }
    private void choseLanguage(String respone){
        Boolean changeLanguageByUser = SaveManager.get(this).getboolen_appINFO().get(SaveManager.changeLanguageByUser);
        if (changeLanguageByUser){
            finish();
            startActivity( new Intent(this, Language.class));
        }else {
            if (!CheckVersion.Deprecated(Splash.this,getApplicationContext(),respone)){
                singUpTemp();
            }
        }
    }
    private Boolean userIsAdded() {
        String usercode = SaveManager.get(this).getstring_appINFO().get(SaveManager.userCode);
        String zoneid = SaveManager.get(this).getstring_appINFO().get(SaveManager.zoneID);
        String apikey = SaveManager.get(this).getstring_appINFO().get(SaveManager.apiKey);
        if (usercode == null || zoneid == null || apikey == null)
        {
            return false;
        }
        return true;
    }
    private void singUpTemp(){
        if ( !userIsAdded() ) {
            Token.GetToken(new Token.TokenListener() {
                @Override
                public void onTokenRecieved(String token) {
                    addUserTamp(token);
                }

                @Override
                public void onTokenFailed(String error) {
                    SnackBar();
                }
            });
        }
        else {
            nextActivity();
        }
    }
    private void addUserTamp(String Token){
        SingUpUser.Singing(new SingUpUser.SingUpTampListener() {
            @Override
            public void UserAddToServer(Boolean UserAddToServer) {
                nextActivity();
            }

            @Override
            public void FiledUserAdd(Boolean FiledUserAdd) {
                SnackBar();
            }
        }, getApplicationContext(), Token);
    }

    private void nextActivity() {
        handler.removeCallbacks(runnable);
        Boolean intro_isChecked = SaveManager.get(this).getboolen_appINFO().get(SaveManager.introIsChacked);
        if (intro_isChecked) {
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            finish();
            startActivity(new Intent(this, Intro.class));
        }
    }

    private void SnackBar() {
        Snackbar snackbar = Snackbar.make(linearLayout, getString(R.string.errorNet_title_snackBar), Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(R.string.errorNet_button_snackBar), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        startActivity(getIntent());
                    }
                });
        snackbar.setActionTextColor(Color.RED);
        snackbar.setDuration(10*1000);
        snackbar.show();
        handler.postDelayed(runnable,11*1000);


    }

}
