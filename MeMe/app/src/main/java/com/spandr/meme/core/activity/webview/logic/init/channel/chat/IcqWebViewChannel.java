package com.spandr.meme.core.activity.webview.logic.init.channel.chat;

import android.annotation.SuppressLint;

import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

public class IcqWebViewChannel extends WebViewChannel {

    @SuppressWarnings("unused")
    private IcqWebViewChannel(){}

    public IcqWebViewChannel(WebViewActivity activity,
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
    protected IcqWebViewChannel init() {
        initUserAgent();
        initSwipeListeners();
        initWebClients();
        initOrientationSensor();
        initCacheSettings();
        initStartURL();
        return this;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initSwipeListeners(){
        mWebView.setOnTouchListener((v, event) -> {
            activity.getSwipeRefreshLayout().setEnabled(false);
            activity.getBackButton().setAlpha(.45f);
            mWebView.performClick();
            return false;
        });
    }

    public String getUrl() {
        return url;
    }
}
