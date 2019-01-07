package com.spandr.meme.core.activity.main.logic.notification;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.Map;
import java.util.concurrent.ExecutionException;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.spandr.meme.core.activity.main.logic.notification.ShowNotificationIntentService.buildSystemNotification;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.EMPTY_STRING;

public class NotificationDisplayer {

    private static NotificationDisplayer instance;
    private boolean isNotificationSentFlag;

    private NotificationDisplayer(){ }

    public static NotificationDisplayer getInstance(){
        if(instance == null){
            instance = new NotificationDisplayer();
        }
        return instance;
    }

    public void display(Context context, String channelName, int notificationCounter){
        ViewChannelManager viewChannelManager = ViewChannelManager.getInstance();
        Map<String, TextView> channelViews = viewChannelManager.getChannelViews();

        TextView channelTextView = channelViews.get(channelName);
        if(channelTextView == null){
            channelViews.remove(channelName);
            return;
        }

        if(notificationCounter > 0) {
            String formattedNotificationCounter = String.valueOf(notificationCounter);
            if(notificationCounter < 9){
                channelTextView.setText(String.format(" %s ", formattedNotificationCounter));
            } else {
                channelTextView.setText(formattedNotificationCounter);
            }
            channelTextView.setVisibility(VISIBLE);
        } else {
            channelTextView.setText(EMPTY_STRING);
            channelTextView.setVisibility(INVISIBLE);
        }

        informInAndroidEnv(context);
    }

    private void informInAndroidEnv(Context context) {
        try {
            boolean foreground = new ForegroundCheckTask().execute(context).get();
            if(!foreground && !isNotificationSentFlag){
                buildSystemNotification(context);
                isNotificationSentFlag = true;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void updateNotificationflag(){
        isNotificationSentFlag = false;
    }


}
