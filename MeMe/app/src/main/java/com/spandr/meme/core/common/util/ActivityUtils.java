package com.spandr.meme.core.common.util;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.intro.WelcomeActivity;

import java.util.Locale;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.APP_SUPPORTED_LANGUAGES;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.EN;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_CURRENT_APP_LANGUAGE;

public final class ActivityUtils {

    private static Boolean isLocaleSet;

    private static final String OK = "OK";

    public static void invokeOkAlertMessage(Context context, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(OK, (dialog, id) -> {});
        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void initVersionNumber(AppCompatActivity activity) {
        // app version part
        TextView mAppVersionView = activity.findViewById(R.id.app_build_version);
        try {
            PackageInfo pInfo = activity.getPackageManager().getPackageInfo(activity.getPackageName(), 0);
            String version = pInfo.versionName;
            mAppVersionView.setText(String.format("%s %s%s", activity.getString(R.string.app_name),
                    activity.getString(R.string.app_version), version));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void initSloganPart(AppCompatActivity activity, int fieldId) {
        TextView mAppStyledName = activity.findViewById(fieldId);
        final SpannableStringBuilder sb = new SpannableStringBuilder(activity.getString(R.string.app_name_styled));
        final ForegroundColorSpan fcs = new ForegroundColorSpan(activity.getResources().getColor(R.color.bright_green));
        sb.setSpan(fcs, 0, 2, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        mAppStyledName.setText(sb);
    }

    public static void initLanguage(SharedPreferences sharedPreferences, AppCompatActivity activity){
        String currentLanguage = sharedPreferences.getString(KEY_CURRENT_APP_LANGUAGE, Locale.getDefault().getDisplayLanguage());
        String shortLanguage = APP_SUPPORTED_LANGUAGES.get(currentLanguage);
        if(shortLanguage == null){
            shortLanguage = EN;
        }
        Locale locale = new Locale(shortLanguage);
        Locale.setDefault(locale);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResourcesLocale(activity, locale);
        }

        updateResourcesLocaleLegacy(activity, locale);
        if (isLocaleSet == null) {
            activity.recreate();
            isLocaleSet = true;
        }
    }


    @TargetApi(Build.VERSION_CODES.N)
    public static void updateResourcesLocale(Context context, Locale locale) {
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
    }

    @SuppressWarnings("deprecation")
    public static void updateResourcesLocaleLegacy(Context context, Locale locale) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }



}
