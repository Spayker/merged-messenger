package com.spand.meme.core.model;

import android.provider.BaseColumns;

/**
 * Created by Spayker on 1/28/2018.
 */

public final class SystemAccount {

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    public SystemAccount() {}

    /* Inner class that defines the table contents of System account entity */
    public static abstract class SystemAccountEntry implements BaseColumns {
        public static final String TABLE_NAME = "SYSTEM_ACCOUNT";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_LOGIN = "login";
        public static final String COLUMN_PASSWORD = "password";
    }

    static final String SQL_CREATE_SYSTEM_ACCOUNT_ENTRY =
            "CREATE TABLE " + SystemAccountEntry.TABLE_NAME + " (" +
                    SystemAccountEntry._ID + " INTEGER PRIMARY KEY," +
                    SystemAccountEntry.COLUMN_NAME + TEXT_TYPE + COMMA_SEP +
                    SystemAccountEntry.COLUMN_LOGIN + TEXT_TYPE + COMMA_SEP +
                    SystemAccountEntry.COLUMN_PASSWORD + TEXT_TYPE + COMMA_SEP +
            " )";

    static final String SQL_DELETE_SYSTEM_ACCOUNT_ENTRY =
            "DROP TABLE IF EXISTS " + SystemAccountEntry.TABLE_NAME;

}
