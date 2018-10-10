package com.spandr.meme.core.logic.starter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.spandr.meme.R;
import com.spandr.meme.core.data.memory.channel.Channel;
import com.spandr.meme.core.data.memory.channel.ChannelManager;

import java.util.Arrays;
import java.util.List;

import static com.spandr.meme.core.data.memory.channel.ChannelManager.createNewChannel;
import static com.spandr.meme.core.data.memory.channel.ChannelManager.isChannelExcludedByDefault;
import static com.spandr.meme.core.data.memory.channel.ICON.DC;
import static com.spandr.meme.core.data.memory.channel.ICON.FB;
import static com.spandr.meme.core.data.memory.channel.ICON.GM;
import static com.spandr.meme.core.data.memory.channel.ICON.ICQ;
import static com.spandr.meme.core.data.memory.channel.ICON.IN;
import static com.spandr.meme.core.data.memory.channel.ICON.LN;
import static com.spandr.meme.core.data.memory.channel.ICON.MAIL_RU;
import static com.spandr.meme.core.data.memory.channel.ICON.OK;
import static com.spandr.meme.core.data.memory.channel.ICON.PN;
import static com.spandr.meme.core.data.memory.channel.ICON.SK;
import static com.spandr.meme.core.data.memory.channel.ICON.SL;
import static com.spandr.meme.core.data.memory.channel.ICON.TL;
import static com.spandr.meme.core.data.memory.channel.ICON.TUM;
import static com.spandr.meme.core.data.memory.channel.ICON.TW;
import static com.spandr.meme.core.data.memory.channel.ICON.VK;
import static com.spandr.meme.core.data.memory.channel.ICON.YT;
import static com.spandr.meme.core.data.memory.channel.TYPE.CHAT;
import static com.spandr.meme.core.data.memory.channel.TYPE.EMAIL;
import static com.spandr.meme.core.data.memory.channel.TYPE.SOCIAL;
import static com.spandr.meme.core.logic.starter.SettingsConstants.KEY_CHANNEL_ORDER;
import static com.spandr.meme.core.ui.activity.ActivityConstants.DISCORD_HOME_URL;
import static com.spandr.meme.core.ui.activity.ActivityConstants.FB_HOME_URL;
import static com.spandr.meme.core.ui.activity.ActivityConstants.GMAIL_HOME_URL;
import static com.spandr.meme.core.ui.activity.ActivityConstants.ICQ_HOME_URL;
import static com.spandr.meme.core.ui.activity.ActivityConstants.INSTAGRAM_HOME_URL;
import static com.spandr.meme.core.ui.activity.ActivityConstants.LINKEDIN_HOME_URL;
import static com.spandr.meme.core.ui.activity.ActivityConstants.MAIL_RU_HOME_URL;
import static com.spandr.meme.core.ui.activity.ActivityConstants.ODNOKLASNIKI_HOME_URL;
import static com.spandr.meme.core.ui.activity.ActivityConstants.PINTEREST_HOME_URL;
import static com.spandr.meme.core.ui.activity.ActivityConstants.SKYPE_HOME_URL;
import static com.spandr.meme.core.ui.activity.ActivityConstants.SLACK_HOME_URL;
import static com.spandr.meme.core.ui.activity.ActivityConstants.TELEGRAM_HOME_URL;
import static com.spandr.meme.core.ui.activity.ActivityConstants.TUMBLR_HOME_URL;
import static com.spandr.meme.core.ui.activity.ActivityConstants.TWITTER_HOME_URL;
import static com.spandr.meme.core.ui.activity.ActivityConstants.VK_HOME_URL;
import static com.spandr.meme.core.ui.activity.ActivityConstants.YOUTUBE_HOME_URL;

public class Setupper implements Starter {

    private static Setupper instance;
    private static AppCompatActivity mainActivity;

    private Setupper() {
    }

    public static Setupper createSetupper(AppCompatActivity mA) {
        if (instance == null) {
            instance = new Setupper();
            mainActivity = mA;
        }
        return instance;
    }

    @Override
    public void initApplication(SharedPreferences sharedPreferences) {
        ChannelManager.clearChannels();
        initChannelManager(mainActivity);

        String deviceCountryCode = mainActivity.getResources().getConfiguration().locale.getCountry();
        String[] eastCountryCodes = mainActivity.getResources().getStringArray(R.array.east_country_codes);
        if (Arrays.asList(eastCountryCodes).contains(deviceCountryCode)) {
            initSocialGroupChannels(sharedPreferences, mainActivity).
                    initChatGroupChannels(sharedPreferences, mainActivity).
                    initEmailGroupChannels(sharedPreferences, mainActivity);
        } else {
            initSocialGroupChannels(sharedPreferences, mainActivity).
                    initChatGroupChannels(sharedPreferences, mainActivity).
                    initEmailGroupChannels(sharedPreferences, mainActivity);
        }
        setupChannelOrder(sharedPreferences);
    }

    private void setupChannelOrder(SharedPreferences sharedPreferences) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String deviceCountryCode = mainActivity.getResources().getConfiguration().locale.getCountry();
        String[] eastCountryCodes = mainActivity.getResources().getStringArray(R.array.east_country_codes);

        if (Arrays.asList(eastCountryCodes).contains(deviceCountryCode)) {
            editor.putString(KEY_CHANNEL_ORDER, mainActivity.getString(R.string.main_east_ordered_channel_set));
        } else {
            editor.putString(KEY_CHANNEL_ORDER, mainActivity.getString(R.string.main_west_ordered_channel_set));
        }

        editor.apply();
        editor.commit();
    }

    private Setupper initSocialGroupChannels(SharedPreferences sharedPreferences,
                                                   AppCompatActivity mainActivity) {
        List<Channel> channels = ChannelManager.getInstance().getChannels();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String fbKey = mainActivity.getString(R.string.channel_setting_fb);
        Channel facebookChannel = createNewChannel(fbKey, SOCIAL, FB, FB_HOME_URL,
                !isChannelExcludedByDefault(fbKey, mainActivity));
        channels.add(facebookChannel);
        editor.putBoolean(fbKey, facebookChannel.getActive());

        String vkKey = mainActivity.getString(R.string.channel_setting_vk);
        Channel vkontakteChannel = createNewChannel(vkKey, SOCIAL, VK, VK_HOME_URL,
                !isChannelExcludedByDefault(vkKey, mainActivity));
        channels.add(vkontakteChannel);
        editor.putBoolean(vkKey, vkontakteChannel.getActive());

        String twKey = mainActivity.getString(R.string.channel_setting_tw);
        Channel twitterChannel = createNewChannel(twKey, SOCIAL, TW, TWITTER_HOME_URL,
                !isChannelExcludedByDefault(twKey, mainActivity));
        channels.add(twitterChannel);
        editor.putBoolean(twKey, twitterChannel.getActive());

        String instKey = mainActivity.getString(R.string.channel_setting_inst);
        Channel instagramChannel = createNewChannel(instKey, SOCIAL, IN, INSTAGRAM_HOME_URL,
                !isChannelExcludedByDefault(instKey, mainActivity));
        channels.add(instagramChannel);
        editor.putBoolean(instKey, instagramChannel.getActive());

        String okKey = mainActivity.getString(R.string.channel_setting_ok);
        Channel okChannel = createNewChannel(okKey, SOCIAL, OK, ODNOKLASNIKI_HOME_URL,
                !isChannelExcludedByDefault(okKey, mainActivity));
        channels.add(okChannel);
        editor.putBoolean(okKey, okChannel.getActive());

        String ytKey = mainActivity.getString(R.string.channel_setting_yt);
        Channel youtubeChannel = createNewChannel(ytKey, SOCIAL, YT, YOUTUBE_HOME_URL,
                !isChannelExcludedByDefault(ytKey, mainActivity));
        channels.add(youtubeChannel);
        editor.putBoolean(ytKey, youtubeChannel.getActive());

        String tmbKey = mainActivity.getString(R.string.channel_setting_tmb);
        Channel tumblChannel = createNewChannel(tmbKey, SOCIAL, TUM, TUMBLR_HOME_URL,
                !isChannelExcludedByDefault(tmbKey, mainActivity));
        channels.add(tumblChannel);
        editor.putBoolean(tmbKey, tumblChannel.getActive());

        String pnKey = mainActivity.getString(R.string.channel_setting_pn);
        Channel pinterestChannel = createNewChannel(pnKey, SOCIAL, PN, PINTEREST_HOME_URL,
                !isChannelExcludedByDefault(pnKey, mainActivity));
        channels.add(pinterestChannel);
        editor.putBoolean(pnKey, pinterestChannel.getActive());

        String lnKey = mainActivity.getString(R.string.channel_setting_ln);
        Channel linkedinChannel = createNewChannel(lnKey, SOCIAL, LN, LINKEDIN_HOME_URL,
                !isChannelExcludedByDefault(lnKey, mainActivity));
        channels.add(linkedinChannel);
        editor.putBoolean(lnKey, linkedinChannel.getActive());
        editor.apply();
        editor.commit();
        return instance;
    }

    private Setupper initChatGroupChannels(SharedPreferences sharedPreferences,
                                                 AppCompatActivity mainActivity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<Channel> channels = ChannelManager.getInstance().getChannels();

        String tlKey = mainActivity.getString(R.string.channel_setting_tl);
        Channel telegramChannel = createNewChannel(tlKey, CHAT, TL, TELEGRAM_HOME_URL,
                !isChannelExcludedByDefault(tlKey, mainActivity));
        channels.add(telegramChannel);
        editor.putBoolean(tlKey, telegramChannel.getActive());

        String skpKey = mainActivity.getString(R.string.channel_setting_skp);
        Channel skypeChannel = createNewChannel(skpKey, CHAT, SK, SKYPE_HOME_URL,
                !isChannelExcludedByDefault(skpKey, mainActivity));
        channels.add(skypeChannel);
        editor.putBoolean(skpKey, skypeChannel.getActive());

        String icqKey = mainActivity.getString(R.string.channel_setting_icq);
        Channel icqChannel = createNewChannel(icqKey, CHAT, ICQ, ICQ_HOME_URL,
                !isChannelExcludedByDefault(icqKey, mainActivity));
        channels.add(icqChannel);
        editor.putBoolean(icqKey, icqChannel.getActive());

        String dcKey = mainActivity.getString(R.string.channel_setting_dc);
        Channel discordChannel = createNewChannel(dcKey, CHAT, DC, DISCORD_HOME_URL,
                !isChannelExcludedByDefault(dcKey, mainActivity));
        channels.add(discordChannel);
        editor.putBoolean(dcKey, discordChannel.getActive());

        String slKey = mainActivity.getString(R.string.channel_setting_slack);
        Channel slackChannel = createNewChannel(slKey, CHAT, SL, SLACK_HOME_URL,
                !isChannelExcludedByDefault(slKey, mainActivity));
        channels.add(slackChannel);
        editor.putBoolean(slKey, slackChannel.getActive());

        editor.apply();
        editor.commit();
        return instance;
    }

    private void initEmailGroupChannels(SharedPreferences sharedPreferences,
                                                  AppCompatActivity mainActivity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<Channel> channels = ChannelManager.getInstance().getChannels();

        String gmailKey = mainActivity.getString(R.string.channel_setting_gmail);
        Channel gmailChannel = createNewChannel(gmailKey, EMAIL, GM, GMAIL_HOME_URL,
                !isChannelExcludedByDefault(gmailKey, mainActivity));
        channels.add(gmailChannel);
        editor.putBoolean(gmailKey, gmailChannel.getActive());

        String mainRuKey = mainActivity.getString(R.string.channel_setting_mailru);
        Channel mailruChannel = createNewChannel(mainRuKey, EMAIL, MAIL_RU, MAIL_RU_HOME_URL,
                !isChannelExcludedByDefault(mainRuKey, mainActivity));
        channels.add(mailruChannel);
        editor.putBoolean(mainRuKey, mailruChannel.getActive());
        editor.apply();
        editor.commit();
    }

}
