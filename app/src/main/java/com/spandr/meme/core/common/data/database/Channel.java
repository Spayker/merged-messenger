package com.spandr.meme.core.common.data.database;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.spandr.meme.core.common.data.memory.channel.ICON;
import com.spandr.meme.core.common.data.memory.channel.TYPE;

/**
 *
 *
 * @author  Spayker
 * @version 1.0
 * @since   3/7/2019
 */
@Entity
public class Channel {

    @PrimaryKey
    private int cid;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "type")
    private TYPE type;

    @ColumnInfo(name = "home_url")
    private String homeUrl;

    @ColumnInfo(name = "last_url")
    private String lastUrl;

    @ColumnInfo(name = "icon")
    private ICON icon;

    @ColumnInfo(name = "active")
    private boolean active;

    @ColumnInfo(name = "is_notifications_enabled")
    private boolean isNotificationsEnabled;

}
