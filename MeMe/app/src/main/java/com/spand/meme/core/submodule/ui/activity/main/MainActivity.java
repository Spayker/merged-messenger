package com.spand.meme.core.submodule.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.spand.meme.R;
import com.spand.meme.core.submodule.ui.activity.webview.WebViewActivity;

import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.HOME_URL;

/**
 *  A class handler is linked to appropriate activity xml file and contains backend logic.
 **/
public class MainActivity extends AppCompatActivity {

    private final String VK_HOME_URL = "http://vk.com";
    private final String FB_HOME_URL = "https://www.facebook.com/";
    private final String INSTAGRAM_HOME_URL = "https://www.instagram.com/";
    private final String TELEGRAM_HOME_URL = "https://web.telegram.org/#/login";
    private final String ODNOKLASNIKI_HOME_URL = "https://ok.ru/";
    private final String TUMBLR_HOME_URL = "https://www.tumblr.com/login";
    private final String DISCORD_HOME_URL = "https://discordapp.com/login";
    private final String YOUTUBE_HOME_URL = "https://www.youtube.com/";
    private final String LINKEDIN_HOME_URL = "https://www.linkedin.com/";
    private final String TWITTER_HOME_URL = "https://twitter.com/";
    private final String ICQ_HOME_URL = "https://web.icq.com/";
    private final String SKYPE_HOME_URL = "https://web.skype.com/en/";
    private final String GMAIL_HOME_URL = "www.gmail.com";
    private final String MAIL_RU_HOME_URL = "https://mail.ru/";

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
        intent.putExtra(HOME_URL, VK_HOME_URL);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Facebook.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnFBActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, FB_HOME_URL);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Instagram.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnInstActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, INSTAGRAM_HOME_URL);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Telegram.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnTLActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, TELEGRAM_HOME_URL);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Odnoklasniki.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnOKActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, ODNOKLASNIKI_HOME_URL);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Tumblr.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnTmbActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, TUMBLR_HOME_URL);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Discord.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnDCActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, DISCORD_HOME_URL);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Youtube.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnYTActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, YOUTUBE_HOME_URL);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for LinkedIn.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnLNActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, LINKEDIN_HOME_URL);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Twitter.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnTWActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, TWITTER_HOME_URL);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for ICQ.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnICQActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, ICQ_HOME_URL);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Skype.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnSkpActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, SKYPE_HOME_URL);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Gmail.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnGmailActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, GMAIL_HOME_URL);
        startActivity(intent);
    }

    /**
     *  A listener method which starts new activity for Gmail.
     *  @param view an instance of View class
     *              ( represents the basic building block for user interface components )
     **/
    public void clickOnMailRuActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, MAIL_RU_HOME_URL);
        startActivity(intent);
    }

}
