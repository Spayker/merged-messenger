package com.spandr.meme.core.activity.webview.logic.init.channel.social;

import android.webkit.JavascriptInterface;

import com.spandr.meme.core.activity.main.logic.notification.NotificationDisplayer;
import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VkontakteWebViewChannel extends WebViewChannel {

    private String VKONTAKTE_USER_AGENT_STRING = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.91 Safari/537.36";

    @SuppressWarnings("unused")
    private VkontakteWebViewChannel(){}

    public VkontakteWebViewChannel(WebViewActivity activity,
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

    protected VkontakteWebViewChannel init() {
        //initUserAgent();
        initStartURL();
        initWebChromeClient();
        initListeners();
        initWebClients();
        mWebView.addJavascriptInterface(new VkJavaScriptInterface(), "HTMLOUT");
        return this;
    }

    @Override
    protected void initUserAgent() {
        mWebView.getSettings().setUserAgentString(VKONTAKTE_USER_AGENT_STRING);
    }

    public String getUrl() {
        return url;
    }

    private class VkJavaScriptInterface {

        private final String MESSAGE_NOTIFICATION_REGEX = "<em class=\"mm_counter\">([0-9]+)</em>";
        private final Pattern pattern = Pattern.compile(MESSAGE_NOTIFICATION_REGEX);

        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
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
