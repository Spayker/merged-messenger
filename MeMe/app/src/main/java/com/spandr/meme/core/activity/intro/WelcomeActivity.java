package com.spandr.meme.core.activity.intro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.crashlytics.android.Crashlytics;
import com.spandr.meme.R;
import com.spandr.meme.core.activity.authorization.LoginActivity;
import com.spandr.meme.core.activity.authorization.RegisterActivity;
import com.spandr.meme.core.activity.intro.logic.AutoLoginner;
import com.spandr.meme.core.common.util.ActivityUtils;

import io.fabric.sdk.android.Fabric;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_AUTO_LOGIN;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.common.util.ActivityUtils.initLanguage;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends AppCompatActivity {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = WelcomeActivity.class.getSimpleName();

    private AutoLoginner autoLoginner = new AutoLoginner();

    /**
     * Perform initialization of all fragments of current activity.
     *
     * @param savedInstanceState an instance of Bundle instance
     *                           (A mapping from String keys to various Parcelable values)
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Fabric.with(this, new Crashlytics());
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        ProgressBar progressBar = findViewById(R.id.welcome_progressBar_cyclic);
        progressBar.setVisibility(View.INVISIBLE);
        LinearLayout buttonLayer = findViewById(R.id.fullscreen_content_controls);
        buttonLayer.setVisibility(View.INVISIBLE);

        initLanguage(sharedPreferences, this);
        Log.d(TAG, "Language init is complete");
        ActivityUtils.initSloganPart(this);
        Log.d(TAG, "Slogan init is complete");
        ActivityUtils.initVersionNumber(this);
        Log.d(TAG, "Version number init is complete");
        checkAutoLogin(sharedPreferences, progressBar, buttonLayer);
        Log.d(TAG, "Auto login is checked");
    }

    private void checkAutoLogin(SharedPreferences sharedPreferences,
                                ProgressBar progressBar,
                                LinearLayout buttonLayer) {
        boolean isAutoLoginEnabled = sharedPreferences.getBoolean(KEY_AUTO_LOGIN, false);
        if (isAutoLoginEnabled) {
            autoLoginner.performAutoLogin(this, sharedPreferences, progressBar, buttonLayer);
        }
        buttonLayer.setVisibility(View.VISIBLE);
    }

    /**
     * A listener method which starts new activity.
     *
     * @param view an instance of View class
     *             ( represents the basic building block for user interface components )
     **/
    public void singInActivity(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * A listener method which starts new activity.
     *
     * @param view an instance of View class
     *             ( represents the basic building block for user interface components )
     **/
    public void singUpActivity(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
