package com.spandr.meme.core.activity.webview.logic.init.channel.mail;

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
public class MailruWebViewChannel extends WebViewChannel {

    @SuppressWarnings("unused")
    private MailruWebViewChannel(){}

    public MailruWebViewChannel(WebViewActivity activity,
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
    protected MailruWebViewChannel init() {
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
