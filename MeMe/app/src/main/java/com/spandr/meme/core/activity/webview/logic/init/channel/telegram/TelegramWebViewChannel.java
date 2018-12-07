package com.spandr.meme.core.activity.webview.logic.init.channel.telegram;

import android.annotation.SuppressLint;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.TELEGRAM_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.TELEGRAM_HOME_URL_2;

public class TelegramWebViewChannel extends WebViewChannel {

    private String TELEGRAM_USER_AGENT_STRING = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.91 Safari/537.36";

    private TelegramWebViewChannel(){}

    public TelegramWebViewChannel(WebViewActivity activity,
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

    @SuppressLint("AddJavascriptInterface")
    protected TelegramWebViewChannel init() {
        initUserAgent();
        initStartURL();
        initWebChromeClient();
        initListeners();
        initWebClients();
        mWebView.addJavascriptInterface(new TlJavaScriptInterface(), "HTMLOUT");
        return this;
    }

    @Override
    protected void initUserAgent() {
        mWebView.getSettings().setUserAgentString(TELEGRAM_USER_AGENT_STRING);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void initListeners(){
        mWebView.setOnTouchListener((v, event) -> {
            activity.getSwipeRefreshLayout().setEnabled(false);
            activity.getBackButton().setAlpha(.45f);
            mWebView.performClick();
            return false;
        });
    }

    @Override
    protected void initWebClients(){
        mWebView.setWebViewClient(new TelegramInsideWebViewClient());
    }

    public String getUrl() {
        return url;
    }

    protected class TelegramInsideWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            activity.getSwipeRefreshLayout().setRefreshing(false);
            super.onPageFinished(view, url);
        }

        @Override
        public void onReceivedError(WebView view, int errorCod, String description, String failingUrl) {
            String url = view.getUrl();
            switch (url) {
                case TELEGRAM_HOME_URL: {
                    view.loadUrl(TELEGRAM_HOME_URL_2);
                    view.reload();
                }
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    class TlJavaScriptInterface {

        private final String MESSAGE_NOTIFICATION_REGEX = "muted-class=\"im_dialog_badge_muted\" style=\"\">([0-9]+)</span>";

        @JavascriptInterface
        @SuppressWarnings("unused")
        public void processHTML(String html) {
            mWebView.post(() -> {
                Matcher m = Pattern.compile(MESSAGE_NOTIFICATION_REGEX).matcher(html);
                int notificationCounter = 0;
                while(m.find()) {
                    notificationCounter += Integer.valueOf(m.group(1));
                }
                System.out.println("Telegram notifications: " + notificationCounter);
            });
        }
    }
}
