package com.spandr.meme.core.activity.main.logic.notification;

import android.view.View;
import android.widget.TextView;

import java.util.Map;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.EMPTY_STRING;

public class NotificationDisplayer {

    private static NotificationDisplayer instance;

    private NotificationDisplayer(){ }

    public static NotificationDisplayer getInstance(){
        if(instance == null){
            instance = new NotificationDisplayer();
        }
        return instance;
    }

    public void display(String channelName, int notificationCounter){
        ViewChannelManager viewChannelManager = ViewChannelManager.getInstance();
        Map<String, View> channelViews = viewChannelManager.getChannelViews();
        TextView channelTextView = (TextView) channelViews.get(channelName);
        if(channelTextView != null){
            if(notificationCounter > 0) {
                String formattedNotificationCounter = String.valueOf(notificationCounter);
                if(notificationCounter < 9){
                    channelTextView.setText(String.format(" %s", formattedNotificationCounter));
                } else {
                    channelTextView.setText(formattedNotificationCounter);
                }
                channelTextView.setVisibility(VISIBLE);
            } else {
                channelTextView.setText(EMPTY_STRING);
                channelTextView.setVisibility(INVISIBLE);
            }
        }
    }



}
