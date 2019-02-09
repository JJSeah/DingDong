package com.example.jeremyjohn.dingdong;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.os.Build;

public class NotificationHandler extends ContextWrapper {

    private NotificationManager notificationManager;

    public static final String WATCH_MOVIE_NOTIFICATION_CHANNEL_ID =
                                    "DingDongNotificationChannelID";

    public static final String WATCH_MOVIE_NOTIFICATION_CHANNEL_NAME =
                                    "Ding Dong Notification";



    public NotificationHandler(Context context){

        super(context);

        createNotificationChannel();

    }


    @TargetApi(Build.VERSION_CODES.O)
    public void createNotificationChannel(){

        NotificationChannel notificationChannel = new
                NotificationChannel(WATCH_MOVIE_NOTIFICATION_CHANNEL_ID,
                WATCH_MOVIE_NOTIFICATION_CHANNEL_NAME, notificationManager.IMPORTANCE_DEFAULT);

        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.setShowBadge(true);
        notificationChannel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        getNotificationManager().createNotificationChannel(notificationChannel);
    }


    private NotificationManager getNotificationManager(){

        if (notificationManager == null){
            notificationManager = (NotificationManager)getSystemService
                                    (Context.NOTIFICATION_SERVICE);
        }
        return notificationManager;
    }


    @TargetApi(Build.VERSION_CODES.O)
    public Notification.Builder createAndReturnWatchMovieNotification
                                (String titleText, String bodyText){
        return new Notification.Builder(getApplicationContext(),
                    WATCH_MOVIE_NOTIFICATION_CHANNEL_ID)
                    .setContentTitle(titleText)
                    .setContentText(bodyText)
                    .setSmallIcon(R.drawable.dingdong)
                    .setAutoCancel(true);
    }


    public void notifyTheUser (int notificationID, Notification.Builder notificationBuilder){

        getNotificationManager().notify(notificationID, notificationBuilder.build());

    }

}
