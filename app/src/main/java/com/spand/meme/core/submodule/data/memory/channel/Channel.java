package com.spand.meme.core.submodule.data.memory.channel;

public final class Channel {

    private String name;
    private TYPE type;
    private Boolean active;
    private String homeUrl;
    private ICON icon;

    private Channel() {
    }

    Channel(String name, TYPE type, ICON icon, String homeUrl, Boolean active) {
        this.name = name;
        this.type = type;
        this.homeUrl = homeUrl;
        this.active = active;
        this.icon = icon;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
