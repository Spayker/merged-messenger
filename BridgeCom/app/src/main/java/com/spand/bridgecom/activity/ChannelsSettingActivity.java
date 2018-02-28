package com.spand.bridgecom.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.spand.bridgecom.R;

public class ChannelsSettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channels_setting);
    }

    public void selectChannelSetting(View view) {
        Intent intent = new Intent(this, CertainChannelSettingActivity.class);
        startActivity(intent);
    }
}
