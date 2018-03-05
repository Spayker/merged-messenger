package com.spand.bridgecom.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.spand.bridgecom.R;

public class IncomingMessagesActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_incoming_messages);
    }

    public void clickOnSearchContactActivity(View view) {
        Intent intent = new Intent(this, ContactSearchActivity.class);
        startActivity(intent);
    }

    public void clickOnSomeContactActivity(View view) {
        Intent intent = new Intent(this, MainChatActivity.class);
        startActivity(intent);
    }




}
