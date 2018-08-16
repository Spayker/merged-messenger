package com.spand.meme.core.submodule.ui.activity.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;

import com.spand.meme.R;

import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.PREF_NAME;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_DISCORD;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_FACEBOOK;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_GMAIL;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_ICQ;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_INSTAGRAM;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_LINKED_IN;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_MAIL_RU;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_ODNOKLASSNIKI;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_SKYPE;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_TELEGRAM;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_TUMBLR;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_TWITTER;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_VKONTAKTE;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_YOUTUBE;

public class EditChannelsActivity extends AppCompatActivity {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = EditChannelsActivity.class.getSimpleName();

    // secondary fields
    private static String switcherOn;
    private static String switcherOff;

    private static SharedPreferences sharedPreferences;

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
        switcherOn = getString(R.string.switcher_on);
        switcherOff = getString(R.string.switcher_off);

        sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
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
            addPreferencesFromResource(R.xml.pref_edit_channels);

            SwitchPreference facebookSwitcher  = (SwitchPreference) findPreference(getString(R.string.key_facebook_switcher));
            facebookSwitcher.setChecked(sharedPreferences.getBoolean(KEY_FACEBOOK, false));
            bindGlobalPreferenceToBooleanValue(facebookSwitcher);
            facebookSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                SwitchPreference switchPreference = ((SwitchPreference) preference);

                boolean switched = switchPreference.isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                // handle
                return true;
            });

            SwitchPreference linkedinSwitcher  = (SwitchPreference) findPreference(getString(R.string.key_linkedin_switcher));
            linkedinSwitcher.setChecked(sharedPreferences.getBoolean(KEY_LINKED_IN, false));
            bindGlobalPreferenceToBooleanValue(linkedinSwitcher);
            linkedinSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                SwitchPreference switchPreference = ((SwitchPreference) preference);
                boolean switched = switchPreference.isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            SwitchPreference vkontakteSwitcher  = (SwitchPreference) findPreference(getString(R.string.key_vkontakte_switcher));
            vkontakteSwitcher.setChecked(sharedPreferences.getBoolean(KEY_VKONTAKTE, false));
            bindGlobalPreferenceToBooleanValue(vkontakteSwitcher);
            vkontakteSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                SwitchPreference switchPreference = ((SwitchPreference) preference);
                boolean switched = switchPreference.isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            SwitchPreference twitterSwitcher  = (SwitchPreference) findPreference(getString(R.string.key_twitter_switcher));
            twitterSwitcher.setChecked(sharedPreferences.getBoolean(KEY_TWITTER, false));
            bindGlobalPreferenceToBooleanValue(twitterSwitcher);
            twitterSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                SwitchPreference switchPreference = ((SwitchPreference) preference);
                boolean switched = switchPreference.isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            SwitchPreference tumblrSwitcher  = (SwitchPreference) findPreference(getString(R.string.key_tumblr_switcher));
            tumblrSwitcher.setChecked(sharedPreferences.getBoolean(KEY_TUMBLR, false));
            bindGlobalPreferenceToBooleanValue(tumblrSwitcher);
            tumblrSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                SwitchPreference switchPreference = ((SwitchPreference) preference);
                boolean switched = switchPreference.isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            SwitchPreference instagramSwitcher  = (SwitchPreference) findPreference(getString(R.string.key_instagram_switcher));
            instagramSwitcher.setChecked(sharedPreferences.getBoolean(KEY_INSTAGRAM, false));
            bindGlobalPreferenceToBooleanValue(instagramSwitcher);
            instagramSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                SwitchPreference switchPreference = ((SwitchPreference) preference);
                boolean switched = switchPreference.isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            SwitchPreference okSwitcher  = (SwitchPreference) findPreference(getString(R.string.key_odnoklassniki_switcher));
            okSwitcher.setChecked(sharedPreferences.getBoolean(KEY_ODNOKLASSNIKI, false));
            bindGlobalPreferenceToBooleanValue(okSwitcher);
            okSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                SwitchPreference switchPreference = ((SwitchPreference) preference);
                boolean switched = switchPreference.isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            SwitchPreference telegramSwitcher  = (SwitchPreference) findPreference(getString(R.string.key_telegram_switcher));
            telegramSwitcher.setChecked(sharedPreferences.getBoolean(KEY_TELEGRAM, false));
            bindGlobalPreferenceToBooleanValue(telegramSwitcher);
            telegramSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                SwitchPreference switchPreference = ((SwitchPreference) preference);
                boolean switched = switchPreference.isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            SwitchPreference discordSwitcher  = (SwitchPreference) findPreference(getString(R.string.key_discord_switcher));
            discordSwitcher.setChecked(sharedPreferences.getBoolean(KEY_DISCORD, false));
            bindGlobalPreferenceToBooleanValue(discordSwitcher);
            discordSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                SwitchPreference switchPreference = ((SwitchPreference) preference);
                boolean switched = switchPreference.isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            SwitchPreference skypeSwitcher  = (SwitchPreference) findPreference(getString(R.string.key_skype_switcher));
            skypeSwitcher.setChecked(sharedPreferences.getBoolean(KEY_SKYPE, false));
            bindGlobalPreferenceToBooleanValue(skypeSwitcher);
            skypeSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                SwitchPreference switchPreference = ((SwitchPreference) preference);
                boolean switched = switchPreference.isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            SwitchPreference icqSwitcher  = (SwitchPreference) findPreference(getString(R.string.key_icq_switcher));
            icqSwitcher.setChecked(sharedPreferences.getBoolean(KEY_ICQ, false));
            bindGlobalPreferenceToBooleanValue(icqSwitcher);
            icqSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                SwitchPreference switchPreference = ((SwitchPreference) preference);
                boolean switched = switchPreference.isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            SwitchPreference gmailSwitcher  = (SwitchPreference) findPreference(getString(R.string.key_gmail_switcher));
            gmailSwitcher.setChecked(sharedPreferences.getBoolean(KEY_GMAIL, false));
            bindGlobalPreferenceToBooleanValue(gmailSwitcher);
            gmailSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                SwitchPreference switchPreference = ((SwitchPreference) preference);
                boolean switched = switchPreference.isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            SwitchPreference mailruSwitcher  = (SwitchPreference) findPreference(getString(R.string.key_mailru_switcher));
            mailruSwitcher.setChecked(sharedPreferences.getBoolean(KEY_MAIL_RU, false));
            bindGlobalPreferenceToBooleanValue(mailruSwitcher);
            mailruSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                SwitchPreference switchPreference = ((SwitchPreference) preference);
                boolean switched = switchPreference.isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            SwitchPreference youtubeSwitcher  = (SwitchPreference)findPreference(getString(R.string.key_youtube_switcher));
            youtubeSwitcher.setChecked(sharedPreferences.getBoolean(KEY_YOUTUBE, false));
            bindGlobalPreferenceToBooleanValue(youtubeSwitcher);
            youtubeSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                SwitchPreference switchPreference = ((SwitchPreference) preference);
                boolean switched = switchPreference.isChecked();
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
                        .getBoolean(preference.getKey(), false));
    }

}
