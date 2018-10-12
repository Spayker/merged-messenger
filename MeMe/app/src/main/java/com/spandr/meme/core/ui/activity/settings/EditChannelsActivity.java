package com.spandr.meme.core.ui.activity.settings;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.spandr.meme.R;
import com.spandr.meme.core.data.memory.channel.Channel;
import com.spandr.meme.core.ui.activity.main.MainActivity;

import static com.spandr.meme.core.data.memory.channel.ChannelManager.getChannelByName;
import static com.spandr.meme.core.logic.starter.SettingsConstants.KEY_CHANNEL_ORDER;
import static com.spandr.meme.core.logic.starter.SettingsConstants.PREF_NAME;

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
            String fbKey = getString(R.string.channel_setting_fb);
            facebookSwitcher.setChecked(sharedPreferences.getBoolean(fbKey, false));
            bindGlobalPreferenceToBooleanValue(facebookSwitcher);
            facebookSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(facebookSwitcher, fbKey);
                return true;
            });

            SwitchPreference linkedinSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_linkedin_switcher));
            String lnKey = getString(R.string.channel_setting_ln);
            linkedinSwitcher.setChecked(sharedPreferences.getBoolean(lnKey, false));
            bindGlobalPreferenceToBooleanValue(linkedinSwitcher);
            linkedinSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(linkedinSwitcher, lnKey);
                return true;
            });

            SwitchPreference vkontakteSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_vkontakte_switcher));
            String vkKey = getString(R.string.channel_setting_vk);
            vkontakteSwitcher.setChecked(sharedPreferences.getBoolean(vkKey, false));
            bindGlobalPreferenceToBooleanValue(vkontakteSwitcher);
            vkontakteSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(vkontakteSwitcher, vkKey);
                return true;
            });

            SwitchPreference twitterSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_twitter_switcher));
            String twKey = getString(R.string.channel_setting_tw);
            twitterSwitcher.setChecked(sharedPreferences.getBoolean(twKey, false));
            bindGlobalPreferenceToBooleanValue(twitterSwitcher);
            twitterSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(twitterSwitcher, twKey);
                return true;
            });

            SwitchPreference tumblrSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_tumblr_switcher));
            String tmbKey = getString(R.string.channel_setting_tmb);
            tumblrSwitcher.setChecked(sharedPreferences.getBoolean(tmbKey, false));
            bindGlobalPreferenceToBooleanValue(tumblrSwitcher);
            tumblrSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(tumblrSwitcher, tmbKey);
                return true;
            });

            SwitchPreference instagramSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_instagram_switcher));
            String instKey = getString(R.string.channel_setting_inst);
            instagramSwitcher.setChecked(sharedPreferences.getBoolean(instKey, false));
            bindGlobalPreferenceToBooleanValue(instagramSwitcher);
            instagramSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(instagramSwitcher, instKey);
                return true;
            });

            SwitchPreference okSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_odnoklassniki_switcher));
            String okKey = getString(R.string.channel_setting_ok);
            okSwitcher.setChecked(sharedPreferences.getBoolean(okKey, false));
            bindGlobalPreferenceToBooleanValue(okSwitcher);
            okSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(okSwitcher, okKey);
                return true;
            });

            SwitchPreference pnSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_pinterest_switcher));
            String pnKey = getString(R.string.channel_setting_pn);
            pnSwitcher.setChecked(sharedPreferences.getBoolean(pnKey, false));
            bindGlobalPreferenceToBooleanValue(pnSwitcher);
            pnSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(pnSwitcher, pnKey);
                return true;
            });

            SwitchPreference telegramSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_telegram_switcher));
            String tlKey = getString(R.string.channel_setting_tl);
            telegramSwitcher.setChecked(sharedPreferences.getBoolean(tlKey, false));
            bindGlobalPreferenceToBooleanValue(telegramSwitcher);
            telegramSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(telegramSwitcher, tlKey);
                return true;
            });

            SwitchPreference discordSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_discord_switcher));
            String dcKey = getString(R.string.channel_setting_dc);
            discordSwitcher.setChecked(sharedPreferences.getBoolean(dcKey, false));
            bindGlobalPreferenceToBooleanValue(discordSwitcher);
            discordSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(discordSwitcher, dcKey);
                return true;
            });

            SwitchPreference skypeSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_skype_switcher));
            String skpKey = getString(R.string.channel_setting_skp);
            skypeSwitcher.setChecked(sharedPreferences.getBoolean(skpKey, false));
            bindGlobalPreferenceToBooleanValue(skypeSwitcher);
            skypeSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(skypeSwitcher, skpKey);
                return true;
            });

            SwitchPreference icqSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_icq_switcher));
            String icqKey = getString(R.string.channel_setting_icq);
            icqSwitcher.setChecked(sharedPreferences.getBoolean(icqKey, false));
            bindGlobalPreferenceToBooleanValue(icqSwitcher);
            icqSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(icqSwitcher, icqKey);
                return true;
            });

            SwitchPreference slackSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_slack_switcher));
            String slKey = getString(R.string.channel_setting_slack);
            slackSwitcher.setChecked(sharedPreferences.getBoolean(slKey, false));
            bindGlobalPreferenceToBooleanValue(slackSwitcher);
            slackSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(slackSwitcher, slKey);
                return true;
            });

            SwitchPreference gmailSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_gmail_switcher));
            String gmailKey = getString(R.string.channel_setting_gmail);
            gmailSwitcher.setChecked(sharedPreferences.getBoolean(gmailKey, false));
            bindGlobalPreferenceToBooleanValue(gmailSwitcher);
            gmailSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(gmailSwitcher, gmailKey);
                return true;
            });

            SwitchPreference mailruSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_mailru_switcher));
            String mailKey = getString(R.string.channel_setting_mailru);
            mailruSwitcher.setChecked(sharedPreferences.getBoolean(mailKey, false));
            bindGlobalPreferenceToBooleanValue(mailruSwitcher);
            mailruSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(mailruSwitcher, mailKey);
                return true;
            });

            SwitchPreference youtubeSwitcher  = (SwitchPreference)findPreference(getString(R.string.channel_setting_key_youtube_switcher));
            String ytKey = getString(R.string.channel_setting_yt);
            youtubeSwitcher.setChecked(sharedPreferences.getBoolean(ytKey, false));
            bindGlobalPreferenceToBooleanValue(youtubeSwitcher);
            youtubeSwitcher.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(youtubeSwitcher, ytKey);
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

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_CHANNEL_ORDER, null);
        editor.apply();
        editor.commit();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
