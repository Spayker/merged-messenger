package com.spandr.meme.core.activity.main.logic.updater.async;

import android.os.AsyncTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 *  Defines task wrapper that will be run separately in background when application will load main screen
 *
 * @author  Spayker
 * @version 1.0
 * @since   3/10/2019
 */
public class GetLatestVersion  extends AsyncTask<String, String, String> {

    private final String APP_LINK = "https://play.google.com/store/apps/details?id=com.spandr.meme";
    private final String versionElement = "BgcNfc";
    private final int versionElementPos = 3;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String latestVersion;
        try {
            Document doc = Jsoup.connect(APP_LINK).get();
            Element element = doc.getElementsByClass(versionElement).get(versionElementPos);
            latestVersion = element.parent().children().get(1).children().text();
        } catch (Exception e) {
            e.printStackTrace();
            latestVersion = null;
        }
        return latestVersion;
    }
}