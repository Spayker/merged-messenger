package com.spandr.meme.core.activity.main.logic.notification;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;

public class BackgroundNotificationTask extends AsyncTask<Context, Void, Boolean> {

    private Activity mainActivity;

    private BackgroundNotificationTask(){}

    public BackgroundNotificationTask(Activity activity){
        this.mainActivity = activity;
    }

    @Override
    protected Boolean doInBackground(Context... contexts) {
        final Context context = contexts[0].getApplicationContext();
        new WebViewRunnableInitializer(context, mainActivity);
        return true;
    }


}
