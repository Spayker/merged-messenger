package com.spand.meme.core.submodule.ui.activity.chat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.spand.meme.R;

/**
 *  A class handler is linked to appropriate activity xml file and contains backend logic.
 **/
public class ContactSearchActivity extends AppCompatActivity {

    /**
     *  Perform initialization of all fragments of current activity.
     *  @param savedInstanceState an instance of Bundle instance
     *                            (A mapping from String keys to various Parcelable values)
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_search);
    }
}
