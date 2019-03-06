package com.spandr.meme.core.activity.main.logic.updater;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.main.logic.updater.async.GetLatestVersion;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class AppUpdater {

    private static final String TAG = AppUpdater.class.getSimpleName();

    private AppCompatActivity activity;
    private final String APP_PLAY_MARKET_URI = "market://details?id=com.spandr.meme";

    private static String fullLatestVersion;

    public AppUpdater(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void checkAppForUpdate() {
        String formattedCurrentVersion = getCurrentVersion();
        Log.d(TAG, "Current version = " + formattedCurrentVersion);

        String formattedLatestVersion = null;
        if(fullLatestVersion == null){
            try {
                fullLatestVersion = new GetLatestVersion().execute().get();
                formattedLatestVersion = fullLatestVersion.replace(".","");
                Log.d(TAG, "Latest version = " + fullLatestVersion);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

            //If the versions are not the same
            if (formattedLatestVersion != null &&
                    formattedLatestVersion.length() <= 4 &&
                        formattedCurrentVersion.length() <= 4) {
                if (!formattedCurrentVersion.equals(fullLatestVersion.replace(".", ""))) {
                    AlertDialog.Builder alertDialog = createDialogBox();
                    activity.runOnUiThread(alertDialog::show);
                }
            }
        }
    }

    private AlertDialog.Builder createDialogBox(){
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle(activity.getResources().getString(R.string.main_menu_update_released));
            builder.setPositiveButton(activity.getResources().getString(R.string.main_menu_yes),
                    (dialog, which) -> {
                        activity.startActivity(new Intent(Intent.ACTION_VIEW,
                                Uri.parse(APP_PLAY_MARKET_URI)));
                        dialog.dismiss();
                    });

            builder.setNegativeButton(activity.getResources().getString(R.string.main_menu_later),
                    (dialog, which) -> dialog.dismiss());
            builder.setCancelable(true);
            builder.setIcon(R.mipmap.logo);
        return builder;
    }

    private String getCurrentVersion() {
        PackageManager pm = activity.getPackageManager();
        PackageInfo pInfo = null;
        try {
            pInfo = pm.getPackageInfo(activity.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e1) {
            e1.printStackTrace();
        }
        String fullVersionName = Objects.requireNonNull(pInfo).versionName;
        return fullVersionName.replace(".", "");
    }

}
