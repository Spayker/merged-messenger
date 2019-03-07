package com.spandr.meme.core.activity.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.main.MainActivity;
import com.spandr.meme.core.activity.webview.logic.manager.WebViewChannelManager;
import com.spandr.meme.core.common.data.memory.channel.Channel;
import com.spandr.meme.core.common.data.memory.channel.DataChannelManager;

import java.util.Calendar;
import java.util.List;

import im.delight.android.webview.AdvancedWebView;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.CHANNEL_NAME;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.KEY_LEFT_MARGIN;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.KEY_TOP_MARGIN;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.WEBVIEW_BACK_BUTTON_VIBRATE_DURATION_IN_MS;
import static com.spandr.meme.core.common.data.memory.channel.DataChannelManager.getChannelByName;

/**
 *
 *
 * @author  Spayker
 * @version 1.0
 * @since   3/7/2019
 */
public class WebViewActivity extends AppCompatActivity implements AdvancedWebView.Listener,
        View.OnTouchListener, ViewTreeObserver.OnScrollChangedListener {

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
    private String channelName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        webViewRelativeLayout = findViewById(R.id.webview_relative_layout);
        swipeRefreshLayout = findViewById(R.id.swipeContainer);
        mBackButton = findViewById(R.id.backToMainMenu);

        swipeRefreshLayout.setOnRefreshListener(() -> swipeRefreshLayout.setRefreshing(false));

        Intent webViewIntent = getIntent();
        channelName = webViewIntent.getStringExtra(CHANNEL_NAME);
        createWebView(channelName);
    }

    private void createWebView(String channelName) {
        mWebView = findViewById(R.id.webView);
        initListeners();
        initBackButtonStartPosition();
        WebViewChannelManager webViewChannelManager = WebViewChannelManager.getWebViewChannelManager();
        webViewChannelManager.applyChannelRelatedConfiguration(this, channelName);

        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setScrollbarFadingEnabled(false);
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

    public void clickOnBackToMainMenu(View view) {
        Channel channel = getChannelByName(channelName);
        if (channel != null) {
            String url = mWebView.getUrl();
            String cookies = CookieManager.getInstance().getCookie(url);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(channelName + "cookies", cookies);
            editor.putString(channelName + "lastUrl", url);
            editor.putString(channelName + "userAgent", mWebView.getSettings().getUserAgentString());
            editor.apply();
            editor.commit();
        }
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    public void onBackPressed() {
        mWebView.goBack();
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

    @Override
    public void onScrollChanged() {
        if (mWebView.getScrollY() == 0) {
            swipeRefreshLayout.setEnabled(true);
        } else {
            swipeRefreshLayout.setEnabled(false);
        }
    }
}