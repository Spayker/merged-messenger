package com.spandr.meme.core.logic.starter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import static com.spandr.meme.core.data.memory.channel.ChannelManager.createChannelManager;

public interface Starter {

    String START_TYPE = "startType";
    String REGISTRATOR = "registrator";
    String USERNAME = "username";

    void initApplication(SharedPreferences sharedPreferences);

    default void initChannelManager(AppCompatActivity mainActivity){
        createChannelManager(mainActivity, new ArrayList<>());
    }

}
