package com.spandr.meme.core.activity.authorization.logic.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.spandr.meme.core.activity.authorization.logic.exception.AppAuthorizationActivityException;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_PASS;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_USER_EMAIL_OR_PHONE;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_USER_NAME;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.common.ActivityConstants.EMPTY_STRING;

public class User {

    private static User instance;

    private String userName;
    private String password;
    private String emailAddress;

    @SuppressWarnings("unused")
    private User(){}

    public static User getInstance(AppCompatActivity currentActivity) {
        if(currentActivity == null){
            throw new AppAuthorizationActivityException();
        }

        if(instance == null){
            SharedPreferences sharedPreferences = currentActivity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
            String userName = sharedPreferences.getString(KEY_USER_NAME, EMPTY_STRING);
            String userEmail = sharedPreferences.getString(KEY_USER_EMAIL_OR_PHONE, EMPTY_STRING);
            String password = sharedPreferences.getString(KEY_PASS, EMPTY_STRING);
            instance = new User(userName, userEmail, password);
        }
        return instance;
    }

    public User(String emailAddress, String password) {
        this.password = password;
        this.emailAddress = emailAddress;
    }

    public User(String userName, String emailAddress, String password) {
        this.userName = userName;
        this.password = password;
        this.emailAddress = emailAddress;
        instance = this;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getEmailAddress() {
        return emailAddress;
    }
}
