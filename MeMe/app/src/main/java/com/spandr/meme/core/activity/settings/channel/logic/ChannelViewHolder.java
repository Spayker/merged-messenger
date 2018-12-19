package com.spandr.meme.core.activity.settings.channel.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
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
import static com.spandr.meme.core.common.ActivityConstants.EMPTY_STRING;
import static com.spandr.meme.core.common.data.memory.channel.DataChannelManager.getChannelByName;

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

    private String getChannelActivateValueIdByName(Context context, String channelName) {

        Resources appResources = context.getResources();

        String fbKey = appResources.getString(R.string.channel_setting_fb);
        if(channelName.equalsIgnoreCase(fbKey)){
            return fbKey;
        }

        String vkKey = appResources.getString(R.string.channel_setting_vk);
        if(channelName.equalsIgnoreCase(vkKey)){
            return vkKey;
        }

        String twKey = appResources.getString(R.string.channel_setting_tw);
        if(channelName.equalsIgnoreCase(twKey)){
            return twKey;
        }

        String instKey = appResources.getString(R.string.channel_setting_inst);
        if(channelName.equalsIgnoreCase(instKey)){
            return instKey;
        }

        String okKey = appResources.getString(R.string.channel_setting_ok);
        if(channelName.equalsIgnoreCase(okKey)){
            return okKey;
        }

        String tmbKey = appResources.getString(R.string.channel_setting_tmb);
        if(channelName.equalsIgnoreCase(tmbKey)){
            return tmbKey;
        }

        String lnKey = appResources.getString(R.string.channel_setting_ln);
        if(channelName.equalsIgnoreCase(lnKey)){
            return lnKey;
        }

        String tlKey = appResources.getString(R.string.channel_setting_tl);
        if(channelName.equalsIgnoreCase(tlKey)){
            return tlKey;
        }

        String skpKey = appResources.getString(R.string.channel_setting_skp);
        if(channelName.equalsIgnoreCase(skpKey)){
            return skpKey;
        }

        String icqKey = appResources.getString(R.string.channel_setting_icq);
        if(channelName.equalsIgnoreCase(icqKey)){
            return icqKey;
        }

        String gaduKey = appResources.getString(R.string.channel_setting_gadu);
        if(channelName.equalsIgnoreCase(gaduKey)){
            return gaduKey;
        }

        String dcKey = appResources.getString(R.string.channel_setting_dc);
        if(channelName.equalsIgnoreCase(dcKey)){
            return dcKey;
        }

        String ytKey = appResources.getString(R.string.channel_setting_yt);
        if(channelName.equalsIgnoreCase(ytKey)){
            return ytKey;
        }

        String twitchKey = appResources.getString(R.string.channel_setting_twitch);
        if(channelName.equalsIgnoreCase(twitchKey)){
            return twitchKey;
        }

        String habrKey = appResources.getString(R.string.channel_setting_habr);
        if(channelName.equalsIgnoreCase(habrKey)){
            return habrKey;
        }

        String redditKey = appResources.getString(R.string.channel_setting_reddit);
        if(channelName.equalsIgnoreCase(redditKey)){
            return redditKey;
        }

        String quoraKey = appResources.getString(R.string.channel_setting_quora);
        if(channelName.equalsIgnoreCase(quoraKey)){
            return quoraKey;
        }

        String stackKey = appResources.getString(R.string.channel_setting_stack);
        if(channelName.equalsIgnoreCase(stackKey)){
            return stackKey;
        }

        String gmailKey = appResources.getString(R.string.channel_setting_gmail);
        if(channelName.equalsIgnoreCase(gmailKey)){
            return gmailKey;
        }

        String mailruKey = appResources.getString(R.string.channel_setting_mailru);
        if(channelName.equalsIgnoreCase(mailruKey)){
            return mailruKey;
        }

        return EMPTY_STRING;
    }
}
