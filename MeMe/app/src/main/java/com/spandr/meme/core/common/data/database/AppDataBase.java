package com.spandr.meme.core.common.data.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.spandr.meme.core.common.data.database.dao.ChannelDao;

@Database(entities = {Channel.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract ChannelDao channelDao();

}
