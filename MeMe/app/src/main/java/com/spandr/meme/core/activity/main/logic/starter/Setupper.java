package com.spandr.meme.core.activity.main.logic.starter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.main.logic.notification.ViewChannelManager;
import com.spandr.meme.core.common.data.memory.channel.Channel;
import com.spandr.meme.core.common.data.memory.channel.DataChannelManager;

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
import static com.spandr.meme.core.common.util.SettingsUtils.getChannelNotificationValueIdByName;

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
        DataChannelManager.clearChannels();
        ViewChannelManager.clearChannelViews();
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
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String notificationPrefix = mainActivity.getString(R.string.channel_setting_notifications_prefix);

        String fbKey = mainActivity.getString(R.string.channel_setting_fb);
        String fbKeyNotification = fbKey + notificationPrefix;
        Channel facebookChannel = createNewChannel(fbKey, SOCIAL, FB, FB_HOME_URL,
                !isChannelExcludedByDefault(fbKey, mainActivity), true);
        channels.add(facebookChannel);
        editor.putBoolean(fbKey, facebookChannel.getActive());
        editor.putBoolean(fbKeyNotification, facebookChannel.getNotificationsEnabled());

        String vkKey = mainActivity.getString(R.string.channel_setting_vk);
        String vkKeyNotification = vkKey + notificationPrefix;
        Channel vkontakteChannel = createNewChannel(vkKey, SOCIAL, VK, VK_HOME_URL,
                !isChannelExcludedByDefault(vkKey, mainActivity), true);
        channels.add(vkontakteChannel);
        editor.putBoolean(vkKey, vkontakteChannel.getActive());
        editor.putBoolean(vkKeyNotification, vkontakteChannel.getNotificationsEnabled());

        String twKey = mainActivity.getString(R.string.channel_setting_tw);
        String twKeyNotification = twKey + notificationPrefix;
        Channel twitterChannel = createNewChannel(twKey, SOCIAL, TW, TWITTER_HOME_URL,
                !isChannelExcludedByDefault(twKey, mainActivity), true);
        channels.add(twitterChannel);
        editor.putBoolean(twKey, twitterChannel.getActive());
        editor.putBoolean(twKeyNotification, twitterChannel.getNotificationsEnabled());

        String instKey = mainActivity.getString(R.string.channel_setting_inst);
        String instKeyNotification = instKey + notificationPrefix;
        Channel instagramChannel = createNewChannel(instKey, SOCIAL, IN, INSTAGRAM_HOME_URL,
                !isChannelExcludedByDefault(instKey, mainActivity), true);
        channels.add(instagramChannel);
        editor.putBoolean(instKey, instagramChannel.getActive());
        editor.putBoolean(instKeyNotification, instagramChannel.getNotificationsEnabled());

        String okKey = mainActivity.getString(R.string.channel_setting_ok);
        String okKeyNotification = okKey + notificationPrefix;
        Channel okChannel = createNewChannel(okKey, SOCIAL, OK, ODNOKLASNIKI_HOME_URL,
                !isChannelExcludedByDefault(okKey, mainActivity), true);
        channels.add(okChannel);
        editor.putBoolean(okKey, okChannel.getActive());
        editor.putBoolean(okKeyNotification, okChannel.getNotificationsEnabled());

        String tmbKey = mainActivity.getString(R.string.channel_setting_tmb);
        String tmbKeyNotification = tmbKey + notificationPrefix;
        Channel tumblChannel = createNewChannel(tmbKey, SOCIAL, TUM, TUMBLR_HOME_URL,
                !isChannelExcludedByDefault(tmbKey, mainActivity), true);
        channels.add(tumblChannel);
        editor.putBoolean(tmbKey, tumblChannel.getActive());
        editor.putBoolean(tmbKeyNotification, tumblChannel.getNotificationsEnabled());

        String pnKey = mainActivity.getString(R.string.channel_setting_pn);
        String pnKeyNotification = pnKey + notificationPrefix;
        Channel pinterestChannel = createNewChannel(pnKey, SOCIAL, PN, PINTEREST_HOME_URL,
                !isChannelExcludedByDefault(pnKey, mainActivity), true);
        channels.add(pinterestChannel);
        editor.putBoolean(pnKey, pinterestChannel.getActive());
        editor.putBoolean(pnKeyNotification, tumblChannel.getNotificationsEnabled());

        String lnKey = mainActivity.getString(R.string.channel_setting_ln);
        Channel linkedinChannel = createNewChannel(lnKey, SOCIAL, LN, LINKEDIN_HOME_URL,
                !isChannelExcludedByDefault(lnKey, mainActivity), true);
        channels.add(linkedinChannel);
        editor.putBoolean(lnKey, linkedinChannel.getActive());
        editor.apply();
        editor.commit();
        return instance;
    }

    private Setupper initChatGroupChannels(SharedPreferences sharedPreferences,
                                           AppCompatActivity mainActivity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<Channel> channels = DataChannelManager.getInstance().getChannels();

        String tlKey = mainActivity.getString(R.string.channel_setting_tl);
        Channel telegramChannel = createNewChannel(tlKey, CHAT, TL, TELEGRAM_HOME_URL,
                !isChannelExcludedByDefault(tlKey, mainActivity), true);
        channels.add(telegramChannel);
        editor.putBoolean(tlKey, telegramChannel.getActive());

        String skpKey = mainActivity.getString(R.string.channel_setting_skp);
        Channel skypeChannel = createNewChannel(skpKey, CHAT, SK, SKYPE_HOME_URL,
                !isChannelExcludedByDefault(skpKey, mainActivity), true);
        channels.add(skypeChannel);
        editor.putBoolean(skpKey, skypeChannel.getActive());

        String icqKey = mainActivity.getString(R.string.channel_setting_icq);
        Channel icqChannel = createNewChannel(icqKey, CHAT, ICQ, ICQ_HOME_URL,
                !isChannelExcludedByDefault(icqKey, mainActivity), true);
        channels.add(icqChannel);
        editor.putBoolean(icqKey, icqChannel.getActive());

        String gaduKey = mainActivity.getString(R.string.channel_setting_gadu);
        Channel gaduChannel = createNewChannel(gaduKey, CHAT, GADU, GADU_HOME_URL,
                !isChannelExcludedByDefault(gaduKey, mainActivity), true);
        channels.add(gaduChannel);
        editor.putBoolean(gaduKey, icqChannel.getActive());

        String dcKey = mainActivity.getString(R.string.channel_setting_dc);
        Channel discordChannel = createNewChannel(dcKey, CHAT, DC, DISCORD_HOME_URL,
                !isChannelExcludedByDefault(dcKey, mainActivity), true);
        channels.add(discordChannel);
        editor.putBoolean(dcKey, discordChannel.getActive());

        String slKey = mainActivity.getString(R.string.channel_setting_slack);
        Channel slackChannel = createNewChannel(slKey, CHAT, SL, SLACK_HOME_URL,
                !isChannelExcludedByDefault(slKey, mainActivity), true);
        channels.add(slackChannel);
        editor.putBoolean(slKey, slackChannel.getActive());

        editor.apply();
        editor.commit();
        return instance;
    }

    private Setupper initVideoGroupChannels(SharedPreferences sharedPreferences,
                                            AppCompatActivity mainActivity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<Channel> channels = DataChannelManager.getInstance().getChannels();

        String ytKey = mainActivity.getString(R.string.channel_setting_yt);
        Channel youtubeChannel = createNewChannel(ytKey, VIDEO_SERVICE, YT, YOUTUBE_HOME_URL,
                !isChannelExcludedByDefault(ytKey, mainActivity), true);
        channels.add(youtubeChannel);
        editor.putBoolean(ytKey, youtubeChannel.getActive());

        String twitchKey = mainActivity.getString(R.string.channel_setting_twitch);
        Channel twitchChannel = createNewChannel(twitchKey, VIDEO_SERVICE, TWITCH, TWITCH_HOME_URL,
                !isChannelExcludedByDefault(twitchKey, mainActivity), true);
        channels.add(twitchChannel);
        editor.putBoolean(twitchKey, twitchChannel.getActive());

        editor.apply();
        editor.commit();
        return instance;
    }

    private Setupper initInfoGroupChannels(SharedPreferences sharedPreferences,
                                           AppCompatActivity mainActivity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<Channel> channels = DataChannelManager.getInstance().getChannels();

        String habrKey = mainActivity.getString(R.string.channel_setting_habr);
        Channel habrChannel = createNewChannel(habrKey, INFO_SERVICE, HABR, HABR_HOME_URL,
                !isChannelExcludedByDefault(habrKey, mainActivity), true);
        channels.add(habrChannel);
        editor.putBoolean(habrKey, habrChannel.getActive());

        String redditKey = mainActivity.getString(R.string.channel_setting_reddit);
        Channel redditChannel = createNewChannel(redditKey, INFO_SERVICE, REDDIT, REDDIT_HOME_URL,
                !isChannelExcludedByDefault(redditKey, mainActivity), true);
        channels.add(redditChannel);
        editor.putBoolean(redditKey, redditChannel.getActive());

        String quoraKey = mainActivity.getString(R.string.channel_setting_quora);
        Channel quoraChannel = createNewChannel(quoraKey, INFO_SERVICE, QUORA, QUORA_HOME_URL,
                !isChannelExcludedByDefault(quoraKey, mainActivity), true);
        channels.add(quoraChannel);
        editor.putBoolean(quoraKey, quoraChannel.getActive());

        String stackKey = mainActivity.getString(R.string.channel_setting_stack);
        Channel stackChannel = createNewChannel(quoraKey, INFO_SERVICE, STACK, STACKOVERFLOW_HOME_URL,
                !isChannelExcludedByDefault(stackKey, mainActivity), true);
        channels.add(stackChannel);
        editor.putBoolean(stackKey, stackChannel.getActive());

        editor.apply();
        editor.commit();
        return instance;
    }

    private void initEmailGroupChannels(SharedPreferences sharedPreferences,
                                        AppCompatActivity mainActivity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<Channel> channels = DataChannelManager.getInstance().getChannels();

        String gmailKey = mainActivity.getString(R.string.channel_setting_gmail);
        Channel gmailChannel = createNewChannel(gmailKey, EMAIL, GM, GMAIL_HOME_URL,
                !isChannelExcludedByDefault(gmailKey, mainActivity), true);
        channels.add(gmailChannel);
        editor.putBoolean(gmailKey, gmailChannel.getActive());

        String mainRuKey = mainActivity.getString(R.string.channel_setting_mailru);
        Channel mailruChannel = createNewChannel(mainRuKey, EMAIL, MAIL_RU, MAIL_RU_HOME_URL,
                !isChannelExcludedByDefault(mainRuKey, mainActivity), true);
        channels.add(mailruChannel);
        editor.putBoolean(mainRuKey, mailruChannel.getActive());
        editor.apply();
        editor.commit();
    }

}
