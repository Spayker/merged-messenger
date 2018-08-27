package com.spand.meme.core.submodule.ui.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spand.meme.R;
import com.spand.meme.core.submodule.logic.menu.main.builder.DynamicMenuBuilder;
import com.spand.meme.core.submodule.logic.menu.main.builder.MainMenuBuilder;
import com.spand.meme.core.submodule.ui.activity.settings.EditChannelsActivity;
import com.spand.meme.core.submodule.ui.activity.settings.GlobalSettingsActivity;

import static com.spand.meme.core.submodule.logic.starter.Loginner.createLoginner;
import static com.spand.meme.core.submodule.logic.starter.Setupper.createSetupper;
import static com.spand.meme.core.submodule.logic.starter.Starter.REGISTRATOR;
import static com.spand.meme.core.submodule.logic.starter.Starter.START_TYPE;
import static com.spand.meme.core.submodule.logic.starter.Starter.USERNAME;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.PREF_NAME;

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

        TextView mAppVersionView = findViewById(R.id.app_build_version);
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