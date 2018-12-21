package com.spandr.meme.core.activity.webview.logic.init.channel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.CustomChromeWebClient;
import com.spandr.meme.core.common.data.memory.channel.Channel;
import com.spandr.meme.core.common.data.memory.channel.DataChannelManager;

import im.delight.android.webview.AdvancedWebView;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.MEME_HOME_URL;
import static com.spandr.meme.core.common.util.ActivityUtils.isNetworkAvailable;

public abstract class WebViewChannel {

    //private final static String DEFAULT_USER_AGENT_STRING = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.91 Safari/537.36";
    private final static String JAVASCRIPT_HTML_GRABBER = "javascript:window.HTMLOUT.processHTML('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');";

    private String notificationPrefix;

    protected String url = MEME_HOME_URL;
    protected String channelName;
    protected AdvancedWebView mWebView;
    protected WebViewActivity activity;

    protected void initUserAgent() {
        //mWebView.getSettings().setUserAgentString(DEFAULT_USER_AGENT_STRING);
    }

    protected void initStartURL() {
        String urlToBeLoaded = MEME_HOME_URL;
        if (channelName != null) {
            Channel channel = DataChannelManager.getChannelByName(channelName);
            if (channel != null) {
                urlToBeLoaded = channel.getHomeUrl();
            }
        }
        mWebView.loadUrl(urlToBeLoaded);
    }

    @SuppressLint("SetJavaScriptEnabled")
    protected void initWebSettings() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        webSettings.setLoadsImagesAutomatically(false);
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAppCacheMaxSize(50 * 1024 * 1024);
        webSettings.setAppCachePath(activity.getApplicationContext().getCacheDir().getAbsolutePath());
        webSettings.setAppCacheEnabled(true);

        //This part will load the web page if the network is not available.
        if (!isNetworkAvailable(activity)) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
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

    @SuppressLint("ClickableViewAccessibility")
    protected void initListeners() {
        initWebViewListeners();
    }

    private void initWebViewListeners() {
        SwipeRefreshLayout swipeRefreshLayout = activity.getSwipeRefreshLayout();
        swipeRefreshLayout.setOnRefreshListener(() -> mWebView.reload());
        mWebView.getViewTreeObserver().addOnScrollChangedListener(() -> {
            if (mWebView.getScrollY() == 0) {
                swipeRefreshLayout.setEnabled(true);
            } else {
                swipeRefreshLayout.setEnabled(false);
            }
        });
    }

    protected void initWebClients() {
        mWebView.setWebViewClient(new InsideWebViewClient());
    }

    protected class InsideWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            activity.getSwipeRefreshLayout().setRefreshing(false);
            mWebView.loadUrl(JAVASCRIPT_HTML_GRABBER);
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public static String getJavascriptHtmlGrabber() {
        return JAVASCRIPT_HTML_GRABBER;
    }

    protected boolean isNotificationSettingEnabled(String channelName){
        notificationPrefix = activity.getString(R.string.channel_setting_notifications_prefix);
        String channelKeyNotification = channelName + notificationPrefix;
        SharedPreferences sharedPreferences = activity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(channelKeyNotification, false);
    }
}