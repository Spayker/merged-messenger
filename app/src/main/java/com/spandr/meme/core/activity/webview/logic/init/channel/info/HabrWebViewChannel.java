package com.spandr.meme.core.activity.webview.logic.init.channel.info;

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
public class HabrWebViewChannel extends WebViewChannel {

    @SuppressWarnings("unused")
    private HabrWebViewChannel(){}

    public HabrWebViewChannel(WebViewActivity activity,
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
    protected HabrWebViewChannel init() {
        initUserAgent();
        initWebClients();
        initSwipeListeners();
        initOrientationSensor();
        initCacheSettings();
        mWebView.setDesktopMode(true);
        initStartURL();
        return this;
    }

    public String getUrl() {
        return url;
    }

}
