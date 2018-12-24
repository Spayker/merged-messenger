package com.spandr.meme.core.activity.webview.logic.init.channel.video;

import android.annotation.SuppressLint;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.main.logic.notification.NotificationDisplayer;
import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.CustomChromeWebClient;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import im.delight.android.webview.AdvancedWebView;

public class YoutubeWebViewChannel extends WebViewChannel {

    private AppCompatActivity appCompatActivity;
    @SuppressWarnings("unused")
    private YoutubeWebViewChannel(){}

    public YoutubeWebViewChannel(WebViewActivity activity,
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

    public YoutubeWebViewChannel(AppCompatActivity activity, AdvancedWebView mWebView,
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
    protected YoutubeWebViewChannel init() {
        initUserAgent();
        initWebChromeClient();
        initWebClients();
        initListeners();
        initOrientationSensor();
        initCacheSettings();
        mWebView.addJavascriptInterface(new YoutubeJavaScriptInterface(channelName), "HTMLOUT");
        initStartURL();
        return this;
    }

    @SuppressLint("AddJavascriptInterface")
    private void initBackgroundMode() {
        initBackgroundWebSettings();
        mWebView.addJavascriptInterface(new YoutubeJavaScriptInterface(channelName), "HTMLOUT");
        initStartURL();
    }

    public String getUrl() {
        return url;
    }

    protected void initWebChromeClient() {
        View nonVideoLayout = activity.findViewById(R.id.nonVideoLayout);
        ViewGroup videoLayout = activity.findViewById(R.id.videoLayout);

        View loadingView = activity.getLayoutInflater().inflate(R.layout.view_loading_video, null);
        CustomChromeWebClient webChromeClient = new CustomChromeWebClient(nonVideoLayout,
                videoLayout, loadingView, mWebView) {
            @Override
            public void onProgressChanged(WebView view, int progress) { }
        };

        webChromeClient.setOnToggledFullscreen(fullscreen -> {
            Window window = activity.getWindow();
            FloatingActionButton backButton = activity.getBackButton();
            if (fullscreen) {
                WindowManager.LayoutParams attrs = window.getAttributes();
                attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                window.setAttributes(attrs);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                backButton.show();
            } else {
                WindowManager.LayoutParams attrs = window.getAttributes();
                attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                window.setAttributes(attrs);
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                backButton.hide();
            }
        });
        mWebView.setWebChromeClient(webChromeClient);
    }

    private class YoutubeJavaScriptInterface {

        private String channelName;

        private YoutubeJavaScriptInterface(String channelName){
            this.channelName = channelName;
        }

        private final String MESSAGE_NOTIFICATION_REGEX = "tw-pill--notification\">([0-9]+)</span>";
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

                    final int result = notificationCounter;
                    if (activity == null) {
                        appCompatActivity.runOnUiThread(() -> NotificationDisplayer.getInstance().display(channelName, result));
                    } else {
                        activity.runOnUiThread(() -> NotificationDisplayer.getInstance().display(channelName, result));
                    }
                });
            }
        }
    }
}
