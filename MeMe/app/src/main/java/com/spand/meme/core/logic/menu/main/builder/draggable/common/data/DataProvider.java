package com.spand.meme.core.logic.menu.main.builder.draggable.common.data;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.spand.meme.core.data.memory.channel.Channel;
import com.spand.meme.core.data.memory.channel.ChannelManager;
import com.spand.meme.core.data.memory.channel.ICON;
import com.spand.meme.core.ui.activity.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static com.spand.meme.core.data.memory.channel.TYPE.CHAT;
import static com.spand.meme.core.data.memory.channel.TYPE.EMAIL;
import static com.spand.meme.core.data.memory.channel.TYPE.SOCIAL;
import static com.spand.meme.core.logic.starter.SettingsConstants.KEY_CHANNEL_ORDER;
import static com.spand.meme.core.logic.starter.SettingsConstants.PREF_NAME;
import static com.spand.meme.core.ui.activity.ActivityConstants.EMPTY_STRING;
import static com.spand.meme.core.ui.activity.ActivityConstants.HOME_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.SHALL_LOAD_URL;

public class DataProvider extends AbstractDataProvider {

    private List<ConcreteData> mData;
    private ConcreteData mLastRemovedData;
    private int mLastRemovedPosition = -1;

    public DataProvider(AppCompatActivity mainActivity) {
        List<Channel> channels = ChannelManager.getActiveChannels(SOCIAL);
        channels.addAll(ChannelManager.getActiveChannels(CHAT));
        channels.addAll(ChannelManager.getActiveChannels(EMAIL));
        mData = new LinkedList<>();

        SharedPreferences sharedPreferences = mainActivity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String savedChannelOrder = sharedPreferences.getString(KEY_CHANNEL_ORDER, null);


        final long id = mData.size();
        final int viewType = 0;

        if(savedChannelOrder == null || savedChannelOrder.isEmpty()){
            for (int i = 0; i < channels.size(); i++) {
                String text = channels.get(i).getName();
                String homeUrl = channels.get(i).getHomeUrl();
                ICON icon = channels.get(i).getIcon();
                Drawable drawableIcon = mainActivity.getResources().getDrawable(icon.getIconId());

                View.OnClickListener clickOnListener = v -> {
                    Intent intent = new Intent(mainActivity, WebViewActivity.class);
                    intent.putExtra(HOME_URL, homeUrl);
                    intent.putExtra(SHALL_LOAD_URL, true);
                    mainActivity.startActivity(intent);
                };

                mData.add(new ConcreteData(id, viewType, drawableIcon, text, clickOnListener));

            }
        } else {
            String[] spitedChannels = savedChannelOrder.split("\\|");
            for(String channelName: spitedChannels){
                for (int i = 0; i < channels.size(); i++) {
                    if (channelName.equalsIgnoreCase(channels.get(i).getName())) {
                        String text = channels.get(i).getName();
                        String homeUrl = channels.get(i).getHomeUrl();
                        ICON icon = channels.get(i).getIcon();
                        Drawable drawableIcon = mainActivity.getResources().getDrawable(icon.getIconId());
                        View.OnClickListener clickOnListener = v -> {
                            Intent intent = new Intent(mainActivity, WebViewActivity.class);
                            intent.putExtra(HOME_URL, homeUrl);
                            intent.putExtra(SHALL_LOAD_URL, true);
                            mainActivity.startActivity(intent);
                        };
                        mData.add(new ConcreteData(id, viewType, drawableIcon, text, clickOnListener));
                        break;
                    }
                }
            }
        }
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
        //noinspection UnnecessaryLocalVariable
        final ConcreteData removedItem = mData.remove(position);

        mLastRemovedData = removedItem;
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
            mText = makeText(text);
            mClickOnListener = clickOnListener;
        }

        private static String makeText(String text) {
            final StringBuilder sb = new StringBuilder();
            sb.append(text);
            return sb.toString();
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
