package com.spand.bridgecom.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.spand.bridgecom.model.GlobalSettings.SQL_CREATE_GLOBAL_SETTINGS_ENTRY;
import static com.spand.bridgecom.model.GlobalSettings.SQL_DELETE_GLOBAL_SETTINGS_ENTRY;

/**
 * Created by Spayker on 1/28/2018.
 */

public final class GlobalSettingsDbHelper extends SQLiteOpenHelper {

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "BridgeCom.db";

    public GlobalSettingsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_GLOBAL_SETTINGS_ENTRY);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_GLOBAL_SETTINGS_ENTRY);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }




}
