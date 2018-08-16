package com.spand.meme.core.submodule.logic.starter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.spand.meme.R;
import com.spand.meme.core.submodule.data.memory.channel.Channel;
import com.spand.meme.core.submodule.data.memory.channel.ChannelManager;

import java.util.List;
import java.util.Set;

import static com.spand.meme.core.submodule.data.memory.channel.ChannelManager.createNewChannel;
import static com.spand.meme.core.submodule.data.memory.channel.TYPE.CHAT;
import static com.spand.meme.core.submodule.data.memory.channel.TYPE.EMAIL;
import static com.spand.meme.core.submodule.data.memory.channel.TYPE.SOCIAL;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_ACTIVATED_CHAT_AMOUNT;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_ACTIVATED_EMAIL_AMOUNT;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_ACTIVATED_SOCIAL_NET_AMOUNT;
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
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.DIS_ICON_NAME;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.FB_ICON_NAME;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.GM_ICON_NAME;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.ICQ_ICON_NAME;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.IN_ICON_NAME;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.LN_ICON_NAME;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.MAIL_RU_ICON_NAME;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.OK_ICON_NAME;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.SKP_ICON_NAME;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.TL_ICON_NAME;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.TUM_ICON_NAME;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.TW_ICON_NAME;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.VK_ICON_NAME;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.YOU_ICON_NAME;

public class Setupper implements Starter{

    @Override
    public Boolean initApplication(SharedPreferences sharedPreferences, AppCompatActivity mainActivity) {
        initChannelManager();
        return initTypicalSet(sharedPreferences, mainActivity);
    }

    private static Boolean initTypicalSet(SharedPreferences sharedPreferences, AppCompatActivity mainActivity) {
        int activatedSocialNetChannels = initSocialGroupChannels(sharedPreferences, mainActivity);
        int activatedChatChannels = initChatGroupChannels(sharedPreferences, mainActivity);
        int activatedEmailChannels = initEmailGroupChannels(sharedPreferences, mainActivity);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(KEY_ACTIVATED_SOCIAL_NET_AMOUNT, activatedSocialNetChannels);
        editor.putInt(KEY_ACTIVATED_CHAT_AMOUNT, activatedChatChannels);
        editor.putInt(KEY_ACTIVATED_EMAIL_AMOUNT, activatedEmailChannels);
        editor.apply();
        return true;
    }

    private static int initSocialGroupChannels(SharedPreferences sharedPreferences, AppCompatActivity mainActivity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<Channel> channels = ChannelManager.getInstance().getChannels();

        Channel facebookChannel = createNewChannel(mainActivity.getString(R.string.fb), SOCIAL, FB_ICON_NAME, true);
        channels.add(facebookChannel);
        editor.putBoolean(KEY_FACEBOOK, facebookChannel.getActive());

        Channel vkontakteChannel = createNewChannel(mainActivity.getString(R.string.vk), SOCIAL, VK_ICON_NAME, true);
        channels.add(vkontakteChannel);
        editor.putBoolean(KEY_VKONTAKTE, vkontakteChannel.getActive());

        Channel twitterChannel = createNewChannel(mainActivity.getString(R.string.tw), SOCIAL, TW_ICON_NAME, true);
        channels.add(twitterChannel);
        editor.putBoolean(KEY_TWITTER, twitterChannel.getActive());

        Channel instagramChannel = createNewChannel(mainActivity.getString(R.string.inst), SOCIAL, IN_ICON_NAME, true);
        channels.add(instagramChannel);
        editor.putBoolean(KEY_INSTAGRAM, instagramChannel.getActive());

        Channel okChannel = createNewChannel(mainActivity.getString(R.string.ok), SOCIAL, OK_ICON_NAME, false);
        channels.add(okChannel);
        editor.putBoolean(KEY_ODNOKLASSNIKI, okChannel.getActive());

        Channel youtubeChannel = createNewChannel(mainActivity.getString(R.string.yt), SOCIAL, YOU_ICON_NAME, true);
        channels.add(youtubeChannel);
        editor.putBoolean(KEY_YOUTUBE, youtubeChannel.getActive());

        Channel linkedinChannel = createNewChannel(mainActivity.getString(R.string.ln), SOCIAL, LN_ICON_NAME, true);
        channels.add(linkedinChannel);
        editor.putBoolean(KEY_LINKED_IN, linkedinChannel.getActive());

        editor.apply();
        return 6;
    }

    private static int initChatGroupChannels(SharedPreferences sharedPreferences, AppCompatActivity mainActivity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<Channel> channels = ChannelManager.getInstance().getChannels();

        Channel telegramChannel = createNewChannel(mainActivity.getString(R.string.tl), CHAT, TL_ICON_NAME, false);
        channels.add(telegramChannel);
        editor.putBoolean(KEY_TELEGRAM, telegramChannel.getActive());

        Channel tumblChannel = createNewChannel(mainActivity.getString(R.string.tmb), CHAT, TUM_ICON_NAME, true);
        channels.add(tumblChannel);
        editor.putBoolean(KEY_TUMBLR, tumblChannel.getActive());

        Channel skypeChannel = createNewChannel(mainActivity.getString(R.string.skp), CHAT, SKP_ICON_NAME, true);
        channels.add(skypeChannel);
        editor.putBoolean(KEY_SKYPE, skypeChannel.getActive());

        Channel icqChannel = createNewChannel(mainActivity.getString(R.string.icq), CHAT, ICQ_ICON_NAME, false);
        channels.add(icqChannel);
        editor.putBoolean(KEY_ICQ, icqChannel.getActive());

        Channel discordChannel = createNewChannel(mainActivity.getString(R.string.dc), CHAT, DIS_ICON_NAME, false);
        channels.add(discordChannel);
        editor.putBoolean(KEY_DISCORD, discordChannel.getActive());

        editor.apply();
        return 2;
    }

    private static int initEmailGroupChannels(SharedPreferences sharedPreferences, AppCompatActivity mainActivity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<Channel> channels = ChannelManager.getInstance().getChannels();

        Channel gmailChannel = createNewChannel(mainActivity.getString(R.string.gmail), EMAIL, GM_ICON_NAME, true);
        channels.add(gmailChannel);
        editor.putBoolean(KEY_GMAIL, gmailChannel.getActive());

        Channel mailruChannel = createNewChannel(mainActivity.getString(R.string.mailru), EMAIL, MAIL_RU_ICON_NAME, false);
        channels.add(mailruChannel);
        editor.putBoolean(KEY_MAIL_RU, mailruChannel.getActive());
        editor.apply();
        return 1;
    }


}
