package com.ermile.khadijehapp.api;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ermile.khadijehapp.Static.lookServer;
import com.ermile.khadijehapp.Static.url;
import com.ermile.khadijehapp.utility.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class apiV6 {

    public static void app(final appListener appListener){
        StringRequest getToken = new StringRequest(Request.Method.GET, url.app, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObject = new JSONObject(response);
                    JSONObject result = mainObject.getJSONObject("result");
                    JSONArray homepage = result.getJSONArray("homepage");
                    for (int i = 0; i < homepage.length(); i++) {
                        JSONObject object_homepage = homepage.getJSONObject(i);
                        String type = object_homepage.getString("type");

                        switch (type){
                            case "slider":
                                JSONArray slider_homepage = object_homepage.getJSONArray("slider");
                                appListener.lestener_slider(String.valueOf(slider_homepage));
                                break;
                            case "link1":
                                String image_link1 = object_homepage.getString("image");
                                String url_link1 = object_homepage.getString("url");
                                appListener.lestener_link_1(image_link1,url_link1);
                                break;

                            case "link2":
                                JSONArray link2_homepage = object_homepage.getJSONArray("link");
                                appListener.lestener_link_2(String.valueOf(link2_homepage));
                                break;

                            case "linkdesc":
                                String title,desc,image,url;
                                title = object_homepage.getString("title");
                                desc = object_homepage.getString("desc");
                                image = object_homepage.getString("image");
                                url = object_homepage.getString("url");
                                appListener.lestener_link_3_desc(title,desc,image,url);
                                break;

                            case "link4":
                                JSONArray link4_homepage = object_homepage.getJSONArray("link");
                                appListener.lestener_link_4(String.valueOf(link4_homepage));
                                break;

                            case "titlelink":
                                String titlelink_title,titlelink_image,titlelink_url;
                                titlelink_title = object_homepage.getString("title");
                                titlelink_image = object_homepage.getString("image");
                                titlelink_url = object_homepage.getString("url");
                                appListener.lestener_title_link(titlelink_title,titlelink_image,titlelink_url);
                                break;

                            case "title":
                                String titleNONE_title = object_homepage.getString("title");
                                appListener.lestener_title_none(titleNONE_title);
                                break;

                            case "salawat":
                                int count = object_homepage.getInt("count");
                                appListener.lestener_salavat(count);
                                break;

                            case "news":
                                JSONArray news = object_homepage.getJSONArray("news");
                                appListener.lestener_news(String.valueOf(news));
                                break;

                            case "hr":
                                appListener.lestener_hr();
                                break;
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
            }
        });
        Network.getInstance().addToRequestQueue(getToken);
    }

    public interface appListener{
        void lestener_link_1(String image,String url);
        void lestener_link_2(String link2Array);
        void lestener_link_3_desc(String title,String desc,String image,String url);
        void lestener_link_4(String link4Array);
        void lestener_title_link(String title,String image,String url);
        void lestener_title_none(String title);
        void lestener_salavat(int count);
        void lestener_hadith();
        void lestener_slider(String respone);
        void lestener_news(String newsArray);
        void lestener_hr();
    }

    /*Salavat*/

    public static void salawat(final String apikey, final salawatListener salawatListener){
        StringRequest getToken = new StringRequest(Request.Method.POST, url.salawat, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObject = new JSONObject(response);

                    boolean ok = mainObject.getBoolean("ok");
                    if (ok){
                        JSONArray msg = mainObject.getJSONArray("msg");
                        JSONObject result = mainObject.getJSONObject("result");
                        int count = result.getInt("count");
                        salawatListener.saveSalawat(count, String.valueOf(msg));
                    }else {
                        JSONArray msg = mainObject.getJSONArray("msg");
                        salawatListener.errorSalawat(String.valueOf(msg));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    salawatListener.errorSalawat("JSONException: "+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                salawatListener.errorSalawat("onErrorResponse: "+e);
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("appkey", lookServer.appkey);
                if (apikey !=null){
                    headers.put("apikeey",apikey);
                }
                return headers;
            }
        };
        Network.getInstance().addToRequestQueue(getToken);

    }

    public interface salawatListener{
        void saveSalawat(int count,String msgArray);
        void errorSalawat(String error);
    }

    /*News*/
    public static void news(String id,final newsLinstener newsLinstener ){
        StringRequest getToken = new StringRequest(Request.Method.GET, url.news+id, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObject = new JSONObject(response);
                    boolean ok = mainObject.getBoolean("ok");
                    if (ok){
                        JSONObject result = mainObject.getJSONObject("result");
                        newsLinstener.resultValueNes(String.valueOf(result));
                        JSONObject meta = result.getJSONObject("meta");
                        if (!meta.isNull("gallery")){
                            JSONArray gallery = meta.getJSONArray("gallery");
                            newsLinstener.resultGaleryNws(String.valueOf(gallery));
                        }
                    }else {
                        newsLinstener.failedValueNes("ok: "+ok);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    newsLinstener.failedValueNes("JSONException: "+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                newsLinstener.failedValueNes("VolleyError: "+e);
            }
        });
        Network.getInstance().addToRequestQueue(getToken);
    }

    public interface newsLinstener{
        void resultValueNes(String respone);
        void resultGaleryNws(String responeArray);
        void failedValueNes(String error);
    }
}
