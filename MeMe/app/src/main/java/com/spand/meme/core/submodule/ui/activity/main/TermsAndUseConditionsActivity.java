package com.spand.meme.core.submodule.ui.activity.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.spand.meme.R;

public class TermsAndUseConditionsActivity extends AppCompatActivity implements View.OnClickListener{

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = TermsAndUseConditionsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_use_conditions);
    }

    @Override
    public void onClick(View v) {

    }
}
