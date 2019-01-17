package com.spandr.meme.core.activity.webview.logic.init.channel.mail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;
import com.spandr.meme.core.common.data.memory.channel.Channel;
import com.spandr.meme.core.common.data.memory.channel.DataChannelManager;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import im.delight.android.webview.AdvancedWebView;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.common.data.memory.channel.DataChannelManager.getChannelByName;
import static com.spandr.meme.core.common.util.ActivityUtils.isNetworkAvailable;

public class MailruWebViewChannel extends WebViewChannel {

    private Context parentContext;
    private final String MESSAGE_NOTIFICATION_REGEX = "([0-9]+)</div></div></div><div class=\"toolbar__title__t toolbar__title__t1\">";
    private final String MESSAGE_NOTIFICATION_REGEX_2 = "<span class=\"social__bubble\">([0-9]+)</span>";
    private final Pattern patternOfFirstRegex = Pattern.compile(MESSAGE_NOTIFICATION_REGEX);
    private final Pattern patternOfSecondRegex = Pattern.compile(MESSAGE_NOTIFICATION_REGEX_2);

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
    public MailruWebViewChannel(Context activity, String url, String channelName) {
        if (url.isEmpty()) {
            return;
        }
        this.parentContext = activity;
        this.url = url;
        this.channelName = channelName;
    }

    @SuppressLint("AddJavascriptInterface")
    protected MailruWebViewChannel init() {
        initUserAgent();
        initListeners();
        initWebClients();
        initOrientationSensor();
        initCacheSettings();
        initStartURL();
        return this;
    }

    @Override
    public void processHTML(String html) {
        if(isNotificationSettingEnabled(channelName)){
            final int notificationCounter = parseHtml(html);
            Channel channel = getChannelByName(channelName);
            if (channel != null) {
                Context context = activity != null ? activity : parentContext;
                SharedPreferences.Editor editor = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).edit();
                editor.putInt(channelName + "notifications", notificationCounter);
                editor.apply();
                editor.commit();
            }
        }
    }

    private int parseHtml(String html){
        int notificationCounter = 0;
        Matcher firstMatcher = patternOfFirstRegex.matcher(html);
        while(firstMatcher.find()) {
            notificationCounter += Integer.valueOf(firstMatcher.group(1));
        }
        if (notificationCounter == 0){
            Matcher secondMatcher = patternOfSecondRegex.matcher(html);
            while(secondMatcher.find()) {
                notificationCounter += Integer.valueOf(secondMatcher.group(1));
            }
        }
        return notificationCounter;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initListeners(){
        mWebView.setOnTouchListener((v, event) -> {
            activity.getSwipeRefreshLayout().setEnabled(false);
            activity.getBackButton().setAlpha(.45f);
            mWebView.performClick();
            return false;
        });
    }

    @Override
    protected boolean isNotificationSettingEnabled(String channelName) {
        Context context = activity != null ? activity : this.parentContext;
        notificationPrefix = context.getString(R.string.channel_setting_notifications_prefix);
        String channelKeyNotification = channelName + notificationPrefix;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(channelKeyNotification, false);
    }

    public String getUrl() {
        return url;
    }

}
