package com.spandr.meme.core.activity.main.logic.notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.main.MainActivity;

public class ShowNotificationIntentService {

    private static String mId = "395631925";

    public void buildSystemNotification(AppCompatActivity targetActivity){
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(targetActivity, mId)
                        .setSmallIcon(R.mipmap.logo)
                        .setContentTitle("MeMe app")
                        .setContentText("Received new messages");
        // Creates an explicit intent for an Activity in your app
        Intent resultIntent = new Intent(targetActivity, MainActivity.class);

        // The stack builder object will contain an artificial back stack for the
        // started Activity.
        // This ensures that navigating backward from the Activity leads out of
        // your application to the Home screen.
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(targetActivity);
        // Adds the back stack for the Intent (but not the Intent itself)
        stackBuilder.addParentStack(MainActivity.class);
        // Adds the Intent that starts the Activity to the top of the stack
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) targetActivity.getSystemService(Context.NOTIFICATION_SERVICE);
        // mId allows you to update the notification later on.
        mNotificationManager.notify(Integer.valueOf(mId), mBuilder.build());
    }

}
