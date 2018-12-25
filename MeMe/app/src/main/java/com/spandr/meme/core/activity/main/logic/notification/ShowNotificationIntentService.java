package com.spandr.meme.core.activity.main.logic.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.main.MainActivity;

import static com.spandr.meme.core.activity.main.logic.LogicContants.ANDROID_NOTIFICATION_SENT;

public class ShowNotificationIntentService {

    private static String mId = "395631925";

    public static void buildSystemNotification(AppCompatActivity targetActivity){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(targetActivity, mId)
                        .setSmallIcon(R.mipmap.logo)
                        .setContentTitle(targetActivity.getResources().getString(R.string.app_name))
                        .setContentText(targetActivity.getResources().getString(R.string.notification_received_messages));
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(targetActivity, MainActivity.class);
        resultIntent.putExtra(ANDROID_NOTIFICATION_SENT, true);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(targetActivity);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setAutoCancel(true);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) targetActivity.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mBuilder.build().flags |= Notification.FLAG_AUTO_CANCEL;
        mNotificationManager.notify(Integer.valueOf(mId), mBuilder.build());
    }

}
