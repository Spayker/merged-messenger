package com.spandr.meme.core.logic.starter;

import java.util.HashMap;
import java.util.Map;

public interface SettingsConstants {

    //// SETTINGS RELATED CONSTANTS
    String PREF_NAME = "prefs";
    String KEY_REMEMBER = "remember";
    String KEY_AUTO_LOGIN = "autoLogin";
    String KEY_USER_EMAIL_OR_PHONE = "userEmailOrPhone";
    String KEY_PASS = "password";
    String KEY_OLD_CHANGE_PASS = "oldChangePassword";
    String KEY_CHANNEL_ORDER = "channelOrder";

    // Language Codes
    String RU = "ru";
    String EN = "en";

    Map<String,String> APP_SUPPORTED_LANGUAGES = createMap();
    static Map<String, String> createMap() {
        HashMap<String, String> language = new HashMap<>();
        language.put("english", EN);
        language.put("русский", RU);
        return language;
    }

}
