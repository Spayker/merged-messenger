package com.spandr.meme.core.activity.main.logic.notification;

import android.content.Context;
import android.widget.TextView;

import com.spandr.meme.core.activity.main.logic.builder.draggable.DraggableGridAdapter;

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
        Map<String, DraggableGridAdapter.CustomViewHolder> channelViews = viewChannelManager.getChannelViews();

        DraggableGridAdapter.CustomViewHolder customViewHolder = channelViews.get(channelName);
        if(customViewHolder == null){
            channelViews.remove(channelName);
            return;
        }

        if(notificationCounter > 0) {
            String formattedNotificationCounter = String.valueOf(notificationCounter);
            if(notificationCounter < 9){
                customViewHolder.getmBadgeTextView().setText(String.format(" %s ", formattedNotificationCounter));
            } else {
                customViewHolder.getmBadgeTextView().setText(String.format(" %s ", formattedNotificationCounter));
            }
            customViewHolder.getmBadgeTextView().setVisibility(VISIBLE);
        } else {
            customViewHolder.getmBadgeTextView().setText(EMPTY_STRING);
            customViewHolder.getmBadgeTextView().setVisibility(INVISIBLE);
        }

        sendNotificationInAndroidEnv(context);
    }

    private void sendNotificationInAndroidEnv(Context context) {
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

    public void updateNotificationFlag(){
        isNotificationSentFlag = false;
    }


}
