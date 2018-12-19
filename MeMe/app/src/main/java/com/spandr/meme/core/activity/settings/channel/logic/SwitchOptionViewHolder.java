package com.spandr.meme.core.activity.settings.channel.logic;

import android.view.View;
import android.widget.Checkable;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.settings.channel.logic.listener.SwitcherChannelSettingListenerStorage;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

public class SwitchOptionViewHolder extends CheckableChildViewHolder {

    private Switch childSwitchView;

    public SwitchOptionViewHolder(View itemView) {
        super(itemView);
        childSwitchView = itemView.findViewById(R.id.list_item_switcher_option_name);
    }

    public void initListener(String channelName){
        SwitcherChannelSettingListenerStorage switcherChannelSettingListenerStorage =
                SwitcherChannelSettingListenerStorage.getInstance();
        CompoundButton.OnCheckedChangeListener checkedChangeListener;
        switch (channelName){
            case "Activate":{
                checkedChangeListener =
                        switcherChannelSettingListenerStorage.getChangeChannelActivationListener();
                childSwitchView.setOnCheckedChangeListener(checkedChangeListener);
                break;
            }
            case "Notifications":{
                checkedChangeListener =
                        switcherChannelSettingListenerStorage.getChangeChannelNotificationListener();
                childSwitchView.setOnCheckedChangeListener(checkedChangeListener);
            }
        }
    }

    @Override
    public Checkable getCheckable() {
        return childSwitchView;
    }

    public void setOptionName(String optionName) {
        childSwitchView.setText(optionName);
    }
}
