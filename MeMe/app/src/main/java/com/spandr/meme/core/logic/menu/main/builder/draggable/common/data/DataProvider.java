package com.spandr.meme.core.logic.menu.main.builder.draggable.common.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.spandr.meme.core.data.memory.channel.Channel;
import com.spandr.meme.core.data.memory.channel.ChannelManager;
import com.spandr.meme.core.data.memory.channel.ICON;
import com.spandr.meme.core.ui.activity.webview.WebViewActivity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static com.spandr.meme.core.data.memory.channel.TYPE.CHAT;
import static com.spandr.meme.core.data.memory.channel.TYPE.EMAIL;
import static com.spandr.meme.core.data.memory.channel.TYPE.INFO_SERVICE;
import static com.spandr.meme.core.data.memory.channel.TYPE.SOCIAL;
import static com.spandr.meme.core.data.memory.channel.TYPE.VIDEO_SERVICE;
import static com.spandr.meme.core.logic.LogicContants.CHANNEL_SPLITTER;
import static com.spandr.meme.core.logic.starter.SettingsConstants.KEY_CHANNEL_ORDER;
import static com.spandr.meme.core.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.ui.activity.webview.logic.WebViewConstants.CHANNEL_NAME;
import static com.spandr.meme.core.ui.activity.webview.logic.WebViewConstants.EMPTY_STRING;
import static com.spandr.meme.core.ui.activity.webview.logic.WebViewConstants.HOME_URL;

public class DataProvider extends AbstractDataProvider {

    private List<ConcreteData> mData;
    private ConcreteData mLastRemovedData;
    private int mLastRemovedPosition = -1;

    public DataProvider(AppCompatActivity mainActivity) {
        List<Channel> channels = ChannelManager.getActiveChannels(SOCIAL);
        channels.addAll(ChannelManager.getActiveChannels(CHAT));
        channels.addAll(ChannelManager.getActiveChannels(VIDEO_SERVICE));
        channels.addAll(ChannelManager.getActiveChannels(INFO_SERVICE));
        channels.addAll(ChannelManager.getActiveChannels(EMAIL));
        mData = new LinkedList<>();

        SharedPreferences sharedPreferences = mainActivity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String savedChannelOrder = sharedPreferences.getString(KEY_CHANNEL_ORDER, null);


        final long id = mData.size();
        final int viewType = 0;

        if(savedChannelOrder == null || savedChannelOrder.isEmpty()){
            for (int i = 0; i < channels.size(); i++) {
                Channel channel = channels.get(i);
                String text = channel.getName();
                String homeUrl = channel.getHomeUrl();
                ICON icon = channel.getIcon();
                Drawable drawableIcon = mainActivity.getResources().getDrawable(icon.getIconId());

                View.OnClickListener clickOnListener = v -> {
                    Intent intent = new Intent(mainActivity, WebViewActivity.class);
                    intent.putExtra(CHANNEL_NAME, text);
                    intent.putExtra(HOME_URL, homeUrl);
                    mainActivity.startActivity(intent);
                };
                mData.add(new ConcreteData(id, viewType, drawableIcon, text, clickOnListener));
            }
        } else {
            String[] spitedChannels = savedChannelOrder.split(CHANNEL_SPLITTER);
            for(String channelName: spitedChannels){
                for (int i = 0; i < channels.size(); i++) {
                    if (channelName.equalsIgnoreCase(channels.get(i).getName())) {
                        Channel channel = channels.get(i);
                        String text = channel.getName();
                        String homeUrl = channel.getHomeUrl();
                        ICON icon = channel.getIcon();
                        Drawable drawableIcon = mainActivity.getResources().getDrawable(icon.getIconId());
                        View.OnClickListener clickOnListener = v -> {
                            Intent intent = new Intent(mainActivity, WebViewActivity.class);
                            intent.putExtra(CHANNEL_NAME, text);
                            intent.putExtra(HOME_URL, homeUrl);
                            mainActivity.startActivity(intent);
                        };
                        mData.add(new ConcreteData(id, viewType, drawableIcon, text, clickOnListener));
                        break;
                    }
                }
            }
        }
        Drawable transparentDrawable = new ColorDrawable(Color.TRANSPARENT);
        mData.add(new ConcreteData(id, viewType, transparentDrawable, EMPTY_STRING, null));
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Data getItem(int index) {
        if (index < 0 || index >= getCount()) {
            throw new IndexOutOfBoundsException("index = " + index);
        }

        return mData.get(index);
    }

    @Override
    public View.OnClickListener getOnClickListener() {
        return v -> { };
    }

    @Override
    public int undoLastRemoval() {
        if (mLastRemovedData != null) {
            int insertedPosition;
            if (mLastRemovedPosition >= 0 && mLastRemovedPosition < mData.size()) {
                insertedPosition = mLastRemovedPosition;
            } else {
                insertedPosition = mData.size();
            }

            mData.add(insertedPosition, mLastRemovedData);

            mLastRemovedData = null;
            mLastRemovedPosition = -1;

            return insertedPosition;
        } else {
            return -1;
        }
    }

    @Override
    public void moveItem(int fromPosition, int toPosition) {
        if (fromPosition == toPosition) {
            return;
        }

        final ConcreteData item = mData.remove(fromPosition);

        mData.add(toPosition, item);
        mLastRemovedPosition = -1;
    }

    @Override
    public void swapItem(int fromPosition, int toPosition) {
        if (fromPosition == toPosition) {
            return;
        }

        Collections.swap(mData, toPosition, fromPosition);
        mLastRemovedPosition = -1;
    }

    @Override
    public void removeItem(int position) {
        mLastRemovedData = mData.remove(position);
        mLastRemovedPosition = position;
    }

    public static final class ConcreteData extends Data {

        private final long mId;
        private final String mText;
        private final Drawable mIcon;
        private final int mViewType;
        private boolean mPinned;
        private View.OnClickListener mClickOnListener;

        ConcreteData(long id, int viewType, Drawable icon, String text, View.OnClickListener clickOnListener) {
            mId = id;
            mViewType = viewType;
            mIcon = icon;
            mText = text;
            mClickOnListener = clickOnListener;
        }

        @Override
        public boolean isSectionHeader() {
            return false;
        }

        @Override
        public int getViewType() {
            return mViewType;
        }

        @Override
        public long getId() {
            return mId;
        }

        @Override
        public String toString() {
            return mText;
        }

        @Override
        public String getText() {
            return mText;
        }

        @Override
        public Drawable getIcon() {
            return mIcon;
        }

        @Override
        public View.OnClickListener getOnClickListener() {
            return mClickOnListener;
        }

        @Override
        public boolean isPinned() {
            return mPinned;
        }

        @Override
        public void setPinned(boolean pinned) {
            mPinned = pinned;
        }
    }

}
