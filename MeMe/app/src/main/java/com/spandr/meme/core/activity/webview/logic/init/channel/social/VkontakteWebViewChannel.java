package com.spandr.meme.core.activity.webview.logic.init.channel.social;

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

public class VkontakteWebViewChannel extends WebViewChannel {

    protected Context context;
    private String VKONTAKTE_USER_AGENT_STRING = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.91 Safari/537.36";
    private final String MESSAGE_NOTIFICATION_REGEX = "<em class=\"mm_counter\">([0-9]+)</em>";
    private final Pattern pattern = Pattern.compile(MESSAGE_NOTIFICATION_REGEX);

    @SuppressWarnings("unused")
    private VkontakteWebViewChannel() {
    }

    public VkontakteWebViewChannel(WebViewActivity activity,
                                   String url, String channelName) {
        if (url.isEmpty()) {
            return;
        }
        this.activity = activity;
        this.mWebView = activity.getmWebView();
        this.url = url;
        this.channelName = channelName;
        init();
    }

    public VkontakteWebViewChannel(Context activity, String url, String channelName) {
        if (url.isEmpty()) {
            return;
        }
        this.context = activity;
        this.url = url;
        this.channelName = channelName;
    }

    protected VkontakteWebViewChannel init() {
        initUserAgent();
        initListeners();
        initWebClients();
        initWebSettings();
        initOrientationSensor();
        initCacheSettings();
        initStartURL();
        return this;
    }

    @Override
    public String establishConnection(Channel channel, Context context) {
        try {
            String url = channel.getHomeUrl();
            if (!url.isEmpty()) {
                String cookies = channel.getCookies();
                if (cookies != null && !cookies.isEmpty()) {
                    Document doc = Jsoup.connect(url).
                            userAgent(VKONTAKTE_USER_AGENT_STRING).
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

    @Override
    protected boolean isNotificationSettingEnabled(String channelName) {
        Context context = activity != null ? activity : this.context;
        notificationPrefix = context.getString(R.string.channel_setting_notifications_prefix);
        String channelKeyNotification = channelName + notificationPrefix;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(channelKeyNotification, false);
    }

    /*@Override
    protected void initUserAgent() {
        mWebView.getSettings().setUserAgentString(VKONTAKTE_USER_AGENT_STRING);
    }*/

    public String getUrl() {
        return url;
    }

    @Override
    public void processHTML(String html) {
        if (isNotificationSettingEnabled(channelName)) {
            Matcher m = pattern.matcher(html);
            int notificationCounter = 0;
            while (m.find()) {
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
