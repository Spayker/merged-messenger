package com.spand.meme.core.ui.activity.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.spand.meme.R;
import com.spand.meme.core.data.database.FireBaseDBInitializer;
import com.spand.meme.core.logic.authorization.EmailAuthorizer;
import com.spand.meme.core.logic.menu.authorization.ActivityBehaviourAddon;

import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_AUTO_LOGIN;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_OLD_CHANGE_PASS;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_PASS;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_REMEMBER;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_USER_EMAIL_OR_PHONE;
import static com.spand.meme.core.logic.starter.SettingsConstants.PREF_NAME;
import static com.spand.meme.core.ui.activity.ActivityConstants.EMPTY_STRING;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher,
        CompoundButton.OnCheckedChangeListener, ActivityBehaviourAddon {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = LoginActivity.class.getSimpleName();

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private CheckBox mRememberMeView;
    private CheckBox mAutoLoginView;
    private TextView mForgotPasswordView;
    private TextView mForgotPasswordTimerView;

    final int FORGOT_PASSWORD_COOLDOWN_MS = 30000;
    final int FORGOT_PASSWORD_COOLDOWN_INTERVAL = 10;
    final int FORGOT_PASSWORD_COOLDOWN_INTERVAL_DIVIDER = 1000;

    private static CountDownTimer countDownTimer;
    private static long secondsLeft;

    private SharedPreferences sharedPreferences;

    @VisibleForTesting
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
        setContentView(R.layout.activity_login);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        // Views
        mEmailView = findViewById(R.id.login_form_email);
        mPasswordView = findViewById(R.id.login_form_password);
        mRememberMeView = findViewById(R.id.login_form_rememberMe);
        mAutoLoginView = findViewById(R.id.login_form_autologin);
        mForgotPasswordView = findViewById(R.id.forgot_password);
        mForgotPasswordTimerView = findViewById(R.id.forgot_password_timer);

        if (sharedPreferences.getBoolean(KEY_REMEMBER, false))
            mRememberMeView.setChecked(true);
        else
            mRememberMeView.setChecked(false);

        if (sharedPreferences.getBoolean(KEY_AUTO_LOGIN, false))
            mAutoLoginView.setChecked(true);
        else
            mAutoLoginView.setChecked(false);

        mEmailView.setText(sharedPreferences.getString(KEY_USER_EMAIL_OR_PHONE, EMPTY_STRING));
        mPasswordView.setText(sharedPreferences.getString(KEY_PASS, EMPTY_STRING));

        mEmailView.addTextChangedListener(this);
        mPasswordView.addTextChangedListener(this);
        mRememberMeView.setOnCheckedChangeListener(this);
        mAutoLoginView.setOnCheckedChangeListener(this);

        TextView mForgotPasswordView = findViewById(R.id.forgot_password);
        mForgotPasswordView.setPaintFlags(mForgotPasswordView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        // Buttons
        findViewById(R.id.email_sign_in_button).setOnClickListener(this);

        // init count timer of forgot password
        initForgotPasswordCountTimer();
        if(secondsLeft > 0){
            mForgotPasswordView.setEnabled(false);
            mForgotPasswordView.setTextColor(Color.GRAY);
            countDownTimer.start();
        }

        // db init
        FireBaseDBInitializer.create().init();
    }

    /**
     * Called after onCreate(Bundle) — or after onRestart() when the activity had been stopped,
     * but is now again being displayed to the user. It will be followed by onResume().
     **/
    @Override
    public void onStart() {
        super.onStart();
        updateUI();
    }

    /**
     * Updates ui according to authorization result.
     **/
    @Override
    public void updateUI() {
        hideProgressDialog();
        managePrefs();
    }

    /**
     * A callback method to be invoked when a view is clicked.
     *
     * @param view an instance of View class
     *             ( class represents the basic building block for user interface components )
     **/
    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.email_sign_in_button) {
            signIn(mEmailView.getText().toString(), mPasswordView.getText().toString());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        managePrefs();
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
     * Performs Sign In operation.
     *
     * @param emailOrPhone a String object which will be checked during authorization procedure
     * @param password     a String object which will be checked during authorization procedure
     **/
    private void signIn(String emailOrPhone, String password) {
        Log.d(TAG, getString(R.string.login_log_sign_in) + emailOrPhone);
        if (!validateForm()) {
            return;
        }
        EmailAuthorizer.init(this, EMPTY_STRING, emailOrPhone, password).authorize();
        showProgressDialog();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        managePrefs();
    }

    /**
     * Performs validation procedure before Sign In operation.
     **/
    private boolean validateForm() {
        boolean valid = true;

        String email = mEmailView.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.login_field_required));
            valid = false;
        }

        String password = mPasswordView.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.login_field_required));
            valid = false;
        } else {
            mPasswordView.setError(null);
        }
        return valid;
    }

    private void managePrefs() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_OLD_CHANGE_PASS, mPasswordView.getText().toString().trim());
        if (mRememberMeView.isChecked()) {
            editor.putString(KEY_USER_EMAIL_OR_PHONE, mEmailView.getText().toString().trim());
            editor.putString(KEY_PASS, mPasswordView.getText().toString().trim());
            editor.putBoolean(KEY_REMEMBER, true);
        } else {
            editor.putBoolean(KEY_REMEMBER, false);
            editor.remove(KEY_PASS);
            editor.remove(KEY_USER_EMAIL_OR_PHONE);
        }

        if (mAutoLoginView.isChecked()) {
            editor.putBoolean(KEY_AUTO_LOGIN, true);
        } else {
            editor.putBoolean(KEY_AUTO_LOGIN, false);
        }

        editor.apply();
        editor.commit();
    }

    private void initForgotPasswordCountTimer(){
        long startCountDownValue;
        if (secondsLeft == 0) {
            startCountDownValue = FORGOT_PASSWORD_COOLDOWN_MS;
        } else {
            startCountDownValue = secondsLeft;
        }

        countDownTimer = new CountDownTimer(startCountDownValue, FORGOT_PASSWORD_COOLDOWN_INTERVAL) {
            public void onTick(long millisUntilFinished) {
                Long secondsUntilFinished = millisUntilFinished / FORGOT_PASSWORD_COOLDOWN_INTERVAL_DIVIDER;
                mForgotPasswordTimerView.setText(String.format(" %s %s", secondsUntilFinished,
                        getString(R.string.login_field_short_seconds)));
                secondsLeft = secondsUntilFinished * FORGOT_PASSWORD_COOLDOWN_INTERVAL_DIVIDER;
            }

            @Override
            public void onFinish() {
                mForgotPasswordView.setEnabled(true);
                mForgotPasswordView.setTextColor(R.string.login_forgot_password_link_color);
                mForgotPasswordTimerView.setText(EMPTY_STRING);
                countDownTimer = null;
            }
        };
    }

    public void onForgotPasswordClick(View view) {
        String email = mEmailView.getText().toString();
        if (email.isEmpty()) {
            Toast.makeText(LoginActivity.this, getString(R.string.login_error_email_empty),
                    Toast.LENGTH_SHORT).show();
        } else {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sent.");
                            Toast.makeText(LoginActivity.this, getString(R.string.login_info_email_sent),
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
            mForgotPasswordView.setEnabled(false);
            mForgotPasswordView.setTextColor(Color.GRAY);
            countDownTimer.start();
        }
    }

    @Override
    public void onBackPressed() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
    }

}

