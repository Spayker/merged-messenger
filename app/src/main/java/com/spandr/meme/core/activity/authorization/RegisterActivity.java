package com.spandr.meme.core.activity.authorization;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.authorization.logic.AppAuthorizer;
import com.spandr.meme.core.activity.authorization.logic.data.User;
import com.spandr.meme.core.activity.authorization.logic.firebase.exception.AppFireBaseAuthException;
import com.spandr.meme.core.activity.authorization.logic.validator.FormValidator;
import com.spandr.meme.core.activity.authorization.logic.validator.RegisterFormValidator;
import com.spandr.meme.core.activity.intro.WelcomeActivity;

import static com.spandr.meme.core.activity.main.logic.LogicConstants.APP_BACK_RETURN_FLAG;

/**
* A Register screen that offers a registration procedure via email/password.
*
* @author  Spayker
* @version 1.0
* @since   3/6/2019
*/
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("StaticFieldLeak")
    private static RegisterActivity instance;

    public static RegisterActivity getInstance() {
        return instance;
    }

    // UI references.
    private EditText mNameView;
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mPasswordConfirmView;
    private Button mRegister;
    private ProgressBar progressBar;
    private FormValidator formValidator;

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = RegisterActivity.class.getSimpleName();

    /**
     * Perform initialization of all fragments of current activity.
     *
     * @param savedInstanceState an instance of Bundle instance
     *                           (A mapping from String keys to various Parcelable values)
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle(R.string.register_title_activity);
        instance = this;
        // Views
        mNameView = findViewById(R.id.register_form_name);
        mEmailView = findViewById(R.id.register_form_email);
        mPasswordView = findViewById(R.id.register_form_password);
        mPasswordConfirmView = findViewById(R.id.register_form_password_confirm);
        mRegister = findViewById(R.id.email_sign_up_button);
        CheckBox mAgreement = findViewById(R.id.agreement);

        // Buttons
        mRegister.setEnabled(false);
        findViewById(R.id.email_sign_up_button).setOnClickListener(this);

        // Checkbox
        mAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        mAgreement.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (buttonView.isChecked()) {
                        mRegister.setEnabled(true);
                    } else {
                        mRegister.setEnabled(false);
                    }
                }
        );

        formValidator = new RegisterFormValidator(this);

        progressBar = findViewById(R.id.register_progressBar_cyclic);
        progressBar.setVisibility(View.INVISIBLE);
    }

    /**
     * Called after onCreate(Bundle) — or after onRestart() when the activity had been stopped,
     * but is now again being displayed to the user. It will be followed by onResume().
     **/
    @Override
    public void onStart() {
        super.onStart();
        hideProgressDialog();
    }

    /**
     * Performs a Sign Up procedure with FireBase module.
     *
     * @param email           a String object which will be used by FireBase module during SignUp
     * @param password        a String object which will be used by FireBase module during SignUp
     * @param confirmPassword a String object which will be used during validation form
     **/
    private void signUp(String email, String name, String password, String confirmPassword) throws AppFireBaseAuthException {
        Log.d(TAG, getString(R.string.register_log_sign_up) + email);

        switch(formValidator.validateInputForm(email, password, confirmPassword)){
            case EMPTY_LOGIN:{
                mEmailView.setError(getString(R.string.register_field_required));
                return;
            }
            case EMAIL_INCORRECT_FORMAT:{
                mEmailView.setError(getString(R.string.register_email_field_incorrect_format));
                return;
            }
            case EMPTY_PASSWORD:{
                mPasswordView.setError(getString(R.string.login_field_required));
                return;
            }
            case EMPTY_CONFIRM_PASSWORD:{
                mPasswordView.setError(getString(R.string.login_field_required));
                return;
            }
            case NON_EQUAL_PASSWORDS:{
                mPasswordView.setError(getString(R.string.register_error_not_equal_password));
                mPasswordConfirmView.setError(getString(R.string.register_error_not_equal_password));
                return;
            }
            case SHORT_PASSWORD:{
                mPasswordConfirmView.setError(getString(R.string.register_error_invalid_password));
                return;
            }
            case UNSUPPORTED_EMAIL_DOMAIN:{
                Toast.makeText(this, getString(R.string.register_check_email_unsupported_domain),
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }

        showProgressDialog();
        User user = new User(name, email, password);
        AppAuthorizer appAuthorizer = new AppAuthorizer(this, user);
        appAuthorizer.signUp();
        Toast.makeText(this, getString(R.string.register_check_email),
                Toast.LENGTH_SHORT).show();
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
        if (i == R.id.email_sign_up_button) {
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
                String email = mEmailView.getText().toString();
                String password = mPasswordView.getText().toString();
                String confirmPassword = mPasswordConfirmView.getText().toString();
                String name = mNameView.getText().toString();

                signUp(email, name, password, confirmPassword);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
        Intent intent = new Intent(this, WelcomeActivity.class);
        intent.putExtra(APP_BACK_RETURN_FLAG, true);
        startActivity(intent);
    }

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
}