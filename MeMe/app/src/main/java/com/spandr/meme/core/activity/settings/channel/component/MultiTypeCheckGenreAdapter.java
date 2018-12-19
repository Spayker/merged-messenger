package com.spandr.meme.core.activity.settings.channel.component;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.settings.channel.logic.CheckOptionViewHolder;
import com.spandr.meme.core.activity.settings.channel.logic.ChannelViewHolder;
import com.spandr.meme.core.activity.settings.channel.logic.SwitchOptionViewHolder;
import com.thoughtbot.expandablecheckrecyclerview.ChildCheckController;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnCheckChildClickListener;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnChildCheckChangedListener;
import com.thoughtbot.expandablecheckrecyclerview.listeners.OnChildrenCheckStateChangedListener;
import com.thoughtbot.expandablecheckrecyclerview.models.CheckedExpandableGroup;
import com.thoughtbot.expandablerecyclerview.MultiTypeExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.models.ExpandableListPosition;
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;
import com.spandr.meme.core.activity.settings.channel.model.Option;

import java.util.ArrayList;
import java.util.List;

import static android.view.LayoutInflater.from;

public class MultiTypeCheckGenreAdapter
        extends MultiTypeExpandableRecyclerViewAdapter<ChannelViewHolder, ChildViewHolder>
        implements OnChildCheckChangedListener, OnChildrenCheckStateChangedListener {

    private static final String CHECKED_STATE_MAP = "child_check_controller_checked_state_map";

    private static final int SWITCHER_VIEW_TYPE = 3;
    private static final int CHECKBOX_VIEW_TYPE = 4;

    private ChildCheckController childCheckController;
    private OnCheckChildClickListener childClickListener;

    public MultiTypeCheckGenreAdapter(List<? extends ExpandableGroup> groups) {
        super(groups);
        childCheckController = new ChildCheckController(expandableList, this);
    }

    @Override
    public ChannelViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = from(parent.getContext())
                .inflate(R.layout.list_item_channel_setting, parent, false);
        return new ChannelViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_check_channel_setting, parent, false);
        CheckOptionViewHolder holder = new CheckOptionViewHolder(view);
        holder.setOnChildCheckedListener(this);
        return holder;
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder holder, int flatPosition, ExpandableGroup group,
                                      int childIndex) {
        Option option = (Option) group.getItems().get(childIndex);
        ExpandableListPosition listPosition;
        listPosition = expandableList.getUnflattenedPosition(flatPosition);
        String optionName = option.getName();
        ((CheckOptionViewHolder) holder)
                .onBindViewHolder(flatPosition, childCheckController.isChildChecked(listPosition));
        ((CheckOptionViewHolder) holder).setOptionName(optionName);
        /*((CheckOptionViewHolder) holder).initListener(optionName);*/
    }

    @Override
    public void onBindGroupViewHolder(ChannelViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {
        holder.setChannelTitle(group);
    }

    @Override
    public void onChildCheckChanged(View view, boolean checked, int flatPos) { }

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
