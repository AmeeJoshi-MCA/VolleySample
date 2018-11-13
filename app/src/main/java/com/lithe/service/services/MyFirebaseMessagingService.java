package com.lithe.service.services;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.volleySample.R;
import com.lithe.service.utils.Constants;

/**
 * Created by lithe on 05-Sep-17.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private final String TAG = "MyFirebaseMessagingService";

    public static final String KEY_IS_FROM_MESSAGE = "KEY_IS_FROM_MESSAGE";

    private static OnMessageListener onMessageListener;

    private int NOTIFICATION_ID = 1001;

    @SuppressLint("LongLogTag")
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (onMessageListener != null) {
            onMessageListener.onMessageReceive(remoteMessage);
        }
        sendNotification(remoteMessage);

        Log.i(TAG, "onMessageReceived()");
    }

    public static void setOnMessageListener(OnMessageListener onMessageListener) {
        MyFirebaseMessagingService.onMessageListener = onMessageListener;
    }

    public interface OnMessageListener {
        void onMessageReceive(RemoteMessage remoteMessage);
    }

    private void sendNotification(RemoteMessage remoteMessage) {

        RemoteMessage.Notification notificationData = remoteMessage.getNotification();

        //MyNotificationManager.getInstance(this).displayNotification("Flourish", notificationData.getBody());


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFICATION_ID, null, PendingIntent.FLAG_ONE_SHOT);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            NotificationCompat.Builder builder =
                    new NotificationCompat.Builder(MyFirebaseMessagingService.this)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(remoteMessage.getNotification().getBody())
                            .setAutoCancel(true)
                            .setSound(defaultSoundUri)
                            .setVibrate(new long[]{100, 100})
                            .setContentIntent(pendingIntent);

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(NOTIFICATION_ID, builder.build());
        } else if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O){

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel mChannel = new NotificationChannel(Constants.CHANNEL_ID, "MilkMor Delivery Boy", importance);
            mChannel.setDescription(remoteMessage.getNotification().getBody());
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.enableVibration(true);
            mChannel.setVibrationPattern(new long[]{100, 100});
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(mChannel);
            }
        }

        /*Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        //Dismiss Action Button Code
        Intent intentDismiss = new Intent(this, HomeActivity.class);
        intentDismiss.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntentDismiss = PendingIntent.getActivity(this,
                NOTIFY_CODE, intentDismiss, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Action actionDismiss = new NotificationCompat
                .Action.Builder(R.drawable.ic_close_red, "Dismiss", pendingIntentDismiss)
                .build();

        //View Action Button Code
        Intent intentView = new Intent(this, HomeActivity.class);
        intentView.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntentView = PendingIntent
                .getActivity(this, NOTIFY_CODE, intentView, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, NOTIFY_CODE *//* Request code *//*, intent,
                PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        notificationBuilder.setContentTitle("Flourish Delivery Boy");
        notificationBuilder.setContentText(data);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
        notificationBuilder.setSound(defaultSoundUri);
        notificationBuilder.setVibrate(new long[]{100, 100});
        notificationBuilder.setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFY_CODE *//* ID of notification *//*, notificationBuilder.build());*/
    }
}