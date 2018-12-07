package com.spandr.meme.core.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.spandr.meme.R;
import com.spandr.meme.core.activity.authorization.LoginActivity;
import com.spandr.meme.core.activity.intro.WelcomeActivity;
import com.spandr.meme.core.activity.main.logic.builder.draggable.DraggableGridFragment;
import com.spandr.meme.core.activity.main.logic.builder.draggable.common.data.AbstractDataProvider;
import com.spandr.meme.core.activity.main.logic.builder.draggable.common.fragment.DataProviderFragment;
import com.spandr.meme.core.activity.main.logic.updater.AppUpdater;
import com.spandr.meme.core.activity.settings.channel.EditChannelsActivity;
import com.spandr.meme.core.activity.settings.global.GlobalSettingsActivity;
import com.spandr.meme.core.common.util.ActivityUtils;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import im.delight.android.webview.AdvancedWebView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.spandr.meme.core.activity.main.logic.starter.Loginner.createLoginner;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_CHANNEL_ORDER;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_USER_NAME;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.activity.webview.logic.init.channel.WebViewChannel.getJavascriptHtmlGrabber;
import static com.spandr.meme.core.activity.webview.logic.manager.WebViewManager.getWebViewChannelManager;
import static com.spandr.meme.core.common.ActivityConstants.EMPTY_STRING;
import static com.spandr.meme.core.common.util.ActivityUtils.initLanguage;

/**
 * A class handler is linked to appropriate activity xml file and contains backend logic.
 **/
public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG_DATA_PROVIDER = "data provider";
    private static final String FRAGMENT_LIST_VIEW = "list view";

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    private Runnable notificationRunnable;


    /**
     * Perform initialization of all fragments of current activity.
     *
     * @param savedInstanceState an instance of Bundle instance
     *                           (A mapping from String keys to various Parcelable values)
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // default settings init
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString(KEY_USER_NAME, EMPTY_STRING);
        setTitle(userName);

        createLoginner(this).initApplication(sharedPreferences);
        ActivityUtils.initVersionNumber(this);
        initLanguage(sharedPreferences, this);
        ActivityUtils.initSloganPart(this, R.id.main_app_name_styled);
        initFragment(savedInstanceState);
        initRX();
    }

    private void initRX() {
        // init periodical command
        if(notificationRunnable == null){
            notificationRunnable = () -> {
                Observable<AdvancedWebView> webViewObservable = getWebViewsObservable();
                DisposableObserver<AdvancedWebView> webViewObserver = getWebViewsObserver();

                compositeDisposable.add(
                        webViewObservable
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(webViewObserver));
            };

            scheduler.scheduleAtFixedRate(notificationRunnable,1,10,TimeUnit.SECONDS);
        }
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
            public void onComplete() {
                System.out.println("All channels are processed!");
            }
        };
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        getDelegate().onPostCreate(savedInstanceState);
        checkNewAppVersion();
    }

    private void checkNewAppVersion() {
        new AppUpdater(this).checkAppForUpdate();
    }

    private void initFragment(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(new DataProviderFragment().initActivity(this), FRAGMENT_TAG_DATA_PROVIDER)
                    .commit();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.main_linear_layout, new DraggableGridFragment(), FRAGMENT_LIST_VIEW)
                    .commit();
        }
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                Intent intent = new Intent(this, GlobalSettingsActivity.class);
                startActivity(intent);
                return true;
            }
            default: {
                performSignOut();
                return true;
            }
        }
    }

    public void clickOnExit(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void clickOnEditChannels(View view) {
        Intent intent = new Intent(this, EditChannelsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        saveChannelOrder();
        performSignOut();
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    private void saveChannelOrder() {
        AbstractDataProvider channelProvider = getDataProvider();
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        StringBuilder currentChannelOrder = new StringBuilder();
        int channelCount = channelProvider.getCount();
        for (int i = 0; i != channelCount; i++) {
            AbstractDataProvider.Data item = channelProvider.getItem(i);
            currentChannelOrder.append(item.getText()).append("|");
        }
        editor.putString(KEY_CHANNEL_ORDER, currentChannelOrder.toString());
        editor.apply();
        editor.commit();
    }

    private void performSignOut() {
        saveChannelOrder();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public AbstractDataProvider getDataProvider() {
        final Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TAG_DATA_PROVIDER);
        return ((DataProviderFragment) fragment).getDataProvider();
    }
}