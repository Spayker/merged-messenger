package com.spand.meme.core.ui.activity.main;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.spand.meme.R;
import com.spand.meme.core.data.database.FireBaseDBInitializer;
import com.spand.meme.core.logic.AUTH_WAY;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.spand.meme.core.logic.AUTH_WAY.EMAIL;
import static com.spand.meme.core.logic.AUTH_WAY.PHONE;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_OLD_CHANGE_PASS;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_PASS;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_USER_EMAIL_OR_PHONE;
import static com.spand.meme.core.logic.starter.SettingsConstants.PREF_NAME;
import static com.spand.meme.core.logic.starter.Starter.REGISTRATOR;
import static com.spand.meme.core.logic.starter.Starter.START_TYPE;
import static com.spand.meme.core.logic.starter.Starter.USERNAME;

/**
 * A Register screen that offers a registration procedure via email/password.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    @SuppressLint("StaticFieldLeak")
    private static RegisterActivity instance;

    public static RegisterActivity getInstance(){
        return instance;
    }

    // UI references.
    private EditText mNameView;
    private AutoCompleteTextView mEmailOrPhoneView;
    private EditText mPasswordView;
    private EditText mPasswordConfirmView;

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = RegisterActivity.class.getSimpleName();

    // Firebase related fields
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestoreDB;
    private static String uniqueIdentifier;
    private static final String UNIQUE_ID = "UNIQUE_ID";

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks callbacks;

    private SharedPreferences sharedPreferences;

    private AUTH_WAY authWay;

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
        setContentView(R.layout.activity_register);
        instance = this;
        // Views
        mNameView = findViewById(R.id.register_form_name);
        mEmailOrPhoneView = findViewById(R.id.register_form_email_phone_number);
        mPasswordView = findViewById(R.id.register_form_password);
        mPasswordConfirmView = findViewById(R.id.register_form_password_confirm);

        mEmailOrPhoneView.setText("+48577215683");
        // Buttons
        findViewById(R.id.email_sign_up_button).setOnClickListener(this);

        // auth init
        mAuth = FirebaseAuth.getInstance();
        firestoreDB = FirebaseFirestore.getInstance();
        getInstallationIdentifier();

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        callbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                Log.d(TAG, "verification completed" + credential);
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Log.w(TAG, "verification failed", e);
                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(RegisterActivity.this,
                            "Trying too many timeS",
                            Toast.LENGTH_SHORT).show();
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    Toast.makeText(RegisterActivity.this,
                            "Trying too many timeS",
                            Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
                Log.d(TAG, "code sent " + verificationId);
                addVerificationDataToFirestore(mEmailOrPhoneView.getText().toString(), verificationId);
            }
        };
    }

    private void addVerificationDataToFirestore(String phone, String verificationId) {
        Map verifyMap = new HashMap();
        verifyMap.put("phone", phone);
        verifyMap.put("verificationId", verificationId);
        verifyMap.put("timestamp",System.currentTimeMillis());

        firestoreDB.collection("phoneAuth").document(uniqueIdentifier)
                .set(verifyMap)
                .addOnSuccessListener((OnSuccessListener<DocumentReference>) documentReference -> Log.d(TAG, "phone auth info added to db "))
                .addOnFailureListener((OnFailureListener) e -> Log.w(TAG, "Error adding phone auth info", e));
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "code verified signIn successful");
                    } else {
                        Log.w(TAG, "code verification failed", task.getException());
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
                        callbacks);
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

    public synchronized String getInstallationIdentifier() {
        if (uniqueIdentifier == null) {
            SharedPreferences sharedPrefs = this.getSharedPreferences(
                    UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueIdentifier = sharedPrefs.getString(UNIQUE_ID, null);
            if (uniqueIdentifier == null) {
                uniqueIdentifier = UUID.randomUUID().toString();
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.putString(UNIQUE_ID, uniqueIdentifier);
                editor.commit();
            }
        }
        return uniqueIdentifier;
    }

    private void getVerificationDataFromFirestoreAndVerify(final String code) {
        firestoreDB.collection("phoneAuth").document(uniqueIdentifier)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot ds = task.getResult();
                        if(ds.exists()){
                            // disableSendCodeButton(ds.getLong("timestamp"));
                            if(code != null){
                                createCredentialSignIn(ds.getString("verificationId"),
                                        code);
                            }else{
                                verifyPhoneNumber(ds.getString("phone"));
                            }
                        }else{
                            //showSendCodeButton();
                            Log.d(TAG, "Code hasn't been sent yet");
                        }

                    } else {
                        Log.d(TAG, "Error getting document: ", task.getException());
                    }
                });
    }

    private void verifyPhoneNumber(String phno){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phno, 70,
                TimeUnit.SECONDS, this, callbacks);
    }

    private void createCredentialSignIn(String verificationId, String verifyCode) {
        PhoneAuthCredential credential = PhoneAuthProvider.
                getCredential(verificationId, verifyCode);
        signInWithPhoneAuthCredential(credential);
    }
}