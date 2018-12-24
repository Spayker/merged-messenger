package com.spandr.meme.core.activity.webview.logic.manager;


import android.support.v7.app.AppCompatActivity;

import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.chat.DiscordWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.chat.GGWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.chat.IcqWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.chat.SkypeWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.chat.SlackWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.chat.TelegramWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.info.HabrWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.info.QuoraWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.info.RedditWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.info.StackOverflowWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.mail.GmailWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.mail.MailruWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.social.FacebookWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.social.InstagramWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.social.LinkedInWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.social.OkWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.social.PinterestWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.social.TumblrWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.social.TwitterWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.social.VkontakteWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.video.TwitchWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.video.YoutubeWebViewChannel;
import com.spandr.meme.core.common.data.memory.channel.Channel;
import com.spandr.meme.core.common.data.memory.channel.DataChannelManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import im.delight.android.webview.AdvancedWebView;

import static com.spandr.meme.core.activity.main.logic.LogicContants.TASK_BACKGROUND_PREFIX;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.DISCORD_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.EMPTY_STRING;
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

public class WebViewManager {

    private static WebViewManager webViewManager;
    private Map<String, AdvancedWebView> webViewChannels;

    @SuppressWarnings("unused")
    private WebViewManager() { }

    private WebViewManager(Map<String, AdvancedWebView> webViewChannels) {
        this.webViewChannels = webViewChannels;
    }

    public Map<String, AdvancedWebView> getWebViewChannels() {
        return webViewChannels;
    }

    public Iterable<AdvancedWebView> getWebViewChannelsIterator() {
        return new ArrayList<>(webViewChannels.values());
    }

    public static WebViewManager getWebViewChannelManager(){
        if (webViewManager == null) {
            webViewManager = new WebViewManager(new HashMap<>());
        }
        return webViewManager;
    }

    public static void applyChannelRelatedConfiguration(WebViewActivity activity, String channelName) {
        if (channelName != null) {
            Channel channel = DataChannelManager.getChannelByName(channelName);
            if (channel != null) {
                String homeURL = channel.getHomeUrl();
                switch (homeURL) {
                    case FB_HOME_URL: {
                        new FacebookWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case VK_HOME_URL: {
                        new VkontakteWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case TELEGRAM_HOME_URL: {
                        new TelegramWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case ICQ_HOME_URL: {
                        new IcqWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case GADU_HOME_URL: {
                        new GGWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case SKYPE_HOME_URL: {
                        new SkypeWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case LINKEDIN_HOME_URL: {
                        new LinkedInWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case INSTAGRAM_HOME_URL:{
                        new InstagramWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case DISCORD_HOME_URL:{
                        new DiscordWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case REDDIT_HOME_URL:{
                        new RedditWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case QUORA_HOME_URL:{
                        new QuoraWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case STACKOVERFLOW_HOME_URL:{
                        new StackOverflowWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case HABR_HOME_URL:{
                        new HabrWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case ODNOKLASNIKI_HOME_URL:{
                        new OkWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case PINTEREST_HOME_URL:{
                        new PinterestWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case TUMBLR_HOME_URL:{
                        new TumblrWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case TWITTER_HOME_URL:{
                        new TwitterWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case SLACK_HOME_URL:{
                        new SlackWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case GMAIL_HOME_URL:{
                        new GmailWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case MAIL_RU_HOME_URL:{
                        new MailruWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case TWITCH_HOME_URL:{
                        new TwitchWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case YOUTUBE_HOME_URL:{
                        new YoutubeWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                }
            }
        }
    }

    public static void applyBackgroundChannelRelatedConfiguration(AppCompatActivity activity,
                                                                  AdvancedWebView mWebView,
                                                                  String channelName) {
        if (channelName != null) {
            Channel channel = DataChannelManager.getChannelByName(channelName.replace(TASK_BACKGROUND_PREFIX, EMPTY_STRING));
            if (channel != null) {
                String homeURL = channel.getHomeUrl();
                switch (homeURL) {
                    case FB_HOME_URL: {
                        new FacebookWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case VK_HOME_URL: {
                        new VkontakteWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case TELEGRAM_HOME_URL: {
                        new TelegramWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case ICQ_HOME_URL: {
                        new IcqWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case GADU_HOME_URL: {
                        new GGWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case SKYPE_HOME_URL: {
                        new SkypeWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case LINKEDIN_HOME_URL: {
                        new LinkedInWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case INSTAGRAM_HOME_URL:{
                        new InstagramWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case DISCORD_HOME_URL:{
                        new DiscordWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case REDDIT_HOME_URL:{
                        new RedditWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case QUORA_HOME_URL:{
                        new QuoraWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case STACKOVERFLOW_HOME_URL:{
                        new StackOverflowWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case HABR_HOME_URL:{
                        new HabrWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case ODNOKLASNIKI_HOME_URL:{
                        new OkWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case PINTEREST_HOME_URL:{
                        new PinterestWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case TUMBLR_HOME_URL:{
                        new TumblrWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case TWITTER_HOME_URL:{
                        new TwitterWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case SLACK_HOME_URL:{
                        new SlackWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case GMAIL_HOME_URL:{
                        new GmailWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case MAIL_RU_HOME_URL:{
                        new MailruWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case TWITCH_HOME_URL:{
                        new TwitchWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                    case YOUTUBE_HOME_URL:{
                        new YoutubeWebViewChannel(activity, mWebView, homeURL, channelName);
                        break;
                    }
                }
            }
        }
    }

}
