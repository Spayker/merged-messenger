package com.spandr.meme.core.activity.main.logic.notification;

import com.spandr.meme.core.activity.main.logic.builder.draggable.DraggableGridAdapter;

import java.util.Map;

public class ViewChannelManager {

    private static ViewChannelManager viewChannelManager;
    private static Map<String, DraggableGridAdapter.CustomViewHolder> channelViews;

    @SuppressWarnings("unused")
    private ViewChannelManager() {
    }

    private ViewChannelManager(Map<String, DraggableGridAdapter.CustomViewHolder> chnlsViews) {
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

    public Map<String, DraggableGridAdapter.CustomViewHolder> getChannelViews() {
        return channelViews;
    }


    static ViewChannelManager createChannelViewManager(Map<String, DraggableGridAdapter.CustomViewHolder> channelViews) {
        if (viewChannelManager == null) {
            viewChannelManager = new ViewChannelManager(channelViews);
        }
        return viewChannelManager;
    }
}
