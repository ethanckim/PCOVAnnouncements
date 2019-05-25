package org.pcov.pcovannouncements;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class FirebaseNotifications extends FirebaseMessagingService {
    private static final String TAG = "Firebase Notifications";
    private static final String NOTIFICATION_CHANNEL_ID  = "org.pcov.notifications";

    public FirebaseNotifications() {

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        sendNotification(title, body);
    }

    private void sendNotification(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //run only on Android 8.0 (API level 26) and higher, because the
        //notification channels APIs are not available in the support library for lower v.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "name (Change later)", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setLightColor(Color.CYAN);
            notificationChannel.enableLights(true);
            notificationChannel.setDescription(getString(R.string.firebase_notification_description));
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("info (Change later)");

        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());

    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String s) {
        Log.d(TAG, "Token: " + s);
    }



}