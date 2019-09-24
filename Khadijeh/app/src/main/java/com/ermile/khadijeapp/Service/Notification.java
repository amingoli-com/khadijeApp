package com.ermile.khadijeapp.Service;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.ermile.khadijeapp.R;
import com.ermile.khadijeapp.Static.tag;
import com.ermile.khadijeapp.utility.Network;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Notification extends Service {

    /*Handler 60sec*/
    boolean powerServic = false;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (powerServic){
                Log.d(tag.service_notification, "------------------------------------ runnable");
                handler.postDelayed(runnable, 30000);
            }
        }
    };



    public static final String CHANNEL_ID = "ForegroundServiceChannel";
    NotificationCompat.Builder builder ;
    NotificationManagerCompat notificationManager ;


    @Override
    public IBinder onBind(Intent intent) {
        Log.d(tag.service_notification, "onBind"+intent);
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(tag.service_notification, "onStartCommand BEFORE \n powerServic = "+powerServic);
        createNotificationChannel();
        if (!powerServic){
            Log.d(tag.service_notification, "------------------------------------ ------------------------------------onStartCommand  powerServic = "+powerServic);
            powerServic = true;
            handler.postDelayed(runnable, 0);
        }
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        Log.d(tag.service_notification, "onDestroy");
        super.onDestroy();
        Toast.makeText(this, "Service destroyed by user.", Toast.LENGTH_LONG).show();
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }


    // Notification send header and user Token > new Notif for me?
    public void post_smile(String url_postSmail, final String url_nitif, final String apikey){
        // Json <Post Smile> Method
        StringRequest PostSmile_Request = new StringRequest(Request.Method.POST, this.getString(R.string.smile), new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject get_postRequest = new JSONObject(response);

                    // Check New Notif
                    boolean ok_notif = get_postRequest.getBoolean("ok");
                    if (ok_notif){
                        JSONObject result = get_postRequest.getJSONObject("result");
                        boolean new_notif = result.getBoolean("notif_new");
                        if (new_notif){
                            Notif_is(url_nitif,apikey);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
                // Send Header
        {
            @Override
            public Map<String, String> getHeaders()  {
                HashMap<String, String> headers_postSmile = new HashMap<>();
                headers_postSmile.put("apikey", apikey);
                return headers_postSmile;
            }
        }
                ;
        Network.getInstance().addToRequestQueue(PostSmile_Request);
    }

    // get Notification and run for user > Yes Notif is ..
    public void Notif_is(String url, final String apikey){
        // Post Method
        StringRequest Notif_is_Request = new StringRequest(Request.Method.POST, url , new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject mainObject = new JSONObject(response);
                    boolean ok_getnotif = mainObject.getBoolean("ok");
                    if (ok_getnotif) {
                        JSONArray result = mainObject.getJSONArray("result");
                        for (int j = 0; j < result.length(); j++) {
                            JSONObject jsonObject = result.getJSONObject(j);

                            String title = jsonObject.getString("title");
                            String desc = jsonObject.getString("text");
                            String info = getApplicationContext().getString(R.string.app_name);

                            send_Notif(title,desc,info);
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        })
                // Send Headers
        {
            @Override
            public Map<String, String> getHeaders()  {
                HashMap<String, String> headers_postSmile = new HashMap<>();
                headers_postSmile.put("apikey",apikey );
                return headers_postSmile;
            }
        };
        Network.getInstance().addToRequestQueue(Notif_is_Request);
    }

    private void send_Notif(String title,String desc,String info){
        builder = new NotificationCompat.Builder(this,CHANNEL_ID);

        notificationManager = NotificationManagerCompat.from(getApplicationContext());

        builder.setContentTitle(title)
                .setContentText(desc)
                .setStyle(new NotificationCompat
                        .BigTextStyle()
                        .bigText(desc))
                .setContentInfo(info)
                .setSmallIcon(R.drawable.logo_xml);
        notificationManager.notify(100, builder.build());
    }
}
