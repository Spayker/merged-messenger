package com.spandr.meme.core.activity.settings.channel;

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
import com.spandr.meme.core.common.data.memory.channel.Channel;
import com.spandr.meme.core.activity.main.MainActivity;

import static com.spandr.meme.core.common.data.memory.channel.ChannelManager.getChannelByName;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_CHANNEL_ORDER;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;

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
            initSwitcher(facebookSwitcher, fbKey);

            SwitchPreference linkedinSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_linkedin_switcher));
            String lnKey = getString(R.string.channel_setting_ln);
            initSwitcher(linkedinSwitcher, lnKey);

            SwitchPreference vkontakteSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_vkontakte_switcher));
            String vkKey = getString(R.string.channel_setting_vk);
            initSwitcher(vkontakteSwitcher, vkKey);

            SwitchPreference twitterSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_twitter_switcher));
            String twKey = getString(R.string.channel_setting_tw);
            initSwitcher(twitterSwitcher, twKey);

            SwitchPreference tumblrSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_tumblr_switcher));
            String tmbKey = getString(R.string.channel_setting_tmb);
            initSwitcher(tumblrSwitcher, tmbKey);

            SwitchPreference instagramSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_instagram_switcher));
            String instKey = getString(R.string.channel_setting_inst);
            initSwitcher(instagramSwitcher, instKey);

            SwitchPreference okSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_odnoklassniki_switcher));
            String okKey = getString(R.string.channel_setting_ok);
            initSwitcher(okSwitcher, okKey);

            SwitchPreference pnSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_pinterest_switcher));
            String pnKey = getString(R.string.channel_setting_pn);
            initSwitcher(pnSwitcher, pnKey);

            SwitchPreference telegramSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_telegram_switcher));
            String tlKey = getString(R.string.channel_setting_tl);
            initSwitcher(telegramSwitcher, tlKey);

            SwitchPreference discordSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_discord_switcher));
            String dcKey = getString(R.string.channel_setting_dc);
            initSwitcher(discordSwitcher, dcKey);

            SwitchPreference skypeSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_skype_switcher));
            String skpKey = getString(R.string.channel_setting_skp);
            initSwitcher(skypeSwitcher, skpKey);

            SwitchPreference icqSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_icq_switcher));
            String icqKey = getString(R.string.channel_setting_icq);
            initSwitcher(icqSwitcher, icqKey);

            SwitchPreference gaduSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_gadu_switcher));
            String gaduKey = getString(R.string.channel_setting_gadu);
            initSwitcher(gaduSwitcher, gaduKey);

            SwitchPreference slackSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_slack_switcher));
            String slKey = getString(R.string.channel_setting_slack);
            initSwitcher(slackSwitcher, slKey);

            SwitchPreference gmailSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_gmail_switcher));
            String gmailKey = getString(R.string.channel_setting_gmail);
            initSwitcher(gmailSwitcher, gmailKey);

            SwitchPreference mailruSwitcher  = (SwitchPreference) findPreference(getString(R.string.channel_setting_key_mailru_switcher));
            String mailKey = getString(R.string.channel_setting_mailru);
            initSwitcher(mailruSwitcher, mailKey);

            SwitchPreference twitchSwitcher  = (SwitchPreference)findPreference(getString(R.string.channel_setting_key_twitch_switcher));
            String twitchKey = getString(R.string.channel_setting_twitch);
            initSwitcher(twitchSwitcher, twitchKey);

            SwitchPreference youtubeSwitcher  = (SwitchPreference)findPreference(getString(R.string.channel_setting_key_youtube_switcher));
            String ytKey = getString(R.string.channel_setting_yt);
            initSwitcher(youtubeSwitcher, ytKey);

            SwitchPreference redditSwitcher  = (SwitchPreference)findPreference(getString(R.string.channel_setting_key_reddit_switcher));
            String redditKey = getString(R.string.channel_setting_reddit);
            initSwitcher(redditSwitcher, redditKey);

            SwitchPreference quoraSwitcher  = (SwitchPreference)findPreference(getString(R.string.channel_setting_key_quora_switcher));
            String quoraKey = getString(R.string.channel_setting_quora);
            initSwitcher(quoraSwitcher, quoraKey);

            SwitchPreference habrSwitcher  = (SwitchPreference)findPreference(getString(R.string.channel_setting_key_habr_switcher));
            String habrKey = getString(R.string.channel_setting_habr);
            initSwitcher(habrSwitcher, habrKey);

            SwitchPreference stackSwitcher  = (SwitchPreference)findPreference(getString(R.string.channel_setting_key_stack_switcher));
            String stackKey = getString(R.string.channel_setting_stack);
            initSwitcher(stackSwitcher, stackKey);
        }

        private void initSwitcher(SwitchPreference switchPreference, String key){
            switchPreference.setChecked(sharedPreferences.getBoolean(key, false));
            bindGlobalPreferenceToBooleanValue(switchPreference);
            addPreferenceChangeListener(switchPreference, key);
        }

        private void addPreferenceChangeListener(SwitchPreference switchPreference, String key){
            switchPreference.setOnPreferenceChangeListener((preference, newValue) -> {
                handleSwitcherChange(switchPreference, key);
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
