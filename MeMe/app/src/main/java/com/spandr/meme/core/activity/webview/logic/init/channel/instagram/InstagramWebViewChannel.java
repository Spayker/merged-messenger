package com.spandr.meme.core.activity.webview.logic.init.channel.instagram;

import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

public class InstagramWebViewChannel extends WebViewChannel {

    private InstagramWebViewChannel(){}

    public InstagramWebViewChannel(WebViewActivity activity,
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

    protected InstagramWebViewChannel init() {
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
