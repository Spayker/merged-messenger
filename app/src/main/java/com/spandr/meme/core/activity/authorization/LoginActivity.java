package com.spandr.meme.core.activity.authorization;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.spandr.meme.R;
import com.spandr.meme.core.activity.authorization.logic.AppAuthorizer;
import com.spandr.meme.core.activity.authorization.logic.data.User;
import com.spandr.meme.core.activity.authorization.logic.firebase.exception.AppFireBaseAuthException;
import com.spandr.meme.core.activity.authorization.logic.validator.FormValidator;
import com.spandr.meme.core.activity.authorization.logic.validator.LoginFormValidator;
import com.spandr.meme.core.activity.intro.WelcomeActivity;

import java.util.Objects;

import static com.spandr.meme.core.activity.main.logic.LogicContants.APP_BACK_RETURN_FLAG;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_AUTO_LOGIN;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_OLD_CHANGE_PASS;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_PASS;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_REMEMBER;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_USER_EMAIL_OR_PHONE;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.common.ActivityConstants.EMPTY_STRING;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher,
        CompoundButton.OnCheckedChangeListener {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = LoginActivity.class.getSimpleName();

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private CheckBox mRememberMeView;
    private CheckBox mAutoLoginView;
    private TextView mForgotPasswordView;
    private TextView mForgotPasswordTimerView;
    private ProgressBar progressBar;

    final int FORGOT_PASSWORD_COOLDOWN_MS = 5000;
    final int FORGOT_PASSWORD_COOLDOWN_INTERVAL = 10;
    final int FORGOT_PASSWORD_COOLDOWN_INTERVAL_DIVIDER = 1000;

    private static CountDownTimer countDownTimer;
    private static long secondsLeft;

    private SharedPreferences sharedPreferences;
    private FormValidator formValidator;

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
        setTitle(R.string.login_title_activity);
        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        progressBar = findViewById(R.id.login_progressBar_cyclic);
        progressBar.setVisibility(View.INVISIBLE);

        // Views
        mEmailView = findViewById(R.id.login_form_email);
        mPasswordView = findViewById(R.id.login_form_password);
        mRememberMeView = findViewById(R.id.login_form_rememberMe);
        mAutoLoginView = findViewById(R.id.login_form_autologin);
        mForgotPasswordView = findViewById(R.id.forgot_password);
        mForgotPasswordTimerView = findViewById(R.id.forgot_password_timer);

        if (sharedPreferences.getBoolean(KEY_REMEMBER, false)) {
            mRememberMeView.setChecked(true);
        } else {
            mRememberMeView.setChecked(false);
        }

        if (sharedPreferences.getBoolean(KEY_AUTO_LOGIN, false)) {
            mAutoLoginView.setChecked(true);
        } else {
            mAutoLoginView.setChecked(false);
        }

        String userEmail = User.getInstance(this).getEmailAddress();
        String userPassword = User.getInstance(this).getPassword();
        if (userEmail == null){
            userEmail = sharedPreferences.getString(KEY_USER_EMAIL_OR_PHONE, EMPTY_STRING);
        }

        if (userPassword == null){
            userPassword = sharedPreferences.getString(KEY_PASS, EMPTY_STRING);
        }

        mEmailView.setText(userEmail);
        mPasswordView.setText(userPassword);

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
        if (secondsLeft > 0) {
            mForgotPasswordView.setEnabled(false);
            mForgotPasswordView.setTextColor(Color.GRAY);
            countDownTimer.start();
        }

        // init form validator
        formValidator = new LoginFormValidator();

        // db init
        //FireBaseDBInitializer.create().init();
    }

    /**
     * Called after onCreate(Bundle) — or after onRestart() when the activity had been stopped,
     * but is now again being displayed to the user. It will be followed by onResume().
     **/
    @Override
    public void onStart() {
        super.onStart();
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
            InputMethodManager inputMethodManager =
                    (InputMethodManager) getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                View currentFocus = getCurrentFocus();
                if(currentFocus != null) {
                    inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                }
            }
            try {
                signIn(mEmailView.getText().toString(), mPasswordView.getText().toString());
            } catch (AppFireBaseAuthException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

    /**
     * Shows progress dialog while backend action is in progress.
     **/
    public void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    /**
     * Hides progress dialog from screen.
     **/
    public void hideProgressDialog() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * Performs Sign In operation.
     *
     * @param login a String object which will be checked during authorization procedure
     * @param password     a String object which will be checked during authorization procedure
     **/
    public void signIn(String login, String password) {
        switch(formValidator.validateInputForm(login, password)){
            case EMPTY_LOGIN:{
                mEmailView.setError(getString(R.string.login_field_required));
                return;
            }
            case EMPTY_PASSWORD:{
                mPasswordView.setError(getString(R.string.login_field_required));
                return;
            }
            case OK:{
                mPasswordView.setError(null);
            }
        }

        managePrefs(login, password);
        AppAuthorizer appAuthorizer = new AppAuthorizer(this);
        appAuthorizer.signIn(login, password);
        showProgressDialog();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        String email = mEmailView.getText().toString().trim();
        String password = mPasswordView.getText().toString().trim();
        managePrefs(email, password);
    }

    public void managePrefs(String email, String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_OLD_CHANGE_PASS, password);
        if (mRememberMeView.isChecked()) {
            editor.putString(KEY_USER_EMAIL_OR_PHONE, email);
            editor.putString(KEY_PASS, password);
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

    public void initForgotPasswordCountTimer() {
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
                mForgotPasswordView.setTextColor(Color.CYAN);
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
                                    Toast.LENGTH_LONG).show();
                        }
                    });
            mForgotPasswordView.setEnabled(false);
            mForgotPasswordView.setTextColor(Color.GRAY);
            if (countDownTimer == null) {
                initForgotPasswordCountTimer();
            }
            countDownTimer.start();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default: {
                onBackPressed();
                return true;
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra(APP_BACK_RETURN_FLAG, true);
        startActivity(intent);
    }

}

