package com.spand.meme.core.submodule.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.spand.meme.R;
import com.spand.meme.core.submodule.logic.menu.main.builder.DynamicMenuBuilder;
import com.spand.meme.core.submodule.logic.menu.main.builder.MainMenuBuilder;
import com.spand.meme.core.submodule.logic.starter.Loginner;
import com.spand.meme.core.submodule.logic.starter.Setupper;
import com.spand.meme.core.submodule.logic.starter.Starter;
import com.spand.meme.core.submodule.ui.activity.settings.EditChannelsActivity;
import com.spand.meme.core.submodule.ui.activity.settings.GlobalSettingsActivity;
import com.spand.meme.core.submodule.ui.activity.webview.WebViewActivity;

import static com.spand.meme.core.submodule.logic.starter.Starter.LOGINNER;
import static com.spand.meme.core.submodule.logic.starter.Starter.REGISTRATOR;
import static com.spand.meme.core.submodule.logic.starter.Starter.START_TYPE;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.DISCORD_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.EMPTY_STRING;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.FB_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.GMAIL_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.ICQ_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.INSTAGRAM_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.LINKEDIN_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.MAIL_RU_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.ODNOKLASNIKI_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.PREF_NAME;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.SKYPE_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.SPACE_CHARACTER;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.TELEGRAM_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.TUMBLR_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.TWITTER_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.VK_HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.YOUTUBE_HOME_URL;

/**
 * A class handler is linked to appropriate activity xml file and contains backend logic.
 **/
public class MainActivity extends AppCompatActivity {

    /**
     * Perform initialization of all fragments of current activity.
     *
     * @param savedInstanceState an instance of Bundle instance
     *                           (A mapping from String keys to various Parcelable values)
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // default settings init
        Intent intent = getIntent();
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
//        switch (intent.getStringExtra(START_TYPE)){
//            case LOGINNER: {
//                Starter loginner = new Loginner();
//                loginner.initApplication(sharedPreferences, intent);
//            }
//            case REGISTRATOR: {
                Starter setupper = new Setupper();
                setupper.initApplication(sharedPreferences, this);
//            }
//        }

        MainMenuBuilder menuBuilder = new DynamicMenuBuilder(this);
        menuBuilder.build(sharedPreferences);
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
     *
     **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings: {
                Intent intent = new Intent(this, GlobalSettingsActivity.class);
                startActivity(intent);
                return true;
            }
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    /**
     * A listener method which starts new activity for Vkontakte.
     *
     * @param view an instance of View class
     *             ( represents the basic building block for user interface components )
     **/
    public void clickOnVKActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, VK_HOME_URL);
        startActivity(intent);
    }

    /**
     * A listener method which starts new activity for Facebook.
     *
     * @param view an instance of View class
     *             ( represents the basic building block for user interface components )
     **/
    public void clickOnFBActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, FB_HOME_URL);
        startActivity(intent);
    }

    /**
     * A listener method which starts new activity for Instagram.
     *
     * @param view an instance of View class
     *             ( represents the basic building block for user interface components )
     **/
    public void clickOnInstActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, INSTAGRAM_HOME_URL);
        startActivity(intent);
    }

    /**
     * A listener method which starts new activity for Telegram.
     *
     * @param view an instance of View class
     *             ( represents the basic building block for user interface components )
     **/
    public void clickOnTLActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, TELEGRAM_HOME_URL);
        startActivity(intent);
    }

    /**
     * A listener method which starts new activity for Odnoklasniki.
     *
     * @param view an instance of View class
     *             ( represents the basic building block for user interface components )
     **/
    public void clickOnOKActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, ODNOKLASNIKI_HOME_URL);
        startActivity(intent);
    }

    /**
     * A listener method which starts new activity for Tumblr.
     *
     * @param view an instance of View class
     *             ( represents the basic building block for user interface components )
     **/
    public void clickOnTmbActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, TUMBLR_HOME_URL);
        startActivity(intent);
    }

    /**
     * A listener method which starts new activity for Discord.
     *
     * @param view an instance of View class
     *             ( represents the basic building block for user interface components )
     **/
    public void clickOnDCActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, DISCORD_HOME_URL);
        startActivity(intent);
    }

    /**
     * A listener method which starts new activity for Youtube.
     *
     * @param view an instance of View class
     *             ( represents the basic building block for user interface components )
     **/
    public void clickOnYTActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, YOUTUBE_HOME_URL);
        startActivity(intent);
    }

    /**
     * A listener method which starts new activity for LinkedIn.
     *
     * @param view an instance of View class
     *             ( represents the basic building block for user interface components )
     **/
    public void clickOnLNActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, LINKEDIN_HOME_URL);
        startActivity(intent);
    }

    /**
     * A listener method which starts new activity for Twitter.
     *
     * @param view an instance of View class
     *             ( represents the basic building block for user interface components )
     **/
    public void clickOnTWActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, TWITTER_HOME_URL);
        startActivity(intent);
    }

    /**
     * A listener method which starts new activity for ICQ.
     *
     * @param view an instance of View class
     *             ( represents the basic building block for user interface components )
     **/
    public void clickOnICQActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, ICQ_HOME_URL);
        startActivity(intent);
    }

    /**
     * A listener method which starts new activity for Skype.
     *
     * @param view an instance of View class
     *             ( represents the basic building block for user interface components )
     **/
    public void clickOnSkpActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, SKYPE_HOME_URL);
        startActivity(intent);
    }

    /**
     * A listener method which starts new activity for Gmail.
     *
     * @param view an instance of View class
     *             ( represents the basic building block for user interface components )
     **/
    public void clickOnGmailActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, GMAIL_HOME_URL);
        startActivity(intent);
    }

    /**
     * A listener method which starts new activity for Gmail.
     *
     * @param view an instance of View class
     *             ( represents the basic building block for user interface components )
     **/
    public void clickOnMailRuActivity(View view) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra(HOME_URL, MAIL_RU_HOME_URL);
        startActivity(intent);
    }

    public void clickOnEditChannels(View view){
        Intent intent = new Intent(this, EditChannelsActivity.class);
        startActivity(intent);
    }



}