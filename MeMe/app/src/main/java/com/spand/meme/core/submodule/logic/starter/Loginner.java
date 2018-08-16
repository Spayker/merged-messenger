package com.spand.meme.core.submodule.logic.starter;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

public class Loginner implements Starter {

    @Override
    public Boolean initApplication(SharedPreferences sharedPreferences, AppCompatActivity mainActivity) {
        initChannelManager();
        return null;
    }

}
