package com.spand.meme.core.data.memory.channel;

import android.support.v7.app.AppCompatActivity;

import com.spand.meme.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import static com.spand.meme.core.logic.starter.SettingsConstants.RU;

public class ChannelManager {

    private static ChannelManager channelManager;
    private static List<String> westExcludedChannels;
    private static List<String> eastExcludedChannels;
    private static List<Channel> channels;

    private ChannelManager() {
    }

    private ChannelManager(List<Channel> channels) {
        this.channels = channels;
    }

    public static ChannelManager getInstance() {
        return channelManager;
    }

    public static Boolean clearChannels() {
        if (channels != null) {
            channels.clear();
            return true;
        }
        return false;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public static ChannelManager createChannelManager(AppCompatActivity mainActivity, List<Channel> channels) {
        if (channelManager == null) {
            channelManager = new ChannelManager(channels);
            westExcludedChannels = new LinkedList<>(Arrays.asList(mainActivity.getResources().getStringArray(R.array.default_excluded_west_social_group)));
            eastExcludedChannels = new LinkedList<>(Arrays.asList(mainActivity.getResources().getStringArray(R.array.default_excluded_east_social_group)));

            westExcludedChannels.addAll(new LinkedList<>(Arrays.asList(mainActivity.getResources().getStringArray(R.array.default_excluded_west_chat_group))));
            eastExcludedChannels.addAll(new LinkedList<>(Arrays.asList(mainActivity.getResources().getStringArray(R.array.default_excluded_east_chat_group))));

            westExcludedChannels.addAll(new LinkedList<>(Arrays.asList(mainActivity.getResources().getStringArray(R.array.default_excluded_west_email_group))));
            eastExcludedChannels.addAll(new LinkedList<>(Arrays.asList(mainActivity.getResources().getStringArray(R.array.default_excluded_east_email_group))));
        }
        return channelManager;
    }

    public static Channel createNewChannel(String name, TYPE type, ICON icon, String homeUrl, Boolean active) {
        return new Channel(name, type, icon, homeUrl, active);
    }

    public static List<Channel> getActiveChannels(TYPE type) {
        List<Channel> channels = new ArrayList<>();
        for (Channel chn : channelManager.getChannels()) {
            if (chn.getType().equals(type) && chn.getActive()) {
                channels.add(chn);
            }
        }
        return channels;
    }

    public static Channel getChannelByName(String name) {
        for (Channel chn : channelManager.getChannels()) {
            if (chn.getName().equals(name)) {
                return chn;
            }
        }
        return null;
    }

    public static Boolean isChannelExcludedByDefault(String key) {
        String selectedLanguage = Locale.getDefault().getLanguage();
        switch (selectedLanguage) {
            case RU: {
                for (String excChannel : eastExcludedChannels) {
                    if (excChannel.equalsIgnoreCase(key)) {
                        return true;
                    }
                }
                return false;
            }
            default: {
                for (String excChannel : westExcludedChannels) {
                    if (excChannel.equalsIgnoreCase(key)) {
                        return true;
                    }
                }
                return false;
            }
        }
    }
}