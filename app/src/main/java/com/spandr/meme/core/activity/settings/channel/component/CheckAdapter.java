package com.spandr.meme.core.activity.settings.channel.component;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckedTextView;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.settings.channel.logic.ChannelViewHolder;
import com.spandr.meme.core.activity.settings.channel.logic.CheckOptionViewHolder;
import com.spandr.meme.core.activity.settings.channel.model.Option;
import com.spandr.meme.core.common.data.memory.channel.Channel;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnChildCheckChangedListener;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnChildrenCheckStateChangedListener;
import com.thoughtbot.expandablerecyclerview.MultiTypeExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static android.view.LayoutInflater.from;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static com.spandr.meme.core.common.data.memory.channel.DataChannelManager.getChannelByName;
import static com.spandr.meme.core.common.util.SettingsUtils.getChannelNotificationValueIdByName;

/**
 *  Declares check adapter logic that keeps main handling methods for proper initialization
 *  channel setting items in channel settings activity
 * @author  Spayker
 * @version 1.0
 * @since   3/10/2019
 */
public class CheckAdapter
        extends MultiTypeExpandableRecyclerViewAdapter<ChannelViewHolder, ChildViewHolder>
        implements OnChildCheckChangedListener, OnChildrenCheckStateChangedListener {

    private static final String CHECKED_STATE_MAP = "child_check_controller_checked_state_map";

    private static final int SWITCHER_VIEW_TYPE = 3;
    private static final int CHECKBOX_VIEW_TYPE = 4;

    private AppCompatActivity activity;


    public CheckAdapter(List<? extends ExpandableGroup> groups, AppCompatActivity activity) {
        super(groups);
        this.activity = activity;
    }

    @Override
    public ChannelViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = from(parent.getContext())
                .inflate(R.layout.list_item_channel_setting, parent, false);
        return new ChannelViewHolder(view, activity);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_check_channel_setting, parent, false);

        FrameLayout viewGroupFrameLayout = (FrameLayout) parent.getChildAt(parent.getChildCount() - 1);
        TextView channelTextView = (TextView) viewGroupFrameLayout.getChildAt(1);
        String channelName = channelTextView.getText().toString();

        FrameLayout frameLayout = (FrameLayout)view;
        CheckedTextView child = (CheckedTextView) frameLayout.getChildAt(frameLayout.getChildCount()-1);
        child.setTag(channelName);

        CheckOptionViewHolder holder = new CheckOptionViewHolder(view);
        holder.setOnChildCheckedListener(this);
        return holder;
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int flatPosition, ExpandableGroup group,
                                      int childIndex) {
        Option option = (Option) group.getItems().get(childIndex);
        String optionName = option.getName();
        String key = getChannelNotificationValueIdByName(activity, group.getTitle());
        SharedPreferences sharedPreferences = activity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        boolean isNotificationActivated = sharedPreferences.getBoolean(key, false);
        ((CheckOptionViewHolder) holder)
                .onBindViewHolder(flatPosition, isNotificationActivated);
        ((CheckOptionViewHolder) holder).setOptionName(optionName);
    }

    @Override
    public void onBindGroupViewHolder(ChannelViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {
        holder.setChannelTitle(group);
        holder.setStateValue(group);
        holder.setSwitcherChangeListener(group);
    }

    @Override
    public void onChildCheckChanged(View view, boolean checked, int flatPos) {
        FrameLayout frameLayout = (FrameLayout)view;
        CheckedTextView child = (CheckedTextView) frameLayout.getChildAt(frameLayout.getChildCount()-1);
        String channelName = child.getTag().toString();

        Channel channel = getChannelByName(channelName);
        Objects.requireNonNull(channel).setActive(checked);
    }

    @Override
    public void updateChildrenCheckState(int firstChildFlattenedIndex, int numChildren) {
        notifyItemRangeChanged(firstChildFlattenedIndex, numChildren);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(CHECKED_STATE_MAP, new ArrayList(expandableList.groups));
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState == null || !savedInstanceState.containsKey(CHECKED_STATE_MAP)) {
            return;
        }
        expandableList.groups = savedInstanceState.getParcelableArrayList(CHECKED_STATE_MAP);
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public boolean isChild(int viewType) {
        return viewType == SWITCHER_VIEW_TYPE || viewType == CHECKBOX_VIEW_TYPE;
    }

    @Override
    public int getChildViewType(int position, ExpandableGroup group, int childIndex) {
        if (((Option) (group).getItems().get(childIndex)).isSwitcher()) {
            return SWITCHER_VIEW_TYPE;
        } else {
            return CHECKBOX_VIEW_TYPE;
        }
    }
}
