package com.spand.meme.core.submodule.ui.activity.channel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.spand.meme.R;
import com.spand.meme.core.submodule.ui.activity.chat.IncomingMessagesActivity;

/**
 *  A class handler is linked to appropriate activity xml file and contains backend logic.
 **/
public class ChannelsActivity extends AppCompatActivity {

    /**
     *  Perform initialization of all fragments of current activity.
     *  @param savedInstanceState an instance of Bundle instance
     *                            (A mapping from String keys to various Parcelable values)
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channels);
    }

    /**
     *  A listener method which starts new activity.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnChannelActivity(View view) {
        Intent intent = new Intent(this, IncomingMessagesActivity.class);
        startActivity(intent);
    }
}
