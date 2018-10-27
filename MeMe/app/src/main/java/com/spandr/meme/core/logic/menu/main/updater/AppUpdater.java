package com.spandr.meme.core.logic.menu.main.updater;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.spandr.meme.R;
import com.spandr.meme.core.logic.menu.main.updater.async.GetLatestVersion;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class AppUpdater {

    private static final String TAG = AppUpdater.class.getSimpleName();

    private AppCompatActivity activity;
    private static AlertDialog.Builder builder;
    private final String APP_PLAY_MARKET_URI = "market://details?id=com.spandr.meme";
    private final String SPACE_CHARACTER = " ";

    public AppUpdater(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void checkAppForUpdate() {
        String currentVersion = "1.0.4"/*getCurrentVersion()*/;
        Log.d(TAG, "Current version = " + currentVersion);
        String latestVersion = null;
        try {
            latestVersion = new GetLatestVersion().execute().get();
            Log.d(TAG, "Latest version = " + latestVersion);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //If the versions are not the same
        if (!currentVersion.equals(latestVersion)) {
            if(builder == null){
                String finalLatestVersion = latestVersion;
                activity.runOnUiThread(() -> {
                    builder = new AlertDialog.Builder(activity);
                    builder.setTitle(activity.getResources().getString(R.string.app_name) +
                            SPACE_CHARACTER +
                            activity.getResources().getString(R.string.app_version) +
                            finalLatestVersion +
                            SPACE_CHARACTER +
                            activity.getResources().getString(R.string.main_menu_update_released));
                    builder.setPositiveButton(activity.getResources().getString(R.string.main_menu_yes),
                            (dialog, which) -> {
                                activity.startActivity(new Intent(Intent.ACTION_VIEW,
                                        Uri.parse(APP_PLAY_MARKET_URI)));
                                dialog.dismiss();
                            });

                    builder.setNegativeButton(activity.getResources().getString(R.string.main_menu_no),
                            (dialog, which) -> dialog.dismiss());
                    builder.setCancelable(true);
                    builder.setIcon(R.mipmap.logo);
                    builder.show();
                });
            } else {
                builder = null;
            }
        }
    }

    private String getCurrentVersion() {
        PackageManager pm = activity.getPackageManager();
        PackageInfo pInfo = null;
        try {
            pInfo = pm.getPackageInfo(activity.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        return Objects.requireNonNull(pInfo).versionName;
    }

}
