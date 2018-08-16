package com.spand.meme.core.submodule.ui.activity.webview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.spand.meme.R;

import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.HOME_URL;

public class WebViewActivity extends Activity {

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        mWebView = findViewById(R.id.activity_main_webview);

        // Force links and redirects to open in the WebView instead of in a browser
        mWebView.setWebViewClient(new WebViewClient());

        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // REMOTE RESOURCE
        Intent webViewIntent = getIntent();
        mWebView.loadUrl(webViewIntent.getStringExtra(HOME_URL));
        mWebView.setWebViewClient(new CustomWebViewClient());
        mWebView.getSettings()
                .setUserAgentString("Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36");

        mWebView.getSettings().setLoadWithOverviewMode(true);
        mWebView.getSettings().setUseWideViewPort(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(mWebView, true);
    }

    // Prevent the back-button from closing the app
    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }




}
