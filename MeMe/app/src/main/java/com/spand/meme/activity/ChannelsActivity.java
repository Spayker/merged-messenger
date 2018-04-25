package com.spand.meme.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.spand.meme.R;

public class ChannelsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channels);
    }

    public void clickOnChannelActivity(View view) {
        Intent intent = new Intent(this, IncomingMessagesActivity.class);
        startActivity(intent);
    }
}