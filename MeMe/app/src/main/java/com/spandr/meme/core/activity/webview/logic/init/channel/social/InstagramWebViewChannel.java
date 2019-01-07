package com.spandr.meme.core.activity.webview.logic.init.channel.social;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.main.logic.notification.NotificationDisplayer;
import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;
import com.spandr.meme.core.common.data.memory.channel.Channel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import im.delight.android.webview.AdvancedWebView;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.common.ActivityConstants.EMPTY_STRING;
import static com.spandr.meme.core.common.data.memory.channel.DataChannelManager.getChannelByName;

public class InstagramWebViewChannel extends WebViewChannel {

    private Context appCompatActivity;
    private final String MESSAGE_NOTIFICATION_REGEX = "\"></span><span>([0-9]+)</span></div>";
    private final Pattern pattern = Pattern.compile(MESSAGE_NOTIFICATION_REGEX);

    @SuppressWarnings("unused")
    private InstagramWebViewChannel(){}

    public InstagramWebViewChannel(WebViewActivity activity,
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

    public InstagramWebViewChannel(Context activity, String url, String channelName) {
        if (url.isEmpty()) {
            return;
        }
        this.appCompatActivity = activity;
        this.url = url;
        this.channelName = channelName;
    }

    @SuppressLint("AddJavascriptInterface")
    protected InstagramWebViewChannel init() {
        initUserAgent();
        initWebClients();
        initListeners();
        initOrientationSensor();
        initCacheSettings();
        initStartURL();
        return this;
    }

    @Override
    protected boolean isNotificationSettingEnabled(String channelName) {
        Context context = activity != null ? activity : appCompatActivity;
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
                String foundNotification = m.group(1);
                if(!foundNotification.isEmpty()){
                    notificationCounter += Integer.valueOf(foundNotification);
                }
            }

            Channel channel = getChannelByName(channelName);
            if(channel != null){
                channel.setNotifications(notificationCounter);
            }

            /*if(notificationCounter > 0){
                if (activity == null) {
                        appCompatActivity.runOnUiThread(() -> {
                            NotificationDisplayer.getInstance().display(appCompatActivity, channelName, result);
                            mWebView.stopLoading();
                        });
                } else {
                    activity.runOnUiThread(() -> {
                        NotificationDisplayer.getInstance().display(activity, channelName, result);
                        mWebView.stopLoading();
                    });
                }
            }*/
        }
    }
}
