package com.spandr.meme.core.activity.webview.logic.init.channel.info;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;
import com.spandr.meme.core.common.data.memory.channel.Channel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.common.data.memory.channel.DataChannelManager.getChannelByName;

public class QuoraWebViewChannel extends WebViewChannel {

    private Context context;
    private final String MESSAGE_NOTIFICATION_REGEX = "_badge\">([0-9]+)</div>";
    private final Pattern pattern = Pattern.compile(MESSAGE_NOTIFICATION_REGEX);

    @SuppressWarnings("unused")
    private QuoraWebViewChannel(){}

    public QuoraWebViewChannel(WebViewActivity activity,
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

    public QuoraWebViewChannel(Context activity, String url, String channelName) {
        if (url.isEmpty()) {
            return;
        }
        this.context = activity;
        this.url = url;
        this.channelName = channelName;
    }

    @SuppressLint("AddJavascriptInterface")
    protected QuoraWebViewChannel init() {
        initUserAgent();
        initWebClients();
        initSwipeListeners();
        initOrientationSensor();
        initCacheSettings();
        initStartURL();
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

    public String getUrl() {
        return url;
    }

    @Override
    public void processHTML(String html) {
        if(isNotificationSettingEnabled(channelName)){
            Matcher m = pattern.matcher(html);
            int notificationCounter = 0;
            while(m.find()) {
                notificationCounter += Integer.valueOf(m.group(1));
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
}