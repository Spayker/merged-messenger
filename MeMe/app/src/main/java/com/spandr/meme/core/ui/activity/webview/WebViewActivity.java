package com.spandr.meme.core.ui.activity.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
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

import im.delight.android.webview.AdvancedWebView;

import static com.spandr.meme.core.ui.activity.ActivityConstants.HOME_URL;
import static com.spandr.meme.core.ui.activity.ActivityConstants.SHALL_LOAD_URL;
import static com.spandr.meme.core.ui.activity.ActivityConstants.TUMBLR_HOME_URL;
import static com.spandr.meme.core.ui.activity.ActivityConstants.YOUTUBE_HOME_URL;

public class WebViewActivity extends Activity implements AdvancedWebView.Listener, View.OnTouchListener {

    private SwipeRefreshLayout swipeRefreshLayout;
    private ViewTreeObserver.OnScrollChangedListener mOnScrollChangedListener;
    private AdvancedWebView mWebView;
    private View mBackButton;
    private boolean isBackButtonMoved;
    private RelativeLayout.LayoutParams relativelayoutParams;

    private ViewGroup webViewRelativeLayout;
    private int _xDelta;
    private int _yDelta;

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webViewRelativeLayout = findViewById(R.id.webview_relative_layout);
        mWebView = findViewById(R.id.webView);
        mBackButton = findViewById(R.id.backToMainMenu);
        swipeRefreshLayout = findViewById(R.id.swipeContainer);

        swipeRefreshLayout.getViewTreeObserver().addOnScrollChangedListener(
                mOnScrollChangedListener = () -> {
                    if (mWebView.getScrollY() == 0) {
                        swipeRefreshLayout.setEnabled(true);
                    } else {
                        swipeRefreshLayout.setEnabled(false);
                    }
                });

        // Initialize the VideoEnabledWebChromeClient and set event handlers
        View nonVideoLayout = findViewById(R.id.nonVideoLayout);
        ViewGroup videoLayout = findViewById(R.id.videoLayout);

        View loadingView = getLayoutInflater().inflate(R.layout.view_loading_video, null);
        CustomChromeWebClient webChromeClient = new CustomChromeWebClient(nonVideoLayout,
                videoLayout, loadingView, mWebView) {
            @Override
            public void onProgressChanged(WebView view, int progress) {
            }
        };

        mWebView.setOnTouchListener((v, event) -> {
            mBackButton.setAlpha(.45f);
            mWebView.performClick();
            return false;
        });

        mBackButton.setOnTouchListener(this);
        mBackButton.setOnClickListener(this::clickOnBackToMainMenu);

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

        mWebView.setWebChromeClient(webChromeClient);
        mWebView.setWebViewClient(new InsideWebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                swipeRefreshLayout.setRefreshing(false);
                super.onPageFinished(view, url);
            }
        });
        mWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        mWebView.setScrollbarFadingEnabled(false);

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowFileAccess(true);
        webSettings.setAllowContentAccess(true);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);


        // REMOTE RESOURCE
        Intent webViewIntent = getIntent();
        String loadUrl = webViewIntent.getStringExtra(HOME_URL);
        if (webViewIntent.getBooleanExtra(SHALL_LOAD_URL, false)) {
            mWebView.loadUrl(loadUrl);
        }

        if (!loadUrl.contains(TUMBLR_HOME_URL) && !loadUrl.contains(YOUTUBE_HOME_URL)) {
            mWebView.getSettings()
                    .setUserAgentString("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.91 Safari/537.36");
        }

        swipeRefreshLayout.setOnRefreshListener(() -> mWebView.reload());
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            mWebView.removeAllViews();
            mWebView.clearHistory();
            mWebView.onPause();
            mWebView.destroyDrawingCache();
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

        if(relativelayoutParams == null){
            relativelayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
            relativelayoutParams.topMargin = Y - 300;
            relativelayoutParams.leftMargin = X - 250;
            relativelayoutParams.bottomMargin = -250;
            relativelayoutParams.rightMargin = -250;
        }

        boolean outOfTop = relativelayoutParams.topMargin < 0;
        boolean outOfLeft = relativelayoutParams.leftMargin < 0;
        boolean outOfBottom = relativelayoutParams.topMargin + v.getMeasuredHeight() > webViewRelativeLayout.getMeasuredHeight();
        boolean outOfRight = relativelayoutParams.leftMargin + v.getMeasuredWidth() > webViewRelativeLayout.getMeasuredWidth();

        if (outOfTop) {
            relativelayoutParams.topMargin = 50;
            v.setLayoutParams(relativelayoutParams);
            return true;
        }

        if (outOfLeft) {
            relativelayoutParams.leftMargin = 50;
            v.setLayoutParams(relativelayoutParams);
            return true;
        }

        if (outOfBottom) {
            relativelayoutParams.topMargin = Y - 300;
            v.setLayoutParams(relativelayoutParams);
            return true;
        }

        if (outOfRight) {
            relativelayoutParams.leftMargin = X - 250;
            v.setLayoutParams(relativelayoutParams);
            return true;
        }

        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN: {
                _xDelta = X - relativelayoutParams.leftMargin;
                _yDelta = Y - relativelayoutParams.topMargin;
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (!isBackButtonMoved) {
                    v.performClick();
                    break;
                }
            }
            case MotionEvent.ACTION_MOVE: {
                int newLeftMargin = X - _xDelta;
                int newTopMargin = Y - _yDelta;

                isBackButtonMoved = relativelayoutParams.leftMargin != newLeftMargin ||
                        relativelayoutParams.topMargin != newTopMargin;
                relativelayoutParams.leftMargin = newLeftMargin;
                relativelayoutParams.topMargin = newTopMargin;
                relativelayoutParams.rightMargin = -250;
                relativelayoutParams.bottomMargin = -250;
                v.setLayoutParams(relativelayoutParams);
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
        mWebView.clearCache(true);
        mWebView.onPause();
        mWebView.removeAllViews();
        mWebView.destroyDrawingCache();
        onBackPressed();
    }

    @SuppressLint("NewApi")
    @Override
    protected void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @SuppressLint("NewApi")
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
    public void onPageStarted(String url, Bitmap favicon) { }

    @Override
    public void onPageFinished(String url) { }

    @Override
    public void onPageError(int errorCode, String description, String failingUrl) { }

    @Override
    public void onDownloadRequested(String url, String suggestedFilename, String mimeType,
                                    long contentLength, String contentDisposition,
                                    String userAgent) { }

    @Override
    public void onExternalPageRequest(String url) { }

}
