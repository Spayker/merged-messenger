package com.spandr.meme.core.activity.webview;

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
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.main.MainActivity;
import com.spandr.meme.core.activity.webview.logic.CustomChromeWebClient;
import com.spandr.meme.core.activity.webview.logic.init.channel.DefaultWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.fb.FacebookWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.icq.IcqWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.skype.SkypeWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.telegram.TelegramWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.init.channel.vk.VkontakteWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.manager.WebViewActivityManager;
import com.spandr.meme.core.common.data.memory.channel.Channel;
import com.spandr.meme.core.common.data.memory.channel.ChannelManager;

import java.util.Calendar;
import java.util.Map;

import im.delight.android.webview.AdvancedWebView;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.CHANNEL_NAME;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.FB_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.ICQ_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.KEY_LEFT_MARGIN;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.KEY_TOP_MARGIN;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.MAIL_RU_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.SKYPE_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.TELEGRAM_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.TELEGRAM_HOME_URL_2;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.VK_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.WEBVIEW_BACK_BUTTON_VIBRATE_DURATION_IN_MS;
import static com.spandr.meme.core.activity.webview.logic.manager.WebViewActivityManager.getWebViewChannelManager;
import static com.spandr.meme.core.common.util.ActivityUtils.isNetworkAvailable;

public class WebViewActivity extends AppCompatActivity implements AdvancedWebView.Listener, View.OnTouchListener {

    private static final int BACK_BUTTON_MIN_CLICK_DURATION = 500;

    private SwipeRefreshLayout swipeRefreshLayout;
    private AdvancedWebView mWebView;

    private FloatingActionButton mBackButton;
    private RelativeLayout.LayoutParams relativeLayoutParams;
    private SharedPreferences sharedPreferences;

    private ViewGroup webViewRelativeLayout;
    private RelativeLayout nonVideoLayout;
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
        nonVideoLayout = findViewById(R.id.nonVideoLayout);
        mBackButton = findViewById(R.id.backToMainMenu);

        WebViewActivityManager webViewActivityManager = getWebViewChannelManager();
        Map<String, AdvancedWebView> availableWebViewActivities = webViewActivityManager.getWebViewActivities();
        Intent webViewIntent = getIntent();
        String channelName = webViewIntent.getStringExtra(CHANNEL_NAME);

        if (availableWebViewActivities.containsKey(channelName)) {
            mWebView = availableWebViewActivities.get(channelName);
            nonVideoLayout.removeAllViews();

            ViewGroup parent = (ViewGroup) mWebView.getParent();
            if (parent != null) {
                parent.removeView(mWebView);
            }
            RelativeLayout.LayoutParams newRelativeLayoutParams =
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                            RelativeLayout.LayoutParams.MATCH_PARENT);
            mWebView.setLayoutParams(newRelativeLayoutParams);
            nonVideoLayout.addView(mWebView);
        } else {
            mWebView = findViewById(R.id.webView);
            initListeners();
            initWebSettings();
            initBackButtonStartPosition();
            applyChannelRelatedConfiguration();

            mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            mWebView.setScrollbarFadingEnabled(false);
            availableWebViewActivities.put(channelName, mWebView);
        }
    }

    private void applyChannelRelatedConfiguration() {
        Intent webViewIntent = getIntent();
        String channelName = webViewIntent.getStringExtra(CHANNEL_NAME);
        if (channelName != null) {
            Channel channel = ChannelManager.getChannelByName(channelName);
            if (channel != null) {
                String homeURL = channel.getHomeUrl();
                switch (homeURL) {
                    case FB_HOME_URL: {
                        new FacebookWebViewChannel(this, homeURL, channelName);
                        break;
                    }
                    case VK_HOME_URL: {
                        new VkontakteWebViewChannel(this, homeURL, channelName);
                        break;
                    }
                    case TELEGRAM_HOME_URL: {
                        new TelegramWebViewChannel(this, homeURL, channelName);
                        break;
                    }
                    case ICQ_HOME_URL: {
                        new IcqWebViewChannel(this, homeURL, channelName);
                        break;
                    }
                    case SKYPE_HOME_URL: {
                        new SkypeWebViewChannel(this, homeURL, channelName);
                        break;
                    }
                    default:
                        new DefaultWebViewChannel(this, homeURL, channelName);
                }
            }
        }
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
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        webSettings.setAllowFileAccessFromFileURLs(true);
        webSettings.setAllowUniversalAccessFromFileURLs(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

        webSettings.setAppCacheMaxSize(50 * 1024 * 1024);
        webSettings.setAppCachePath(getApplicationContext().getCacheDir().getAbsolutePath());
        webSettings.setAppCacheEnabled(true);

        //This part will load the web page if the network is not available.
        if (!isNetworkAvailable(this)) {
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListeners() {
        mWebView.setOnTouchListener((v, event) -> {
            mBackButton.setAlpha(.45f);
            mWebView.performClick();
            return false;
        });
        mBackButton.setOnTouchListener(this);
        mBackButton.setOnClickListener(this::clickOnBackToMainMenu);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            /*mWebView.removeAllViews();
            mWebView.clearHistory();
            mWebView.onPause();
            mWebView.removeAllViews();*/
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
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

    public void clickOnBackToMainMenu(View view) {
        /*mWebView.removeAllViews();
        mWebView.clearHistory();
        mWebView.onPause();
        mWebView.removeAllViews();*/
        onBackPressed();
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

    public AdvancedWebView getmWebView() {
        return mWebView;
    }

    public FloatingActionButton getBackButton() {
        return mBackButton;
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }
}