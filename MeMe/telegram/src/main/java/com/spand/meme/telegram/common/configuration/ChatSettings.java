package com.spand.meme.telegram.common.configuration;

import com.github.badoualy.telegram.api.TelegramApp;

public class ChatSettings {

    // Get them from Telegram's console
    public static final int API_ID = 0;
    public static final String API_HASH = "fe3dd87ab4cffee3cb9a4c34406a0719";

    // What you want to appear in the "all sessions" screen
    public static final String APP_VERSION = "1.0";
    public static final String MODEL = "Model";
    public static final String SYSTEM_VERSION = "SysVer";
    public static final String LANG_CODE = "en";

    public static TelegramApp application = new TelegramApp(API_ID, API_HASH, MODEL, SYSTEM_VERSION,
                                                                APP_VERSION, LANG_CODE);

    // Phone number used for tests
    public static final String PHONE_NUMBER = "+00000000000"; // International format

}
