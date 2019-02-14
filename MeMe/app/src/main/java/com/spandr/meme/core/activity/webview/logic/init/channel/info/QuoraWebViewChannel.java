package com.spandr.meme.core.activity.webview.logic.init.channel.info;

import android.annotation.SuppressLint;

import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

public class QuoraWebViewChannel extends WebViewChannel {

    @SuppressWarnings("unused")
    private QuoraWebViewChannel(){}

    public QuoraWebViewChannel(WebViewActivity activity,
                                String url, String channelName) {
        if(url.isEmpty()){
            return;
        }
        this.activity = activity;
        this.mWebView = activity.getmWebView();
        this.url = url;
        this.channelName = channelName;
        init();
    }

    @SuppressLint("AddJavascriptInterface")
    protected QuoraWebViewChannel init() {
        initUserAgent();
        initWebClients();
        initSwipeListeners();
        initOrientationSensor();
        initCacheSettings();
        initStartURL();
        return this;
    }

    public String getUrl() {
        return url;
    }

}
