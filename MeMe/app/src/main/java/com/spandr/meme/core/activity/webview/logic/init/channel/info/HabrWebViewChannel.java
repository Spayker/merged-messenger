package com.spandr.meme.core.activity.webview.logic.init.channel.info;

import android.annotation.SuppressLint;
import android.webkit.JavascriptInterface;

import com.spandr.meme.core.activity.main.logic.notification.NotificationDisplayer;
import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HabrWebViewChannel extends WebViewChannel {

    @SuppressWarnings("unused")
    private HabrWebViewChannel(){}

    public HabrWebViewChannel(WebViewActivity activity,
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
    protected HabrWebViewChannel init() {
        initUserAgent();
        initStartURL();
        initWebChromeClient();
        initWebClients();
        initListeners();
        mWebView.addJavascriptInterface(new HabrJavaScriptInterface(channelName), "HTMLOUT");
        mWebView.setDesktopMode(true);
        return this;
    }

    public String getUrl() {
        return url;
    }

    class HabrJavaScriptInterface {

        private String channelName;

        private HabrJavaScriptInterface(String channelName){
            this.channelName = channelName;
        }

        private final String MESSAGE_NOTIFICATION_REGEX = "item-counter_new\">\\+([0-9]+)</span>";

        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            mWebView.post(() -> {
                Matcher m = Pattern.compile(MESSAGE_NOTIFICATION_REGEX).matcher(html);
                int notificationCounter = 0;
                while(m.find()) {
                    notificationCounter += Integer.valueOf(m.group(1));
                }

                NotificationDisplayer.getInstance().display(channelName, notificationCounter);
            });
        }
    }




}
