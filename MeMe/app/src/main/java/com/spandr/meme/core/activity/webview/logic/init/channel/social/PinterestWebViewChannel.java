package com.spandr.meme.core.activity.webview.logic.init.channel.social;

import android.annotation.SuppressLint;

import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

public class PinterestWebViewChannel extends WebViewChannel {

    @SuppressWarnings("unused")
    private PinterestWebViewChannel(){}

    public PinterestWebViewChannel(WebViewActivity activity,
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
    protected PinterestWebViewChannel init() {
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
