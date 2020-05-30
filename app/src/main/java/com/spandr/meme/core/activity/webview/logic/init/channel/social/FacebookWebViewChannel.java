package com.spandr.meme.core.activity.webview.logic.init.channel.social;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;
import com.spandr.meme.core.common.data.memory.channel.Channel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.EMPTY_STRING;
import static com.spandr.meme.core.common.data.memory.channel.DataChannelManager.getChannelByName;

public class FacebookWebViewChannel extends WebViewChannel {

    private Context context;
    private final static String FACEBOOK_USER_AGENT_STRING = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.91 Safari/537.36";

    private final String MESSAGE_NOTIFICATION_REGEX = "\"_59tg\" data-sigil=\"count\">([0-9]+)</span>";
    private final String MESSAGE_NOTIFICATION_REGEX_2 = "([0-9]+)\\)</span></div>";
    private final Pattern patternOfFirstRegex = Pattern.compile(MESSAGE_NOTIFICATION_REGEX);
    private final Pattern patternOfSecondRegex = Pattern.compile(MESSAGE_NOTIFICATION_REGEX_2);

    @SuppressWarnings("unused")
    private FacebookWebViewChannel(){}

    public FacebookWebViewChannel(WebViewActivity activity,
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

    @Override
    public String establishConnection(Channel channel, Context context) {
        try {
            String url = channel.getHomeUrl();
            if (!url.isEmpty()) {
                String cookies = channel.getCookies();
                if (cookies != null && !cookies.isEmpty()) {
                    Document doc = Jsoup.connect(url).
                            userAgent(FACEBOOK_USER_AGENT_STRING).
                            header("Cookie", cookies).
                            timeout(0).
                            get();
                    return doc.toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EMPTY_STRING;
    }

    public FacebookWebViewChannel(Context activity, String url, String channelName) {
        if (url.isEmpty()) {
            return;
        }
        this.context = activity;
        this.url = url;
        this.channelName = channelName;
    }

    @SuppressLint("AddJavascriptInterface")
    protected FacebookWebViewChannel init() {
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

    @Override
    protected void initUserAgent() {
        mWebView.getSettings().setUserAgentString(FACEBOOK_USER_AGENT_STRING);
    }

    public String getUrl() {
        return url;
    }

    @Override
    public void processHTML(String html) {
        if(isNotificationSettingEnabled(channelName)){
            int notificationCounter = parseHtml(html);
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

    private int parseHtml(String html){
        int notificationCounter = 0;
        Matcher firstMatcher = patternOfFirstRegex.matcher(html);
        while(firstMatcher.find()) {
            notificationCounter += Integer.valueOf(firstMatcher.group(1));
        }
        Matcher secondMatcher = patternOfSecondRegex.matcher(html);
        while(secondMatcher.find()) {
            notificationCounter += Integer.valueOf(secondMatcher.group(1));
        }
        return notificationCounter;
    }

}
