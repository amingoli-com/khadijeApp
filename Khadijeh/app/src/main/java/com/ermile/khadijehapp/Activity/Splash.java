package com.ermile.khadijehapp.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.ermile.khadijehapp.R;
import com.ermile.khadijehapp.api.GetAndroidDetail;
import com.ermile.khadijehapp.api.SingUpUser;
import com.ermile.khadijehapp.api.Token;
import com.ermile.khadijehapp.utility.CheckVersion;
import com.ermile.khadijehapp.utility.Dialog;
import com.ermile.khadijehapp.utility.SaveManager;

import java.io.IOException;
import java.util.Locale;

import static com.ermile.khadijehapp.utility.SaveManager.appLanguage;


public class Splash extends AppCompatActivity {

    Handler handler = new Handler();
    Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        setAppLanguage();

    }


    private void setAppLanguage(){
        String AppLanguage = SaveManager.get(this).getstring_appINFO().get(appLanguage);
        if (AppLanguage == null){
            setFirstLanguages();
        }
        else {
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
                setSettingApp();
                break;
            default:
                SaveManager.get(this).change_appLanguage("en");
                SaveManager.get(this).change_LanguageByUser(true);
                setSettingApp();
                break;
        }
    }
    private void setSettingApp(){
        GetAndroidDetail.GetJson(new GetAndroidDetail.JsonLocalListener() {
            @Override
            public void onGetJson_Online(String ResponeOnline) {
                choseLanguage(ResponeOnline);
            }

            @Override
            public void onGetJson_error(String error) {
            }

        });
    }
    private void choseLanguage(String respone){
        Boolean changeLanguageByUser = SaveManager.get(this).getboolen_appINFO().get(SaveManager.changeLanguageByUser);
        if (changeLanguageByUser){
            handler.removeCallbacks(runnable);
            finish();
            startActivity( new Intent(this, Language.class));
        }else {
            if (!CheckVersion.Deprecated(this,respone)){
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
                    nextActivity();
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
                nextActivity();
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

    /*private void errorNet(String title,String desc,String btnTitle){
        new Dialog(Splash.this,title,desc,btnTitle,false,null);
    }*/


}
