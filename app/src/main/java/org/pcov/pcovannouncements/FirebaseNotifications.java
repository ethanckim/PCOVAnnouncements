package org.pcov.pcovannouncements;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.pcov.pcovannouncements.Fragments.AnnouncementFragment;

import java.util.Map;
import java.util.Random;

public class FirebaseNotifications extends FirebaseMessagingService {
    private static final String TAG = "Firebase Notifications";
    public static final String NOTIFICATION_CHANNEL_ID  = "org.pcov.notifications";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // TODO(developer): Handle FCM messages here.
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().isEmpty()) {
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();
            sendNotification(title, body);
        } else {
            sendNotification(remoteMessage.getData());
        }
    }

    private void sendNotification(Map<String, String> data) {
        String title = data.get("title");
        String body = data.get("body");

        sendNotification(title, body);
    }

    private void sendNotification(String title, String body) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        //run only on Android 8.0 (API level 26) and higher, because the
        //notification channels APIs are not available in the support library for lower v.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "Subscription", NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setLightColor(Color.CYAN);
            notificationChannel.enableLights(true);
            notificationChannel.setDescription(getString(R.string.firebase_notification_description));
            notificationManager.createNotificationChannel(notificationChannel);
        }

        // Create an Intent for the activity you want to start
        Intent resultIntent = new Intent(this, MainActivity.class);
        resultIntent.putExtra("menuFragment", "announcementsFragment");

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntentWithParentStack(resultIntent);

        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        notificationBuilder.setContentIntent(resultPendingIntent);
        notificationBuilder.setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_launcher))
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Notifications of New Videos and Announcements")
                .setContentIntent(resultPendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(new Random().nextInt(), notificationBuilder.build());

    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        Log.d(TAG, "Token: " + s);
    }

}