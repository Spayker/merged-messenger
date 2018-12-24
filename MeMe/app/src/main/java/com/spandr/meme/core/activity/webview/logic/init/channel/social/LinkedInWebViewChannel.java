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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import im.delight.android.webview.AdvancedWebView;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;

public class LinkedInWebViewChannel extends WebViewChannel {

    private AppCompatActivity appCompatActivity;
    @SuppressWarnings("unused")
    private LinkedInWebViewChannel(){}

    public LinkedInWebViewChannel(WebViewActivity activity,
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

    public LinkedInWebViewChannel(AppCompatActivity activity, AdvancedWebView mWebView,
                                  String url, String channelName) {
        if (url.isEmpty()) {
            return;
        }
        this.appCompatActivity = activity;
        this.mWebView = mWebView;
        this.url = url;
        this.channelName = channelName;
        initBackgroundMode();
    }

    @SuppressLint("AddJavascriptInterface")
    protected LinkedInWebViewChannel init() {
        initUserAgent();
        initWebClients();
        initListeners();
        initOrientationSensor();
        initCacheSettings();
        mWebView.addJavascriptInterface(new LnJavaScriptInterface(channelName), "HTMLOUT");
        initStartURL();
        return this;
    }

    @SuppressLint("AddJavascriptInterface")
    private void initBackgroundMode() {
        initBackgroundWebSettings();
        mWebView.addJavascriptInterface(new LnJavaScriptInterface(channelName), "HTMLOUT");
        initStartURL();
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

    private class LnJavaScriptInterface {

        private String channelName;

        private LnJavaScriptInterface(String channelName){
            this.channelName = channelName;
        }

        private final String MESSAGE_NOTIFICATION_REGEX = "class=\"nav-item__badge\">([0-9]+)</span>";
        private final Pattern pattern = Pattern.compile(MESSAGE_NOTIFICATION_REGEX);

        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            if(isNotificationSettingEnabled(channelName)){
                Matcher m = pattern.matcher(html);
                int notificationCounter = 0;
                while(m.find()) {
                    notificationCounter += Integer.valueOf(m.group(1));
                }

                final int result = notificationCounter;
                if(notificationCounter > 0){
                    if (activity == null) {
                        appCompatActivity.runOnUiThread(() -> NotificationDisplayer.getInstance().display(channelName, result));
                    } else {
                        activity.runOnUiThread(() -> NotificationDisplayer.getInstance().display(channelName, result));
                    }
                }
            }
        }
    }












}
