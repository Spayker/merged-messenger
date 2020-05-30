package com.spand.meme.core.submodule.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.spand.meme.R;

public class TermsAndUseConditionsActivity extends AppCompatActivity{

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = TermsAndUseConditionsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_use_conditions);
    }

    public void clickOnAccept(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void clickOnDecline(View view) {
        onBackPressed();
    }
}
