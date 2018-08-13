package com.spand.meme.core.submodule.ui.activity.settings;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;

import com.spand.meme.R;

public class EditChannelsActivity extends AppCompatActivity {

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
            addPreferencesFromResource(R.xml.pref_edit_channels);

            Preference facebookSwitcher  = findPreference(getString(R.string.key_facebook_switcher));
            bindGlobalPreferenceToBooleanValue(facebookSwitcher);
            facebookSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean switched = ((SwitchPreference) preference).isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            Preference linkedinSwitcher  = findPreference(getString(R.string.key_linkedin_switcher));
            bindGlobalPreferenceToBooleanValue(linkedinSwitcher);
            linkedinSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean switched = ((SwitchPreference) preference).isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            Preference vkontakteSwitcher  = findPreference(getString(R.string.key_vkontakte_switcher));
            bindGlobalPreferenceToBooleanValue(vkontakteSwitcher);
            vkontakteSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean switched = ((SwitchPreference) preference).isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            Preference twitterSwitcher  = findPreference(getString(R.string.key_twitter_switcher));
            bindGlobalPreferenceToBooleanValue(twitterSwitcher);
            twitterSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean switched = ((SwitchPreference) preference).isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            Preference tumblrSwitcher  = findPreference(getString(R.string.key_tumblr_switcher));
            bindGlobalPreferenceToBooleanValue(tumblrSwitcher);
            tumblrSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean switched = ((SwitchPreference) preference).isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            Preference instagramSwitcher  = findPreference(getString(R.string.key_instagram_switcher));
            bindGlobalPreferenceToBooleanValue(instagramSwitcher);
            instagramSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean switched = ((SwitchPreference) preference).isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            Preference okSwitcher  = findPreference(getString(R.string.key_odnoklassniki_switcher));
            bindGlobalPreferenceToBooleanValue(okSwitcher);
            okSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean switched = ((SwitchPreference) preference).isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            Preference telegramSwitcher  = findPreference(getString(R.string.key_telegram_switcher));
            bindGlobalPreferenceToBooleanValue(telegramSwitcher);
            telegramSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean switched = ((SwitchPreference) preference).isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            Preference discordSwitcher  = findPreference(getString(R.string.key_discord_switcher));
            bindGlobalPreferenceToBooleanValue(discordSwitcher);
            discordSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean switched = ((SwitchPreference) preference).isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            Preference skypeSwitcher  = findPreference(getString(R.string.key_skype_switcher));
            bindGlobalPreferenceToBooleanValue(skypeSwitcher);
            skypeSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean switched = ((SwitchPreference) preference).isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            Preference icqSwitcher  = findPreference(getString(R.string.key_icq_switcher));
            bindGlobalPreferenceToBooleanValue(icqSwitcher);
            icqSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean switched = ((SwitchPreference) preference).isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            Preference gmailSwitcher  = findPreference(getString(R.string.key_gmail_switcher));
            bindGlobalPreferenceToBooleanValue(gmailSwitcher);
            gmailSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean switched = ((SwitchPreference) preference).isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            Preference mailruSwitcher  = findPreference(getString(R.string.key_mailru_switcher));
            bindGlobalPreferenceToBooleanValue(mailruSwitcher);
            mailruSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                boolean switched = ((SwitchPreference) preference).isChecked();
                preference.setSummary(!switched ? switcherOn : switcherOff);
                return true;
            });

            Preference youtubeSwitcher  = findPreference(getString(R.string.key_youtube_switcher));
            bindGlobalPreferenceToBooleanValue(youtubeSwitcher);
            youtubeSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
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
