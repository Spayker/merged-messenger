package com.spandr.meme.core.activity.webview.logic.init.channel.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.JavascriptInterface;

import com.spandr.meme.core.activity.main.logic.notification.NotificationDisplayer;
import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;

public class DiscordWebViewChannel extends WebViewChannel {

    private final static String DISCORD_USER_AGENT_STRING = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
    private final static String DISCORD_SCALE_JAVASCRIPT = "javascript: var metaList = document.getElementsByTagName(\"META\");metaList[1].setAttribute(\"content\",\"width=900, user-scalable=yes\");";

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
        initWebChromeClient();
        initWebClients();
        initListeners();
        initWebSettings();
        initStartURL();
        mWebView.addJavascriptInterface(new DsJavaScriptInterface(channelName), "HTMLOUT");
        return this;
    }

    @Override
    protected void initUserAgent(){
        mWebView.getSettings().setUserAgentString(DISCORD_USER_AGENT_STRING);
    }

    public String getUrl() {
        return url;
    }

    private class DsJavaScriptInterface {

        private String channelName;

        private DsJavaScriptInterface(String channelName){
            this.channelName = channelName;
        }

        private final String MESSAGE_NOTIFICATION_REGEX = "guild-1EfMGQ (unread-qLkInr)\"";
        private final Pattern pattern = Pattern.compile(MESSAGE_NOTIFICATION_REGEX);

        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            if(isNotificationSettingEnabled(channelName)){
                mWebView.post(() -> {
                    mWebView.loadUrl(DISCORD_SCALE_JAVASCRIPT);
                    Matcher m = pattern.matcher(html);
                    int notificationCounter = 0;
                    while(m.find()) {
                        notificationCounter ++;
                    }
                    NotificationDisplayer.getInstance().display(channelName, notificationCounter);
                });
            }
        }
    }

}
