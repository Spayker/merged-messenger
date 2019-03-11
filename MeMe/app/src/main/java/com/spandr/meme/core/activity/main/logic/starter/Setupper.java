package com.spandr.meme.core.activity.main.logic.starter;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.spandr.meme.R;
import com.spandr.meme.core.common.data.memory.channel.Channel;
import com.spandr.meme.core.common.data.memory.channel.DataChannelManager;
import com.spandr.meme.core.common.data.memory.channel.ICON;
import com.spandr.meme.core.common.data.memory.channel.TYPE;

import java.util.Arrays;
import java.util.List;

import static com.spandr.meme.core.common.data.memory.channel.DataChannelManager.createNewChannel;
import static com.spandr.meme.core.common.data.memory.channel.DataChannelManager.isChannelExcludedByDefault;
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
import static com.spandr.meme.core.common.data.memory.channel.TYPE.CHAT;
import static com.spandr.meme.core.common.data.memory.channel.TYPE.EMAIL;
import static com.spandr.meme.core.common.data.memory.channel.TYPE.INFO_SERVICE;
import static com.spandr.meme.core.common.data.memory.channel.TYPE.SOCIAL;
import static com.spandr.meme.core.common.data.memory.channel.TYPE.VIDEO_SERVICE;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_CHANNEL_ORDER;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.DISCORD_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.FB_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.GADU_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.GMAIL_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.HABR_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.ICQ_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.INSTAGRAM_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.LINKEDIN_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.MAIL_RU_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.ODNOKLASNIKI_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.PINTEREST_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.QUORA_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.REDDIT_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.SKYPE_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.SLACK_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.STACKOVERFLOW_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.TELEGRAM_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.TUMBLR_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.TWITCH_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.TWITTER_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.VK_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.YOUTUBE_HOME_URL;

/**
 *  Keeps logic that is responsible for channel setup during sign up scenario
 *
 * @author  Spayker
 * @version 1.0
 * @since   3/10/2019
 */
public class Setupper implements Starter {

    @SuppressLint("StaticFieldLeak")
    private static Setupper instance;
    @SuppressLint("StaticFieldLeak")
    private static AppCompatActivity mainActivity;
    private static String notificationPrefix;

    private Setupper() {
    }

    public static Setupper createSetupper(AppCompatActivity mA) {
        if (instance == null) {
            instance = new Setupper();
            mainActivity = mA;
            notificationPrefix = mainActivity.getString(R.string.channel_setting_notifications_prefix);
        }
        return instance;
    }

    @Override
    public void initApplication(SharedPreferences sharedPreferences) {
        DataChannelManager.clearChannels();
        initChannelManager(mainActivity);

        String deviceCountryCode = mainActivity.getResources().getConfiguration().locale.getCountry();
        String[] eastCountryCodes = mainActivity.getResources().getStringArray(R.array.east_country_codes);
        if (Arrays.asList(eastCountryCodes).contains(deviceCountryCode)) {
            initSocialGroupChannels(sharedPreferences, mainActivity).
                    initChatGroupChannels(sharedPreferences, mainActivity).
                    initVideoGroupChannels(sharedPreferences, mainActivity).
                    initInfoGroupChannels(sharedPreferences, mainActivity).
                    initEmailGroupChannels(sharedPreferences, mainActivity);
        } else {
            initSocialGroupChannels(sharedPreferences, mainActivity).
                    initChatGroupChannels(sharedPreferences, mainActivity).
                    initVideoGroupChannels(sharedPreferences, mainActivity).
                    initInfoGroupChannels(sharedPreferences, mainActivity).
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
        List<Channel> channels = DataChannelManager.getInstance().getChannels();

        initChannel(mainActivity.getString(R.string.channel_setting_fb),
                sharedPreferences, channels, SOCIAL, FB, FB_HOME_URL);

        initChannel(mainActivity.getString(R.string.channel_setting_vk),
                sharedPreferences, channels, SOCIAL, VK, VK_HOME_URL);

        initChannel(mainActivity.getString(R.string.channel_setting_tw),
                sharedPreferences, channels, SOCIAL, TW, TWITTER_HOME_URL);

        initChannel(mainActivity.getString(R.string.channel_setting_inst),
                sharedPreferences, channels, SOCIAL, IN, INSTAGRAM_HOME_URL);

        initChannel(mainActivity.getString(R.string.channel_setting_ok),
                sharedPreferences, channels, SOCIAL, OK, ODNOKLASNIKI_HOME_URL);

        initChannel(mainActivity.getString(R.string.channel_setting_tmb),
                sharedPreferences, channels, SOCIAL, TUM, TUMBLR_HOME_URL);

        initChannel(mainActivity.getString(R.string.channel_setting_pn),
                sharedPreferences, channels, SOCIAL, PN, PINTEREST_HOME_URL);

        initChannel(mainActivity.getString(R.string.channel_setting_ln),
                sharedPreferences, channels, SOCIAL, LN, LINKEDIN_HOME_URL);

        return instance;
    }

    private Setupper initChatGroupChannels(SharedPreferences sharedPreferences,
                                           AppCompatActivity mainActivity) {
        List<Channel> channels = DataChannelManager.getInstance().getChannels();

        initChannel(mainActivity.getString(R.string.channel_setting_tl),
                sharedPreferences, channels, CHAT, TL, TELEGRAM_HOME_URL);

        initChannel(mainActivity.getString(R.string.channel_setting_skp),
                sharedPreferences, channels, CHAT, SK, SKYPE_HOME_URL);

        initChannel(mainActivity.getString(R.string.channel_setting_icq),
                sharedPreferences, channels, CHAT, ICQ, ICQ_HOME_URL);

        initChannel(mainActivity.getString(R.string.channel_setting_gadu),
                sharedPreferences, channels, CHAT, GADU, GADU_HOME_URL);

        initChannel(mainActivity.getString(R.string.channel_setting_dc),
                sharedPreferences, channels, CHAT, DC, DISCORD_HOME_URL);

        initChannel(mainActivity.getString(R.string.channel_setting_slack),
                sharedPreferences, channels, CHAT, SL, SLACK_HOME_URL);

        return instance;
    }

    private Setupper initVideoGroupChannels(SharedPreferences sharedPreferences,
                                            AppCompatActivity mainActivity) {
        List<Channel> channels = DataChannelManager.getInstance().getChannels();

        initChannel(mainActivity.getString(R.string.channel_setting_yt),
                sharedPreferences, channels, VIDEO_SERVICE, YT, YOUTUBE_HOME_URL);

        initChannel(mainActivity.getString(R.string.channel_setting_twitch),
                sharedPreferences, channels, VIDEO_SERVICE, TWITCH, TWITCH_HOME_URL);
        return instance;
    }

    private Setupper initInfoGroupChannels(SharedPreferences sharedPreferences,
                                           AppCompatActivity mainActivity) {
        List<Channel> channels = DataChannelManager.getInstance().getChannels();

        initChannel(mainActivity.getString(R.string.channel_setting_habr),
                sharedPreferences, channels, INFO_SERVICE, HABR, HABR_HOME_URL);

        initChannel(mainActivity.getString(R.string.channel_setting_reddit),
                sharedPreferences, channels, INFO_SERVICE, REDDIT, REDDIT_HOME_URL);

        initChannel(mainActivity.getString(R.string.channel_setting_quora),
                sharedPreferences, channels, INFO_SERVICE, QUORA, QUORA_HOME_URL);

        initChannel(mainActivity.getString(R.string.channel_setting_stack),
                sharedPreferences, channels, INFO_SERVICE, STACK, STACKOVERFLOW_HOME_URL);
        return instance;
    }

    private void initEmailGroupChannels(SharedPreferences sharedPreferences,
                                        AppCompatActivity mainActivity) {
        List<Channel> channels = DataChannelManager.getInstance().getChannels();

        initChannel(mainActivity.getString(R.string.channel_setting_gmail),
                sharedPreferences, channels, EMAIL, GM, GMAIL_HOME_URL);

        initChannel(mainActivity.getString(R.string.channel_setting_mailru),
                sharedPreferences, channels, EMAIL, MAIL_RU, MAIL_RU_HOME_URL);
    }

    private void initChannel(String channelKey, SharedPreferences sharedPreferences, List<Channel> channels,
                             TYPE type, ICON icon, String homeUrl) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String channelKeyNotification = channelKey + notificationPrefix;

        boolean isChannleActive = sharedPreferences.getBoolean(channelKey, !isChannelExcludedByDefault(channelKey, mainActivity));
        boolean isChannelNotificationActive = sharedPreferences.getBoolean(channelKeyNotification, true);

        Channel channel = createNewChannel(mainActivity, channelKey, type, icon, homeUrl, isChannleActive);
        editor.putBoolean(channelKey, isChannleActive);
        editor.putBoolean(channelKeyNotification, isChannelNotificationActive);
        channels.add(channel);
        editor.apply();
        editor.commit();
    }

}
