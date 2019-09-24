package com.ermile.khadijeapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ermile.khadijeapp.R;
import com.ermile.khadijeapp.Static.value;
import com.ermile.khadijeapp.utility.SaveManager;

import java.util.HashMap;
import java.util.Map;

public class Web_View extends AppCompatActivity {
    Map<String, String> sernd_headers = new HashMap<>();
    private String URL = null;
    int a = 0;

    SwipeRefreshLayout swipeRefreshLayout;
    WebView webView_object;

    String[] url_pay = {"https://khadije.com/pay/","https://khadije.com/ar/pay/","https://khadije.com/en/pay/","https://khadije.com/pay/"};
    String[] url_del = {"https://khadije.com/delneveshte","https://khadije.com/ar/delneveshte","https://khadije.com/en/delneveshte","https://khadije.com/delneveshte"};
    String[] url_news = {"https://khadije.com/blog/","https://khadije.com/ar/blog/","https://khadije.com/en/blog/","https://khadije.com/blog/"};
    String url_site = "https://khadije.com";


    @SuppressLint({"NewApi", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);


        String apikey = SaveManager.get(this).getstring_appINFO().get(SaveManager.apiKey);
        String usercode = SaveManager.get(this).getstring_appINFO().get(SaveManager.userCode);
        String zonid = SaveManager.get(this).getstring_appINFO().get(SaveManager.zoneID);


        URL = getIntent().getStringExtra("url");
        sernd_headers.put("x-app-request", "android");
        sernd_headers.put("apikey",apikey);
        sernd_headers.put("usercode",usercode);
        sernd_headers.put("zonid",zonid);
        sernd_headers.put("versionCode",String.valueOf(value.versionCode));
        sernd_headers.put("versionName",value.versionName);

        swipeRefreshLayout = findViewById(R.id.swipRefresh_WebView);
        webView_object = findViewById(R.id.webView_WebView);
        WebSettings webSettings = webView_object.getSettings();
        webSettings.setJavaScriptEnabled(true);




        if (URL != null){
            swipeRefreshLayout.setRefreshing(true);
            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @SuppressLint("NewApi")
                @Override
                public void onRefresh() {
                    webView_object.loadUrl(webView_object.getUrl(), sernd_headers);
                }
            });
            webView_object.loadUrl(URL, sernd_headers);
            webView_object.setWebViewClient(new WebViewClient() {
                @Override
                public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error){
                    finish();
                    if (a == 0){
                        Toast.makeText(Web_View.this, getString(R.string.errorNet_title_snackBar), Toast.LENGTH_SHORT).show();
                        a++;
                    }
                }
                // in refresh send header
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url, sernd_headers);


                    for (int i = 0; i < 3; i++) {
                        if (url.startsWith(url_pay[i])) {
                            Intent browser = new Intent(Intent.ACTION_VIEW);
                            browser.setData(Uri.parse(url));
                            startActivity(browser);
                            finish();
                            return true;
                        }
                        else if (!url.substring(0,19).startsWith(url_site)){
                            Intent browser = new Intent(Intent.ACTION_VIEW);
                            browser.setData(Uri.parse(url));
                            startActivity(browser);
                            finish();
                        }
                        else if (url.startsWith(url_del[i])){
                            startActivity(new Intent(Web_View.this,Delneveshte.class));
                            finish();
                        }
                        else if ((url.startsWith(url_news[i])))
                        {
                            startActivity(new Intent(Web_View.this,ListNews.class));
                            finish();
                        }
                    }


                    return false;
                }
                @Override
                public void onPageFinished(WebView view, String url) {
                    swipeRefreshLayout.setRefreshing(false);
                }});
        }
        else {
            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void onBackPressed() {
        if(webView_object.canGoBack())
        {
            webView_object.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
}