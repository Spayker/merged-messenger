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
public class VkontakteWebViewChannel extends WebViewChannel {

    @SuppressWarnings("unused")
    private VkontakteWebViewChannel() { }

    public VkontakteWebViewChannel(WebViewActivity activity,
                                   String url, String channelName) {
        if (url.isEmpty()) {
            return;
        }
        this.activity = activity;
        this.mWebView = activity.getmWebView();
        this.url = url;
        this.channelName = channelName;
        init();
    }

    protected VkontakteWebViewChannel init() {
        initUserAgent();
        initWebClients();
        initWebSettings();
        initOrientationSensor();
        initCacheSettings();
        initStartURL();
        initSwipeListeners();
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
