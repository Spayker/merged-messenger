package com.spandr.meme.core.activity.webview.logic.init.channel.fb;

import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

public class FacebookWebViewChannel extends WebViewChannel {

    private FacebookWebViewChannel(){}

    public FacebookWebViewChannel(WebViewActivity activity,
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

    protected FacebookWebViewChannel init() {
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
