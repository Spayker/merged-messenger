package com.spand.bridgecom.activity;

import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceScreen;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.spand.bridgecom.R;

public class CertainChannelSettingActivity extends PreferenceActivity {

    public static class SettingsFragment extends PreferenceFragment {

        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
                    getPreferenceManager().createPreferenceScreen(getActivity());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager()
                .beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }




}
