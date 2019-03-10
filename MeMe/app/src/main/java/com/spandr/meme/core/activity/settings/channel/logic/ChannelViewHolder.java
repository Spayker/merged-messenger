package com.spandr.meme.core.activity.settings.channel.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.spandr.meme.R;
import com.spandr.meme.core.common.data.memory.channel.Channel;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.common.data.memory.channel.DataChannelManager.getChannelByName;
import static com.spandr.meme.core.common.util.SettingsUtils.getChannelActivateValueIdByName;

/**
 *
 *
 * @author  Spayker
 * @version 1.0
 * @since   3/10/2019
 */
public class ChannelViewHolder extends GroupViewHolder {

    private TextView channelName;
    private ImageView arrow;
    private ImageView icon;
    private Switch switcher;

    private AppCompatActivity activity;


    // secondary fields
    private static String switcherOn;
    private static String switcherOff;

    public ChannelViewHolder(View itemView, AppCompatActivity activity) {
        super(itemView);
        channelName = itemView.findViewById(R.id.list_item_channel_name);
        arrow = itemView.findViewById(R.id.list_item_channel_arrow);
        icon = itemView.findViewById(R.id.list_item_channel_icon);
        switcher = itemView.findViewById(R.id.list_item_channel_switcher);

        Context context = switcher.getContext();
        switcherOn = context.getString(R.string.channel_setting_switcher_on);
        switcherOff = context.getString(R.string.channel_setting_switcher_off);
        this.activity = activity;
    }

    public void setChannelTitle(ExpandableGroup channel) {
        channelName.setText(channel.getTitle());
        icon.setBackgroundResource(((SingleCheckChannel) channel).getIconResId());
    }

    public void setStateValue(ExpandableGroup channel){
        String key = getChannelActivateValueIdByName(switcher.getContext(), channel.getTitle());
        SharedPreferences sharedPreferences = activity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        switcher.setChecked(sharedPreferences.getBoolean(key, false));
        boolean switched = switcher.isChecked();
        switcher.setText(switched ? switcherOn : switcherOff);

        // disables notification popup menu
        arrow.setActivated(false);
        arrow.setEnabled(false);
        arrow.setVisibility(View.INVISIBLE);
        arrow.setOnClickListener(null);
        setOnGroupClickListener(null);

    }

    public void setSwitcherChangeListener(ExpandableGroup expandableGroupChannel){
        String title = expandableGroupChannel.getTitle();
        switcher.setOnCheckedChangeListener((buttonView, isChecked) -> {
            boolean switched = switcher.isChecked();
            switcher.setText(switched ? switcherOn : switcherOff);

            Channel channel = getChannelByName(title);
            if(channel != null){
                channel.setActive(switched);
                SharedPreferences sharedPreferences = activity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String key = getChannelActivateValueIdByName(activity, title);
                editor.putBoolean(key, switched);
                editor.apply();
                editor.commit();
            }
        });
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, .5f, RELATIVE_TO_SELF, .5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.startAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, .5f, RELATIVE_TO_SELF, .5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.startAnimation(rotate);
    }
}
