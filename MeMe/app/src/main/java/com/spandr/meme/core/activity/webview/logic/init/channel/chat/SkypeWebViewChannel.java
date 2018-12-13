package com.spandr.meme.core.activity.webview.logic.init.channel.chat;

import android.annotation.SuppressLint;
import android.webkit.JavascriptInterface;

import com.spandr.meme.core.activity.main.logic.notification.NotificationDisplayer;
import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SkypeWebViewChannel extends WebViewChannel {

    private final static String SKYPE_USER_AGENT_STRING = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.91 Safari/537.36";

    @SuppressWarnings("unused")
    private SkypeWebViewChannel(){}

    public SkypeWebViewChannel(WebViewActivity activity,
                                  String url, String channelName) {
        if(url.isEmpty()){
            return;
        }
        this.activity = activity;
        this.mWebView = activity.getmWebView();
        this.url = url;
        this.channelName = channelName;
        init();
    }

    @SuppressLint("AddJavascriptInterface")
    protected SkypeWebViewChannel init() {
        initUserAgent();
        initStartURL();
        initWebChromeClient();
        initListeners();
        initWebClients();
        mWebView.addJavascriptInterface(new SkJavaScriptInterface(channelName), "HTMLOUT");
        return this;
    }

    @Override
    protected void initUserAgent() {
        mWebView.getSettings().setUserAgentString(SKYPE_USER_AGENT_STRING);
    }

    public String getUrl() {
        return url;
    }

    class SkJavaScriptInterface {

        private String channelName;

        private final String MESSAGE_NOTIFICATION_REGEX = "with ([0-9]+) unread message";
        private final String MESSAGE_NOTIFICATION_REGEX_2 = ", ([0-9]+) unread message";

        private SkJavaScriptInterface(String channelName){
            this.channelName = channelName;
        }

        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            mWebView.post(() -> {
                int notificationCounter = parseHtml(html);
                NotificationDisplayer.getInstance().display(channelName, notificationCounter);
            });
        }

        private int parseHtml(String html){
            int notificationCounter = 0;
            Matcher firstMatcher = Pattern.compile(MESSAGE_NOTIFICATION_REGEX).matcher(html);
            while(firstMatcher.find()) {
                notificationCounter += Integer.valueOf(firstMatcher.group(1));
            }
            if (notificationCounter == 0){
                Matcher secondMatcher = Pattern.compile(MESSAGE_NOTIFICATION_REGEX_2).matcher(html);
                while(secondMatcher.find()) {
                    notificationCounter += Integer.valueOf(secondMatcher.group(1));
                }
            }
            return notificationCounter;
        }
    }
}
