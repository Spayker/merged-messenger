package com.spand.meme.core.submodule.data.memory.channel;

public class Channel {

    private String name;
    private TYPE type;
    private String iconPath;
    private Boolean active;

    private Channel() { }

    Channel(String name, TYPE type, String iconPath, Boolean active) {
        this.name = name;
        this.type = type;
        this.iconPath = iconPath;
        this.iconPath = iconPath;
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public TYPE getType() {
        return type;
    }

    public String getIconPath() {
        return iconPath;
    }

    public Boolean getActive() {
        return active;
    }
}
