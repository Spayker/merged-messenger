package com.spandr.meme.core.common.data.memory.channel;

public final class Channel {

    private String name;
    private TYPE type;
    private String homeUrl;
    private ICON icon;
    private boolean active;
    private boolean isNotificationsEnabled;

    private Channel() { }

    Channel(String name, TYPE type, ICON icon, String homeUrl, boolean active, boolean isNotificationsEnabled) {
        this.name = name;
        this.type = type;
        this.homeUrl = homeUrl;
        this.active = active;
        this.icon = icon;
        this.isNotificationsEnabled = isNotificationsEnabled;
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
}
