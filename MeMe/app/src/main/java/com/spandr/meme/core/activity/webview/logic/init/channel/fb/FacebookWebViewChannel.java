package com.spandr.meme.core.activity.webview.logic.init.channel.fb;

import android.annotation.SuppressLint;
import android.webkit.JavascriptInterface;

import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FacebookWebViewChannel extends WebViewChannel {

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

    public String getUrl() {
        return url;
    }

    class FbJavaScriptInterface {

        private final String NOTIFICATION_REGEX = "data-sigil=\"count\">([0-9]+)</span>";

        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            mWebView.post(() -> {
                Matcher m = Pattern.compile(NOTIFICATION_REGEX).matcher(html);
                if (m.find()) {
                    m.group(1);
                }
            });
        }
    }

}
