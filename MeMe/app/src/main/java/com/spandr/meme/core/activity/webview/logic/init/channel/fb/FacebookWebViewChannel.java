package com.spandr.meme.core.activity.webview.logic.init.channel.fb;

import android.annotation.SuppressLint;
import android.webkit.JavascriptInterface;

import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FacebookWebViewChannel extends WebViewChannel {

    private final static String FACEBOOK_USER_AGENT_STRING = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.91 Safari/537.36";

    private FacebookWebViewChannel(){}

    public FacebookWebViewChannel(WebViewActivity activity,
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
    protected FacebookWebViewChannel init() {
        initUserAgent();
        initStartURL();
        initWebChromeClient();
        initWebClients();
        initListeners();
        mWebView.addJavascriptInterface(new FbJavaScriptInterface(), "HTMLOUT");
        return this;
    }

    @Override
    protected void initUserAgent() {
        mWebView.getSettings().setUserAgentString(FACEBOOK_USER_AGENT_STRING);
    }

    public String getUrl() {
        return url;
    }

    class FbJavaScriptInterface {

        private final String MESSAGE_NOTIFICATION_REGEX = "\"_59tg\" data-sigil=\"count\">([0-9]+)</span>";

        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            mWebView.post(() -> {
                Matcher m = Pattern.compile(MESSAGE_NOTIFICATION_REGEX).matcher(html);
                int notificationCounter = 0;
                while(m.find()) {
                    notificationCounter += Integer.valueOf(m.group(1));
                }
                System.out.println("Facebook notifications: " + notificationCounter);
            });
        }
    }

}
