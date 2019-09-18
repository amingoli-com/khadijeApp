package com.ermile.khadijehapp.Activity;

import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.ermile.khadijehapp.R;
import com.ermile.khadijehapp.api.apiV6;

import org.json.JSONException;
import org.json.JSONObject;

public class News extends AppCompatActivity {

    TextView tite,text_news;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        final String ID = getIntent().getStringExtra("id");
        swipeRefreshLayout = findViewById(R.id.swipRefresh_news);
        tite = findViewById(R.id.title_news);
        text_news = findViewById(R.id.text_news);

        text_news.setMovementMethod(new ScrollingMovementMethod());

        swipeRefreshLayout.setRefreshing(true);
        getNews(ID);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getNews(ID);
            }
        });






    }


    private void getNews(String ID){
        apiV6.news(ID,new apiV6.newsLinstener() {
            @Override
            public void resultValueNes(String respone) {
                try {
                    JSONObject result = new JSONObject(respone);
                    String id = result.getString("id");
                    String excerpt = result.getString("excerpt");
                    String publishdate = result.getString("publishdate");
                    String content = result.getString("content");
                    Spanned html_content = Html.fromHtml(content);
                    String title = result.getString("title");
                    String link = result.getString("link");

                    JSONObject meta = result.getJSONObject("meta");
                    String thumb = meta.getString("thumb");

                    swipeRefreshLayout.setRefreshing(false);

                    tite.setText(title);
                    text_news.setText(html_content);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void resultGaleryNws(String responeArray) {

            }

            @Override
            public void failedValueNes(String error) {

            }
        });
    }
}
