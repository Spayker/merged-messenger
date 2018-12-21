package com.spandr.meme.core.activity.webview.logic.init.channel.social;

import android.annotation.SuppressLint;
import android.webkit.JavascriptInterface;

import com.spandr.meme.core.activity.main.logic.notification.NotificationDisplayer;
import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OkWebViewChannel extends WebViewChannel {

    @SuppressWarnings("unused")
    private OkWebViewChannel(){}

    public OkWebViewChannel(WebViewActivity activity,
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
    protected OkWebViewChannel init() {
        initUserAgent();
        initStartURL();
        initWebChromeClient();
        initWebClients();
        initListeners();
        mWebView.addJavascriptInterface(new OkJavaScriptInterface(channelName), "HTMLOUT");
        return this;
    }

    public String getUrl() {
        return url;
    }

    private class OkJavaScriptInterface {

        private String channelName;

        private OkJavaScriptInterface(String channelName){
            this.channelName = channelName;
        }

        private final String MESSAGE_NOTIFICATION_REGEX = ">([0-9]+)</span></a>";
        private final Pattern pattern = Pattern.compile(MESSAGE_NOTIFICATION_REGEX);

        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            if(isNotificationSettingEnabled(channelName)){
                mWebView.post(() -> {
                    Matcher m = pattern.matcher(html);
                    int notificationCounter = 0;
                    while(m.find()) {
                        notificationCounter += Integer.valueOf(m.group(1));
                    }
                    NotificationDisplayer.getInstance().display(channelName, notificationCounter);
                });
            }
        }
    }
}
