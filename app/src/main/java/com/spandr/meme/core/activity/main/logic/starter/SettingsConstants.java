package com.spandr.meme.core.activity.main.logic.starter;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents constants that are used in global settings, in sign in, sign up scenarios
 *
 * @author  Spayker
 * @version 1.0
 * @since   3/10/2019
 */
public interface SettingsConstants {

    //// SETTINGS RELATED CONSTANTS
    String PREF_NAME = "prefs";
    String KEY_REMEMBER = "remember";
    String KEY_AUTO_LOGIN = "autoLogin";
    String KEY_USER_NAME = "userName";
    String KEY_USER_EMAIL_OR_PHONE = "userEmailOrPhone";
    String KEY_PASS = "password";
    String KEY_OLD_CHANGE_PASS = "oldChangePassword";
    String KEY_CHANNEL_ORDER = "channelOrder";
    String KEY_CURRENT_APP_LANGUAGE = "currentAppLanguage";

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
