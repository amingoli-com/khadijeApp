package com.ermile.khadijeapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ermile.khadijeapp.R;
import com.ermile.khadijeapp.Static.value;
import com.ermile.khadijeapp.utility.SaveManager;
import com.ermile.khadijeapp.utility.set_language_device;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
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


    private String mCM;
    private ValueCallback<Uri> mUM;
    private ValueCallback<Uri[]> mUMA;
    private final static int FCR = 1;
    private final static int FILECHOOSER_RESULTCODE = 1;


    @Override
    protected void onResume() {
        new set_language_device(this);
        super.onResume();
    }

    @SuppressLint({"NewApi", "SetJavaScriptEnabled"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        URL = getIntent().getStringExtra("url");
        try {
            setContentView(R.layout.activity_web_view);


            String apikey = SaveManager.get(this).getstring_appINFO().get(SaveManager.apiKey);
            String usercode = SaveManager.get(this).getstring_appINFO().get(SaveManager.userCode);
            String zonid = SaveManager.get(this).getstring_appINFO().get(SaveManager.zoneID);



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
            webSettings.setAllowFileAccess(true);
            webSettings.setAllowFileAccess(true);
            webSettings.setAllowContentAccess(true);




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
                webView_object.setWebChromeClient(new WebChromeClient(){

                    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                        if (mUMA != null) {
                            mUMA.onReceiveValue(null);
                        }
                        mUMA = filePathCallback;
                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (takePictureIntent.resolveActivity(Web_View.this.getPackageManager()) != null) {
                            File photoFile = null;
                            try {
                                photoFile = createImageFile();
                                takePictureIntent.putExtra("PhotoPath", mCM);
                            } catch (IOException ex) {
                                Log.e("Webview", "Image file creation failed", ex);
                            }
                            if (photoFile != null) {
                                mCM = "file:" + photoFile.getAbsolutePath();
                                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                            } else {
                                takePictureIntent = null;
                            }
                        }

                        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
                        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
                        contentSelectionIntent.setType("*/*");
                        Intent[] intentArray;
                        if (takePictureIntent != null) {
                            intentArray = new Intent[]{takePictureIntent};
                        } else {
                            intentArray = new Intent[0];
                        }

                        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
                        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
                        chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser");
                        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray);
                        startActivityForResult(chooserIntent, FCR);
                        return true;
                    }

                });
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

                        if (url.startsWith("tel:")) {
                            Intent intent = new Intent(Intent.ACTION_DIAL,
                                    Uri.parse(url));
                            startActivity(intent);
                        }else {
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
                        }

                        return false;
                    }
                    @Override
                    public void onPageFinished(WebView view, String url) {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
            else {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e){
            Intent brower = new Intent(Intent.ACTION_VIEW);
            brower.setData(Uri.parse(URL));
            startActivity(brower);
            finish();
        }
    }


    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        this.openFileChooser(uploadMsg, "*/*");
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        this.openFileChooser(uploadMsg, acceptType, null);
    }

    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType("*/*");
        Web_View.this.startActivityForResult(Intent.createChooser(i, "File Browser"), FILECHOOSER_RESULTCODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (Build.VERSION.SDK_INT >= 21) {
            Uri[] results = null;
            //Check if response is positive
            if (resultCode == Activity.RESULT_OK) {
                if (requestCode == FCR) {
                    if (null == mUMA) {
                        return;
                    }
                    if (intent == null) {
                        //Capture Photo if no image available
                        if (mCM != null) {
                            results = new Uri[]{Uri.parse(mCM)};
                        }
                    } else {
                        String dataString = intent.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{Uri.parse(dataString)};
                        }
                    }
                }
            }
            mUMA.onReceiveValue(results);
            mUMA = null;
        } else {
            if (requestCode == FCR) {
                if (null == mUM) return;
                Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
                mUM.onReceiveValue(result);
                mUM = null;
            }
        }
    }

    // Create an image file
    private File createImageFile() throws IOException {
        @SuppressLint("SimpleDateFormat") String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "img_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(imageFileName, ".jpg", storageDir);


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