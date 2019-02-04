package com.spandr.meme.core.activity.webview.logic.init.channel.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.JavascriptInterface;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;
import com.spandr.meme.core.common.data.memory.channel.Channel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.common.data.memory.channel.DataChannelManager.getChannelByName;

public class DiscordWebViewChannel extends WebViewChannel {

    private Context context;
    private final static String DISCORD_USER_AGENT_STRING = "Mozilla/5.0 (X11; U; Linux i686; en-US; rv:1.9.0.4) Gecko/20100101 Firefox/4.0";
    private final static String DISCORD_SCALE_JAVASCRIPT = "javascript: var metaList = document.getElementsByTagName(\"META\");metaList[1].setAttribute(\"content\",\"width=900, user-scalable=yes\");";

    private final String MESSAGE_NOTIFICATION_REGEX = "guild-1EfMGQ (unread-qLkInr)\"";
    private final Pattern pattern = Pattern.compile(MESSAGE_NOTIFICATION_REGEX);

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

    public DiscordWebViewChannel(Context activity,
                                  String url, String channelName) {
        if (url.isEmpty()) {
            return;
        }
        this.context = activity;
        this.url = url;
        this.channelName = channelName;
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
        mWebView.addJavascriptInterface(new DsJavaScriptInterface(), "HTMLOUT");
        return this;
    }

    @Override
    protected boolean isNotificationSettingEnabled(String channelName) {
        Context context = activity != null ? activity : this.context;
        notificationPrefix = context.getString(R.string.channel_setting_notifications_prefix);
        String channelKeyNotification = channelName + notificationPrefix;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(channelKeyNotification, false);
    }

    @Override
    protected void initUserAgent(){
        mWebView.getSettings().setUserAgentString(DISCORD_USER_AGENT_STRING);
    }

    public String getUrl() {
        return url;
    }

    @Override
    public void processHTML(String html) {
        if(isNotificationSettingEnabled(channelName)){

            Matcher m = pattern.matcher(html);
            int notificationCounter = 0;
            while(m.find()) {
                notificationCounter ++;
            }

            Channel channel = getChannelByName(channelName);
            if (channel != null) {
                channel.setNotifications(notificationCounter);
                Context context = activity != null ? activity : this.context;
                SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
                editor.putInt(channelName + "notifications", notificationCounter);
                editor.apply();
                editor.commit();
            }
        }
    }

    private class DsJavaScriptInterface {
        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            mWebView.loadUrl(DISCORD_SCALE_JAVASCRIPT);
        }
    }

}
