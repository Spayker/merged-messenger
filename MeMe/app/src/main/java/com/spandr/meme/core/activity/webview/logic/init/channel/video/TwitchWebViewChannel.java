package com.spandr.meme.core.activity.webview.logic.init.channel.video;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.main.logic.notification.NotificationDisplayer;
import com.spandr.meme.core.activity.webview.WebViewActivity;
import com.spandr.meme.core.activity.webview.logic.CustomChromeWebClient;
import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;
import com.spandr.meme.core.common.data.memory.channel.Channel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import im.delight.android.webview.AdvancedWebView;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.common.ActivityConstants.EMPTY_STRING;
import static com.spandr.meme.core.common.data.memory.channel.DataChannelManager.getChannelByName;

public class TwitchWebViewChannel extends WebViewChannel {

    private Context appCompatActivity;
    private final String MESSAGE_NOTIFICATION_REGEX = "tw-pill--notification\">([0-9]+)</span>";
    private final Pattern pattern = Pattern.compile(MESSAGE_NOTIFICATION_REGEX);

    @SuppressWarnings("unused")
    private TwitchWebViewChannel(){}

    public TwitchWebViewChannel(WebViewActivity activity,
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

    public TwitchWebViewChannel(Context activity, String url, String channelName) {
        if (url.isEmpty()) {
            return;
        }
        this.appCompatActivity = activity;
        this.url = url;
        this.channelName = channelName;
    }

    @SuppressLint("AddJavascriptInterface")
    protected TwitchWebViewChannel init() {
        initUserAgent();
        initWebChromeClient();
        initWebClients();
        initListeners();
        initOrientationSensor();
        initCacheSettings();
        initStartURL();
        return this;
    }

    @Override
    protected boolean isNotificationSettingEnabled(String channelName) {
        Context context = activity != null ? activity : appCompatActivity;
        notificationPrefix = context.getString(R.string.channel_setting_notifications_prefix);
        String channelKeyNotification = channelName + notificationPrefix;
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(channelKeyNotification, false);
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

    public String getUrl() {
        return url;
    }

    @Override
    public void processHTML(String html) {
        if(isNotificationSettingEnabled(channelName)){
            Matcher m = pattern.matcher(html);
            int notificationCounter = 0;
            while(m.find()) {
                notificationCounter += Integer.valueOf(m.group(1));
            }

            Channel channel = getChannelByName(channelName);
            if(channel != null){
                channel.setNotifications(notificationCounter);
            }

            /*if(notificationCounter > 0){
                if (activity == null) {
                        appCompatActivity.runOnUiThread(() -> {
                            NotificationDisplayer.getInstance().display(appCompatActivity, channelName, result);
                            mWebView.stopLoading();
                        });
                } else {
                    activity.runOnUiThread(() -> {
                        NotificationDisplayer.getInstance().display(activity, channelName, result);
                        mWebView.stopLoading();
                    });
                }
            }*/
        }
    }

}
