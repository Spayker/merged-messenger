package com.spandr.meme.core.activity.webview.logic.init.channel.chat;

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
public class DiscordWebViewChannel extends WebViewChannel {

    private final static String DISCORD_USER_AGENT_STRING = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";

    @SuppressWarnings("unused")
    private DiscordWebViewChannel(){}

    public DiscordWebViewChannel(WebViewActivity activity,
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
    protected DiscordWebViewChannel init() {
        initUserAgent();
        initWebClients();
        initSwipeListeners();
        initWebSettings();
        initOrientationSensor();
        initCacheSettings();
        initStartURL();
        return this;
    }

    @Override
    protected void initUserAgent(){
        mWebView.getSettings().setUserAgentString(DISCORD_USER_AGENT_STRING);
    }

    public String getUrl() {
        return url;
    }

}
