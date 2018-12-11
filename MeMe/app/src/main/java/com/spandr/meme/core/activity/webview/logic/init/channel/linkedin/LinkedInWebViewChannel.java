package com.spandr.meme.core.activity.webview.logic.init.channel.linkedin;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.TextView;

import com.spandr.meme.core.activity.main.logic.notification.NotificationDisplayer;
import com.spandr.meme.core.activity.main.logic.notification.ViewChannelManager;
import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.EMPTY_STRING;

public class LinkedInWebViewChannel extends WebViewChannel {

    @SuppressWarnings("unused")
    private LinkedInWebViewChannel(){}

    public LinkedInWebViewChannel(WebViewActivity activity,
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
    protected LinkedInWebViewChannel init() {
        initUserAgent();
        initStartURL();
        initWebChromeClient();
        initWebClients();
        initListeners();
        mWebView.addJavascriptInterface(new LnJavaScriptInterface(channelName), "HTMLOUT");
        return this;
    }

    public String getUrl() {
        return url;
    }

    class LnJavaScriptInterface {

        private String channelName;

        private LnJavaScriptInterface(String channelName){
            this.channelName = channelName;
        }

        private final String MESSAGE_NOTIFICATION_REGEX = "class=\"nav-item__badge\">([0-9]+)</span>";

        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            mWebView.post(() -> {
                // LinkedIn does not update notifications dynamically. Page refresh can help.
                // However period of time for page must be bigger
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
