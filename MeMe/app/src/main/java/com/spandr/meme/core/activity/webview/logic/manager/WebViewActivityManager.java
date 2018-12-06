package com.spandr.meme.core.activity.webview.logic.manager;


import java.util.HashMap;
import java.util.Map;

import im.delight.android.webview.AdvancedWebView;

public class WebViewActivityManager {

    private static WebViewActivityManager webViewActivityManager;
    private static Map<String, AdvancedWebView> webViewChannels;

    private WebViewActivityManager() { }

    private WebViewActivityManager(Map<String, AdvancedWebView> webViewChannels) {
        this.webViewChannels = webViewChannels;
    }

    public Map<String, AdvancedWebView> getWebViewActivities() {
        return webViewChannels;
    }

    public static WebViewActivityManager getWebViewChannelManager(){
        if (webViewActivityManager == null) {
            webViewActivityManager = new WebViewActivityManager(new HashMap<>());
        }
        return webViewActivityManager;
    }



}
