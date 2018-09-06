package com.spand.meme.core.ui.activity.main;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.auth.PhoneAuthCredential;
import com.spand.meme.R;
import com.spand.meme.core.data.database.FireBaseDBInitializer;
import com.spand.meme.core.logic.AUTH_WAY;

import java.util.concurrent.TimeUnit;

import static com.spand.meme.core.logic.AUTH_WAY.EMAIL;
import static com.spand.meme.core.logic.AUTH_WAY.PHONE;
import static com.spand.meme.core.logic.starter.Starter.REGISTRATOR;
import static com.spand.meme.core.logic.starter.Starter.START_TYPE;
import static com.spand.meme.core.logic.starter.Starter.USERNAME;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_OLD_CHANGE_PASS;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_PASS;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_USER_EMAIL_OR_PHONE;
import static com.spand.meme.core.logic.starter.SettingsConstants.PREF_NAME;

/**
 * A Register screen that offers a registration procedure via email/password.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    // UI references.
    private EditText mNameView;
    private AutoCompleteTextView mEmailOrPhoneView;
    private EditText mPasswordView;
    private EditText mPasswordConfirmView;

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = RegisterActivity.class.getSimpleName();

    // Firebase related fields
    private FirebaseAuth mAuth;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private SharedPreferences sharedPreferences;

    private AUTH_WAY authWay;

    private boolean mVerificationInProgress = false;
    private String mVerificationId;

    @VisibleForTesting
    private ProgressDialog mProgressDialog;

    private static final int STATE_INITIALIZED = 1;
    private static final int STATE_CODE_SENT = 2;
    private static final int STATE_VERIFY_FAILED = 3;
    private static final int STATE_VERIFY_SUCCESS = 4;
    private static final int STATE_SIGNIN_FAILED = 5;
    private static final int STATE_SIGNIN_SUCCESS = 6;



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

        // Views
        mNameView = findViewById(R.id.register_form_name);
        mEmailOrPhoneView = findViewById(R.id.register_form_email_phone_number);
        mPasswordView = findViewById(R.id.register_form_password);
        mPasswordConfirmView = findViewById(R.id.register_form_password_confirm);

        // Buttons
        findViewById(R.id.email_sign_up_button).setOnClickListener(this);

        // auth init
        mAuth = FirebaseAuth.getInstance();

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                // [START_EXCLUDE silent]
                // Update the UI and attempt sign in with the phone credential
                // updateUI(STATE_VERIFY_SUCCESS, credential);
                // [END_EXCLUDE]
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    // mPhoneNumberField.setError("Invalid phone number.");
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

                // Show a message and update the UI
                // [START_EXCLUDE]
                //updateUI(STATE_VERIFY_FAILED);
                // [END_EXCLUDE]
            }

            @Override
            public void onCodeSent(String verificationId,
                                   PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

                // [START_EXCLUDE]
                // Update UI
                //updateUI(STATE_CODE_SENT);
                // [END_EXCLUDE]
            }
        };
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "signInWithCredential:success");

                        FirebaseUser user = task.getResult().getUser();
                        // [START_EXCLUDE]
                        // updateUI(STATE_SIGNIN_SUCCESS, user);
                        // [END_EXCLUDE]
                    } else {
                        // Sign in failed, display a message and update the UI
                        Log.w(TAG, "signInWithCredential:failure", task.getException());
                        if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            // The verification code entered was invalid
                            // [START_EXCLUDE silent]
                            // mVerificationField.setError("Invalid code.");
                            // [END_EXCLUDE]
                        }
                        // [START_EXCLUDE silent]
                        // Update UI
                        // updateUI(STATE_SIGNIN_FAILED);
                        // [END_EXCLUDE]
                    }
                });
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
     * Updates ui according to registration result.
     **/
    private void updateUI() {
        hideProgressDialog();
    }

    /**
     * Starts main activity of the application.
     **/
    public void finishSingUpActivity() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(mNameView.getText().toString()).build();
            user.updateProfile(profileUpdates);
        }

        // db init
        FireBaseDBInitializer.create().init();

        managePrefs();

        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(START_TYPE, REGISTRATOR);
        intent.putExtra(USERNAME, mNameView.getText().toString());
        startActivity(intent);
    }

    /**
     * Performs a Sign Up procedure with FireBase module.
     *
     * @param email           a String object which will be used by FireBase module during SignUp
     * @param password        a String object which will be used by FireBase module during SignUp
     * @param confirmPassword a String object which will be used during validation form
     **/
    private void signUp(String email, String password, String confirmPassword) {
        Log.d(TAG, getString(R.string.register_log_sign_up) + email);
        if (!validateForm()) {
            return;
        }

        showProgressDialog();
        // Check for a valid password confirmation, if the user entered one.
        if (TextUtils.isEmpty(confirmPassword)) {
            mPasswordConfirmView.setError(getString(R.string.register_error_empty_password));
        } else {
            if (!isPasswordValid(confirmPassword)) {
                mPasswordConfirmView.setError(getString(R.string.register_error_invalid_password));
            }
        }

        switch (authWay){
            case EMAIL:{
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this, task -> {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, getString(R.string.register_log_create_user_with_email_success));
                                updateUI();
                                finishSingUpActivity();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, getString(R.string.register_log_create_user_with_email_failure), task.getException());
                                Toast.makeText(RegisterActivity.this, getString(R.string.register_error_auth_failed),
                                        Toast.LENGTH_SHORT).show();
                                updateUI();
                            }
                            hideProgressDialog();
                        });
                break;
            }
            case PHONE:{
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        email,                      // Phone number to verify
                        60,                         // Timeout duration
                        TimeUnit.SECONDS,             // Unit of timeout
                        this,                  // Activity (for callback binding)
                        mCallbacks);
            }
        }


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
            try {
                signUp(mEmailOrPhoneView.getText().toString()
                        , mPasswordView.getText().toString()
                        , mPasswordConfirmView.getText().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Shows progress dialog while backend action is in progress.
     **/
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.register_checking));
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    /**
     * Hides progress dialog from screen.
     **/
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * Returns true or false if a password is valid or not.
     *
     * @param password a String object which must be validated
     * @return a boolean value. Depends on validation result
     **/
    private boolean isPasswordValid(String password) {
        return (!password.isEmpty() && password.length() > 4);
    }

    /**
     * Validates filled values in all fields of current activity on screen.
     **/
    private boolean validateForm() {
        String email = mEmailOrPhoneView.getText().toString();
        if (TextUtils.isEmpty(email)) {
            mEmailOrPhoneView.setError(getString(R.string.register_field_required));
            return false;
        } else {
            if (Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                authWay = EMAIL;
            } else if (Patterns.PHONE.matcher(email).matches()) {
                authWay = PHONE;
            }
        }

        String password = mPasswordView.getText().toString();
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.register_field_required));
            return false;
        }

        String confirmPassword = mPasswordConfirmView.getText().toString();
        if (TextUtils.isEmpty(confirmPassword)) {
            mPasswordView.setError(getString(R.string.register_field_required));
            return false;
        }
        return true;
    }

    private void managePrefs() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_OLD_CHANGE_PASS, mPasswordView.getText().toString().trim());
        editor.putString(KEY_USER_EMAIL_OR_PHONE, mEmailOrPhoneView.getText().toString().trim());
        editor.putString(KEY_PASS, mPasswordView.getText().toString().trim());
        editor.apply();
        editor.commit();
    }
}