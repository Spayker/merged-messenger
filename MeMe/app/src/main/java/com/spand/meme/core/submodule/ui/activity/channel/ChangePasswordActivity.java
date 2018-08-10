package com.spand.meme.core.submodule.ui.activity.channel;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.spand.meme.R;

/**
 *  A class handler is linked to appropriate activity xml file and contains backend logic.
 **/
public class ChangePasswordActivity extends AppCompatActivity implements View.OnClickListener{

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private EditText mOldPasswordView;
    private EditText mNewPasswordView;
    private EditText mNewPasswordConfirmView;

    private static final String PREF_NAME = "prefs";
    private static final String KEY_PASS = "password";

    /**
     *  Perform initialization of all fragments of current activity.
     *  @param savedInstanceState an instance of Bundle instance
     *                            (A mapping from String keys to various Parcelable values)
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        mOldPasswordView = findViewById(R.id.old_password);
        mNewPasswordView = findViewById(R.id.new_password);
        mNewPasswordConfirmView = findViewById(R.id.confirm_password);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    public void onClick(View v) {
        if(isPasswordValid(mNewPasswordView.getText().toString().trim())){
            editor.putString(KEY_PASS, mNewPasswordView.getText().toString().trim());
            editor.apply();
        } else {

        }
    }

    /**
     *  Returns true or false if a password is valid or not.
     *  @param password a String object which must be validated
     *  @return a boolean value. Depends on validation result
     **/
    private boolean isPasswordValid(String password) {
        return (password.length() > 4 && !password.isEmpty());
    }
}
