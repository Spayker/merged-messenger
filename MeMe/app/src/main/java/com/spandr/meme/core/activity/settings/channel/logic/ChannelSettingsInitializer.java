package com.spandr.meme.core.activity.settings.channel.logic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.spandr.meme.R;
import com.spandr.meme.core.common.data.memory.channel.Channel;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_CHANNEL_ORDER;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.common.data.memory.channel.ChannelManager.getChannelByName;

public class ChannelSettingsInitializer {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = ChannelSettingsInitializer.class.getSimpleName();

    // secondary fields
    private static String switcherOn;
    private static String switcherOff;

    private AppCompatActivity activity;
    private SharedPreferences sharedPreferences;

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

    public ChannelSettingsInitializer(PreferenceFragment fragment, AppCompatActivity activity) {
        switcherOn = fragment.getString(R.string.channel_setting_switcher_on);
        switcherOff = fragment.getString(R.string.channel_setting_switcher_off);

        this.activity = activity;
        sharedPreferences = activity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void initChannelSwitcher(PreferenceFragment fragment) {

        SwitchPreference facebookSwitcher  = (SwitchPreference) fragment.findPreference(fragment.getString(R.string.channel_setting_key_facebook_switcher));
        String fbKey = fragment.getString(R.string.channel_setting_fb);
        initSwitcher(facebookSwitcher, fbKey);

        SwitchPreference linkedinSwitcher  = (SwitchPreference) fragment.findPreference(fragment.getString(R.string.channel_setting_key_linkedin_switcher));
        String lnKey = fragment.getString(R.string.channel_setting_ln);
        initSwitcher(linkedinSwitcher, lnKey);

        SwitchPreference vkontakteSwitcher  = (SwitchPreference)fragment.findPreference(fragment.getString(R.string.channel_setting_key_vkontakte_switcher));
        String vkKey = fragment.getString(R.string.channel_setting_vk);
        initSwitcher(vkontakteSwitcher, vkKey);

        SwitchPreference twitterSwitcher  = (SwitchPreference)fragment.findPreference(fragment.getString(R.string.channel_setting_key_twitter_switcher));
        String twKey = fragment.getString(R.string.channel_setting_tw);
        initSwitcher(twitterSwitcher, twKey);

        SwitchPreference tumblrSwitcher  = (SwitchPreference)fragment.findPreference(fragment.getString(R.string.channel_setting_key_tumblr_switcher));
        String tmbKey = fragment.getString(R.string.channel_setting_tmb);
        initSwitcher(tumblrSwitcher, tmbKey);

        SwitchPreference instagramSwitcher  = (SwitchPreference)fragment.findPreference(fragment.getString(R.string.channel_setting_key_instagram_switcher));
        String instKey = fragment.getString(R.string.channel_setting_inst);
        initSwitcher(instagramSwitcher, instKey);

        SwitchPreference okSwitcher  = (SwitchPreference)fragment.findPreference(fragment.getString(R.string.channel_setting_key_odnoklassniki_switcher));
        String okKey = fragment.getString(R.string.channel_setting_ok);
        initSwitcher(okSwitcher, okKey);

        SwitchPreference pnSwitcher  = (SwitchPreference)fragment.findPreference(fragment.getString(R.string.channel_setting_key_pinterest_switcher));
        String pnKey = fragment.getString(R.string.channel_setting_pn);
        initSwitcher(pnSwitcher, pnKey);

        SwitchPreference telegramSwitcher  = (SwitchPreference)fragment.findPreference(fragment.getString(R.string.channel_setting_key_telegram_switcher));
        String tlKey = fragment.getString(R.string.channel_setting_tl);
        initSwitcher(telegramSwitcher, tlKey);

        SwitchPreference discordSwitcher  = (SwitchPreference)fragment.findPreference(fragment.getString(R.string.channel_setting_key_discord_switcher));
        String dcKey = fragment.getString(R.string.channel_setting_dc);
        initSwitcher(discordSwitcher, dcKey);

        SwitchPreference skypeSwitcher  = (SwitchPreference)fragment.findPreference(fragment.getString(R.string.channel_setting_key_skype_switcher));
        String skpKey = fragment.getString(R.string.channel_setting_skp);
        initSwitcher(skypeSwitcher, skpKey);

        SwitchPreference icqSwitcher  = (SwitchPreference)fragment.findPreference(fragment.getString(R.string.channel_setting_key_icq_switcher));
        String icqKey = fragment.getString(R.string.channel_setting_icq);
        initSwitcher(icqSwitcher, icqKey);

        SwitchPreference gaduSwitcher  = (SwitchPreference)fragment.findPreference(fragment.getString(R.string.channel_setting_key_gadu_switcher));
        String gaduKey = fragment.getString(R.string.channel_setting_gadu);
        initSwitcher(gaduSwitcher, gaduKey);

        SwitchPreference slackSwitcher  = (SwitchPreference)fragment.findPreference(fragment.getString(R.string.channel_setting_key_slack_switcher));
        String slKey = fragment.getString(R.string.channel_setting_slack);
        initSwitcher(slackSwitcher, slKey);

        SwitchPreference gmailSwitcher  = (SwitchPreference)fragment.findPreference(fragment.getString(R.string.channel_setting_key_gmail_switcher));
        String gmailKey = fragment.getString(R.string.channel_setting_gmail);
        initSwitcher(gmailSwitcher, gmailKey);

        SwitchPreference mailruSwitcher  = (SwitchPreference)fragment.findPreference(fragment.getString(R.string.channel_setting_key_mailru_switcher));
        String mailKey = fragment.getString(R.string.channel_setting_mailru);
        initSwitcher(mailruSwitcher, mailKey);

        SwitchPreference twitchSwitcher  = (SwitchPreference)fragment.findPreference(fragment.getString(R.string.channel_setting_key_twitch_switcher));
        String twitchKey = fragment.getString(R.string.channel_setting_twitch);
        initSwitcher(twitchSwitcher, twitchKey);

        SwitchPreference youtubeSwitcher  = (SwitchPreference)fragment.findPreference(fragment.getString(R.string.channel_setting_key_youtube_switcher));
        String ytKey = fragment.getString(R.string.channel_setting_yt);
        initSwitcher(youtubeSwitcher, ytKey);

        SwitchPreference redditSwitcher  = (SwitchPreference)fragment.findPreference(fragment.getString(R.string.channel_setting_key_reddit_switcher));
        String redditKey = fragment.getString(R.string.channel_setting_reddit);
        initSwitcher(redditSwitcher, redditKey);

        SwitchPreference quoraSwitcher  = (SwitchPreference)fragment.findPreference(fragment.getString(R.string.channel_setting_key_quora_switcher));
        String quoraKey = fragment.getString(R.string.channel_setting_quora);
        initSwitcher(quoraSwitcher, quoraKey);

        SwitchPreference habrSwitcher  = (SwitchPreference)fragment.findPreference(fragment.getString(R.string.channel_setting_key_habr_switcher));
        String habrKey = fragment.getString(R.string.channel_setting_habr);
        initSwitcher(habrSwitcher, habrKey);

        SwitchPreference stackSwitcher  = (SwitchPreference)fragment.findPreference(fragment.getString(R.string.channel_setting_key_stack_switcher));
        String stackKey = fragment.getString(R.string.channel_setting_stack);
        initSwitcher(stackSwitcher, stackKey);
    }

    private void initSwitcher(SwitchPreference switchPreference, String key){
        switchPreference.setChecked(sharedPreferences.getBoolean(key, false));
        bindGlobalPreferenceToBooleanValue(switchPreference);
        addPreferenceChangeListener(switchPreference, key);
    }

    /**
     *  Binds a global preference to Boolean value.
     *  @param preference is an instance Preference class which will be placed inside of activity
     **/
    private void bindGlobalPreferenceToBooleanValue(Preference preference) {
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
            Log.d(TAG, activity.getString(R.string.channel_settings_log_channel_is_null));
        }
    }



}
