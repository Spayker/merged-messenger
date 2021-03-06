package com.spandr.meme.core.activity.webview.logic.init.channel;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.support.v4.widget.SwipeRefreshLayout;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.common.data.memory.channel.Channel;
import com.spandr.meme.core.common.data.memory.channel.DataChannelManager;

import im.delight.android.webview.AdvancedWebView;

import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.MEME_HOME_URL;
import static com.spandr.meme.core.common.util.ActivityUtils.isNetworkAvailable;

/**
 *
 *
 * @author  Spayker
 * @version 1.0
 * @since   3/7/2019
 */
public abstract class WebViewChannel {

    protected String url = MEME_HOME_URL;
    protected String channelName;
    protected AdvancedWebView mWebView;
    protected WebViewActivity activity;

    protected void initUserAgent() {
    }

    protected void initStartURL() {
        String urlToBeLoaded = MEME_HOME_URL;
        if (channelName != null) {
            Channel channel = DataChannelManager.getChannelByName(channelName);
            if (channel != null) {
                String channelLastUsedUrl = channel.getLastUrl();
                if (channelLastUsedUrl != null) {
                    if (channelLastUsedUrl.isEmpty()) {
                        urlToBeLoaded = channel.getHomeUrl();
                    } else {
                        urlToBeLoaded = channelLastUsedUrl;
                    }
                } else {
                    urlToBeLoaded = channel.getHomeUrl();
                }
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
        webSettings.setLoadsImagesAutomatically(true);
    }

    protected void initOrientationSensor() {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
    }

    protected void initCacheSettings() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setAppCacheMaxSize(100 * 1024 * 1024);
        webSettings.setAppCachePath(activity.getApplicationContext().getCacheDir().getAbsolutePath());
        webSettings.setAppCacheEnabled(true);

        //This part will load the web page if the network is not available.
        if (!isNetworkAvailable(activity)) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    protected void initSwipeListeners() {
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

    public class InsideWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            if (activity != null) {
                activity.getSwipeRefreshLayout().setRefreshing(false);
            }
            super.onPageFinished(view, url);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public WebViewActivity getActivity() {
        return activity;
    }
}