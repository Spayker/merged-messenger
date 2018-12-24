package com.spandr.meme.core.activity.webview.logic.init.channel.mail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.main.logic.notification.NotificationDisplayer;
import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import im.delight.android.webview.AdvancedWebView;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;

public class MailruWebViewChannel extends WebViewChannel {

    private AppCompatActivity appCompatActivity;
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

    public MailruWebViewChannel(AppCompatActivity activity, AdvancedWebView mWebView,
                                  String url, String channelName) {
        if (url.isEmpty()) {
            return;
        }
        this.appCompatActivity = activity;
        this.mWebView = mWebView;
        this.url = url;
        this.channelName = channelName;
        initBackgroundMode();
    }

    @SuppressLint("AddJavascriptInterface")
    protected MailruWebViewChannel init() {
        initUserAgent();
        initListeners();
        initWebClients();
        initOrientationSensor();
        initCacheSettings();
        mWebView.addJavascriptInterface(new MailruJavaScriptInterface(channelName), "HTMLOUT");
        initStartURL();
        return this;
    }

    @SuppressLint("AddJavascriptInterface")
    private void initBackgroundMode() {
        initBackgroundWebSettings();
        mWebView.addJavascriptInterface(new MailruJavaScriptInterface(channelName), "HTMLOUT");
        initStartURL();
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

    @Override
    protected boolean isNotificationSettingEnabled(String channelName) {
        Context context = activity != null ? activity : appCompatActivity;
        notificationPrefix = context.getString(R.string.channel_setting_notifications_prefix);
        String channelKeyNotification = channelName + notificationPrefix;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(channelKeyNotification, false);
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
                final int notificationCounter = parseHtml(html);
                if(notificationCounter > 0){
                    if (activity == null) {
                        appCompatActivity.runOnUiThread(() -> NotificationDisplayer.getInstance().display(channelName, notificationCounter));
                    } else {
                        activity.runOnUiThread(() -> NotificationDisplayer.getInstance().display(channelName, notificationCounter));
                    }
                }
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
