package com.spand.meme.core.activity.settings;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.spand.meme.R;

/**
 *  A class handler is linked to appropriate activity xml file and contains backend logic.
 **/
public class CertainChannelSettingActivity extends AppCompatActivity {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = SettingsActivity.class.getSimpleName();

    /**
     *  A static class which adds preference fragment to current activity.
     **/
    public static class CertainChannelPreferenceFragment extends PreferenceFragment {
        /**
         *  Perform initialization of all fragments of current activity.
         *  @param savedInstanceState an instance of Bundle instance
         *                            (A mapping from String keys to various Parcelable values)
         **/
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_certain_channel);
            // notification preference change listener
            bindGlobalPreferenceToStringValue(findPreference(getString(R.string.key_font_size_list_preference)));
        }
    }

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
        getFragmentManager().beginTransaction().replace(android.R.id.content, new CertainChannelPreferenceFragment()).commit();
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
                        .getString(preference.getKey(), ""));
    }

}
