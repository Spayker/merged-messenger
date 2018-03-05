package com.spand.bridgecom.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.spand.bridgecom.R;

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
