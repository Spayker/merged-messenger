package com.spand.meme.core.submodule.logic.starter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.spand.meme.R;
import com.spand.meme.core.submodule.data.memory.channel.Channel;
import com.spand.meme.core.submodule.data.memory.channel.ChannelManager;

import java.util.List;
import java.util.Locale;

import static com.spand.meme.core.submodule.data.memory.channel.ChannelManager.createNewChannel;
import static com.spand.meme.core.submodule.data.memory.channel.ICON.DC;
import static com.spand.meme.core.submodule.data.memory.channel.ICON.FB;
import static com.spand.meme.core.submodule.data.memory.channel.ICON.GM;
import static com.spand.meme.core.submodule.data.memory.channel.ICON.ICQ;
import static com.spand.meme.core.submodule.data.memory.channel.ICON.IN;
import static com.spand.meme.core.submodule.data.memory.channel.ICON.LN;
import static com.spand.meme.core.submodule.data.memory.channel.ICON.MAIL_RU;
import static com.spand.meme.core.submodule.data.memory.channel.ICON.OK;
import static com.spand.meme.core.submodule.data.memory.channel.ICON.SK;
import static com.spand.meme.core.submodule.data.memory.channel.ICON.SL;
import static com.spand.meme.core.submodule.data.memory.channel.ICON.TL;
import static com.spand.meme.core.submodule.data.memory.channel.ICON.TUM;
import static com.spand.meme.core.submodule.data.memory.channel.ICON.TW;
import static com.spand.meme.core.submodule.data.memory.channel.ICON.VK;
import static com.spand.meme.core.submodule.data.memory.channel.ICON.YT;
import static com.spand.meme.core.submodule.data.memory.channel.TYPE.CHAT;
import static com.spand.meme.core.submodule.data.memory.channel.TYPE.EMAIL;
import static com.spand.meme.core.submodule.data.memory.channel.TYPE.SOCIAL;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_DISCORD;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_FACEBOOK;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_GMAIL;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_ICQ;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_INSTAGRAM;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_LINKED_IN;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_MAIL_RU;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_ODNOKLASSNIKI;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_SKYPE;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_SLACK;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_TELEGRAM;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_TUMBLR;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_TWITTER;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_VKONTAKTE;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_YOUTUBE;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.RU;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.DISCORD_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.FB_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.GMAIL_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.ICQ_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.INSTAGRAM_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.LINKEDIN_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.MAIL_RU_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.ODNOKLASNIKI_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.SKYPE_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.SLACK_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.TELEGRAM_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.TUMBLR_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.TWITTER_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.VK_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.YOUTUBE_HOME_URL;

public class Loginner implements Starter {

    private static Loginner instance;

    private Loginner() { }

    public static Loginner createLoginner() {
        if (instance == null) {
            instance = new Loginner();
        }
        return instance;
    }

    @Override
    public Boolean initApplication(SharedPreferences sharedPreferences, AppCompatActivity mainActivity) {
        initChannelManager();
        initChannelManager();
        String selectedLanguage = Locale.getDefault().getLanguage();
        switch (selectedLanguage) {
            case RU: {
                return initSocialGroupChannels(sharedPreferences, mainActivity) &&
                        initChatGroupChannels(sharedPreferences, mainActivity) &&
                        initEmailGroupChannels(sharedPreferences, mainActivity);
            }
            default: {
                return initSocialGroupChannels(sharedPreferences, mainActivity) &&
                        initChatGroupChannels(sharedPreferences, mainActivity) &&
                        initEmailGroupChannels(sharedPreferences, mainActivity);
            }
        }
    }

    private static Boolean initSocialGroupChannels(SharedPreferences sharedPreferences,
                                                   AppCompatActivity mainActivity) {
        List<Channel> channels = ChannelManager.getInstance().getChannels();
        if (channels.isEmpty()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            Channel facebookChannel = createNewChannel(mainActivity.getString(R.string.channel_setting_fb),
                    SOCIAL, FB, FB_HOME_URL,
                    sharedPreferences.getBoolean(KEY_FACEBOOK, false));
            channels.add(facebookChannel);

            Channel vkontakteChannel = createNewChannel(mainActivity.getString(R.string.channel_setting_vk),
                    SOCIAL, VK, VK_HOME_URL,
                    sharedPreferences.getBoolean(KEY_VKONTAKTE, false));
            channels.add(vkontakteChannel);

            Channel twitterChannel = createNewChannel(mainActivity.getString(R.string.channel_setting_tw),
                    SOCIAL, TW, TWITTER_HOME_URL,
                    sharedPreferences.getBoolean(KEY_TWITTER, false));
            channels.add(twitterChannel);

            Channel instagramChannel = createNewChannel(mainActivity.getString(R.string.channel_setting_inst),
                    SOCIAL, IN, INSTAGRAM_HOME_URL,
                    sharedPreferences.getBoolean(KEY_INSTAGRAM, false));
            channels.add(instagramChannel);

            Channel okChannel = createNewChannel(mainActivity.getString(R.string.channel_setting_ok),
                    SOCIAL, OK, ODNOKLASNIKI_HOME_URL,
                    sharedPreferences.getBoolean(KEY_ODNOKLASSNIKI, false));
            channels.add(okChannel);

            Channel youtubeChannel = createNewChannel(mainActivity.getString(R.string.channel_setting_yt),
                    SOCIAL, YT, YOUTUBE_HOME_URL,
                    sharedPreferences.getBoolean(KEY_YOUTUBE, false));
            channels.add(youtubeChannel);

            Channel linkedinChannel = createNewChannel(mainActivity.getString(R.string.channel_setting_ln),
                    SOCIAL, LN, LINKEDIN_HOME_URL,
                    sharedPreferences.getBoolean(KEY_LINKED_IN, false));
            channels.add(linkedinChannel);
            editor.apply();
            editor.commit();
            return true;
        }
        return false;
    }

    private static Boolean initChatGroupChannels(SharedPreferences sharedPreferences,
                                                 AppCompatActivity mainActivity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<Channel> channels = ChannelManager.getInstance().getChannels();

        Channel telegramChannel = createNewChannel(mainActivity.getString(R.string.channel_setting_tl),
                CHAT, TL, TELEGRAM_HOME_URL,
                sharedPreferences.getBoolean(KEY_TELEGRAM, false));
        channels.add(telegramChannel);

        Channel tumblChannel = createNewChannel(mainActivity.getString(R.string.channel_setting_tmb),
                CHAT, TUM, TUMBLR_HOME_URL,
                sharedPreferences.getBoolean(KEY_TUMBLR, false));
        channels.add(tumblChannel);

        Channel skypeChannel = createNewChannel(mainActivity.getString(R.string.channel_setting_skp),
                CHAT, SK, SKYPE_HOME_URL,
                sharedPreferences.getBoolean(KEY_SKYPE, false));
        channels.add(skypeChannel);

        Channel icqChannel = createNewChannel(mainActivity.getString(R.string.channel_setting_icq),
                CHAT, ICQ, ICQ_HOME_URL,
                sharedPreferences.getBoolean(KEY_ICQ, false));
        channels.add(icqChannel);

        Channel discordChannel = createNewChannel(mainActivity.getString(R.string.channel_setting_dc),
                CHAT, DC, DISCORD_HOME_URL,
                sharedPreferences.getBoolean(KEY_DISCORD, false));
        channels.add(discordChannel);

        Channel slackChannel = createNewChannel(mainActivity.getString(R.string.channel_setting_slack),
                CHAT, SL, SLACK_HOME_URL,
                sharedPreferences.getBoolean(KEY_SLACK, false));
        channels.add(slackChannel);

        editor.apply();
        editor.commit();
        return true;
    }

    private static Boolean initEmailGroupChannels(SharedPreferences sharedPreferences,
                                                  AppCompatActivity mainActivity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<Channel> channels = ChannelManager.getInstance().getChannels();

        Channel gmailChannel = createNewChannel(mainActivity.getString(R.string.channel_setting_gmail),
                EMAIL, GM, GMAIL_HOME_URL,
                sharedPreferences.getBoolean(KEY_GMAIL, false));
        channels.add(gmailChannel);

        Channel mailruChannel = createNewChannel(mainActivity.getString(R.string.channel_setting_mailru),
                EMAIL, MAIL_RU, MAIL_RU_HOME_URL,
                sharedPreferences.getBoolean(KEY_MAIL_RU, false));
        channels.add(mailruChannel);
        editor.apply();
        editor.commit();
        return true;
    }

}
