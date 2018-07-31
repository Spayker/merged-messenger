package com.spand.meme.core.submodule.database.model;

import android.provider.BaseColumns;

/**
 * Created by Spayker on 1/28/2018.
 */

public class GlobalSettings {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public GlobalSettings() {}

    /* Inner class that defines the table contents of System account entity */
    public static abstract class GlobalSettingsEntry implements BaseColumns {
        public static final String TABLE_NAME = "GLOBAL_SETTINGS";
        public static final String COLUMN_NAME = "NAME";
        public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
        public static final String COLUMN_DEFAULT_VALUE = "DEFAULT_VALUE";
    }

    static final String SQL_CREATE_GLOBAL_SETTINGS_ENTRY =
            "CREATE TABLE " + GlobalSettingsEntry.TABLE_NAME + " (" +
                    GlobalSettingsEntry._ID + " INTEGER PRIMARY KEY," +
                    GlobalSettingsEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    GlobalSettingsEntry.COLUMN_DESCRIPTION + TEXT_TYPE + COMMA_SEP +
                    GlobalSettingsEntry.COLUMN_DEFAULT_VALUE + TEXT_TYPE + COMMA_SEP +
                    " )";

    static final String SQL_DELETE_GLOBAL_SETTINGS_ENTRY =
            "DROP TABLE IF EXISTS " + GlobalSettingsEntry.TABLE_NAME;

}
