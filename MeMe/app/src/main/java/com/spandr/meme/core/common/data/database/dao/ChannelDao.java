package com.spandr.meme.core.common.data.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.spandr.meme.core.common.data.database.Channel;
import com.spandr.meme.core.common.data.memory.channel.TYPE;

import java.util.List;

@Dao
public interface ChannelDao {

    @Query("SELECT * FROM channel")
    List<Channel> getAll();

    @Query("SELECT * FROM channel WHERE cid IN (:channelIds)")
    List<Channel> loadAllByIds(int[] channelIds);

    @Query("SELECT * FROM channel WHERE name LIKE :name LIMIT 1")
    Channel findByName(String name);

    @Query("SELECT * FROM channel WHERE type LIKE :type")
    List<Channel> findByType(TYPE type);

    @Insert
    void insertAll(Channel... channels);

    @Delete
    void delete(Channel channel);
}
