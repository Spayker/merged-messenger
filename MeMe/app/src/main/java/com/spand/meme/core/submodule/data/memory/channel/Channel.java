package com.spand.meme.core.submodule.data.memory.channel;

public final class Channel {

    private String name;
    private String shortName;
    private TYPE type;
    private Boolean active;
    private String homeUrl;
    private ICON icon;

    private Channel() { }

    Channel(String name, TYPE type, ICON icon, String homeUrl, Boolean active) {
        this.name = name;
        this.shortName = shortName;
        this.type = type;
        this.homeUrl = homeUrl;
        this.active = active;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getShortName() {
        return shortName;
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
}
