package com.spandr.meme.core.activity.settings.channel.logic.listener;

import android.support.v7.app.AppCompatActivity;
import android.widget.CompoundButton;

public class SwitcherChannelSettingListenerStorage {

    private static SwitcherChannelSettingListenerStorage instance;

    private SwitcherChannelSettingListenerStorage() { }

    public static SwitcherChannelSettingListenerStorage getInstance(){
        if(instance == null){
            instance = new SwitcherChannelSettingListenerStorage();
        }
        return instance;
    }

    private CompoundButton.OnCheckedChangeListener changeChannelActivationListener = (buttonView, isChecked) -> {
        System.out.println("test activate");
    };

    private CompoundButton.OnCheckedChangeListener changeChannelNotificationListener = (buttonView, isChecked) -> {
        System.out.println("test notification");
    };

    public CompoundButton.OnCheckedChangeListener getChangeChannelActivationListener() {
        return changeChannelActivationListener;
    }

    public CompoundButton.OnCheckedChangeListener getChangeChannelNotificationListener() {
        return changeChannelNotificationListener;
    }

}
