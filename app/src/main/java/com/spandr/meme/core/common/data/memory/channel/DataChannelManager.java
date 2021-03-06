package com.spandr.meme.core.common.data.memory.channel;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.spandr.meme.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
*
*
* @author  Spayker
* @version 1.0
* @since   3/6/2019
*/
public class DataChannelManager {

    private static DataChannelManager dataChannelManager;
    private static List<String> westExcludedChannels;
    private static List<String> eastExcludedChannels;
    private static List<Channel> channels;

    @SuppressWarnings("unused")
    private DataChannelManager() {
    }

    public static void clearChannels() {
        if (channels != null) {
            channels.clear();
        }
    }

    private DataChannelManager(List<Channel> chnls) {
        channels = chnls;
    }

    public static DataChannelManager getInstance() {
        return dataChannelManager;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public static DataChannelManager createChannelManager(AppCompatActivity mainActivity, List<Channel> channels) {
        if (dataChannelManager == null) {
            dataChannelManager = new DataChannelManager(channels);
            westExcludedChannels = new LinkedList<>(Arrays.asList(mainActivity.getResources().getStringArray(R.array.default_excluded_west_social_group)));
            eastExcludedChannels = new LinkedList<>(Arrays.asList(mainActivity.getResources().getStringArray(R.array.default_excluded_east_social_group)));

            westExcludedChannels.addAll(new LinkedList<>(Arrays.asList(mainActivity.getResources().getStringArray(R.array.default_excluded_west_chat_group))));
            eastExcludedChannels.addAll(new LinkedList<>(Arrays.asList(mainActivity.getResources().getStringArray(R.array.default_excluded_east_chat_group))));

            westExcludedChannels.addAll(new LinkedList<>(Arrays.asList(mainActivity.getResources().getStringArray(R.array.default_excluded_west_info_group))));
            eastExcludedChannels.addAll(new LinkedList<>(Arrays.asList(mainActivity.getResources().getStringArray(R.array.default_excluded_east_info_group))));

            westExcludedChannels.addAll(new LinkedList<>(Arrays.asList(mainActivity.getResources().getStringArray(R.array.default_excluded_west_email_group))));
            eastExcludedChannels.addAll(new LinkedList<>(Arrays.asList(mainActivity.getResources().getStringArray(R.array.default_excluded_east_email_group))));

            westExcludedChannels.addAll(new LinkedList<>(Arrays.asList(mainActivity.getResources().getStringArray(R.array.default_excluded_west_video_group))));
            eastExcludedChannels.addAll(new LinkedList<>(Arrays.asList(mainActivity.getResources().getStringArray(R.array.default_excluded_east_video_group))));
        }
        return dataChannelManager;
    }

    public static Channel createNewChannel(Context context, String name, TYPE type, ICON icon, String homeUrl,
                                           boolean active) {
        return new Channel(context, name, type, icon, homeUrl, active);
    }

    private static List<Channel> getActiveChannelsByType(TYPE type) {
        List<Channel> channels = new ArrayList<>();
        for (Channel chn : dataChannelManager.getChannels()) {
            if (chn.getType().equals(type) && chn.getActive()) {
                channels.add(chn);
            }
        }
        return channels;
    }

    public static List<Channel> getAllActiveChannels() {
        List<Channel> channels = new ArrayList<>();
        channels.addAll(getActiveChannelsByType(TYPE.SOCIAL));
        channels.addAll(getActiveChannelsByType(TYPE.CHAT));
        channels.addAll(getActiveChannelsByType(TYPE.VIDEO_SERVICE));
        channels.addAll(getActiveChannelsByType(TYPE.INFO_SERVICE));
        channels.addAll(getActiveChannelsByType(TYPE.EMAIL));
        return channels;
    }

    public static Channel getChannelByName(String name) {
        for (Channel chn : new ArrayList<>(dataChannelManager.getChannels())) {
            if (chn.getName().equals(name)) {
                return chn;
            }
        }
        return null;
    }

    public static Boolean isChannelExcludedByDefault(String key, AppCompatActivity mainActivity) {
        String deviceCountryCode = mainActivity.getResources().getConfiguration().locale.toString().toUpperCase();
        String[] eastCountryCodes = mainActivity.getResources().getStringArray(R.array.east_country_codes);

        if (Arrays.asList(eastCountryCodes).contains(deviceCountryCode)) {
            for (String excChannel : eastExcludedChannels) {
                if (excChannel.equalsIgnoreCase(key)) {
                    return true;
                }
            }
            return false;
        } else {
            for (String excChannel : westExcludedChannels) {
                if (excChannel.equalsIgnoreCase(key)) {
                    return true;
                }
            }
            return false;
        }
    }
}
