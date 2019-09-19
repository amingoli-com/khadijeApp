package com.ermile.khadijehapp.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ermile.khadijehapp.Static.url;
import com.ermile.khadijehapp.utility.Network;

import java.io.IOException;

public class GetAndroidDetail {


    public static void GetJson(final JsonLocalListener jsonLocalListener){
        StringRequest get_local = new StringRequest(Request.Method.GET, url.app, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                jsonLocalListener.onGetJson_Online(response);

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                jsonLocalListener.onGetJson_error("VolleyError: "+error);
            }
        });
        Network.getInstance().addToRequestQueue(get_local);

    }

    public interface JsonLocalListener {

        void onGetJson_Online(String ResponeOnline);

        void onGetJson_error(String error);
    }
}
