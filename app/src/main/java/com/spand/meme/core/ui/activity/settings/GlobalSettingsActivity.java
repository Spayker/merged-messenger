package com.spand.meme.core.ui.activity.settings;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;

import com.spand.meme.R;
import com.spand.meme.core.ui.activity.main.MainActivity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.spand.meme.core.logic.starter.SettingsConstants.APP_SUPPORTED_LANGUAGES;
import static com.spand.meme.core.ui.activity.ActivityConstants.EMPTY_STRING;

/**
 * A class handler is linked to appropriate activity xml file and contains backend logic.
 **/
public class GlobalSettingsActivity extends AppCompatActivity {

    // tested with android profiler on possible memory leaks. Results shows no leaks at all
    // static field of activity can be used here
    @SuppressLint("StaticFieldLeak")
    private static AppCompatActivity settingsActivityInstance;

    private final static int MELODY_NOTIFICATION_INDEX = 1;

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
     * Perform initialization of all fragments of current activity.
     *
     * @param savedInstanceState an instance of Bundle instance
     *                           (A mapping from String keys to various Parcelable values)
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // load settings fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
        settingsActivityInstance = this;
    }

    /**
     * A static class which adds preference fragment to current activity.
     **/
    public static class MainPreferenceFragment extends PreferenceFragment {
        /**
         * Perform initialization of all fragments of current activity.
         *
         * @param savedInstanceState an instance of Bundle instance
         *                           (A mapping from String keys to various Parcelable values)
         **/
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.pref_global);

            // notification preference change listener
            ListPreference notificationPreference = (ListPreference) findPreference(getString(R.string.global_settings_key_notification_list_preference));
            initNotificationPreference(notificationPreference);

            // melody preference change listener
            ListPreference melodyPreference = (ListPreference) findPreference(getString(R.string.global_settings_key_melody_list_preference));
            initMelodyPreference(melodyPreference, notificationPreference);

            // language preference change listener
            ListPreference languagePreference = (ListPreference) findPreference(getString(R.string.global_settings_key_language_list_preference));
            initLanguagePreference(languagePreference);

            Preference changePasswordButton = findPreference(getString(R.string.global_settings_pref_change_password_button));
            changePasswordButton.setOnPreferenceClickListener(preference -> {
                //code for what you want it to do
                Intent intent = new Intent(settingsActivityInstance, ChangePasswordActivity.class);
                startActivity(intent);
                return true;
            });

            Preference removeAccountButton = findPreference(getString(R.string.global_settings_pref_remove_account_button));
            removeAccountButton.setOnPreferenceClickListener(preference -> {
                //code for what you want it to do
                Intent intent = new Intent(settingsActivityInstance, RemoveAccountActivity.class);
                startActivity(intent);
                return true;
            });
        }

        private void initNotificationPreference(ListPreference notificationPreference) {
            notificationPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                String stringValue = newValue.toString();
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);
                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);
                ListPreference melodyPreference = (ListPreference) findPreference(getString(R.string.global_settings_key_melody_list_preference));
                if (index == MELODY_NOTIFICATION_INDEX) {
                    melodyPreference.setEnabled(true);
                } else {
                    melodyPreference.setEnabled(false);
                }
                return true;
            });
            setNewValueOnPreferenceChange(notificationPreference);
        }

        private void initMelodyPreference(ListPreference melodyPreference, ListPreference notificationPreference) {
            RingtoneManager manager = new RingtoneManager(settingsActivityInstance);
            manager.setType(RingtoneManager.TYPE_RINGTONE);
            Cursor cursor = manager.getCursor();

            Map<String, String> mapItems = new HashMap<>();
            while (cursor.moveToNext()) {
                String notificationTitle = cursor.getString(RingtoneManager.TITLE_COLUMN_INDEX);
                String notificationUri = cursor.getString(RingtoneManager.URI_COLUMN_INDEX);
                mapItems.put(notificationTitle, notificationUri);
            }
            CharSequence[] melodyEntries = mapItems.keySet().toArray(new CharSequence[mapItems.size()]);
            CharSequence[] melodyValues = mapItems.values().toArray(new CharSequence[mapItems.size()]);
            melodyPreference.setEntries(melodyEntries);
            melodyPreference.setEntryValues(melodyValues);
            melodyPreference.setValueIndex(0);

            melodyPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                String stringValue = newValue.toString();
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);
                return true;
            });
            setNewValueOnPreferenceChange(melodyPreference);

            Integer currentValueIndex = notificationPreference.findIndexOfValue(notificationPreference.getValue());
            if (currentValueIndex.equals(MELODY_NOTIFICATION_INDEX)) {
                melodyPreference.setEnabled(true);
            } else {
                melodyPreference.setEnabled(false);
            }
        }

        private void initLanguagePreference(ListPreference languagePreference) {
            String language = Locale.getDefault().getDisplayLanguage();
            String[] languageListArray = getResources().getStringArray(R.array.global_settings_language_list);
            for (int i = 0; i < languageListArray.length; i++) {
                if (languageListArray[i].equalsIgnoreCase(language)) {
                    languagePreference.setValueIndex(i);
                    break;
                }
            }
            languagePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                List<String> languages = Arrays.asList(getResources().getStringArray(R.array.global_settings_language_list));
                updateBaseContextLocale(settingsActivityInstance, APP_SUPPORTED_LANGUAGES.get(languages.get(Integer.valueOf((String)newValue))));

                String stringValue = newValue.toString();
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);
                settingsActivityInstance.recreate();
                return true;
            });
            setNewValueOnPreferenceChange(languagePreference);
        }

        private void updateBaseContextLocale(Context context, String language) {
            Locale locale = new Locale(language);
            Locale.setDefault(locale);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                updateResourcesLocale(context, locale);
            }

            updateResourcesLocaleLegacy(context, locale);
        }

        @TargetApi(Build.VERSION_CODES.N)
        private void updateResourcesLocale(Context context, Locale locale) {
            Configuration configuration = context.getResources().getConfiguration();
            configuration.setLocale(locale);
        }

        @SuppressWarnings("deprecation")
        private void updateResourcesLocaleLegacy(Context context, Locale locale) {
            Resources resources = context.getResources();
            Configuration configuration = resources.getConfiguration();
            configuration.locale = locale;
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
    }

    /**
     * Binds a global preference to String value.
     *
     * @param preference is an instance Preference class which will be placed inside of activity
     **/
    private static void setNewValueOnPreferenceChange(Preference preference) {
        sBindPreferenceSummaryToValueListener.onPreferenceChange(preference,
                PreferenceManager
                        .getDefaultSharedPreferences(preference.getContext())
                        .getString(preference.getKey(), EMPTY_STRING));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}