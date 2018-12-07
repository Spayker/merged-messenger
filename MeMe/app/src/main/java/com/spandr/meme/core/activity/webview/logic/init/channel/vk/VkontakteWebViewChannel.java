package com.spandr.meme.core.activity.webview.logic.init.channel.vk;

import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

public class VkontakteWebViewChannel extends WebViewChannel {

    private String VKONTAKTE_USER_AGENT_STRING = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.91 Safari/537.36";

    private VkontakteWebViewChannel(){}

    public VkontakteWebViewChannel(WebViewActivity activity,
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

    protected VkontakteWebViewChannel init() {
        initUserAgent();
        initStartURL();
        initWebChromeClient();
        initListeners();
        initWebClients();
        return this;
    }

    @Override
    protected void initUserAgent() {
        mWebView.getSettings().setUserAgentString(VKONTAKTE_USER_AGENT_STRING);
    }

    public String getUrl() {
        return url;
    }

}
