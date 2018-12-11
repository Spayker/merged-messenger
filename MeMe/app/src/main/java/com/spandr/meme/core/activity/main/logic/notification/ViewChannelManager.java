package com.spandr.meme.core.activity.main.logic.notification;

import android.view.View;

import java.util.Map;

public class ViewChannelManager {

    private static ViewChannelManager viewChannelManager;
    private static Map<String, View> channelViews;

    @SuppressWarnings("unused")
    private ViewChannelManager() {
    }

    private ViewChannelManager(Map<String, View> chnlsViews) {
        channelViews = chnlsViews;
    }

    public static ViewChannelManager getInstance() {
        return viewChannelManager;
    }

    public static Boolean clearChannelViews() {
        if (channelViews != null) {
            channelViews.clear();
            return true;
        }
        return false;
    }

    public Map<String, View> getChannelViews() {
        return channelViews;
    }


    public static ViewChannelManager createChannelViewManager(Map<String, View> channelViews) {
        if (viewChannelManager == null) {
            viewChannelManager = new ViewChannelManager(channelViews);
        }
        return viewChannelManager;
    }
}
