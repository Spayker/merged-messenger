package com.spandr.meme.core.activity.main.logic.starter;

import android.annotation.SuppressLint;
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

public class Loginner implements Starter {

    @SuppressLint("StaticFieldLeak")
    private static Loginner instance;
    @SuppressLint("StaticFieldLeak")
    private static AppCompatActivity mainActivity;

    private static String notificationPrefix;

    private Loginner() {
    }

    public static Loginner createLoginner(AppCompatActivity mA) {
        if (instance == null) {
            instance = new Loginner();
            mainActivity = mA;
            notificationPrefix = mainActivity.getString(R.string.channel_setting_notifications_prefix);
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
    }

    private Loginner initSocialGroupChannels(SharedPreferences sharedPreferences,
                                             AppCompatActivity mainActivity) {
        List<Channel> channels = DataChannelManager.getInstance().getChannels();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String fbKey = mainActivity.getString(R.string.channel_setting_fb);
        String fbKeyNotification = fbKey + notificationPrefix;
        boolean isFbActive = sharedPreferences.getBoolean(fbKey, !isChannelExcludedByDefault(fbKey, mainActivity));
        boolean isFbNotificationActive = sharedPreferences.getBoolean(fbKeyNotification, true);
        Channel facebookChannel = createNewChannel(fbKey, SOCIAL, FB, FB_HOME_URL, isFbActive, isFbNotificationActive);
        editor.putBoolean(fbKey, isFbActive);
        editor.putBoolean(fbKeyNotification, isFbNotificationActive);
        channels.add(facebookChannel);

        String vkKey = mainActivity.getString(R.string.channel_setting_vk);
        String vkKeyNotification = vkKey + notificationPrefix;
        boolean isVkActive = sharedPreferences.getBoolean(vkKey, !isChannelExcludedByDefault(vkKey, mainActivity));
        boolean isVkNotificationActive = sharedPreferences.getBoolean(vkKeyNotification, true);
        Channel vkontakteChannel = createNewChannel(vkKey,
                SOCIAL, VK, VK_HOME_URL, isVkActive, isVkNotificationActive);
        editor.putBoolean(vkKey, isVkActive);
        editor.putBoolean(vkKeyNotification, isVkNotificationActive);
        channels.add(vkontakteChannel);

        String twKey = mainActivity.getString(R.string.channel_setting_tw);
        String twKeyNotification = twKey + notificationPrefix;
        boolean isTwActive = sharedPreferences.getBoolean(twKey, !isChannelExcludedByDefault(twKey, mainActivity));
        boolean isTwNotificationActive = sharedPreferences.getBoolean(twKeyNotification, true);
        Channel twitterChannel = createNewChannel(twKey,
                SOCIAL, TW, TWITTER_HOME_URL, isTwActive, isTwNotificationActive);
        editor.putBoolean(twKey, isTwActive);
        editor.putBoolean(twKeyNotification, isTwNotificationActive);
        channels.add(twitterChannel);

        String instKey = mainActivity.getString(R.string.channel_setting_inst);
        String instKeyNotification = instKey + notificationPrefix;
        boolean isInstActive = sharedPreferences.getBoolean(instKey, !isChannelExcludedByDefault(instKey, mainActivity));
        boolean isInstNotificationActive = sharedPreferences.getBoolean(instKeyNotification, true);
        Channel instagramChannel = createNewChannel(instKey, SOCIAL, IN, INSTAGRAM_HOME_URL, isInstActive, isInstNotificationActive);
        editor.putBoolean(instKey, isInstActive);
        editor.putBoolean(instKeyNotification, isInstNotificationActive);
        channels.add(instagramChannel);

        String okKey = mainActivity.getString(R.string.channel_setting_ok);
        String okKeyNotification = okKey + notificationPrefix;
        boolean isOkActive = sharedPreferences.getBoolean(okKey, !isChannelExcludedByDefault(okKey, mainActivity));
        boolean isOkNotificationActive = sharedPreferences.getBoolean(okKeyNotification, true);
        Channel okChannel = createNewChannel(okKey, SOCIAL, OK, ODNOKLASNIKI_HOME_URL, isOkActive, isOkNotificationActive);
        editor.putBoolean(okKey, isOkActive);
        editor.putBoolean(okKeyNotification, isOkNotificationActive);
        channels.add(okChannel);

        String tmbKey = mainActivity.getString(R.string.channel_setting_tmb);
        String tmbKeyNotification = tmbKey + notificationPrefix;
        boolean isTmbActive = sharedPreferences.getBoolean(tmbKey, !isChannelExcludedByDefault(tmbKey, mainActivity));
        boolean isTmbNotificationActive = sharedPreferences.getBoolean(tmbKeyNotification, true);
        Channel tumblChannel = createNewChannel(tmbKey, SOCIAL, TUM, TUMBLR_HOME_URL, isTmbActive, isTmbNotificationActive);
        editor.putBoolean(tmbKey, isTmbActive);
        editor.putBoolean(tmbKeyNotification, isTmbNotificationActive);
        channels.add(tumblChannel);

        String pnKey = mainActivity.getString(R.string.channel_setting_pn);
        String pnKeyNotification = pnKey + notificationPrefix;
        boolean isPnActive = sharedPreferences.getBoolean(pnKey, !isChannelExcludedByDefault(pnKey, mainActivity));
        boolean isPnNotificationActive = sharedPreferences.getBoolean(pnKeyNotification, true);
        Channel pnChannel = createNewChannel(pnKey, SOCIAL, PN, PINTEREST_HOME_URL, isPnActive, isPnNotificationActive);
        editor.putBoolean(pnKey, isPnActive);
        editor.putBoolean(pnKeyNotification, isPnNotificationActive);
        channels.add(pnChannel);

        String lnKey = mainActivity.getString(R.string.channel_setting_ln);
        String lnKeyNotification = lnKey + notificationPrefix;
        boolean isLnActive = sharedPreferences.getBoolean(lnKey, !isChannelExcludedByDefault(lnKey, mainActivity));
        boolean isLnNotificationActive = sharedPreferences.getBoolean(lnKeyNotification, true);
        Channel linkedinChannel = createNewChannel(lnKey,
                SOCIAL, LN, LINKEDIN_HOME_URL, isLnActive, isLnNotificationActive);
        channels.add(linkedinChannel);
        editor.putBoolean(lnKey, isLnActive);
        editor.putBoolean(lnKeyNotification, isLnNotificationActive);
        editor.apply();
        editor.commit();
        return instance;
    }

    private Loginner initChatGroupChannels(SharedPreferences sharedPreferences,
                                           AppCompatActivity mainActivity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<Channel> channels = DataChannelManager.getInstance().getChannels();

        String tlKey = mainActivity.getString(R.string.channel_setting_tl);
        String tlKeyNotification = tlKey + notificationPrefix;
        boolean isTlActive = sharedPreferences.getBoolean(tlKey, !isChannelExcludedByDefault(tlKey, mainActivity));
        boolean isTlNotificationActive = sharedPreferences.getBoolean(tlKeyNotification, true);
        Channel telegramChannel = createNewChannel(tlKey, CHAT, TL, TELEGRAM_HOME_URL, isTlActive, isTlNotificationActive);
        editor.putBoolean(tlKey, isTlActive);
        editor.putBoolean(tlKeyNotification, isTlNotificationActive);
        channels.add(telegramChannel);

        String skpKey = mainActivity.getString(R.string.channel_setting_skp);
        String skpKeyNotification = skpKey + notificationPrefix;
        boolean isSkpActive = sharedPreferences.getBoolean(skpKey, !isChannelExcludedByDefault(skpKey, mainActivity));
        boolean isSkpNotificationActive = sharedPreferences.getBoolean(skpKeyNotification, true);
        Channel skypeChannel = createNewChannel(skpKey, CHAT, SK, SKYPE_HOME_URL, isSkpActive, isSkpNotificationActive);
        editor.putBoolean(skpKey, isSkpActive);
        editor.putBoolean(skpKeyNotification, isSkpNotificationActive);
        channels.add(skypeChannel);

        String icqKey = mainActivity.getString(R.string.channel_setting_icq);
        String icqKeyNotification = icqKey + notificationPrefix;
        boolean isIcqActive = sharedPreferences.getBoolean(icqKey, !isChannelExcludedByDefault(icqKey, mainActivity));
        boolean isIcqNotificationActive = sharedPreferences.getBoolean(icqKeyNotification, true);
        Channel icqChannel = createNewChannel(icqKey, CHAT, ICQ, ICQ_HOME_URL, isIcqActive, isIcqNotificationActive);
        editor.putBoolean(icqKey, isIcqActive);
        editor.putBoolean(icqKeyNotification, isIcqNotificationActive);
        channels.add(icqChannel);

        String gaduKey = mainActivity.getString(R.string.channel_setting_gadu);
        String gaduKeyNotification = gaduKey + notificationPrefix;
        boolean isGaduActive = sharedPreferences.getBoolean(gaduKey, !isChannelExcludedByDefault(gaduKey, mainActivity));
        boolean isGaduNotificationActive = sharedPreferences.getBoolean(gaduKeyNotification, true);
        Channel gaduChannel = createNewChannel(gaduKey, CHAT, GADU, GADU_HOME_URL, isGaduActive, isGaduNotificationActive);
        editor.putBoolean(gaduKey, isGaduActive);
        editor.putBoolean(gaduKeyNotification, isGaduNotificationActive);
        channels.add(gaduChannel);

        String dcKey = mainActivity.getString(R.string.channel_setting_dc);
        String dcKeyNotification = dcKey + notificationPrefix;
        boolean isDcActive = sharedPreferences.getBoolean(dcKey, !isChannelExcludedByDefault(dcKey, mainActivity));
        boolean isDcNotificationActive = sharedPreferences.getBoolean(dcKeyNotification, true);
        Channel discordChannel = createNewChannel(dcKey, CHAT, DC, DISCORD_HOME_URL, isDcActive, isDcNotificationActive);
        editor.putBoolean(dcKey, isDcActive);
        editor.putBoolean(dcKeyNotification, isDcNotificationActive);
        channels.add(discordChannel);

        String slKey = mainActivity.getString(R.string.channel_setting_slack);
        String slKeyNotification = slKey + notificationPrefix;
        boolean isSlActive = sharedPreferences.getBoolean(slKey, !isChannelExcludedByDefault(slKey, mainActivity));
        boolean isSlNotificationActive = sharedPreferences.getBoolean(slKeyNotification, true);
        Channel slackChannel = createNewChannel(slKey, CHAT, SL, SLACK_HOME_URL, isSlActive, isSlNotificationActive);
        editor.putBoolean(slKey, isSlActive);
        editor.putBoolean(slKeyNotification, isSlNotificationActive);
        channels.add(slackChannel);

        editor.apply();
        editor.commit();
        return instance;
    }

    private Loginner initVideoGroupChannels(SharedPreferences sharedPreferences,
                                            AppCompatActivity mainActivity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<Channel> channels = DataChannelManager.getInstance().getChannels();

        String ytKey = mainActivity.getString(R.string.channel_setting_yt);
        String ytKeyNotification = ytKey + notificationPrefix;
        boolean isYtActive = sharedPreferences.getBoolean(ytKey, !isChannelExcludedByDefault(ytKey, mainActivity));
        boolean isYtNotificationActive = sharedPreferences.getBoolean(ytKeyNotification, true);
        Channel youtubeChannel = createNewChannel(ytKey, VIDEO_SERVICE, YT, YOUTUBE_HOME_URL, isYtActive, isYtNotificationActive);
        editor.putBoolean(ytKey, isYtActive);
        editor.putBoolean(ytKeyNotification, isYtNotificationActive);
        channels.add(youtubeChannel);

        String twitchKey = mainActivity.getString(R.string.channel_setting_twitch);
        String twitchKeyNotification = twitchKey + notificationPrefix;
        boolean isTwitchActive = sharedPreferences.getBoolean(twitchKey, !isChannelExcludedByDefault(twitchKey, mainActivity));
        boolean isTwitchNotificationActive = sharedPreferences.getBoolean(twitchKeyNotification, true);
        Channel twitchChannel = createNewChannel(twitchKey, VIDEO_SERVICE, TWITCH, TWITCH_HOME_URL, isTwitchActive, isTwitchNotificationActive);
        editor.putBoolean(twitchKey, isTwitchActive);
        editor.putBoolean(twitchKeyNotification, isTwitchNotificationActive);
        channels.add(twitchChannel);

        editor.apply();
        editor.commit();
        return instance;
    }

    private Loginner initInfoGroupChannels(SharedPreferences sharedPreferences,
                                           AppCompatActivity mainActivity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<Channel> channels = DataChannelManager.getInstance().getChannels();

        String habrKey = mainActivity.getString(R.string.channel_setting_habr);
        String habrKeyNotification = habrKey + notificationPrefix;
        boolean isHabrActive = sharedPreferences.getBoolean(habrKey, !isChannelExcludedByDefault(habrKey, mainActivity));
        boolean isHabrNotificationActive = sharedPreferences.getBoolean(habrKeyNotification, true);
        Channel habrChannel = createNewChannel(habrKey, INFO_SERVICE, HABR, HABR_HOME_URL, isHabrActive, isHabrNotificationActive);
        editor.putBoolean(habrKey, isHabrActive);
        editor.putBoolean(habrKeyNotification, isHabrNotificationActive);
        channels.add(habrChannel);

        String redditKey = mainActivity.getString(R.string.channel_setting_reddit);
        String redditKeyNotification = redditKey + notificationPrefix;
        boolean isRedditActive = sharedPreferences.getBoolean(redditKey, !isChannelExcludedByDefault(redditKey, mainActivity));
        boolean isRedditNotificationActive = sharedPreferences.getBoolean(redditKeyNotification, true);
        Channel redditChannel = createNewChannel(redditKey, INFO_SERVICE, REDDIT, REDDIT_HOME_URL, isRedditActive, isRedditNotificationActive);
        editor.putBoolean(redditKey, isRedditActive);
        editor.putBoolean(redditKeyNotification, isRedditNotificationActive);
        channels.add(redditChannel);

        String quoraKey = mainActivity.getString(R.string.channel_setting_quora);
        String quoraKeyNotification = quoraKey + notificationPrefix;
        boolean isQuoraActive = sharedPreferences.getBoolean(quoraKey, !isChannelExcludedByDefault(quoraKey, mainActivity));
        boolean isQuoraNotificationActive = sharedPreferences.getBoolean(quoraKeyNotification, true);
        Channel quoraChannel = createNewChannel(quoraKey, INFO_SERVICE, QUORA, QUORA_HOME_URL, isQuoraActive, isQuoraNotificationActive);
        editor.putBoolean(quoraKey, isQuoraActive);
        editor.putBoolean(quoraKeyNotification, isRedditNotificationActive);
        channels.add(quoraChannel);

        String stackKey = mainActivity.getString(R.string.channel_setting_stack);
        String stackKeyNotification = stackKey + notificationPrefix;
        boolean isStackActive = sharedPreferences.getBoolean(stackKey, !isChannelExcludedByDefault(stackKey, mainActivity));
        boolean isStackNotificationActive = sharedPreferences.getBoolean(stackKeyNotification, true);
        Channel stackChannel = createNewChannel(stackKey, INFO_SERVICE, STACK, STACKOVERFLOW_HOME_URL, isStackActive, isStackNotificationActive);
        editor.putBoolean(stackKey, isStackActive);
        editor.putBoolean(stackKeyNotification, isStackNotificationActive);
        channels.add(stackChannel);

        editor.apply();
        editor.commit();
        return instance;

    }

    private void initEmailGroupChannels(SharedPreferences sharedPreferences,
                                        AppCompatActivity mainActivity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<Channel> channels = DataChannelManager.getInstance().getChannels();

        String gmailKey = mainActivity.getString(R.string.channel_setting_gmail);
        String gmailKeyNotification = gmailKey + notificationPrefix;
        boolean isGmailActive = sharedPreferences.getBoolean(gmailKey, !isChannelExcludedByDefault(gmailKey, mainActivity));
        boolean isGmailNotificationActive = sharedPreferences.getBoolean(gmailKeyNotification, true);
        Channel gmailChannel = createNewChannel(gmailKey, EMAIL, GM, GMAIL_HOME_URL, isGmailActive, isGmailNotificationActive);
        editor.putBoolean(gmailKey, isGmailActive);
        editor.putBoolean(gmailKeyNotification, isGmailNotificationActive);
        channels.add(gmailChannel);

        String mailRuKey = mainActivity.getString(R.string.channel_setting_mailru);
        String mailKeyNotification = mailRuKey + notificationPrefix;
        boolean isMailRuActive = sharedPreferences.getBoolean(mailRuKey, !isChannelExcludedByDefault(mailRuKey, mainActivity));
        boolean isMailRuNotificationActive = sharedPreferences.getBoolean(mailKeyNotification, true);
        Channel mailruChannel = createNewChannel(mailRuKey, EMAIL, MAIL_RU, MAIL_RU_HOME_URL, isMailRuActive, isMailRuNotificationActive);
        editor.putBoolean(mailRuKey, isMailRuActive);
        editor.putBoolean(mailKeyNotification, isMailRuNotificationActive);
        channels.add(mailruChannel);
        editor.apply();
        editor.commit();
    }

}
