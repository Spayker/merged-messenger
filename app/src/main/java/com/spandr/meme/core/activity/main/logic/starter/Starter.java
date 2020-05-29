package com.spandr.meme.core.activity.main.logic.starter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

import static com.spandr.meme.core.common.data.memory.channel.DataChannelManager.createChannelManager;

/**
 * Represents start behaviour of application in context of channels. According to auth scenario
 * different approaches of channel setup will be used.
 * @author  Spayker
 * @version 1.0
 * @since   3/10/2019
 */
public interface Starter {

    void initApplication(SharedPreferences sharedPreferences);

    default void initChannelManager(AppCompatActivity mainActivity){
        createChannelManager(mainActivity, new ArrayList<>());
    }

}
