package com.spand.meme.core.submodule.logic.starter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashSet;

import static com.spand.meme.core.submodule.data.memory.channel.ChannelManager.createChannelManager;

public interface Starter {

    String START_TYPE = "startType";
    String LOGINNER = "loginner";
    String REGISTRATOR = "registrator";

    Boolean initApplication(SharedPreferences sharedPreferences, AppCompatActivity mainActivity);

    default void initChannelManager(){
        createChannelManager(new ArrayList<>());
    }

}
