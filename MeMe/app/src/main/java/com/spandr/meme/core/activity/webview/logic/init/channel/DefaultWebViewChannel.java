package com.spandr.meme.core.activity.webview.logic.init.channel;

import com.spandr.meme.core.activity.webview.WebViewActivity;

public class DefaultWebViewChannel extends WebViewChannel{

    @SuppressWarnings("unused")
    private DefaultWebViewChannel(){}

    public DefaultWebViewChannel(WebViewActivity activity,
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

    protected DefaultWebViewChannel init() {
        initUserAgent();
        initStartURL();
        initWebChromeClient();
        initListeners();
        initWebSettings();
        return this;
    }

    public String getUrl() {
        return url;
    }
}
