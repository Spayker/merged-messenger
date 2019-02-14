package com.spandr.meme.core.activity.webview.logic.init.channel.video;

import android.annotation.SuppressLint;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.CustomChromeWebClient;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

public class YoutubeWebViewChannel extends WebViewChannel {

    @SuppressWarnings("unused")
    private YoutubeWebViewChannel(){}

    public YoutubeWebViewChannel(WebViewActivity activity, String url, String channelName) {
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
    protected YoutubeWebViewChannel init() {
        initUserAgent();
        initWebChromeClient();
        initWebClients();
        initSwipeListeners();
        initOrientationSensor();
        initCacheSettings();
        initStartURL();
        return this;
    }

    public String getUrl() {
        return url;
    }

    private void initWebChromeClient() {
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

}
