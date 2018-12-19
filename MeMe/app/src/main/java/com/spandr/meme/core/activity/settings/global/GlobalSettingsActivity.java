package com.spandr.meme.core.activity.settings.global;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.main.MainActivity;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.APP_SUPPORTED_LANGUAGES;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.EN;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_CHANNEL_ORDER;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_CURRENT_APP_LANGUAGE;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.common.ActivityConstants.EMPTY_STRING;
import static com.spandr.meme.core.common.util.ActivityUtils.updateResourcesLocale;
import static com.spandr.meme.core.common.util.ActivityUtils.updateResourcesLocaleLegacy;

/**
 * A class handler is linked to appropriate activity xml file and contains backend logic.
 **/
public class GlobalSettingsActivity extends AppCompatActivity {

    // tested with android profiler on possible memory leaks. Results shows no leaks at all
    // static field of activity can be used here
    @SuppressLint("StaticFieldLeak")
    private static AppCompatActivity settingsActivityInstance;

    private static SharedPreferences sharedPreferences;

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
        setTitle(R.string.global_setting_title);

        // load settings fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content, new MainPreferenceFragment()).commit();
        settingsActivityInstance = this;
        sharedPreferences = settingsActivityInstance.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
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

            // language preference change listener
            ListPreference languagePreference = (ListPreference) findPreference(getString(R.string.global_settings_key_language_list_preference));
            initLanguagePreference(languagePreference);

            Preference changePasswordButton = findPreference(getString(R.string.global_settings_pref_change_password_button));
            changePasswordButton.setOnPreferenceClickListener(preference -> {
                Intent intent = new Intent(settingsActivityInstance, ChangePasswordActivity.class);
                startActivity(intent);
                return true;
            });

            Preference removeAccountButton = findPreference(getString(R.string.global_settings_pref_remove_account_button));
            removeAccountButton.setOnPreferenceClickListener(preference -> {
                Intent intent = new Intent(settingsActivityInstance, RemoveAccountActivity.class);
                startActivity(intent);
                return true;
            });
        }

        private void initLanguagePreference(ListPreference languagePreference) {

            String language = sharedPreferences.getString(KEY_CURRENT_APP_LANGUAGE, Locale.getDefault().getDisplayLanguage());
            String[] languageListArray = getResources().getStringArray(R.array.global_settings_language_list);
            for (int i = 0; i < languageListArray.length; i++) {
                if (languageListArray[i].equalsIgnoreCase(language)) {
                    languagePreference.setValueIndex(i);
                    break;
                }
            }
            languagePreference.setOnPreferenceChangeListener((preference, newValue) -> {
                List<String> languages = Arrays.asList(getResources().getStringArray(R.array.global_settings_language_list));
                updateBaseContextLocale(settingsActivityInstance, languages.get(Integer.valueOf((String) newValue)));

                String stringValue = newValue.toString();
                ListPreference listPreference = (ListPreference) preference;
                int index = listPreference.findIndexOfValue(stringValue);

                preference.setSummary(
                        index >= 0
                                ? listPreference.getEntries()[index]
                                : null);


                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(KEY_CHANNEL_ORDER, null);
                editor.apply();
                editor.commit();

                settingsActivityInstance.startActivity(new Intent(settingsActivityInstance, MainActivity.class));
                settingsActivityInstance.finish();
                Toast.makeText(settingsActivityInstance, getString(R.string.global_settings_language_changed),
                        Toast.LENGTH_SHORT).show();
                return true;
            });
            setNewValueOnPreferenceChange(languagePreference);
        }

        private void updateBaseContextLocale(Context context, String language) {
            String shortLanguage = APP_SUPPORTED_LANGUAGES.get(language);
            if(shortLanguage == null){
                shortLanguage = EN;
            }
            Locale locale = new Locale(shortLanguage);
            Locale.setDefault(locale);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                updateResourcesLocale(context, locale);
            }

            updateResourcesLocaleLegacy(context, locale);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(KEY_CURRENT_APP_LANGUAGE, language);
            editor.apply();
            editor.commit();
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