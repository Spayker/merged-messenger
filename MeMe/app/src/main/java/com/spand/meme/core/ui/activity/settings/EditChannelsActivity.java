package com.spand.meme.core.ui.activity.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.spand.meme.R;
import com.spand.meme.core.data.memory.channel.Channel;
import com.spand.meme.core.logic.menu.main.builder.AlphaDynamicMenuBuilder;

import static com.spand.meme.core.data.memory.channel.ChannelManager.getChannelByName;
import static com.spand.meme.core.logic.menu.main.builder.AlphaDynamicMenuBuilder.getMenuBuilder;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_SLACK;
import static com.spand.meme.core.logic.starter.SettingsConstants.PREF_NAME;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_DISCORD;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_FACEBOOK;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_GMAIL;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_ICQ;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_INSTAGRAM;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_LINKED_IN;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_MAIL_RU;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_ODNOKLASSNIKI;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_SKYPE;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_TELEGRAM;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_TUMBLR;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_TWITTER;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_VKONTAKTE;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_YOUTUBE;

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
        switcherOn = getString(R.string.channel_setting_switcher_on);
        switcherOff = getString(R.string.channel_setting_switcher_off);

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

            SwitchPreference facebookSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_facebook_switcher));
            facebookSwitcher.setChecked(sharedPreferences.getBoolean(KEY_FACEBOOK, false));
            bindGlobalPreferenceToBooleanValue(facebookSwitcher);
            facebookSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(facebookSwitcher, KEY_FACEBOOK);
                return true;
            });

            SwitchPreference linkedinSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_linkedin_switcher));
            linkedinSwitcher.setChecked(sharedPreferences.getBoolean(KEY_LINKED_IN, false));
            bindGlobalPreferenceToBooleanValue(linkedinSwitcher);
            linkedinSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(linkedinSwitcher, KEY_LINKED_IN);
                return true;
            });

            SwitchPreference vkontakteSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_vkontakte_switcher));
            vkontakteSwitcher.setChecked(sharedPreferences.getBoolean(KEY_VKONTAKTE, false));
            bindGlobalPreferenceToBooleanValue(vkontakteSwitcher);
            vkontakteSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(vkontakteSwitcher, KEY_VKONTAKTE);
                return true;
            });

            SwitchPreference twitterSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_twitter_switcher));
            twitterSwitcher.setChecked(sharedPreferences.getBoolean(KEY_TWITTER, false));
            bindGlobalPreferenceToBooleanValue(twitterSwitcher);
            twitterSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(twitterSwitcher, KEY_TWITTER);
                return true;
            });

            SwitchPreference tumblrSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_tumblr_switcher));
            tumblrSwitcher.setChecked(sharedPreferences.getBoolean(KEY_TUMBLR, false));
            bindGlobalPreferenceToBooleanValue(tumblrSwitcher);
            tumblrSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(tumblrSwitcher, KEY_TUMBLR);
                return true;
            });

            SwitchPreference instagramSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_instagram_switcher));
            instagramSwitcher.setChecked(sharedPreferences.getBoolean(KEY_INSTAGRAM, false));
            bindGlobalPreferenceToBooleanValue(instagramSwitcher);
            instagramSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(instagramSwitcher, KEY_INSTAGRAM);
                return true;
            });

            SwitchPreference okSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_odnoklassniki_switcher));
            okSwitcher.setChecked(sharedPreferences.getBoolean(KEY_ODNOKLASSNIKI, false));
            bindGlobalPreferenceToBooleanValue(okSwitcher);
            okSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(okSwitcher, KEY_ODNOKLASSNIKI);
                return true;
            });

            SwitchPreference telegramSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_telegram_switcher));
            telegramSwitcher.setChecked(sharedPreferences.getBoolean(KEY_TELEGRAM, false));
            bindGlobalPreferenceToBooleanValue(telegramSwitcher);
            telegramSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(telegramSwitcher, KEY_TELEGRAM);
                return true;
            });

            SwitchPreference discordSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_discord_switcher));
            discordSwitcher.setChecked(sharedPreferences.getBoolean(KEY_DISCORD, false));
            bindGlobalPreferenceToBooleanValue(discordSwitcher);
            discordSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(discordSwitcher, KEY_DISCORD);
                return true;
            });

            SwitchPreference skypeSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_skype_switcher));
            skypeSwitcher.setChecked(sharedPreferences.getBoolean(KEY_SKYPE, false));
            bindGlobalPreferenceToBooleanValue(skypeSwitcher);
            skypeSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(skypeSwitcher, KEY_SKYPE);
                return true;
            });

            SwitchPreference icqSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_icq_switcher));
            icqSwitcher.setChecked(sharedPreferences.getBoolean(KEY_ICQ, false));
            bindGlobalPreferenceToBooleanValue(icqSwitcher);
            icqSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(icqSwitcher, KEY_ICQ);
                return true;
            });

            SwitchPreference slackSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_slack_switcher));
            slackSwitcher.setChecked(sharedPreferences.getBoolean(KEY_SLACK, false));
            bindGlobalPreferenceToBooleanValue(slackSwitcher);
            slackSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(slackSwitcher, KEY_SLACK);
                return true;
            });

            SwitchPreference gmailSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_gmail_switcher));
            gmailSwitcher.setChecked(sharedPreferences.getBoolean(KEY_GMAIL, false));
            bindGlobalPreferenceToBooleanValue(gmailSwitcher);
            gmailSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(gmailSwitcher, KEY_GMAIL);
                return true;
            });

            SwitchPreference mailruSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_mailru_switcher));
            mailruSwitcher.setChecked(sharedPreferences.getBoolean(KEY_MAIL_RU, false));
            bindGlobalPreferenceToBooleanValue(mailruSwitcher);
            mailruSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(mailruSwitcher, KEY_MAIL_RU);
                return true;
            });

            SwitchPreference youtubeSwitcher  = (SwitchPreference)findPreference(getString(R.string.channel_setting_key_youtube_switcher));
            youtubeSwitcher.setChecked(sharedPreferences.getBoolean(KEY_YOUTUBE, false));
            bindGlobalPreferenceToBooleanValue(youtubeSwitcher);
            youtubeSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(youtubeSwitcher, KEY_YOUTUBE);
                return true;
            });
        }

        private void handleSwitcherChange(SwitchPreference preference, String key){
            boolean switched = !preference.isChecked();
            preference.setSummary(switched ? switcherOn : switcherOff);
            Channel channel = getChannelByName(preference.getTitle().toString());
            if(channel != null){
                channel.setActive(switched);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(key, switched);
                editor.apply();
                editor.commit();
            } else {
                Log.d(TAG, getString(R.string.channel_settings_log_channel_is_null));
            }
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

    @Override
    public void onBackPressed() {
        AlphaDynamicMenuBuilder menuBuilder = getMenuBuilder();
        menuBuilder.rebuild(sharedPreferences);
        super.onBackPressed();
    }

}
