package com.spandr.meme.core.activity.webview.logic.init.channel.mail;

import android.annotation.SuppressLint;
import android.webkit.JavascriptInterface;

import com.spandr.meme.core.activity.main.logic.notification.NotificationDisplayer;
import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailruWebViewChannel extends WebViewChannel {

    @SuppressWarnings("unused")
    private MailruWebViewChannel(){}

    public MailruWebViewChannel(WebViewActivity activity,
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
    protected MailruWebViewChannel init() {
        initUserAgent();
        initStartURL();
        initWebChromeClient();
        initListeners();
        initWebClients();
        mWebView.addJavascriptInterface(new MailruJavaScriptInterface(channelName), "HTMLOUT");
        return this;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initListeners(){
        mWebView.setOnTouchListener((v, event) -> {
            activity.getSwipeRefreshLayout().setEnabled(false);
            activity.getBackButton().setAlpha(.45f);
            mWebView.performClick();
            return false;
        });
    }

    public String getUrl() {
        return url;
    }

    private class MailruJavaScriptInterface {

        private String channelName;

        private MailruJavaScriptInterface(String channelName){
            this.channelName = channelName;
        }

        private final String MESSAGE_NOTIFICATION_REGEX = "([0-9]+)</div></div></div><div class=\"toolbar__title__t toolbar__title__t1\">";
        private final String MESSAGE_NOTIFICATION_REGEX_2 = "<span class=\"social__bubble\">([0-9]+)</span>";
        private final Pattern patternOfFirstRegex = Pattern.compile(MESSAGE_NOTIFICATION_REGEX);
        private final Pattern patternOfSecondRegex = Pattern.compile(MESSAGE_NOTIFICATION_REGEX_2);

        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            if(isNotificationSettingEnabled(channelName)){
                mWebView.post(() -> {
                    int notificationCounter = parseHtml(html);
                    NotificationDisplayer.getInstance().display(channelName, notificationCounter);
                });
            }
        }
        private int parseHtml(String html){
            int notificationCounter = 0;
            Matcher firstMatcher = patternOfFirstRegex.matcher(html);
            while(firstMatcher.find()) {
                notificationCounter += Integer.valueOf(firstMatcher.group(1));
            }
            if (notificationCounter == 0){
                Matcher secondMatcher = patternOfSecondRegex.matcher(html);
                while(secondMatcher.find()) {
                    notificationCounter += Integer.valueOf(secondMatcher.group(1));
                }
            }
            return notificationCounter;
        }

    }

}
