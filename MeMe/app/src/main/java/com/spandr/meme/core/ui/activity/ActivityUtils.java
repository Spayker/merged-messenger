package com.spandr.meme.core.ui.activity;

import android.content.Context;
import android.support.v7.app.AlertDialog;

public final class ActivityUtils {

    private static final String OK = "OK";

    public static void invokeOkAlertMessage(Context context, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setCancelable(false)
                .setPositiveButton(OK, (dialog, id) -> {
                    //do things
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

}
