package com.spand.meme.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.spand.meme.R;

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
