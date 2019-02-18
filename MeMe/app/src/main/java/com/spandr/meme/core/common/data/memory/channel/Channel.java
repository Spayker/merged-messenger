package com.spandr.meme.core.common.data.memory.channel;

import android.content.Context;

import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.common.ActivityConstants.EMPTY_STRING;

public final class Channel {

    private String name;
    private TYPE type;
    private String homeUrl;
    private String lastUrl;
    private ICON icon;
    private boolean active;
    private boolean isNotificationsEnabled;
    private String userAgent;

    private WebViewChannel webViewChannel;

    private Channel() { }

    Channel(Context context, String name, TYPE type, ICON icon, String homeUrl,
            boolean active,
            boolean isNotificationsEnabled) {
        this.name = name;
        this.type = type;
        this.homeUrl = homeUrl;
        this.active = active;
        this.icon = icon;
        this.isNotificationsEnabled = isNotificationsEnabled;
        this.lastUrl = context.
                getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).
                getString(name + "lastUrl", EMPTY_STRING);
        this.userAgent = context.
                getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE).
                getString(name + "userAgent", EMPTY_STRING);
    }

    public String getName() {
        return name;
    }

    public ICON getIcon() {
        return icon;
    }

    public TYPE getType() {
        return type;
    }

    public String getHomeUrl() {
        return homeUrl;
    }

    public String getLastUrl() {
        return lastUrl;
    }

    public void setLastUrl(String lastUrl) {
        this.lastUrl = lastUrl;
    }

    public boolean getNotificationsEnabled() {
        return isNotificationsEnabled;
    }

    public void setNotificationsEnabled(boolean notificationsEnabled) {
        isNotificationsEnabled = notificationsEnabled;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public WebViewChannel getWebViewChannel() {
        return webViewChannel;
    }

    public void setWebViewChannel(WebViewChannel webViewChannel) {
        this.webViewChannel = webViewChannel;
    }

    public String getUserAgent() {
        return userAgent;
    }

}
