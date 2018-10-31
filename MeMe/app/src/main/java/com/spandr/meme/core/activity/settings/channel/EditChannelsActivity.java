package com.spandr.meme.core.activity.settings.channel;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.main.MainActivity;
import com.spandr.meme.core.activity.settings.channel.logic.ChannelSettingsInitializer;

public class EditChannelsActivity extends AppCompatActivity {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = EditChannelsActivity.class.getSimpleName();

    @SuppressLint("StaticFieldLeak")
    private static AppCompatActivity editChannelActivity;

    /**
     *  Perform initialization of all fragments of current activity.
     *  @param savedInstanceState an instance of Bundle instance
     *                            (A mapping from String keys to various Parcelable values)
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editChannelActivity = this;
        // load settings fragment
        getFragmentManager().beginTransaction().replace(android.R.id.content, new ChannelSettingsPreferenceFragment()).commit();
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
            ChannelSettingsInitializer channelSettingsInitializer = new ChannelSettingsInitializer(this, editChannelActivity);
            channelSettingsInitializer.initChannelSwitcher(this);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
