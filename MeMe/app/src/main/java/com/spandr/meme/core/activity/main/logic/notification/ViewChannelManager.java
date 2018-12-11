package com.spandr.meme.core.activity.main.logic.notification;

import android.widget.TextView;

import java.util.Map;

public class ViewChannelManager {

    private static ViewChannelManager viewChannelManager;
    private static Map<String, TextView> channelViews;

    @SuppressWarnings("unused")
    private ViewChannelManager() {
    }

    private ViewChannelManager(Map<String, TextView> chnlsViews) {
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

    public Map<String, TextView> getChannelViews() {
        return channelViews;
    }


    static ViewChannelManager createChannelViewManager(Map<String, TextView> channelViews) {
        if (viewChannelManager == null) {
            viewChannelManager = new ViewChannelManager(channelViews);
        }
        return viewChannelManager;
    }
}
