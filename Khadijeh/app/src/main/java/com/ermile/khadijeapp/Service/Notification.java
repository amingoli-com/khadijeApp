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

import com.ermile.khadijeapp.R;
import com.ermile.khadijeapp.Static.tag;

public class Notification extends Service {

    /*Handler 60sec*/
    boolean powerServic = false;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (powerServic){
                Log.d(tag.service_notification, "------------------------------------ runnable");
                post_smile();
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


    private void post_smile(){
        Log.d(tag.service_notification, "------------------------------------ post_smile VOID");
        send_Notif("test",
                "defencelessness's\n" +
                "defencelessness's\n" +
                "defencelessness's\n" +
                "defencelessness's","info");
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
