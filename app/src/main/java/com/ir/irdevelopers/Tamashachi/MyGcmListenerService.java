package com.ir.irdevelopers.Tamashachi;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import com.android.volley.RequestQueue;
import com.google.android.gms.gcm.GcmListenerService;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.Random;


/**
 * Created by Alip on 5/10/2016.
 */
public class MyGcmListenerService extends GcmListenerService {
    private Bitmap largeIcon;
    private RequestQueue reQueue;
    private Context context;
    private Intent notificationIntent;

//    public static final int MESSAGE_NOTIFICATION_ID = ;

    @Override
    public void onMessageReceived(String from, Bundle data) {
//        Bundle notification = data.getBundle("data");
        String body = data.getString("body");
        String title = data.getString("title");
        String icon = data.getString("icon");

        createNotification(body,title,icon);
    }

    // Creates notification based on title and body received
    private void createNotification(final String title, final String body, String icon) {
        context = getBaseContext();

        notificationIntent = new Intent(context, MainActivity.class);

        PendingIntent intent = PendingIntent.getActivity(context, 0,
                notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);


        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher).setContentTitle(title)
                .setLargeIcon(getBitmapFromURL(".jpg"))
                .setContentText(body)
                .setContentIntent(intent)
                .setSound(alarmSound);
        NotificationManager mNotificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);

        Random random = new Random();
        mNotificationManager.notify(random.nextInt(), mBuilder.build());

    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
