package com.spandr.meme.core.activity.webview.logic.manager;


import android.content.Context;

import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;
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

public class WebViewChannelManager {

    private static WebViewChannelManager webViewChannelManager;

    @SuppressWarnings("unused")
    private WebViewChannelManager() { }

    public static WebViewChannelManager getWebViewChannelManager(){
        if (webViewChannelManager == null) {
            webViewChannelManager = new WebViewChannelManager();
        }
        return webViewChannelManager;
    }

    public void applyChannelRelatedConfiguration(WebViewActivity activity, String channelName) {
        if (channelName != null) {
            Channel channel = DataChannelManager.getChannelByName(channelName);
            if (channel != null) {
                String homeURL = channel.getHomeUrl();
                WebViewChannel webViewChannel = null;
                switch (homeURL) {
                    case FB_HOME_URL: {
                        webViewChannel= new FacebookWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case VK_HOME_URL: {
                        webViewChannel = new VkontakteWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case TELEGRAM_HOME_URL: {
                        webViewChannel = new TelegramWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case ICQ_HOME_URL: {
                        webViewChannel = new IcqWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case GADU_HOME_URL: {
                        webViewChannel = new GGWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case SKYPE_HOME_URL: {
                        webViewChannel = new SkypeWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case LINKEDIN_HOME_URL: {
                        webViewChannel = new LinkedInWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case INSTAGRAM_HOME_URL:{
                        webViewChannel = new InstagramWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case DISCORD_HOME_URL:{
                        webViewChannel = new DiscordWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case REDDIT_HOME_URL:{
                        webViewChannel = new RedditWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case QUORA_HOME_URL:{
                        webViewChannel = new QuoraWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case STACKOVERFLOW_HOME_URL:{
                        webViewChannel = new StackOverflowWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case HABR_HOME_URL:{
                        webViewChannel = new HabrWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case ODNOKLASNIKI_HOME_URL:{
                        webViewChannel = new OkWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case PINTEREST_HOME_URL:{
                        webViewChannel = new PinterestWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case TUMBLR_HOME_URL:{
                        webViewChannel = new TumblrWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case TWITTER_HOME_URL:{
                        webViewChannel = new TwitterWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case SLACK_HOME_URL:{
                        webViewChannel = new SlackWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case GMAIL_HOME_URL:{
                        webViewChannel = new GmailWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case MAIL_RU_HOME_URL:{
                        webViewChannel = new MailruWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case TWITCH_HOME_URL:{
                        webViewChannel = new TwitchWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case YOUTUBE_HOME_URL:{
                        webViewChannel = new YoutubeWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                }
                channel.setWebViewChannel(webViewChannel);
            }
        }
    }

    public void applyBackgroundChannelRelatedConfiguration(Context activity,
                                                                  String channelName) {
        if (channelName != null) {
            Channel channel = DataChannelManager.getChannelByName(channelName);
            if (channel != null) {
                String homeURL = channel.getHomeUrl();
                WebViewChannel webViewChannel = null;
                switch (homeURL) {
                    case FB_HOME_URL: {
                        webViewChannel = new FacebookWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case VK_HOME_URL: {
                        webViewChannel = new VkontakteWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case TELEGRAM_HOME_URL: {
                        webViewChannel = new TelegramWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case ICQ_HOME_URL: {
                        webViewChannel = new IcqWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case GADU_HOME_URL: {
                        webViewChannel = new GGWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case SKYPE_HOME_URL: {
                        webViewChannel = new SkypeWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case LINKEDIN_HOME_URL: {
                        webViewChannel = new LinkedInWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case INSTAGRAM_HOME_URL:{
                        webViewChannel = new InstagramWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case DISCORD_HOME_URL:{
                        webViewChannel = new DiscordWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case REDDIT_HOME_URL:{
                        webViewChannel = new RedditWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case QUORA_HOME_URL:{
                        webViewChannel = new QuoraWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case STACKOVERFLOW_HOME_URL:{
                        webViewChannel = new StackOverflowWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case HABR_HOME_URL:{
                        webViewChannel = new HabrWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case ODNOKLASNIKI_HOME_URL:{
                        webViewChannel = new OkWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case PINTEREST_HOME_URL:{
                        webViewChannel = new PinterestWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case TUMBLR_HOME_URL:{
                        webViewChannel = new TumblrWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case TWITTER_HOME_URL:{
                        webViewChannel = new TwitterWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case SLACK_HOME_URL:{
                        webViewChannel = new SlackWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case GMAIL_HOME_URL:{
                        webViewChannel = new GmailWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case MAIL_RU_HOME_URL:{
                        webViewChannel = new MailruWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case TWITCH_HOME_URL:{
                        webViewChannel = new TwitchWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                    case YOUTUBE_HOME_URL:{
                        webViewChannel = new YoutubeWebViewChannel(activity, homeURL, channelName);
                        break;
                    }
                }
                channel.setWebViewChannel(webViewChannel);
            }
        }
    }

}
