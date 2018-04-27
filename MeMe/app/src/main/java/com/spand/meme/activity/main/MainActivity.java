package com.spand.meme.activity.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.spand.meme.R;
import com.spand.meme.activity.chat.IncomingMessagesActivity;
import com.spand.meme.activity.channel.ChannelsActivity;
import com.spand.meme.activity.setting.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void clickOnIncomeActivity(View view) {
        Intent intent = new Intent(this, IncomingMessagesActivity.class);
        startActivity(intent);
    }

    public void clickOnMyChatsActivity(View view) {
        Intent intent = new Intent(this, ChannelsActivity.class);
        startActivity(intent);
    }

    public void clickOnAttachAccountActivity(View view) {
        Intent intent = new Intent(this, ChannelsActivity.class);
        startActivity(intent);
    }

    public void clickOnSettingActivity(View view) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

}
