package com.spand.meme.core.submodule.ui.activity.settings;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.preference.Preference;

import com.spand.meme.R;

public class EditChannelsActivity extends AppCompatActivity {

    // tested with android profiler on possible memory leaks. Results shows no leaks at all
    // static field of activity can be used here
    @SuppressLint("StaticFieldLeak")
    private static AppCompatActivity channelSettingsActivityInstance;


    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = EditChannelsActivity.class.getSimpleName();

    // secondary fields
    private static String switcherOn;
    private static String switcherOff;

    /**
     * A preference value change listener that updates the preference's summary
     * to reflect its new value.
     */
    private static Preference.OnPreferenceChangeListener sBindPreferenceSummaryToValueListener = (preference, newValue) -> {

        if (preference instanceof SwitchPreference){
            // For all other preferences, set the summary to the value's
            // simple string representation.
            Boolean booleanValue = (Boolean) newValue;
            preference.setSummary(booleanValue ? switcherOn : switcherOff);
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
        getFragmentManager().beginTransaction().replace(android.R.id.content, new ChannelSettingsPreferenceFragment()).commit();
        channelSettingsActivityInstance = this;
        switcherOn = getString(R.string.switcher_on);
        switcherOff = getString(R.string.switcher_off);
    }

    /**
     *  A static class which adds preference fragment to current activity.
     **/
    public static class ChannelSettingsPreferenceFragment extends PreferenceFragment {
        /**
         *  Perform initialization of all fragments of current activity.
         *  @param savedInstanceState an instance of Bundle instance
         *                            (A mapping from String keys to various Parcelable values)
         **/
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_global);

            Preference autoRunSwitcher  = findPreference(getString(R.string.key_autorun_switch));
            bindGlobalPreferenceToBooleanValue(autoRunSwitcher);
            autoRunSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean switched = ((SwitchPreference) preference).isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });
        }
    }

    /**
     *  Binds a global preference to Boolean value.
     *  @param preference is an instance Preference class which will be placed inside of activity
     **/
    private static void bindGlobalPreferenceToBooleanValue(Preference preference) {
        preference.setOnPreferenceChangeListener(sBindPreferenceSummaryToValueListener);
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getBoolean(preference.getKey(), true));
    }

}
