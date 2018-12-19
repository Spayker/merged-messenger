package com.spandr.meme.core.activity.settings.channel.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.support.v7.preference.PreferenceManager;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.spandr.meme.R;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.common.ActivityConstants.EMPTY_STRING;

public class ChannelViewHolder extends GroupViewHolder {

    private TextView channelName;
    private ImageView arrow;
    private ImageView icon;
    private Switch switcher;

    private SharedPreferences sharedPreferences;

    public ChannelViewHolder(View itemView) {
        super(itemView);
        channelName = itemView.findViewById(R.id.list_item_channel_name);
        arrow = itemView.findViewById(R.id.list_item_channel_arrow);
        icon = itemView.findViewById(R.id.list_item_channel_icon);
        switcher = itemView.findViewById(R.id.list_item_channel_switcher);

    }

    public void setChannelTitle(ExpandableGroup channel) {
        channelName.setText(channel.getTitle());
        icon.setBackgroundResource(((SingleCheckChannel) channel).getIconResId());
    }

    public void setStateValue(ExpandableGroup channel){
        Context context = itemView.getContext();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        String key = getChannelActivateValueIdByName(context, channel.getTitle());
        switcher.setChecked(sharedPreferences.getBoolean(key, false));
    }

    public void setSwitcherListener() {
        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

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
            return context.getString(R.string.channel_setting_key_facebook_switcher);
        }

        String vkKey = appResources.getString(R.string.channel_setting_vk);
        if(channelName.equalsIgnoreCase(vkKey)){
            return context.getString(R.string.channel_setting_key_vkontakte_switcher);
        }

        String twKey = appResources.getString(R.string.channel_setting_tw);
        if(channelName.equalsIgnoreCase(twKey)){
            return context.getString(R.string.channel_setting_key_twitch_switcher);
        }

        String instKey = appResources.getString(R.string.channel_setting_inst);
        if(channelName.equalsIgnoreCase(instKey)){
            return context.getString(R.string.channel_setting_key_instagram_switcher);
        }

        String okKey = appResources.getString(R.string.channel_setting_ok);
        if(channelName.equalsIgnoreCase(okKey)){
            return context.getString(R.string.channel_setting_key_odnoklassniki_switcher);
        }

        String tmbKey = appResources.getString(R.string.channel_setting_tmb);
        if(channelName.equalsIgnoreCase(tmbKey)){
            return context.getString(R.string.channel_setting_key_tumblr_switcher);
        }

        String lnKey = appResources.getString(R.string.channel_setting_ln);
        if(channelName.equalsIgnoreCase(lnKey)){
            return context.getString(R.string.channel_setting_key_linkedin_switcher);
        }

        String tlKey = appResources.getString(R.string.channel_setting_tl);
        if(channelName.equalsIgnoreCase(tlKey)){
            return context.getString(R.string.channel_setting_key_telegram_switcher);
        }

        String skpKey = appResources.getString(R.string.channel_setting_skp);
        if(channelName.equalsIgnoreCase(skpKey)){
            return context.getString(R.string.channel_setting_key_skype_switcher);
        }

        String icqKey = appResources.getString(R.string.channel_setting_icq);
        if(channelName.equalsIgnoreCase(icqKey)){
            return context.getString(R.string.channel_setting_key_icq_switcher);
        }

        String gaduKey = appResources.getString(R.string.channel_setting_gadu);
        if(channelName.equalsIgnoreCase(gaduKey)){
            return context.getString(R.string.channel_setting_key_gadu_switcher);
        }

        String dcKey = appResources.getString(R.string.channel_setting_dc);
        if(channelName.equalsIgnoreCase(dcKey)){
            return context.getString(R.string.channel_setting_key_discord_switcher);
        }

        String ytKey = appResources.getString(R.string.channel_setting_yt);
        if(channelName.equalsIgnoreCase(ytKey)){
            return context.getString(R.string.channel_setting_key_youtube_switcher);
        }

        String twitchKey = appResources.getString(R.string.channel_setting_twitch);
        if(channelName.equalsIgnoreCase(twitchKey)){
            return context.getString(R.string.channel_setting_key_twitch_switcher);
        }

        String habrKey = appResources.getString(R.string.channel_setting_habr);
        if(channelName.equalsIgnoreCase(habrKey)){
            return context.getString(R.string.channel_setting_key_habr_switcher);
        }

        String redditKey = appResources.getString(R.string.channel_setting_reddit);
        if(channelName.equalsIgnoreCase(redditKey)){
            return context.getString(R.string.channel_setting_key_reddit_switcher);
        }

        String quoraKey = appResources.getString(R.string.channel_setting_quora);
        if(channelName.equalsIgnoreCase(quoraKey)){
            return context.getString(R.string.channel_setting_key_quora_switcher);
        }

        String stackKey = appResources.getString(R.string.channel_setting_stack);
        if(channelName.equalsIgnoreCase(stackKey)){
            return context.getString(R.string.channel_setting_key_stack_switcher);
        }

        String gmailKey = appResources.getString(R.string.channel_setting_gmail);
        if(channelName.equalsIgnoreCase(gmailKey)){
            return context.getString(R.string.channel_setting_key_gmail_switcher);
        }

        String mailruKey = appResources.getString(R.string.channel_setting_mailru);
        if(channelName.equalsIgnoreCase(mailruKey)){
            return context.getString(R.string.channel_setting_key_mailru_switcher);
        }

        return EMPTY_STRING;
    }
}
