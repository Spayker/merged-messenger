package com.spand.meme.core.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spand.meme.R;
import com.spand.meme.core.logic.menu.main.builder.draggable.DraggableGridFragment;
import com.spand.meme.core.logic.menu.main.builder.draggable.common.data.AbstractDataProvider;
import com.spand.meme.core.logic.menu.main.builder.draggable.common.fragment.DataProviderFragment;
import com.spand.meme.core.ui.activity.settings.EditChannelsActivity;
import com.spand.meme.core.ui.activity.settings.GlobalSettingsActivity;

import static com.spand.meme.core.logic.starter.Loginner.createLoginner;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_CHANNEL_ORDER;
import static com.spand.meme.core.logic.starter.SettingsConstants.PREF_NAME;
import static com.spand.meme.core.logic.starter.Setupper.createSetupper;
import static com.spand.meme.core.logic.starter.Starter.REGISTRATOR;
import static com.spand.meme.core.logic.starter.Starter.START_TYPE;
import static com.spand.meme.core.logic.starter.Starter.USERNAME;

/**
 * A class handler is linked to appropriate activity xml file and contains backend logic.
 **/
public class MainActivity extends AppCompatActivity {

    private static final String FRAGMENT_TAG_DATA_PROVIDER = "data provider";
    private static final String FRAGMENT_LIST_VIEW = "list view";

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
        Intent intent = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String startTypeKey = intent.getStringExtra(START_TYPE);
        if (startTypeKey != null) {
            switch (startTypeKey) {
                case REGISTRATOR: {
                    createSetupper(this).initApplication(sharedPreferences);
                    String username = intent.getStringExtra(USERNAME);
                    setTitle(username);
                    break;
                }
                default: {
                    createLoginner(this).initApplication(sharedPreferences);
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if (currentUser != null) {
                        setTitle(currentUser.getDisplayName());
                    }
                }
            }
        } else {
            createLoginner(this).initApplication(sharedPreferences);
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser != null) {
                setTitle(currentUser.getDisplayName());
            }
        }

        TextView mAppVersionView = findViewById(R.id.app_build_version);
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            mAppVersionView.setText(String.format("%s %s%s", getString(R.string.app_name),
                    getString(R.string.app_version), version));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        // slogan part
        TextView mAppStyledName = findViewById(R.id.main_app_name_styled);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Consolas.ttf");
        mAppStyledName.setTypeface(typeface);

        final SpannableStringBuilder sb = new SpannableStringBuilder(getString(R.string.app_name_styled));
        final ForegroundColorSpan fcs = new ForegroundColorSpan(getResources().getColor(R.color.bright_green));
        sb.setSpan(fcs, 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mAppStyledName.setText(sb);

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

    private void saveChannelOrder() {
        AbstractDataProvider channelProvider = getDataProvider();
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        StringBuffer currentChannelOrder = new StringBuffer();
        int channelCount = channelProvider.getCount();
        for(int i = 0; i != channelCount; i++){
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