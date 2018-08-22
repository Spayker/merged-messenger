package com.spand.meme.core.submodule.ui.activity.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.spand.meme.R;

import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.KEY_PASS;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.KEY_REMEMBER;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.KEY_USER_EMAIL;

/**
 *  A class handler is linked to appropriate activity xml file and contains backend logic.
 **/
public class ResetSettingsActivity extends AppCompatActivity implements View.OnClickListener {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = ResetSettingsActivity.class.getSimpleName();

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private static final String PREF_NAME = "prefs";
    private static final String KEY_OLD_CHANGE_PASS = "oldChangePassword";

    /**
     *  Perform initialization of all fragments of current activity.
     *  @param savedInstanceState an instance of Bundle instance
     *                            (A mapping from String keys to various Parcelable values)
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_settings);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }


    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.yes_reset_settings_button) {
            resetSettings();
        }
    }

    private void resetSettings(){
        editor.putString(KEY_OLD_CHANGE_PASS, null);
        editor.putString(KEY_USER_EMAIL, null);
        editor.putString(KEY_PASS, null);
        editor.putBoolean(KEY_REMEMBER, false);
        editor.apply();
        editor.commit();
        Log.d(TAG, getString(R.string.log_settings_dropped_successfully));
    }

    public void onNoClick(View view){
        finish();
    }

}
