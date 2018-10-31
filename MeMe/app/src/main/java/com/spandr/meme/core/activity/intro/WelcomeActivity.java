package com.spandr.meme.core.activity.intro;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spandr.meme.R;
import com.spandr.meme.core.activity.authorization.LoginActivity;
import com.spandr.meme.core.activity.authorization.RegisterActivity;
import com.spandr.meme.core.activity.main.MainActivity;

import java.util.Locale;

import io.fabric.sdk.android.Fabric;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.APP_SUPPORTED_LANGUAGES;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.EN;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_AUTO_LOGIN;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_CURRENT_APP_LANGUAGE;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_PASS;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_USER_EMAIL_OR_PHONE;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.common.ActivityConstants.EMPTY_STRING;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends AppCompatActivity {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = WelcomeActivity.class.getSimpleName();

    private static Boolean isLocaleSet;

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

        initLanguage(sharedPreferences);
        initSloganPart();
        initVersionNumber();

        // auto login part
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        boolean isAutoLoginEnabled = sharedPreferences.getBoolean(KEY_AUTO_LOGIN, false);
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null && user.isEmailVerified() && isAutoLoginEnabled) {
            String email = sharedPreferences.getString(KEY_USER_EMAIL_OR_PHONE, EMPTY_STRING);
            String password = sharedPreferences.getString(KEY_PASS, EMPTY_STRING);
            if (!email.isEmpty() && !password.isEmpty()) {
                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, getString(R.string.login_log_sign_in_with_email_success));
                                Intent intent = new Intent(this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, getString(R.string.login_log_sign_in_with_email_failure), task.getException());
                                Toast.makeText(WelcomeActivity.this, getString(R.string.welcome_error_auth_failed),
                                        Toast.LENGTH_SHORT).show();
                                buttonLayer.setVisibility(View.VISIBLE);
                            }
                        });
            }
            return;
        }
        buttonLayer.setVisibility(View.VISIBLE);
    }


    private void initVersionNumber() {
        // app version part
        TextView mAppVersionView = findViewById(R.id.app_build_version);
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            mAppVersionView.setText(String.format("%s %s%s", getString(R.string.app_name),
                    getString(R.string.app_version), version));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initLanguage(SharedPreferences sharedPreferences) {
        // language part
        String currentLanguage = sharedPreferences.getString(KEY_CURRENT_APP_LANGUAGE, Locale.getDefault().getDisplayLanguage());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String shortLanguage = APP_SUPPORTED_LANGUAGES.get(currentLanguage);
        if(shortLanguage == null){
            shortLanguage = EN;
        }
        Locale locale = new Locale(shortLanguage);
        Locale.setDefault(locale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResourcesLocale(this, locale);
        }

        updateResourcesLocaleLegacy(this, locale);
        if(isLocaleSet == null) {
            recreate();
            isLocaleSet = true;
        }

        editor.putString(KEY_CURRENT_APP_LANGUAGE, currentLanguage);
        editor.apply();
        editor.commit();
    }

    private void initSloganPart(){
        TextView mAppStyledName = findViewById(R.id.welcome_app_name_styled);
        final SpannableStringBuilder sb = new SpannableStringBuilder(getString(R.string.app_name_styled));
        final ForegroundColorSpan fcs = new ForegroundColorSpan(getResources().getColor(R.color.bright_green));
        sb.setSpan(fcs, 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mAppStyledName.setText(sb);
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void updateResourcesLocale(Context context, Locale locale) {
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
    }

    @SuppressWarnings("deprecation")
    private void updateResourcesLocaleLegacy(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
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
