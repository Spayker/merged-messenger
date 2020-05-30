package com.spandr.meme.core.activity.settings.channel.model;

import android.support.v7.app.AppCompatActivity;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.settings.channel.logic.SingleCheckChannel;

import java.util.Arrays;
import java.util.List;

import static com.spandr.meme.core.common.data.memory.channel.ICON.DC;
import static com.spandr.meme.core.common.data.memory.channel.ICON.FB;
import static com.spandr.meme.core.common.data.memory.channel.ICON.GADU;
import static com.spandr.meme.core.common.data.memory.channel.ICON.GM;
import static com.spandr.meme.core.common.data.memory.channel.ICON.HABR;
import static com.spandr.meme.core.common.data.memory.channel.ICON.ICQ;
import static com.spandr.meme.core.common.data.memory.channel.ICON.IN;
import static com.spandr.meme.core.common.data.memory.channel.ICON.LN;
import static com.spandr.meme.core.common.data.memory.channel.ICON.MAIL_RU;
import static com.spandr.meme.core.common.data.memory.channel.ICON.OK;
import static com.spandr.meme.core.common.data.memory.channel.ICON.PN;
import static com.spandr.meme.core.common.data.memory.channel.ICON.QUORA;
import static com.spandr.meme.core.common.data.memory.channel.ICON.REDDIT;
import static com.spandr.meme.core.common.data.memory.channel.ICON.SK;
import static com.spandr.meme.core.common.data.memory.channel.ICON.SL;
import static com.spandr.meme.core.common.data.memory.channel.ICON.STACK;
import static com.spandr.meme.core.common.data.memory.channel.ICON.TL;
import static com.spandr.meme.core.common.data.memory.channel.ICON.TUM;
import static com.spandr.meme.core.common.data.memory.channel.ICON.TW;
import static com.spandr.meme.core.common.data.memory.channel.ICON.TWITCH;
import static com.spandr.meme.core.common.data.memory.channel.ICON.VK;
import static com.spandr.meme.core.common.data.memory.channel.ICON.YT;

public class ChannelDataFactory {

    public static List<SingleCheckChannel> makeSocialChannels(AppCompatActivity activity) {
        return Arrays.asList(makeFacebookChannelSetting(activity),
                makeVkontakteChannelSetting(activity),
                makeTwitterChannelSetting(activity),
                makeInstagramChannelSetting(activity),
                makeOdnoklassnikiChannelSetting(activity),
                makeTumblrChannelSetting(activity),
                makeLinkedInChannelSetting(activity),
                makePinterestChannelSetting(activity),
                makeTelegramChannelSetting(activity),
                makeSkypeChannelSetting(activity),
                makeIcqChannelSetting(activity),
                makeGaduGaduChannelSetting(activity),
                makeDiscordChannelSetting(activity),
                makeSlackChannelSetting(activity),
                makeYoutubeChannelSetting(activity),
                makeTwitchChannelSetting(activity),
                makeHabrChannelSetting(activity),
                makeRedditChannelSetting(activity),
                makeQuoraChannelSetting(activity),
                makeStackOverflowChannelSetting(activity),
                makeGmailChannelSetting(activity),
                makeMailRuChannelSetting(activity)
        );
    }

    private static SingleCheckChannel makeFacebookChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_fb), makeChannelOptions(activity), FB.getIconId());
    }

    private static SingleCheckChannel makeVkontakteChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_vk), makeChannelOptions(activity), VK.getIconId());
    }

    private static SingleCheckChannel makeTwitterChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_tw), makeChannelOptions(activity), TW.getIconId());
    }

    private static SingleCheckChannel makeInstagramChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_inst), makeChannelOptions(activity), IN.getIconId());
    }

    private static SingleCheckChannel makeOdnoklassnikiChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_ok), makeChannelOptions(activity), OK.getIconId());
    }

    private static SingleCheckChannel makeTumblrChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_tmb), makeChannelOptions(activity), TUM.getIconId());
    }

    private static SingleCheckChannel makeLinkedInChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_ln), makeChannelOptions(activity), LN.getIconId());
    }

    private static SingleCheckChannel makePinterestChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_pn), makeChannelOptions(activity), PN.getIconId());
    }

    private static SingleCheckChannel makeTelegramChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_tl), makeChannelOptions(activity), TL.getIconId());
    }

    private static SingleCheckChannel makeSkypeChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_skp), makeChannelOptions(activity), SK.getIconId());
    }

    private static SingleCheckChannel makeIcqChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_icq), makeChannelOptions(activity), ICQ.getIconId());
    }

    private static SingleCheckChannel makeGaduGaduChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_gadu), makeChannelOptions(activity), GADU.getIconId());
    }

    private static SingleCheckChannel makeDiscordChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_dc), makeChannelOptions(activity), DC.getIconId());
    }

    private static SingleCheckChannel makeSlackChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_slack), makeChannelOptions(activity), SL.getIconId());
    }

    private static SingleCheckChannel makeYoutubeChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_yt), makeChannelOptions(activity), YT.getIconId());
    }

    private static SingleCheckChannel makeTwitchChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_twitch), makeChannelOptions(activity), TWITCH.getIconId());
    }

    private static SingleCheckChannel makeHabrChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_habr), makeChannelOptions(activity), HABR.getIconId());
    }

    private static SingleCheckChannel makeRedditChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_reddit), makeChannelOptions(activity), REDDIT.getIconId());
    }

    private static SingleCheckChannel makeQuoraChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_quora), makeChannelOptions(activity), QUORA.getIconId());
    }

    private static SingleCheckChannel makeStackOverflowChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_stack), makeChannelOptions(activity), STACK.getIconId());
    }

    private static SingleCheckChannel makeGmailChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_gmail), makeChannelOptions(activity), GM.getIconId());
    }

    private static SingleCheckChannel makeMailRuChannelSetting(AppCompatActivity activity) {
        return new SingleCheckChannel(activity.getResources().getString(R.string.channel_setting_mailru), makeChannelOptions(activity), MAIL_RU.getIconId());
    }

    private static List<Option> makeChannelOptions(AppCompatActivity activity) {
        Option notifications = new Option(activity.getResources().getString(R.string.channel_setting_notifications), true);
        return Arrays.asList(notifications);
    }

}

