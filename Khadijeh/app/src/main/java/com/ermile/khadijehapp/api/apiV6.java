package com.ermile.khadijehapp.api;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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

    public static void app(String url ,final appListener appListener){

        StringRequest mainRQ = new StringRequest(Request.Method.GET,url, new Response.Listener<String>(){
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
                            case "banner":
                                String baner_image = object_homepage.getString("image");
                                String baner_url = object_homepage.getString("url");
                                appListener.lestener_baner(baner_image,baner_url);
                                break;
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

                            case "inapplink":
                            case "titlelink":
                                String titlelink_title = object_homepage.getString("title");
                                String titlelink_url = object_homepage.getString("link");
                                appListener.lestener_title_link(titlelink_title,null,titlelink_url);
                                break;

                            case "title":
                                String titleNONE_title = object_homepage.getString("title");
                                appListener.lestener_title_none(titleNONE_title);
                                break;

                            case "salawat":
                                String count_salawat = null;
                                if (!object_homepage.isNull("fit_count")){
                                    count_salawat =  object_homepage.getString("fit_count");
                                }
                                else if (!object_homepage.isNull("count")){
                                    count_salawat = String.valueOf(object_homepage.getInt("count"));
                                }
                                if (!object_homepage.isNull("")){}
                                appListener.lestener_salavat(count_salawat);
                                break;

                            case "news":
                                JSONArray news = object_homepage.getJSONArray("news");
                                appListener.lestener_news(String.valueOf(news));
                                break;

                            case "hr":
                                appListener.lestener_hr();
                                break;

                            case "change_language":
                                appListener.lestener_language();
                                break;
                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                    appListener.error();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                appListener.error();
            }
        });mainRQ.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Network.getInstance().addToRequestQueue(mainRQ);
    }

    public interface appListener{
        void lestener_baner(String image,String url);
        void lestener_link_1(String image,String url);
        void lestener_link_2(String link2Array);
        void lestener_link_3_desc(String title,String desc,String image,String url);
        void lestener_link_4(String link4Array);
        void lestener_title_link(String title,String image,String url);
        void lestener_title_none(String title);
        void lestener_salavat(String count);
        void lestener_hadith();
        void lestener_slider(String respone);
        void lestener_news(String newsArray);
        void lestener_hr();
        void lestener_language();
        void error();
    }


    /*List News*/

    public static void listNews(String url,String limit ,final listNewsListener listNewsListener){
        StringRequest mainRQ = new StringRequest(Request.Method.GET,url+"?limit="+limit, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObject = new JSONObject(response);
                    JSONArray result = mainObject.getJSONArray("result");
                    listNewsListener.lestener_news(String.valueOf(result));


                } catch (JSONException e) {
                    e.printStackTrace();
                    listNewsListener.error();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                listNewsListener.error();
            }
        });mainRQ.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Network.getInstance().addToRequestQueue(mainRQ);
    }

    public interface listNewsListener{
        void lestener_news(String newsArray);
        void error();
    }

    /*Salavat*/
    public static void salawat(String url,final String apikey, final salawatListener salawatListener){
        StringRequest salawayRQ = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObject = new JSONObject(response);

                    boolean ok = mainObject.getBoolean("ok");
                    if (ok){
                        JSONArray msg = mainObject.getJSONArray("msg");
                        JSONObject result = mainObject.getJSONObject("result");
                        String count_salawat = null;
                        if (!result.isNull("fit_count")){
                            count_salawat =  result.getString("fit_count");
                        }
                        else if (!result.isNull("count")){
                            count_salawat = String.valueOf(result.getInt("count"));
                        }
                        salawatListener.saveSalawat(count_salawat, String.valueOf(msg));
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
        };salawayRQ.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Network.getInstance().addToRequestQueue(salawayRQ);

    }

    public interface salawatListener{
        void saveSalawat(String count,String msgArray);
        void errorSalawat(String error);
    }

    /*News*/
    public static void news(String url,String id,final newsLinstener newsLinstener ){
        StringRequest newsRQ = new StringRequest(Request.Method.GET, url+id, new Response.Listener<String>(){
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
        newsRQ.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Network.getInstance().addToRequestQueue(newsRQ);
    }

    public interface newsLinstener{
        void resultValueNes(String respone);
        void resultGaleryNws(String responeArray);
        void failedValueNes(String error);
    }


    public static void del(String url,final String apikey, final delListener delListener){
        StringRequest getDelRQ = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObject = new JSONObject(response);
                    boolean ok = mainObject.getBoolean("ok");
                    if (ok){
                        JSONArray result = mainObject.getJSONArray("result");
                        delListener.result(String.valueOf(result));
                    }else {
                        delListener.error("ok: "+ok);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    delListener.error("JSONException: "+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                delListener.error("VolleyError: "+e);
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
        };getDelRQ.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Network.getInstance().addToRequestQueue(getDelRQ);
    }

    public interface delListener{
        void result(String respone);
        void error(String error);
    }


    public static void like_del(String url,final String apikey, final String id, final likeListener likeListener){
        StringRequest likeDelRQ = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObject = new JSONObject(response);

                    boolean ok = mainObject.getBoolean("ok");
                    if (ok){
                        JSONArray msg = mainObject.getJSONArray("msg");
                        likeListener.liked(String.valueOf(msg));
                    }else {
                        JSONArray msg = mainObject.getJSONArray("msg");
                        likeListener.filed(String.valueOf(msg));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    likeListener.filed("JSONException: "+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                likeListener.filed("onErrorResponse: "+e);
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

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> body = new HashMap<>();
                body.put("like", id);
                return body;
            }
        };likeDelRQ.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Network.getInstance().addToRequestQueue(likeDelRQ);
    }

    public interface likeListener{
        void liked(String respone);
        void filed(String error);
    }


    public static void sendDel(String url, final String apikey, final String name, final String text, final String mobile, final String switchGender, final sendelListener sendelListener){
        StringRequest sendDelRQ = new StringRequest(Request.Method.POST, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObject = new JSONObject(response);

                    boolean ok = mainObject.getBoolean("ok");
                    if (ok){
                        JSONArray msg = mainObject.getJSONArray("msg");
                        sendelListener.result(String.valueOf(msg));
                    }else {
                        JSONArray msg = mainObject.getJSONArray("msg");
                        sendelListener.error(String.valueOf(msg));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    sendelListener.error("JSONException: "+e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                sendelListener.error("onErrorResponse: "+e);
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

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> body = new HashMap<>();
                body.put("desc",text);
                if (mobile !=null){
                    body.put("mobile",mobile);
                }
                if (switchGender !=null){
                    body.put("switchGender",switchGender);
                }
                if (switchGender !=null){
                    body.put("name",name);
                }
                return body;
            }
        };
        sendDelRQ.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Network.getInstance().addToRequestQueue(sendDelRQ);
    }

    public interface sendelListener{
        void result(String respone);
        void error(String error);
    }


    public static void getLanguage(String url,final languageListener languageListener){

        StringRequest getLangRQ = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                languageListener.result(String.valueOf(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                languageListener.error("VolleyError: "+e);
            }
        });
        getLangRQ.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Network.getInstance().addToRequestQueue(getLangRQ);

    }

    public interface languageListener{
        void result(String respone);
        void error(String error);
    }

    public static void api(String url,final apiListener apiListener){

        StringRequest apiRQ = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                apiListener.result(String.valueOf(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                apiListener.error("VolleyError: "+e);
            }
        });
        apiRQ.setRetryPolicy(new DefaultRetryPolicy(5 * 1000, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Network.getInstance().addToRequestQueue(apiRQ);

    }

    public interface apiListener{
        void result(String respone);
        void error(String error);
    }

}
