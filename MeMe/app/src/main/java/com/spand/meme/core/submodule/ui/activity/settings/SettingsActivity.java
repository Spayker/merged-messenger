package com.spand.meme.core.submodule.ui.activity.settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.spand.meme.R;

/**
 *  A class handler is linked to appropriate activity xml file and contains backend logic.
 **/
public class SettingsActivity extends AppCompatActivity {

    /**
     *  Perform initialization of all fragments of current activity.
     *  @param savedInstanceState an instance of Bundle instance
     *                            (A mapping from String keys to various Parcelable values)
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    /**
     *  A listener method which starts new activity.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void selectGlobalSettings(View view) {
        Intent intent = new Intent(this, GlobalSettingsActivity.class);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void selectChannelsSetting(View view) {
        Intent intent = new Intent(this, ChannelsSettingActivity.class);
        startActivity(intent);
    }


}
