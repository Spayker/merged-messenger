package com.spandr.meme.core.activity.webview.logic.init.channel.video;

import android.annotation.SuppressLint;

import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

public class TwitchWebViewChannel extends WebViewChannel {

    @SuppressWarnings("unused")
    private TwitchWebViewChannel(){}

    public TwitchWebViewChannel(WebViewActivity activity,
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
    protected TwitchWebViewChannel init() {
        initUserAgent();
        initStartURL();
        initWebChromeClient();
        initWebClients();
        initListeners();
        return this;
    }

    public String getUrl() {
        return url;
    }

}
