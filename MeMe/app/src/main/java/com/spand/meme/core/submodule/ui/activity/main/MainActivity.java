package com.spand.meme.core.submodule.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spand.meme.R;
import com.spand.meme.core.submodule.logic.menu.main.builder.DynamicMenuBuilder;
import com.spand.meme.core.submodule.logic.menu.main.builder.MainMenuBuilder;
import com.spand.meme.core.submodule.logic.starter.Loginner;
import com.spand.meme.core.submodule.logic.starter.Setupper;
import com.spand.meme.core.submodule.logic.starter.Starter;
import com.spand.meme.core.submodule.ui.activity.settings.EditChannelsActivity;
import com.spand.meme.core.submodule.ui.activity.settings.GlobalSettingsActivity;
import com.spand.meme.core.submodule.ui.activity.webview.WebViewActivity;

import static com.spand.meme.core.submodule.logic.starter.Loginner.createLoginner;
import static com.spand.meme.core.submodule.logic.starter.Setupper.createSetupper;
import static com.spand.meme.core.submodule.logic.starter.Starter.LOGINNER;
import static com.spand.meme.core.submodule.logic.starter.Starter.REGISTRATOR;
import static com.spand.meme.core.submodule.logic.starter.Starter.START_TYPE;
import static com.spand.meme.core.submodule.logic.starter.Starter.USERNAME;
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

    private final static int SUCCESS_EXIT_CODE = 1;

    private TextView mAppVersionView;

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
        String startTypeKey = intent.getStringExtra(START_TYPE);
        if (startTypeKey != null) {
            switch (startTypeKey) {
                case REGISTRATOR: {
                    createSetupper().initApplication(sharedPreferences, this);
                    String username = intent.getStringExtra(USERNAME);
                    setTitle(username);
                    break;
                }
                default: {
                    createLoginner().initApplication(sharedPreferences, this);
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    FirebaseUser currentUser = mAuth.getCurrentUser();
                    if(currentUser != null){
                        setTitle(currentUser.getDisplayName());
                    }
                }
            }
        } else {
            createLoginner().initApplication(sharedPreferences, this);
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if(currentUser != null){
                setTitle(currentUser.getDisplayName());
            }
        }

        mAppVersionView =  findViewById(R.id.app_build_version);
        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            String version = pInfo.versionName;
            mAppVersionView.setText("MeMe v" + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


        MainMenuBuilder menuBuilder = new DynamicMenuBuilder(this);
        menuBuilder.build(sharedPreferences);
    }

    /**
     * Initialize the contents of the Activity's standard options menu.  You
     * should place your menu items in to <var>menu</var>.
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
            default: {
                performSignOut();
                return true;
            }
        }
    }

    public void clickOnExit(View view) {
        moveTaskToBack(true);
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(SUCCESS_EXIT_CODE);
    }

    public void clickOnEditChannels(View view) {
        Intent intent = new Intent(this, EditChannelsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        performSignOut();
    }

    private void performSignOut(){
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signOut();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}