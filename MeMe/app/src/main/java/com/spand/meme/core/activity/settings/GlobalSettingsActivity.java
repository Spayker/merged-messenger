package com.spand.meme.core.activity.settings;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.spand.meme.R;
import com.spand.meme.core.activity.channel.ChangePasswordActivity;

public class GlobalSettingsActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_settings);
    }

    public void selectChangePassword(View view) {
        view.getTransitionName();
        Intent intent = new Intent(this, ChangePasswordActivity.class);
        startActivity(intent);
    }

    public void selectResetSettings(View view) {
        Intent intent = new Intent(this, ResetSettingsActivity.class);
        startActivity(intent);
    }

    public void selectDeactivateAccount(View view) {
        Intent intent = new Intent(this, RemoveAccountActivity.class);
        startActivity(intent);
    }

}
