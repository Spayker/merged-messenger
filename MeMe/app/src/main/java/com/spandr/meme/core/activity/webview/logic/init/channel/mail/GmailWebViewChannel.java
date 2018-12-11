package com.spandr.meme.core.activity.webview.logic.init.channel.mail;

import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

public class GmailWebViewChannel extends WebViewChannel {

    @SuppressWarnings("unused")
    private GmailWebViewChannel(){}

    public GmailWebViewChannel(WebViewActivity activity,
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

    protected GmailWebViewChannel init() {
        initUserAgent();
        initStartURL();
        initWebChromeClient();
        initListeners();
        initWebClients();
        return this;
    }

    public String getUrl() {
        return url;
    }




}
