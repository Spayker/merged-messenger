package com.spand.meme.core.submodule.data.memory.channel;

import java.util.HashSet;
import java.util.Set;

public class ChannelManager {

    private static ChannelManager channelManager;
    private static Set<Channel> channels;

    private ChannelManager() { }

    private ChannelManager(Set<Channel> channels) {
        this.channels = channels;
    }

    public Set<Channel> getChannels() {
        return channels;
    }

    public static ChannelManager createChannelManager(Set<Channel> channels){
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

    public static Set<Channel> getActiveChannels(TYPE type) {
        Set<Channel> chns = new HashSet<>();
        for (Channel chn : channelManager.getChannels()) {
            if(chn.getType().equals(type) && chn.getActive()){
                chns.add(chn);
            }
        }
        return chns;
    }
}
