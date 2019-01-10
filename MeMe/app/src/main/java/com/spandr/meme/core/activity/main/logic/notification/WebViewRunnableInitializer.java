package com.spandr.meme.core.activity.main.logic.notification;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel;
import com.spandr.meme.core.activity.webview.logic.manager.WebViewChannelManager;
import com.spandr.meme.core.common.data.memory.channel.Channel;
import com.spandr.meme.core.common.data.memory.channel.DataChannelManager;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.spandr.meme.core.activity.main.logic.notification.ViewChannelManager.createChannelViewManager;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.activity.webview.logic.manager.WebViewChannelManager.getWebViewChannelManager;
import static com.spandr.meme.core.common.ActivityConstants.EMPTY_STRING;
import static com.spandr.meme.core.common.data.memory.channel.DataChannelManager.getAllActiveChannels;
import static com.spandr.meme.core.common.data.memory.channel.DataChannelManager.getChannelByName;

public class WebViewRunnableInitializer {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(5);
    private Runnable notificationRunnable;
    private Context context;
    private Activity mainActivity;

    WebViewRunnableInitializer(Context context, Activity mainActivity) {
        this.context = context;
        this.mainActivity = mainActivity;
        initBackgroundWebViewComponent();
        initRX();
        initViewChannelManager();
    }

    private void initRX() {
        // init periodical command
        if (notificationRunnable == null) {
            notificationRunnable = () -> {
                Observable<String> webViewObservable = getWebViewsObservable();
                DisposableObserver<String> webViewObserver = getWebViewsObserver();

                compositeDisposable.add(webViewObservable
                        .subscribeOn(Schedulers.computation())
                        .observeOn(Schedulers.computation())
                        .subscribeWith(webViewObserver));
            };

            scheduler.scheduleAtFixedRate(notificationRunnable, 1, 3, TimeUnit.SECONDS);
        }
        initViewChannelManager();
    }

    private void initViewChannelManager() {
        createChannelViewManager(new HashMap<>());
    }

    private Observable<String> getWebViewsObservable() {
        return Observable.fromIterable(DataChannelManager.getInstance().getChannelsIterator());
    }

    private DisposableObserver<String> getWebViewsObserver() {
        return new DisposableObserver<String>() {

            @Override
            public void onNext(String channelName) {
                Channel channel = getChannelByName(channelName);
                WebViewChannel webViewChannel = Objects.requireNonNull(channel).getWebViewChannel();

                if(webViewChannel != null){

                    String cookies = channel.getCookies();
                    if(cookies.isEmpty()){
                        SharedPreferences sharedPreferences =
                                context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
                        cookies = sharedPreferences.getString(channelName+"cookies", EMPTY_STRING);
                    }

                    if(!Objects.requireNonNull(cookies).isEmpty()){
                        String html = webViewChannel.establishConnection(channel, context);
                        webViewChannel.processHTML(html);
                        NotificationDisplayer notificationDisplayer = NotificationDisplayer.getInstance();
                        notificationDisplayer.display(context, mainActivity, channelName, channel.getNotifications());
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError: " + e.getMessage());
            }

            @Override
            public void onComplete() { }
        };
    }

    private void initBackgroundWebViewComponent() {
        List<Channel> activeChannels = getAllActiveChannels();
        for (Channel channel : activeChannels) {
            String channelName = channel.getName();
            WebViewChannelManager webViewChannelManager = getWebViewChannelManager();
            //channel.setLastUrl(channel.getHomeUrl());
            webViewChannelManager.applyBackgroundChannelRelatedConfiguration(context, channelName);
        }
    }

}
