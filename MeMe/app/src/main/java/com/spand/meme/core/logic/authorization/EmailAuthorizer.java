package com.spand.meme.core.logic.authorization;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.FirebaseFirestore;
import com.spand.meme.R;
import com.spand.meme.core.data.database.FireBaseDBInitializer;
import com.spand.meme.core.ui.activity.main.MainActivity;
import com.spand.meme.core.ui.activity.main.RegisterActivity;

import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_OLD_CHANGE_PASS;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_PASS;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_USER_EMAIL_OR_PHONE;
import static com.spand.meme.core.logic.starter.SettingsConstants.PREF_NAME;
import static com.spand.meme.core.logic.starter.Starter.REGISTRATOR;
import static com.spand.meme.core.logic.starter.Starter.START_TYPE;
import static com.spand.meme.core.logic.starter.Starter.USERNAME;

public class EmailAuthorizer  extends Authorizer{

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = EmailAuthorizer.class.getSimpleName();

    private SharedPreferences sharedPreferences;

    private Activity currentActivity;
    private String emailAddress;
    private String name;
    private String password;

    @SuppressLint("StaticFieldLeak")
    private static EmailAuthorizer instance;

    private EmailAuthorizer(Activity currentActivity, String name, String phoneNumber, String password) {
        this.currentActivity = currentActivity;
        this.emailAddress = phoneNumber;
        this.name = name;
        this.password = password;

        // auth init
        mAuth = FirebaseAuth.getInstance();
        firestoreDB = FirebaseFirestore.getInstance();
        sharedPreferences = currentActivity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static EmailAuthorizer init(Activity activity, String name, String phoneNumber, String password) {
        if(instance == null){
            instance = new EmailAuthorizer(activity, name, phoneNumber, password);
        }
        return  instance;
    }

    @Override
    public void verify() {
        mAuth.createUserWithEmailAndPassword(emailAddress, password)
                .addOnCompleteListener(currentActivity, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, currentActivity.getString(R.string.register_log_create_user_with_email_success));
                        updateUI();
                        finishSingUpActivity();
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, currentActivity.getString(R.string.register_log_create_user_with_email_failure), task.getException());
                        Toast.makeText(currentActivity, currentActivity.getString(R.string.register_error_auth_failed),
                                Toast.LENGTH_SHORT).show();
                        updateUI();
                    }
                    hideProgressDialog();
                });
    }

}
