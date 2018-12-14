package com.spandr.meme.core.activity.main.logic.notification;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.main.MainActivity;

public class ShowNotificationIntentService extends IntentService {

    private static final String ACTION_SHOW_NOTIFICATION = "com.spandr.meme.service.action.show";
    private static final String ACTION_HIDE_NOTIFICATION = "com.spandr.meme.service.action.hide";


    public ShowNotificationIntentService() {
        super("ShowNotificationIntentService");
    }

    public static void startActionShow(Context context) {
        Intent intent = new Intent(context, ShowNotificationIntentService.class);
        intent.setAction(ACTION_SHOW_NOTIFICATION);
        context.startService(intent);
    }

    public static void startActionHide(Context context) {
        Intent intent = new Intent(context, ShowNotificationIntentService.class);
        intent.setAction(ACTION_HIDE_NOTIFICATION);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_SHOW_NOTIFICATION.equals(action)) {
                handleActionShow();
            } else if (ACTION_HIDE_NOTIFICATION.equals(action)) {
                handleActionHide();
            }
        }
    }

    private void handleActionShow() {
        showStatusBarIcon(ShowNotificationIntentService.this);
    }

    private void handleActionHide() {
//        hideStatusBarIcon(ShowNotificationIntentService.this);
    }

    public static void showStatusBarIcon(Context ctx) {
//        Context context = ctx;
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx)
//                .setContentTitle("MeMe")
//                .setSmallIcon(R.mipmap.logo)
//                .setOngoing(true);
//        Intent intent = new Intent(context, MainActivity.class);
//        PendingIntent pIntent = PendingIntent.getActivity(context, STATUS_ICON_REQUEST_CODE, intent, 0);
//        builder.setContentIntent(pIntent);
//        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        Notification notif = builder.build();
//        notif.flags |= Notification.FLAG_ONGOING_EVENT;
//        mNotificationManager.notify(STATUS_ICON_REQUEST_CODE, notif);
    }

}
