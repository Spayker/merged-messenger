package com.spandr.meme.core.ui.activity.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

import com.spandr.meme.R;
import com.spandr.meme.core.data.memory.channel.Channel;
import com.spandr.meme.core.data.memory.channel.ChannelManager;
import com.spandr.meme.core.ui.activity.webview.logic.CustomChromeWebClient;

import java.util.Calendar;

import im.delight.android.webview.AdvancedWebView;

import static com.spandr.meme.core.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.ui.activity.webview.logic.WebViewConstants.CHANNEL_NAME;
import static com.spandr.meme.core.ui.activity.webview.logic.WebViewConstants.ICQ_HOME_URL;
import static com.spandr.meme.core.ui.activity.webview.logic.WebViewConstants.KEY_LEFT_MARGIN;
import static com.spandr.meme.core.ui.activity.webview.logic.WebViewConstants.KEY_TOP_MARGIN;
import static com.spandr.meme.core.ui.activity.webview.logic.WebViewConstants.MAIL_RU_HOME_URL;
import static com.spandr.meme.core.ui.activity.webview.logic.WebViewConstants.MEME_HOME_URL;
import static com.spandr.meme.core.ui.activity.webview.logic.WebViewConstants.SKYPE_HOME_URL;
import static com.spandr.meme.core.ui.activity.webview.logic.WebViewConstants.TELEGRAM_HOME_URL;
import static com.spandr.meme.core.ui.activity.webview.logic.WebViewConstants.TELEGRAM_HOME_URL_2;
import static com.spandr.meme.core.ui.activity.webview.logic.WebViewConstants.USER_AGENT_STRING;
import static com.spandr.meme.core.ui.activity.webview.logic.WebViewConstants.VK_HOME_URL;
import static com.spandr.meme.core.ui.activity.webview.logic.WebViewConstants.WEBVIEW_BACK_BUTTON_VIBRATE_DURATION_IN_MS;

public class WebViewActivity extends Activity implements AdvancedWebView.Listener, View.OnTouchListener {

    private static final int BACK_BUTTON_MIN_CLICK_DURATION = 500;

    private SwipeRefreshLayout swipeRefreshLayout;
    private AdvancedWebView mWebView;
    private FloatingActionButton mBackButton;
    private RelativeLayout.LayoutParams relativeLayoutParams;
    private SharedPreferences sharedPreferences;

    private ViewGroup webViewRelativeLayout;
    private int _xDelta;
    private int _yDelta;
    private boolean longClicked;
    private boolean isActionUpHappened;
    private boolean shallVibroNotify;
    private long startClickTime;

    @SuppressLint("AddJavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        webViewRelativeLayout = findViewById(R.id.webview_relative_layout);
        swipeRefreshLayout = findViewById(R.id.swipeContainer);
        mWebView = findViewById(R.id.webView);
        mBackButton = findViewById(R.id.backToMainMenu);

        initListeners();
        initWebClients();
        initWebSettings();
        initUserAgent();
        loadStartURL();
        initBackButtonStartPosition();

        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setScrollbarFadingEnabled(false);
    }

    private void initUserAgent() {
        Intent webViewIntent = getIntent();
        String channelName = webViewIntent.getStringExtra(CHANNEL_NAME);
        Channel channel = ChannelManager.getChannelByName(channelName);
        if (channel != null) {
            String homeURL = channel.getHomeUrl();
            switch (homeURL) {
                case VK_HOME_URL:
                case TELEGRAM_HOME_URL:
                case ICQ_HOME_URL:
                case SKYPE_HOME_URL: {
                    mWebView.getSettings()
                            .setUserAgentString(USER_AGENT_STRING);
                    break;
                }
            }
        }
    }

    private void loadStartURL() {
        Intent webViewIntent = getIntent();
        String channelName = webViewIntent.getStringExtra(CHANNEL_NAME);
        Channel channel = ChannelManager.getChannelByName(channelName);
        if (channel != null) {
            mWebView.loadUrl(channel.getHomeUrl());
        } else {
            mWebView.loadUrl(MEME_HOME_URL);
        }
    }

    @SuppressLint("JavascriptInterface")
    private void initWebClients() {
        mWebView.setWebChromeClient(initWebChromeClient());
        mWebView.setWebViewClient(new InsideWebViewClient() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onPageFinished(WebView view, String url) {
                swipeRefreshLayout.setRefreshing(false);
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCod, String description, String failingUrl) {
                String url = view.getUrl();
                switch (url) {
                    case TELEGRAM_HOME_URL: {
                        mWebView.loadUrl(TELEGRAM_HOME_URL_2);
                        mWebView.reload();
                    }
                }
            }
        });
    }

    private void initBackButtonStartPosition() {
        int topMargin = sharedPreferences.getInt(KEY_TOP_MARGIN, 0);
        int leftMargin = sharedPreferences.getInt(KEY_LEFT_MARGIN, 0);
        if (topMargin > 0 || leftMargin > 0) {
            relativeLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            relativeLayoutParams.topMargin = topMargin;
            relativeLayoutParams.leftMargin = leftMargin;
            relativeLayoutParams.bottomMargin = 0;
            relativeLayoutParams.rightMargin = 0;
            mBackButton.setLayoutParams(relativeLayoutParams);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebSettings() {
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

        webSettings.setAppCacheMaxSize(50 * 1024 * 1024);
        webSettings.setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        webSettings.setAppCacheEnabled(true);

        //This part will load the web page if the network is not available.
        if (!isNetworkAvailable()) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    private CustomChromeWebClient initWebChromeClient() {
        View nonVideoLayout = findViewById(R.id.nonVideoLayout);
        ViewGroup videoLayout = findViewById(R.id.videoLayout);

        View loadingView = getLayoutInflater().inflate(R.layout.view_loading_video, null);
        CustomChromeWebClient webChromeClient = new CustomChromeWebClient(nonVideoLayout,
                videoLayout, loadingView, mWebView) {
            @Override
            public void onProgressChanged(WebView view, int progress) {
            }
        };

        webChromeClient.setOnToggledFullscreen(fullscreen -> {
            if (fullscreen) {
                WindowManager.LayoutParams attrs = getWindow().getAttributes();
                attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                getWindow().setAttributes(attrs);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                mBackButton.setVisibility(View.INVISIBLE);
            } else {
                WindowManager.LayoutParams attrs = getWindow().getAttributes();
                attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                getWindow().setAttributes(attrs);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                mBackButton.setVisibility(View.VISIBLE);
            }
        });
        return webChromeClient;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListeners() {

        mWebView.setOnTouchListener((v, event) -> {
            mBackButton.setAlpha(.45f);
            mWebView.performClick();
            return false;
        });

        Intent webViewIntent = getIntent();
        String channelName = webViewIntent.getStringExtra(CHANNEL_NAME);
        Channel channel = ChannelManager.getChannelByName(channelName);
        if (channel != null) {
            String homeURL = channel.getHomeUrl();
            switch (homeURL) {
                case TELEGRAM_HOME_URL:
                case ICQ_HOME_URL:
                case MAIL_RU_HOME_URL: {
                    mWebView.setOnTouchListener((v, event) -> {
                        swipeRefreshLayout.setEnabled(false);
                        mBackButton.setAlpha(.45f);
                        mWebView.performClick();
                        return false;
                    });
                    break;
                }
                default: {
                    swipeRefreshLayout.setOnRefreshListener(() -> mWebView.reload());
                    mWebView.getViewTreeObserver().addOnScrollChangedListener(() -> {
                        if (mWebView.getScrollY() == 0) {
                            swipeRefreshLayout.setEnabled(true);
                        } else {
                            swipeRefreshLayout.setEnabled(false);
                        }
                    });
                }
            }
        }
        mBackButton.setOnTouchListener(this);
        mBackButton.setOnClickListener(this::clickOnBackToMainMenu);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            mWebView.removeAllViews();
            mWebView.clearHistory();
            mWebView.onPause();
            mWebView.removeAllViews();
            super.onBackPressed();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        mBackButton.setAlpha(.99f);
        final int X = (int) event.getRawX();
        final int Y = (int) event.getRawY();

        if (relativeLayoutParams == null) {
            relativeLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            relativeLayoutParams.topMargin = Y - 300;
            relativeLayoutParams.leftMargin = X - 250;
            relativeLayoutParams.bottomMargin = 0;
            relativeLayoutParams.rightMargin = 0;
        }

        boolean outOfTop = relativeLayoutParams.topMargin < 0;
        boolean outOfLeft = relativeLayoutParams.leftMargin < 0;
        boolean outOfBottom = relativeLayoutParams.topMargin + v.getMeasuredHeight() > webViewRelativeLayout.getMeasuredHeight();
        boolean outOfRight = relativeLayoutParams.leftMargin + v.getMeasuredWidth() > webViewRelativeLayout.getMeasuredWidth();

        if (outOfTop) {
            relativeLayoutParams.topMargin = 50;
            v.setLayoutParams(relativeLayoutParams);
            return true;
        }

        if (outOfLeft) {
            relativeLayoutParams.leftMargin = 50;
            v.setLayoutParams(relativeLayoutParams);
            return true;
        }

        if (outOfBottom) {
            relativeLayoutParams.topMargin = Y - 300;
            v.setLayoutParams(relativeLayoutParams);
            return true;
        }

        if (outOfRight) {
            relativeLayoutParams.leftMargin = X - 250;
            v.setLayoutParams(relativeLayoutParams);
            return true;
        }

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                mBackButton.setBackgroundTintList(ColorStateList.
                        valueOf(getResources().getColor(R.color.dark_green)));
                _xDelta = X - relativeLayoutParams.leftMargin;
                _yDelta = Y - relativeLayoutParams.topMargin;
                longClicked = false;
                isActionUpHappened = false;
                shallVibroNotify = true;
                startClickTime = Calendar.getInstance().getTimeInMillis();
                break;
            }
            case MotionEvent.ACTION_UP: {
                mBackButton.setBackgroundTintList(ColorStateList.
                        valueOf(getResources().getColor(R.color.bright_green)));
                isActionUpHappened = true;
                if (!longClicked) {
                    v.performClick();
                    break;
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt(KEY_TOP_MARGIN, relativeLayoutParams.topMargin);
                editor.putInt(KEY_LEFT_MARGIN, relativeLayoutParams.leftMargin);
                editor.apply();
                editor.commit();
            }
            case MotionEvent.ACTION_MOVE: {
                long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                if (!isActionUpHappened) {
                    if (clickDuration >= BACK_BUTTON_MIN_CLICK_DURATION) {
                        if (shallVibroNotify) {
                            Vibrator vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                            if (vibrator != null) {
                                vibrator.vibrate(WEBVIEW_BACK_BUTTON_VIBRATE_DURATION_IN_MS);
                            }
                            shallVibroNotify = false;
                        }

                        longClicked = true;
                        int newLeftMargin = X - _xDelta;
                        int newTopMargin = Y - _yDelta;

                        relativeLayoutParams.leftMargin = newLeftMargin;
                        relativeLayoutParams.topMargin = newTopMargin;
                        relativeLayoutParams.rightMargin = 0;
                        relativeLayoutParams.bottomMargin = 0;
                        v.setLayoutParams(relativeLayoutParams);
                    }
                }
            }
        }
        webViewRelativeLayout.invalidate();
        return true;
    }

    private class InsideWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public void clickOnBackToMainMenu(View view) {
        mWebView.removeAllViews();
        mWebView.clearHistory();
        mWebView.onPause();
        mWebView.removeAllViews();
        onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void onPause() {
        mWebView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mWebView.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        mWebView.onActivityResult(requestCode, resultCode, intent);
    }

    @Override
    public void onPageStarted(String url, Bitmap favicon) {
    }

    @Override
    public void onPageFinished(String url) {
    }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) {
    }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType,
                                    long contentLength, String contentDisposition,
                                    String userAgent) {
    }

    @Override
    public void onExternalPageRequest(String url) {
    }

}