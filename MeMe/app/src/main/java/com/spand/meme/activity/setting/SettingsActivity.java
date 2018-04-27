package com.spand.meme.activity.setting;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.spand.meme.R;
import com.spand.meme.activity.setting.ChannelsSettingActivity;
import com.spand.meme.activity.setting.GlobalSettingsActivity;

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
