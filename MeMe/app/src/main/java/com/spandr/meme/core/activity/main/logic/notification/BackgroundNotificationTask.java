package com.spandr.meme.core.activity.main.logic.notification;

import android.content.Context;
import android.os.AsyncTask;

public class BackgroundNotificationTask extends AsyncTask<Context, Void, Boolean> {

    @Override
    protected Boolean doInBackground(Context... contexts) {
        final Context context = contexts[0].getApplicationContext();
        new WebViewRunnableInitializer(context);
        return true;
    }


}
