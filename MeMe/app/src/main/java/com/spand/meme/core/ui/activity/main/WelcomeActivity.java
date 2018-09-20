package com.spand.meme.core.ui.activity.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.spand.meme.R;
import com.spand.meme.core.logic.menu.authorization.ActivityBehaviourAddon;

import static com.spand.meme.core.ui.activity.ActivityConstants.EMPTY_STRING;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_AUTO_LOGIN;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_PASS;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_USER_EMAIL_OR_PHONE;
import static com.spand.meme.core.logic.starter.SettingsConstants.PREF_NAME;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class WelcomeActivity extends AppCompatActivity implements ActivityBehaviourAddon {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = WelcomeActivity.class.getSimpleName();

    private ProgressDialog mProgressDialog;

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

        LinearLayout buttonLayer = findViewById(R.id.fullscreen_content_controls);
        buttonLayer.setVisibility(View.INVISIBLE);

        // slogan part
        TextView mAppStyledName = findViewById(R.id.welcome_app_name_styled);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/Consolas.ttf");
        mAppStyledName.setTypeface(typeface);

        final SpannableStringBuilder sb = new SpannableStringBuilder(getString(R.string.app_name_styled));
        final ForegroundColorSpan fcs = new ForegroundColorSpan(getResources().getColor(R.color.bright_green));
        sb.setSpan(fcs, 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mAppStyledName.setText(sb);

        // auto login part
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean isAutoLoginEnabled = sharedPreferences.getBoolean(KEY_AUTO_LOGIN, false);
        if (mAuth.getCurrentUser() != null && isAutoLoginEnabled) {
            String email = sharedPreferences.getString(KEY_USER_EMAIL_OR_PHONE, EMPTY_STRING);
            String password = sharedPreferences.getString(KEY_PASS, EMPTY_STRING);
            if (!email.isEmpty() && !password.isEmpty()) {
                showProgressDialog();
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
                            hideProgressDialog();
                        });
            }
            return;
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

    /**
     * Shows progress dialog while backend action is in progress.
     **/
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.login_checking));
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    /**
     * Hides progress dialog from screen.
     **/
    @Override
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * Updates ui according to authorization result.
     **/
    @Override
    public void updateUI() {
        hideProgressDialog();
    }
}
