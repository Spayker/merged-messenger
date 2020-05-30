package com.spandr.meme.core.common.util;

import android.content.Context;
import android.content.res.Resources;

import com.spandr.meme.R;

import static com.spandr.meme.core.common.ActivityConstants.EMPTY_STRING;

public final class SettingsUtils {

    public static String getChannelActivateValueIdByName(Context context, String channelName) {

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

        String pnKey = appResources.getString(R.string.channel_setting_pn);
        if(channelName.equalsIgnoreCase(pnKey)){
            return pnKey;
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

        String slackKey = appResources.getString(R.string.channel_setting_slack);
        if(channelName.equalsIgnoreCase(slackKey)){
            return slackKey;
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

    public static String getChannelNotificationValueIdByName(Context context, String channelName) {
        Resources appResources = context.getResources();
        String notificationPrefix = appResources.getString(R.string.channel_setting_notifications_prefix);

        String fbKey = appResources.getString(R.string.channel_setting_fb);
        if(channelName.equalsIgnoreCase(fbKey)){
            return fbKey+notificationPrefix;
        }

        String vkKey = appResources.getString(R.string.channel_setting_vk);
        if(channelName.equalsIgnoreCase(vkKey)){
            return vkKey+notificationPrefix;
        }

        String twKey = appResources.getString(R.string.channel_setting_tw);
        if(channelName.equalsIgnoreCase(twKey)){
            return twKey+notificationPrefix;
        }

        String instKey = appResources.getString(R.string.channel_setting_inst);
        if(channelName.equalsIgnoreCase(instKey)){
            return instKey+notificationPrefix;
        }

        String okKey = appResources.getString(R.string.channel_setting_ok);
        if(channelName.equalsIgnoreCase(okKey)){
            return okKey+notificationPrefix;
        }

        String tmbKey = appResources.getString(R.string.channel_setting_tmb);
        if(channelName.equalsIgnoreCase(tmbKey)){
            return tmbKey+notificationPrefix;
        }

        String lnKey = appResources.getString(R.string.channel_setting_ln);
        if(channelName.equalsIgnoreCase(lnKey)){
            return lnKey+notificationPrefix;
        }

        String tlKey = appResources.getString(R.string.channel_setting_tl);
        if(channelName.equalsIgnoreCase(tlKey)){
            return tlKey+notificationPrefix;
        }

        String skpKey = appResources.getString(R.string.channel_setting_skp);
        if(channelName.equalsIgnoreCase(skpKey)){
            return skpKey+notificationPrefix;
        }

        String icqKey = appResources.getString(R.string.channel_setting_icq);
        if(channelName.equalsIgnoreCase(icqKey)){
            return icqKey+notificationPrefix;
        }

        String gaduKey = appResources.getString(R.string.channel_setting_gadu);
        if(channelName.equalsIgnoreCase(gaduKey)){
            return gaduKey+notificationPrefix;
        }

        String dcKey = appResources.getString(R.string.channel_setting_dc);
        if(channelName.equalsIgnoreCase(dcKey)){
            return dcKey+notificationPrefix;
        }

        String ytKey = appResources.getString(R.string.channel_setting_yt);
        if(channelName.equalsIgnoreCase(ytKey)){
            return ytKey+notificationPrefix;
        }

        String twitchKey = appResources.getString(R.string.channel_setting_twitch);
        if(channelName.equalsIgnoreCase(twitchKey)){
            return twitchKey+notificationPrefix;
        }

        String habrKey = appResources.getString(R.string.channel_setting_habr);
        if(channelName.equalsIgnoreCase(habrKey)){
            return habrKey+notificationPrefix;
        }

        String redditKey = appResources.getString(R.string.channel_setting_reddit);
        if(channelName.equalsIgnoreCase(redditKey)){
            return redditKey+notificationPrefix;
        }

        String quoraKey = appResources.getString(R.string.channel_setting_quora);
        if(channelName.equalsIgnoreCase(quoraKey)){
            return quoraKey+notificationPrefix;
        }

        String stackKey = appResources.getString(R.string.channel_setting_stack);
        if(channelName.equalsIgnoreCase(stackKey)){
            return stackKey+notificationPrefix;
        }

        String gmailKey = appResources.getString(R.string.channel_setting_gmail);
        if(channelName.equalsIgnoreCase(gmailKey)){
            return gmailKey+notificationPrefix;
        }

        String mailruKey = appResources.getString(R.string.channel_setting_mailru);
        if(channelName.equalsIgnoreCase(mailruKey)){
            return mailruKey+notificationPrefix;
        }

        return EMPTY_STRING;
    }


}
