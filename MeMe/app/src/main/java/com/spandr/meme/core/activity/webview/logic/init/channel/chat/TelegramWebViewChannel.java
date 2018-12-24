package com.spandr.meme.core.activity.webview.logic.init.channel.chat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.main.logic.notification.NotificationDisplayer;
import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import im.delight.android.webview.AdvancedWebView;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.TELEGRAM_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.TELEGRAM_HOME_URL_2;

public class TelegramWebViewChannel extends WebViewChannel {

    private AppCompatActivity appCompatActivity;
    private String TELEGRAM_USER_AGENT_STRING = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.91 Safari/537.36";

    @SuppressWarnings("unused")
    private TelegramWebViewChannel(){}

    public TelegramWebViewChannel(WebViewActivity activity,
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

    public TelegramWebViewChannel(AppCompatActivity activity, AdvancedWebView mWebView,
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
    protected TelegramWebViewChannel init() {
        initUserAgent();
        initListeners();
        initWebClients();
        initOrientationSensor();
        initCacheSettings();
        mWebView.addJavascriptInterface(new TlJavaScriptInterface(channelName), "HTMLOUT");
        initStartURL();
        return this;
    }

    @SuppressLint("AddJavascriptInterface")
    private void initBackgroundMode() {
        initBackgroundWebSettings();
        mWebView.addJavascriptInterface(new TlJavaScriptInterface(channelName), "HTMLOUT");
        initStartURL();
    }

    @Override
    protected boolean isNotificationSettingEnabled(String channelName) {
        Context context = activity != null ? activity : appCompatActivity;
        notificationPrefix = context.getString(R.string.channel_setting_notifications_prefix);
        String channelKeyNotification = channelName + notificationPrefix;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(channelKeyNotification, false);
    }

    @Override
    protected void initUserAgent() {
        mWebView.getSettings().setUserAgentString(TELEGRAM_USER_AGENT_STRING);
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
    protected void initWebClients(){
        mWebView.setWebViewClient(new TelegramInsideWebViewClient());
    }

    public String getUrl() {
        return url;
    }

    protected class TelegramInsideWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            activity.getSwipeRefreshLayout().setRefreshing(false);
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCod, String description, String failingUrl) {
            String url = view.getUrl();
            switch (url) {
                case TELEGRAM_HOME_URL: {
                    view.loadUrl(TELEGRAM_HOME_URL_2);
                    view.reload();
                }
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    private class TlJavaScriptInterface {

        private String channelName;

        private final String MESSAGE_NOTIFICATION_REGEX = "muted-class=\"im_dialog_badge_muted\" style=\"\">([0-9]+)</span>";
        private final Pattern pattern = Pattern.compile(MESSAGE_NOTIFICATION_REGEX);

        private TlJavaScriptInterface(String channelName){
            this.channelName = channelName;
        }

        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            if(isNotificationSettingEnabled(channelName)){
                Matcher m = pattern.matcher(html);
                int notificationCounter = 0;
                while(m.find()) {
                    notificationCounter += Integer.valueOf(m.group(1));
                }

                final int result = notificationCounter;
                if(notificationCounter > 0){
                    if (activity == null) {
                        appCompatActivity.runOnUiThread(() -> NotificationDisplayer.getInstance().display(channelName, result));
                    } else {
                        activity.runOnUiThread(() -> NotificationDisplayer.getInstance().display(channelName, result));
                    }
                }
            }
        }
    }
}
