package com.spand.meme.core.logic.starter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.spand.meme.R;
import com.spand.meme.core.data.memory.channel.Channel;
import com.spand.meme.core.data.memory.channel.ChannelManager;

import java.util.List;
import java.util.Locale;

import static com.spand.meme.core.data.memory.channel.ChannelManager.createNewChannel;
import static com.spand.meme.core.data.memory.channel.ChannelManager.isChannelExcludedByDefault;
import static com.spand.meme.core.data.memory.channel.ICON.DC;
import static com.spand.meme.core.data.memory.channel.ICON.FB;
import static com.spand.meme.core.data.memory.channel.ICON.GM;
import static com.spand.meme.core.data.memory.channel.ICON.ICQ;
import static com.spand.meme.core.data.memory.channel.ICON.IN;
import static com.spand.meme.core.data.memory.channel.ICON.LN;
import static com.spand.meme.core.data.memory.channel.ICON.MAIL_RU;
import static com.spand.meme.core.data.memory.channel.ICON.OK;
import static com.spand.meme.core.data.memory.channel.ICON.PN;
import static com.spand.meme.core.data.memory.channel.ICON.SK;
import static com.spand.meme.core.data.memory.channel.ICON.SL;
import static com.spand.meme.core.data.memory.channel.ICON.TL;
import static com.spand.meme.core.data.memory.channel.ICON.TUM;
import static com.spand.meme.core.data.memory.channel.ICON.TW;
import static com.spand.meme.core.data.memory.channel.ICON.VK;
import static com.spand.meme.core.data.memory.channel.ICON.YT;
import static com.spand.meme.core.data.memory.channel.TYPE.CHAT;
import static com.spand.meme.core.data.memory.channel.TYPE.EMAIL;
import static com.spand.meme.core.data.memory.channel.TYPE.SOCIAL;
import static com.spand.meme.core.logic.starter.SettingsConstants.RU;
import static com.spand.meme.core.ui.activity.ActivityConstants.DISCORD_HOME_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.FB_HOME_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.GMAIL_HOME_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.ICQ_HOME_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.INSTAGRAM_HOME_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.LINKEDIN_HOME_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.MAIL_RU_HOME_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.ODNOKLASNIKI_HOME_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.PINTEREST_HOME_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.SKYPE_HOME_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.SLACK_HOME_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.TELEGRAM_HOME_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.TUMBLR_HOME_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.TWITTER_HOME_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.VK_HOME_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.YOUTUBE_HOME_URL;

public class Loginner implements Starter {

    private static Loginner instance;
    private static AppCompatActivity mainActivity;
    private Loginner() { }

    public static Loginner createLoginner(AppCompatActivity mA) {
        if (instance == null) {
            instance = new Loginner();
            mainActivity = mA;
        }
        return instance;
    }

    @Override
    public void initApplication(SharedPreferences sharedPreferences) {
        ChannelManager.clearChannels();
        initChannelManager(mainActivity);
        String selectedLanguage = Locale.getDefault().getLanguage();
        switch (selectedLanguage) {
            case RU: {
                initSocialGroupChannels(sharedPreferences, mainActivity).
                        initChatGroupChannels(sharedPreferences, mainActivity).
                        initEmailGroupChannels(sharedPreferences, mainActivity);
                break;
            }
            default: {
                initSocialGroupChannels(sharedPreferences, mainActivity).
                        initChatGroupChannels(sharedPreferences, mainActivity).
                        initEmailGroupChannels(sharedPreferences, mainActivity);
            }
        }

    }

    private Loginner initSocialGroupChannels(SharedPreferences sharedPreferences,
                                                   AppCompatActivity mainActivity) {
        List<Channel> channels = ChannelManager.getInstance().getChannels();
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String fbKey = mainActivity.getString(R.string.channel_setting_fb);
        boolean isFbActive = sharedPreferences.getBoolean(fbKey, !isChannelExcludedByDefault(fbKey));
        Channel facebookChannel = createNewChannel(fbKey, SOCIAL, FB, FB_HOME_URL, isFbActive);
        editor.putBoolean(fbKey, isFbActive);
        channels.add(facebookChannel);

        String vkKey = mainActivity.getString(R.string.channel_setting_vk);
        boolean isVkActive = sharedPreferences.getBoolean(vkKey, !isChannelExcludedByDefault(vkKey));
        Channel vkontakteChannel = createNewChannel(vkKey,
                SOCIAL, VK, VK_HOME_URL, isVkActive);
        editor.putBoolean(vkKey, isVkActive);
        channels.add(vkontakteChannel);

        String twKey = mainActivity.getString(R.string.channel_setting_tw);
        boolean isTwActive = sharedPreferences.getBoolean(twKey, !isChannelExcludedByDefault(twKey));
        Channel twitterChannel = createNewChannel(twKey,
                SOCIAL, TW, TWITTER_HOME_URL, isTwActive);
        editor.putBoolean(twKey, isTwActive);
        channels.add(twitterChannel);

        String instKey = mainActivity.getString(R.string.channel_setting_inst);
        boolean isInstActive = sharedPreferences.getBoolean(instKey, !isChannelExcludedByDefault(instKey));
        Channel instagramChannel = createNewChannel(instKey, SOCIAL, IN, INSTAGRAM_HOME_URL, isInstActive);
        editor.putBoolean(instKey, isInstActive);
        channels.add(instagramChannel);

        String okKey = mainActivity.getString(R.string.channel_setting_ok);
        boolean isOkActive = sharedPreferences.getBoolean(okKey, !isChannelExcludedByDefault(okKey));
        Channel okChannel = createNewChannel(okKey, SOCIAL, OK, ODNOKLASNIKI_HOME_URL, isOkActive);
        editor.putBoolean(okKey, isOkActive);
        channels.add(okChannel);

        String ytKey = mainActivity.getString(R.string.channel_setting_yt);
        boolean isYtActive = sharedPreferences.getBoolean(ytKey, !isChannelExcludedByDefault(ytKey));
        Channel youtubeChannel = createNewChannel(ytKey, SOCIAL, YT, YOUTUBE_HOME_URL, isYtActive);
        editor.putBoolean(ytKey, isYtActive);
        channels.add(youtubeChannel);

        String tmbKey = mainActivity.getString(R.string.channel_setting_tmb);
        boolean isTmbActive = sharedPreferences.getBoolean(tmbKey, !isChannelExcludedByDefault(tmbKey));
        Channel tumblChannel = createNewChannel(tmbKey, SOCIAL, TUM, TUMBLR_HOME_URL, isTmbActive);
        editor.putBoolean(tmbKey, isTmbActive);
        channels.add(tumblChannel);

        String pnKey = mainActivity.getString(R.string.channel_setting_pn);
        boolean isPnActive = sharedPreferences.getBoolean(pnKey, !isChannelExcludedByDefault(pnKey));
        Channel pnChannel = createNewChannel(pnKey, SOCIAL, PN, PINTEREST_HOME_URL, isPnActive);
        editor.putBoolean(pnKey, isPnActive);
        channels.add(pnChannel);

        String lnKey = mainActivity.getString(R.string.channel_setting_ln);
        boolean isLnActive = sharedPreferences.getBoolean(lnKey, !isChannelExcludedByDefault(lnKey));
        Channel linkedinChannel = createNewChannel(lnKey,
                SOCIAL, LN, LINKEDIN_HOME_URL, isLnActive);
        channels.add(linkedinChannel);
        editor.putBoolean(lnKey, isLnActive);
        editor.apply();
        editor.commit();
        return instance;
    }

    private Loginner initChatGroupChannels(SharedPreferences sharedPreferences,
                                                 AppCompatActivity mainActivity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<Channel> channels = ChannelManager.getInstance().getChannels();

        String tlKey = mainActivity.getString(R.string.channel_setting_tl);
        boolean isTlActive = sharedPreferences.getBoolean(tlKey, !isChannelExcludedByDefault(tlKey));
        Channel telegramChannel = createNewChannel(tlKey, CHAT, TL, TELEGRAM_HOME_URL, isTlActive);
        editor.putBoolean(tlKey, isTlActive);
        channels.add(telegramChannel);

        String skpKey = mainActivity.getString(R.string.channel_setting_skp);
        boolean isSkpActive = sharedPreferences.getBoolean(skpKey, !isChannelExcludedByDefault(skpKey));
        Channel skypeChannel = createNewChannel(skpKey, CHAT, SK, SKYPE_HOME_URL, isSkpActive);
        editor.putBoolean(skpKey, isSkpActive);
        channels.add(skypeChannel);

        String icqKey = mainActivity.getString(R.string.channel_setting_icq);
        boolean isIcqActive = sharedPreferences.getBoolean(icqKey, !isChannelExcludedByDefault(icqKey));
        Channel icqChannel = createNewChannel(icqKey, CHAT, ICQ, ICQ_HOME_URL, isIcqActive);
        editor.putBoolean(icqKey, isIcqActive);
        channels.add(icqChannel);

        String dcKey = mainActivity.getString(R.string.channel_setting_dc);
        boolean isDcActive = sharedPreferences.getBoolean(dcKey, !isChannelExcludedByDefault(dcKey));
        Channel discordChannel = createNewChannel(dcKey, CHAT, DC, DISCORD_HOME_URL, isDcActive);
        editor.putBoolean(dcKey, isDcActive);
        channels.add(discordChannel);

        String slKey = mainActivity.getString(R.string.channel_setting_slack);
        boolean isSlActive = sharedPreferences.getBoolean(slKey, !isChannelExcludedByDefault(slKey));
        Channel slackChannel = createNewChannel(slKey, CHAT, SL, SLACK_HOME_URL, isSlActive);
        editor.putBoolean(slKey, isSlActive);
        channels.add(slackChannel);

        editor.apply();
        editor.commit();
        return instance;
    }

    private Loginner initEmailGroupChannels(SharedPreferences sharedPreferences,
                                                  AppCompatActivity mainActivity) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        List<Channel> channels = ChannelManager.getInstance().getChannels();

        String gmailKey = mainActivity.getString(R.string.channel_setting_gmail);
        boolean isGmailActive = sharedPreferences.getBoolean(gmailKey, !isChannelExcludedByDefault(gmailKey));
        Channel gmailChannel = createNewChannel(gmailKey, EMAIL, GM, GMAIL_HOME_URL, isGmailActive);
        editor.putBoolean(gmailKey, isGmailActive);
        channels.add(gmailChannel);

        String mailRuKey = mainActivity.getString(R.string.channel_setting_mailru);
        boolean isMailRuActive = sharedPreferences.getBoolean(mailRuKey, !isChannelExcludedByDefault(mailRuKey));
        Channel mailruChannel = createNewChannel(mailRuKey, EMAIL, MAIL_RU, MAIL_RU_HOME_URL,  isMailRuActive);
        editor.putBoolean(mailRuKey, isMailRuActive);
        channels.add(mailruChannel);
        editor.apply();
        editor.commit();
        return instance;
    }

}
