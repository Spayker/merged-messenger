package com.spandr.meme.core.activity.main.logic.notification;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import im.delight.android.webview.AdvancedWebView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.spandr.meme.core.activity.main.logic.notification.ViewChannelManager.createChannelViewManager;
import static com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel.getJavascriptHtmlGrabber;
import static com.spandr.meme.core.activity.webview.logic.manager.WebViewManager.getWebViewChannelManager;
import static io.reactivex.schedulers.Schedulers.computation;

public class WebViewRunnableInitializer {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private Runnable notificationRunnable;
    private static WebViewRunnableInitializer instance;

    public WebViewRunnableInitializer(){
        instance = this;
        initRX();
        initViewChannelManager();
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
                advancedWebView.loadUrl(getJavascriptHtmlGrabber());
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("onError: " + e.getMessage());
            }

            @Override
            public void onComplete() { }
        };
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public static WebViewRunnableInitializer getInstance() {
        return instance;
    }

}
