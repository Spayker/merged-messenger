package com.spandr.meme.core.activity.main.logic.notification;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.spandr.meme.core.activity.webview.logic.init.channel.social.VkontakteWebViewChannel;
import com.spandr.meme.core.activity.webview.logic.manager.WebViewManager;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import im.delight.android.webview.AdvancedWebView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.spandr.meme.core.activity.main.logic.LogicContants.CHANNEL_SPLITTER;
import static com.spandr.meme.core.activity.main.logic.LogicContants.TASK_BACKGROUND_PREFIX;
import static com.spandr.meme.core.activity.main.logic.notification.ViewChannelManager.createChannelViewManager;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_LAST_USED_CHANNELS;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.activity.webview.logic.WebViewConstants.VK_HOME_URL;
import static com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel.getJavascriptHtmlGrabber;
import static com.spandr.meme.core.activity.webview.logic.manager.WebViewManager.applyBackgroundChannelRelatedConfiguration;
import static com.spandr.meme.core.activity.webview.logic.manager.WebViewManager.getWebViewChannelManager;
import static com.spandr.meme.core.common.ActivityConstants.EMPTY_STRING;

public class WebViewRunnableInitializer {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
    private Runnable notificationRunnable;
    private static WebViewRunnableInitializer instance;
    private AppCompatActivity activity;

    public WebViewRunnableInitializer(AppCompatActivity activity){
        instance = this;
        this.activity = activity;
        initRX();
        initViewChannelManager();
        lastUsedChannelsCheckingNotifications();
    }

    private void initRX() {
        // init periodical command
        if (notificationRunnable == null) {
            notificationRunnable = () -> {
                Observable<AdvancedWebView> webViewObservable = getWebViewsObservable();
                DisposableObserver<AdvancedWebView> webViewObserver = getWebViewsObserver();

                compositeDisposable.add(
                        webViewObservable
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(webViewObserver));
            };

            scheduler.scheduleAtFixedRate(notificationRunnable, 1, 2, TimeUnit.SECONDS);
        }
        initViewChannelManager();
    }

    private void initViewChannelManager() {
        createChannelViewManager(new HashMap<>());
    }

    private Observable<AdvancedWebView> getWebViewsObservable() {
        return Observable.fromIterable(getWebViewChannelManager().getWebViewChannelsIterator());
    }

    private DisposableObserver<AdvancedWebView> getWebViewsObserver() {
        return new DisposableObserver<AdvancedWebView>() {
            @Override
            public void onNext(AdvancedWebView advancedWebView) {
                if(!advancedWebView.isShown()) {
                    advancedWebView.getSettings().setLoadsImagesAutomatically(false);
                    advancedWebView.reload();
                    advancedWebView.loadUrl(getJavascriptHtmlGrabber());
                }
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {}
        };
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public static WebViewRunnableInitializer getInstance() {
        return instance;
    }

    private void lastUsedChannelsCheckingNotifications() {
        SharedPreferences sharedPreferences = activity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String lastUsedChannels = sharedPreferences.getString(KEY_LAST_USED_CHANNELS, EMPTY_STRING);
        if(lastUsedChannels.isEmpty()){
            return;
        }
        String[] splitedChannels = lastUsedChannels.split(CHANNEL_SPLITTER);
        for(String channelName: splitedChannels){
            AdvancedWebView mWebView = new AdvancedWebView(activity);
            channelName = channelName.replace(TASK_BACKGROUND_PREFIX, EMPTY_STRING);
            applyBackgroundChannelRelatedConfiguration(activity, mWebView, channelName);
            WebViewManager webViewManager = getWebViewChannelManager();
            Map<String, AdvancedWebView> availableWebViewActivities = webViewManager.getWebViewChannels();
            availableWebViewActivities.put(channelName + TASK_BACKGROUND_PREFIX, mWebView);
        }
    }

}
