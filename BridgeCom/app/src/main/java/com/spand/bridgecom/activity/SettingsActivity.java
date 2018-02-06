package com.spand.bridgecom.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.spand.bridgecom.R;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    public void selectGlobalSettings(View view) {
        Intent intent = new Intent(this, GlobalSettingsActivity.class);
        startActivity(intent);
    }

    public void selectChannelsSetting(View view) {
        Intent intent = new Intent(this, ChannelsSettingActivity.class);
        startActivity(intent);
    }


}
