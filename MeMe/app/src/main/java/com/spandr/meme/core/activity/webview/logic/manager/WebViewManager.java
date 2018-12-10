package com.spandr.meme.core.activity.webview.logic.manager;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import im.delight.android.webview.AdvancedWebView;

public class WebViewManager {

    private static WebViewManager webViewManager;
    private Map<String, AdvancedWebView> webViewChannels;

    @SuppressWarnings("unused")
    private WebViewManager() { }

    private WebViewManager(Map<String, AdvancedWebView> webViewChannels) {
        this.webViewChannels = webViewChannels;
    }

    public Map<String, AdvancedWebView> getWebViewChannels() {
        return webViewChannels;
    }

    public Iterable<AdvancedWebView> getWebViewChannelsIterator() {
        Iterable<AdvancedWebView> iterable = new ArrayList<>(webViewChannels.values());
        return iterable;
    }

    public static WebViewManager getWebViewChannelManager(){
        if (webViewManager == null) {
            webViewManager = new WebViewManager(new HashMap<>());
        }
        return webViewManager;
    }



}
