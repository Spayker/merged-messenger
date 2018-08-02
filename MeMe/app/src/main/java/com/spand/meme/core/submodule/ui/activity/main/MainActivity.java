package com.spand.meme.core.submodule.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.spand.meme.R;
import com.spand.meme.core.submodule.ui.activity.webview.WebViewActivity;

/**
 *  A class handler is linked to appropriate activity xml file and contains backend logic.
 **/
public class MainActivity extends AppCompatActivity {

    /**
     *  Perform initialization of all fragments of current activity.
     *  @param savedInstanceState an instance of Bundle instance
     *                            (A mapping from String keys to various Parcelable values)
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     *  A listener method which starts new activity for Vkontakte.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnVKActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Facebook.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnFBActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Instagram.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnInstActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Telegram.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnTLActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Odnoklasniki.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnOKActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Tumblr.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnTmbActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Discord.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnDCActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Youtube.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnYTActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for LinkedIn.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnLNActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Twitter.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnTWActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for ICQ.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnICQActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Skype.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnSkpActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Gmail.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnGmailActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        startActivity(intent);
    }

}
