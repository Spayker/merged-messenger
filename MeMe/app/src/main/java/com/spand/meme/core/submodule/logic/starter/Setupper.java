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
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.TELEGRAM_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.TUMBLR_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.TWITTER_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.VK_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.YOUTUBE_HOME_URL;

public class Setupper implements Starter {

    private static Setupper instance;

    private Setupper() {
    }

    public static Setupper createSetupper() {
        if (instance == null) {
            instance = new Setupper();
        }
        return instance;
    }

    @Override
    public Boolean initApplication(SharedPreferences sharedPreferences, AppCompatActivity mainActivity) {
        initChannelManager();
        String selectedLanguage = Locale.getDefault().getLanguage();
        switch (selectedLanguage) {
            case RU: {
                return initSocialGroupChannels(sharedPreferences, mainActivity, KEY_LINKED_IN) &&
                        initChatGroupChannels(sharedPreferences, mainActivity, KEY_TUMBLR, KEY_TELEGRAM) &&
                        initEmailGroupChannels(sharedPreferences, mainActivity);
            }
            default: {
                return initSocialGroupChannels(sharedPreferences, mainActivity, KEY_VKONTAKTE, KEY_ODNOKLASSNIKI) &&
                        initChatGroupChannels(sharedPreferences, mainActivity) &&
                        initEmailGroupChannels(sharedPreferences, mainActivity, KEY_MAIL_RU);
            }
        }
    }

    private static Boolean initSocialGroupChannels(SharedPreferences sharedPreferences,
                                                   AppCompatActivity mainActivity,
                                                   String... excludedChannels) {
        List<Channel> channels = ChannelManager.getInstance().getChannels();
        if (channels.isEmpty()) {
            SharedPreferences.Editor editor = sharedPreferences.edit();

            Channel facebookChannel = createNewChannel(mainActivity.getString(R.string.fb), SOCIAL, FB, FB_HOME_URL,
                    !isChannelExcluded(excludedChannels, KEY_FACEBOOK));
            channels.add(facebookChannel);
            editor.putBoolean(KEY_FACEBOOK, facebookChannel.getActive());

            Channel vkontakteChannel = createNewChannel(mainActivity.getString(R.string.vk), SOCIAL, VK, VK_HOME_URL,
                    !isChannelExcluded(excludedChannels, KEY_VKONTAKTE));
            channels.add(vkontakteChannel);
            editor.putBoolean(KEY_VKONTAKTE, vkontakteChannel.getActive());

            Channel twitterChannel = createNewChannel(mainActivity.getString(R.string.tw), SOCIAL, TW, TWITTER_HOME_URL,
                    !isChannelExcluded(excludedChannels, KEY_TWITTER));
            channels.add(twitterChannel);
            editor.putBoolean(KEY_TWITTER, twitterChannel.getActive());

            Channel instagramChannel = createNewChannel(mainActivity.getString(R.string.inst), SOCIAL, IN, INSTAGRAM_HOME_URL,
                    !isChannelExcluded(excludedChannels, KEY_INSTAGRAM));
            channels.add(instagramChannel);
            editor.putBoolean(KEY_INSTAGRAM, instagramChannel.getActive());

            Channel okChannel = createNewChannel(mainActivity.getString(R.string.ok), SOCIAL, OK, ODNOKLASNIKI_HOME_URL,
                    !isChannelExcluded(excludedChannels, KEY_ODNOKLASSNIKI));
            channels.add(okChannel);
            editor.putBoolean(KEY_ODNOKLASSNIKI, okChannel.getActive());

            Channel youtubeChannel = createNewChannel(mainActivity.getString(R.string.yt), SOCIAL, YT, YOUTUBE_HOME_URL,
                    !isChannelExcluded(excludedChannels, KEY_YOUTUBE));
            channels.add(youtubeChannel);
            editor.putBoolean(KEY_YOUTUBE, youtubeChannel.getActive());

            Channel linkedinChannel = createNewChannel(mainActivity.getString(R.string.ln), SOCIAL, LN, LINKEDIN_HOME_URL,
                    !isChannelExcluded(excludedChannels, KEY_LINKED_IN));
            channels.add(linkedinChannel);
            editor.putBoolean(KEY_LINKED_IN, linkedinChannel.getActive());
            editor.apply();
            editor.commit();
            return true;
        }
        return false;
    }

    private static Boolean initChatGroupChannels(SharedPreferences sharedPreferences,
                                                 AppCompatActivity mainActivity,
                                                 String... excludedChannels) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<Channel> channels = ChannelManager.getInstance().getChannels();

        Channel telegramChannel = createNewChannel(mainActivity.getString(R.string.tl), CHAT, TL, TELEGRAM_HOME_URL,
                !isChannelExcluded(excludedChannels, KEY_TELEGRAM));
        channels.add(telegramChannel);
        editor.putBoolean(KEY_TELEGRAM, telegramChannel.getActive());

        Channel tumblChannel = createNewChannel(mainActivity.getString(R.string.tmb), CHAT, TUM, TUMBLR_HOME_URL,
                !isChannelExcluded(excludedChannels, KEY_TUMBLR));
        channels.add(tumblChannel);
        editor.putBoolean(KEY_TUMBLR, tumblChannel.getActive());

        Channel skypeChannel = createNewChannel(mainActivity.getString(R.string.skp), CHAT, SK, SKYPE_HOME_URL,
                !isChannelExcluded(excludedChannels, KEY_SKYPE));
        channels.add(skypeChannel);
        editor.putBoolean(KEY_SKYPE, skypeChannel.getActive());

        Channel icqChannel = createNewChannel(mainActivity.getString(R.string.icq), CHAT, ICQ, ICQ_HOME_URL,
                !isChannelExcluded(excludedChannels, KEY_ICQ));
        channels.add(icqChannel);
        editor.putBoolean(KEY_ICQ, icqChannel.getActive());

        Channel discordChannel = createNewChannel(mainActivity.getString(R.string.dc), CHAT, DC, DISCORD_HOME_URL,
                !isChannelExcluded(excludedChannels, KEY_DISCORD));
        channels.add(discordChannel);
        editor.putBoolean(KEY_DISCORD, discordChannel.getActive());
        editor.apply();
        editor.commit();
        return true;
    }

    private static Boolean initEmailGroupChannels(SharedPreferences sharedPreferences,
                                                  AppCompatActivity mainActivity,
                                                  String... excludedChannels) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<Channel> channels = ChannelManager.getInstance().getChannels();

        Channel gmailChannel = createNewChannel(mainActivity.getString(R.string.gmail), EMAIL, GM, GMAIL_HOME_URL,
                !isChannelExcluded(excludedChannels, KEY_GMAIL));
        channels.add(gmailChannel);
        editor.putBoolean(KEY_GMAIL, gmailChannel.getActive());

        Channel mailruChannel = createNewChannel(mainActivity.getString(R.string.mailru), EMAIL, MAIL_RU, MAIL_RU_HOME_URL,
                !isChannelExcluded(excludedChannels, KEY_MAIL_RU));
        channels.add(mailruChannel);
        editor.putBoolean(KEY_MAIL_RU, mailruChannel.getActive());
        editor.apply();
        editor.commit();
        return true;
    }

    private static Boolean isChannelExcluded(String[] excludedChannels, String key){
        for (String excludedChannel : excludedChannels) {
            if (excludedChannel.equalsIgnoreCase(key)){
                return true;
            }
        }
        return false;
    }

}
