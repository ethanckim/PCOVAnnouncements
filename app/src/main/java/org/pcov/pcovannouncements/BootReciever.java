package org.pcov.pcovannouncements;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import org.pcov.pcovannouncements.FirebaseNotifications;

public class BootReciever extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            Intent serviceIntent = new Intent(context, FirebaseNotifications.class);
            context.startService(serviceIntent);
        }
    }
}
