package com.spandr.meme.core.activity.webview.logic.init.channel.social;

import android.annotation.SuppressLint;

import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

/**
 *
 *
 * @author  Spayker
 * @version 1.0
 * @since   3/7/2019
 */
public class TumblrWebViewChannel extends WebViewChannel {

    @SuppressWarnings("unused")
    private TumblrWebViewChannel(){}

    public TumblrWebViewChannel(WebViewActivity activity,
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
    protected TumblrWebViewChannel init() {
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
