package com.spand.meme.core.ui.activity.webview;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.spand.meme.R;

import static com.spand.meme.core.ui.activity.ActivityConstants.DISCORD_ACTIVITY_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.HOME_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.ICQ_HOME_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.SHALL_LOAD_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.TELEGRAM_HOME_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.TUMBLR_HOME_URL;

public class WebViewActivity extends Activity {

    private CustomWebView mWebView;
    private View mBackButton;

    @SuppressLint({"SetJavaScriptEnabled", "ClickableViewAccessibility"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        mWebView = findViewById(R.id.webView);
        mBackButton = findViewById(R.id.backToMainMenu);

        // Initialize the VideoEnabledWebChromeClient and set event handlers
        View nonVideoLayout = findViewById(R.id.nonVideoLayout);
        ViewGroup videoLayout = findViewById(R.id.videoLayout);

        View loadingView = getLayoutInflater().inflate(R.layout.view_loading_video,null); // Your own view, read class comments
        CustomChromeWebClient webChromeClient = new CustomChromeWebClient(nonVideoLayout, videoLayout, loadingView, mWebView) // See all available constructors...
        {
            // Subscribe to standard events, such as onProgressChanged()...
            @Override
            public void onProgressChanged(WebView view, int progress) {
                // Your code...
            }
        };

        mWebView.setOnTouchListener((v, event) -> {
            mBackButton.setAlpha(.05f);
            mWebView.performClick();
            return false;
        });

        mBackButton.setOnTouchListener((v, event) -> {
            mBackButton.setAlpha(.99f);
            mBackButton.performClick();
            return false;
        });

        webChromeClient.setOnToggledFullscreen(fullscreen -> {
            // Your code to handle the full-screen change, for example showing and hiding the title bar. Example:
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
        // Call private class InsideWebViewClient
        mWebView.setWebViewClient(new InsideWebViewClient());
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
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);


        // REMOTE RESOURCE
        Intent webViewIntent = getIntent();
        String loadUrl = webViewIntent.getStringExtra(HOME_URL);
        if(webViewIntent.getBooleanExtra(SHALL_LOAD_URL, false)){
            mWebView.loadUrl(loadUrl);
        }

        if(!loadUrl.contains(TUMBLR_HOME_URL)) {
            mWebView.getSettings()
                    .setUserAgentString("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.91 Safari/537.36");
        }

        // in order to support sdk from v16
        //CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
            mWebView.removeAllViews();
            mWebView.clearHistory();
            mWebView.clearCache(true);
            mWebView.onPause();
            mWebView.removeAllViews();
            mWebView.destroyDrawingCache();
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private class InsideWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    public void clickOnBackToMainMenu(View view){
        mWebView.removeAllViews();
        mWebView.clearHistory();
        mWebView.clearCache(true);
        mWebView.onPause();
        mWebView.removeAllViews();
        mWebView.destroyDrawingCache();
        onBackPressed();
    }

}
