package com.spand.meme.core.submodule.ui.activity.settings;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.spand.meme.R;

import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.EMPTY_STRING;

/**
 *  A class handler is linked to appropriate activity xml file and contains backend logic.
 **/
public class GlobalSettingsActivity extends AppCompatActivity {

    // tested with android profiler on possible memory leaks. Results shows no leaks at all
    // static field of activity can be used here
    @SuppressLint("StaticFieldLeak")
    private static AppCompatActivity settingsActivityInstance;

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = GlobalSettingsActivity.class.getSimpleName();

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = (preference, newValue) -> {

        if (preference instanceof ListPreference) {
            String stringValue = newValue.toString();
            // For list preferences, look up the correct display value in
            // the preference's 'entries' list.
            ListPreference listPreference = (ListPreference) preference;
            int index = listPreference.findIndexOfValue(stringValue);

            // Set the summary to reflect the new value.
            preference.setSummary(
                    index >= 0
                            ? listPreference.getEntries()[index]
                            : null);
        }
        return true;
    };

    /**
     *  Perform initialization of all fragments of current activity.
     *  @param savedInstanceState an instance of Bundle instance
     *                            (A mapping from String keys to various Parcelable values)
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // load settings fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
        settingsActivityInstance = this;
    }

    /**
     *  A static class which adds preference fragment to current activity.
     **/
    public static class MainPreferenceFragment extends PreferenceFragment {
        /**
         *  Perform initialization of all fragments of current activity.
         *  @param savedInstanceState an instance of Bundle instance
         *                            (A mapping from String keys to various Parcelable values)
         **/
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_global);

            // notification preference change listener
            bindGlobalPreferenceToStringValue(findPreference(getString(R.string.key_notification_list_preference)));

            // melody preference change listener
            bindGlobalPreferenceToStringValue(findPreference(getString(R.string.key_melody_list_preference)));

            // language preference change listener
            bindGlobalPreferenceToStringValue(findPreference(getString(R.string.key_language_list_preference)));

            Preference changePasswordButton = findPreference(getString(R.string.pref_change_password_button));
            changePasswordButton.setOnPreferenceClickListener(preference -> {
                //code for what you want it to do
                Intent intent = new Intent(settingsActivityInstance, ChangePasswordActivity.class);
                startActivity(intent);
                return true;
            });

            Preference resetSettingsButton = findPreference(getString(R.string.pref_reset_settings_button));
            resetSettingsButton.setOnPreferenceClickListener(preference -> {
                //code for what you want it to do
                Intent intent = new Intent(settingsActivityInstance, ResetSettingsActivity.class);
                startActivity(intent);
                return true;
            });

            Preference deactivateAccountButton = findPreference(getString(R.string.pref_deactivate_account_button));
            deactivateAccountButton.setOnPreferenceClickListener(preference -> {
                //code for what you want it to do
                Intent intent = new Intent(settingsActivityInstance, RemoveAccountActivity.class);
                startActivity(intent);
                return true;
            });
        }
    }

    /**
     *  Binds a global preference to String value.
     *  @param preference is an instance Preference class which will be placed inside of activity
     **/
    private static void bindGlobalPreferenceToStringValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
            sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                    PreferenceManager
                            .getDefaultSharedPreferences(preference.getContext())
                            .getString(preference.getKey(), EMPTY_STRING));
    }

}
