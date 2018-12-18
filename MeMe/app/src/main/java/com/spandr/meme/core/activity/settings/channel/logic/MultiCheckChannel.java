package com.spandr.meme.core.activity.settings.channel.logic;

import com.thoughtbot.expandablecheckrecyclerview.models.MultiCheckExpandableGroup;

import java.util.List;

import com.spandr.meme.core.activity.settings.channel.model.Option;

public class MultiCheckChannel extends MultiCheckExpandableGroup {

    private int iconResId;

    public MultiCheckChannel(String title, List<Option> items, int iconResId) {
        super(title, items);
        this.iconResId = iconResId;
    }

    int getIconResId() {
        return iconResId;
    }
}

