package com.spandr.meme.core.activity.webview.logic.init.channel;

import com.spandr.meme.core.activity.webview.WebViewActivity;

public class DefaultWebViewChannel extends WebViewChannel{

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
        return this;
    }

    public String getUrl() {
        return url;
    }
}
