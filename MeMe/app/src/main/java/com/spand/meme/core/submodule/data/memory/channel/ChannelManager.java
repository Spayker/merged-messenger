package com.spand.meme.core.submodule.data.memory.channel;

import java.util.ArrayList;
import java.util.List;

public class ChannelManager {

    private static ChannelManager channelManager;
    private static List<Channel> channels;

    private ChannelManager() { }

    private ChannelManager(List<Channel> channels) {
        this.channels = channels;
    }

    public List<Channel> getChannels() {
        return channels;
    }

    public static ChannelManager createChannelManager(List<Channel> channels){
        if(channelManager == null) {
            channelManager = new ChannelManager(channels);
        }
        return channelManager;
    }

    public static ChannelManager getInstance(){
        return channelManager;
    }

    public static Channel createNewChannel(String name, TYPE type, String iconPath, Boolean active){
        return new Channel(name, type, iconPath, active);
    }

    public static List<Channel> getActiveChannels(TYPE type) {
        List<Channel> chns = new ArrayList<>();
        for (Channel chn : channelManager.getChannels()) {
            if(chn.getType().equals(type) && chn.getActive()){
                chns.add(chn);
            }
        }
        return chns;
    }
}
