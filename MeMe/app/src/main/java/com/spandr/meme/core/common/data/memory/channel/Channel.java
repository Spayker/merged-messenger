package com.spandr.meme.core.common.data.memory.channel;

import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

import static com.spandr.meme.core.common.ActivityConstants.EMPTY_STRING;

public final class Channel {

    private String name;
    private TYPE type;
    private String homeUrl;
    private String lastUrl;
    private ICON icon;
    private boolean active;
    private boolean isNotificationsEnabled;
    private int notifications;
    private String cookies;

    private WebViewChannel webViewChannel;

    private Channel() { }

    Channel(String name, TYPE type, ICON icon, String homeUrl, boolean active, boolean isNotificationsEnabled) {
        this.name = name;
        this.type = type;
        this.homeUrl = homeUrl;
        this.lastUrl = EMPTY_STRING;
        this.active = active;
        this.icon = icon;
        this.isNotificationsEnabled = isNotificationsEnabled;
        this.notifications = 0;
        this.cookies = EMPTY_STRING;
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

    public int getNotifications() {
        return notifications;
    }

    public void setNotifications(int notifications) {
        this.notifications = notifications;
    }

    public WebViewChannel getWebViewChannel() {
        return webViewChannel;
    }

    public void setWebViewChannel(WebViewChannel webViewChannel) {
        this.webViewChannel = webViewChannel;
    }

    public String getCookies() {
        return cookies;
    }

    public void setCookies(String cookies) {
        this.cookies = cookies;
    }
}
