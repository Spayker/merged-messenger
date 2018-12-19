package com.spandr.meme.core.activity.settings.channel.logic;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.settings.channel.model.Channel;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.ABSOLUTE;
import static android.view.animation.Animation.RELATIVE_TO_PARENT;
import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class ChannelViewHolder extends GroupViewHolder {

    private TextView channelName;
    private ImageView arrow;
    private ImageView icon;
    private Switch switcher;

    public ChannelViewHolder(View itemView) {
        super(itemView);
        channelName = itemView.findViewById(R.id.list_item_channel_name);
        arrow = itemView.findViewById(R.id.list_item_channel_arrow);
        icon = itemView.findViewById(R.id.list_item_channel_icon);
        switcher = itemView.findViewById(R.id.list_item_channel_switcher);
    }

    public void setChannelTitle(ExpandableGroup channel) {
        channelName.setText(channel.getTitle());
        icon.setBackgroundResource(((SingleCheckChannel) channel).getIconResId());
    }

    public void setSwitcherListener() {
        switcher.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            }
        });
    }

    @Override
    public void expand() {
        animateExpand();
    }

    @Override
    public void collapse() {
        animateCollapse();
    }

    private void animateExpand() {
        RotateAnimation rotate =
                new RotateAnimation(360, 180, RELATIVE_TO_SELF, .5f, RELATIVE_TO_SELF, .5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.startAnimation(rotate);
    }

    private void animateCollapse() {
        RotateAnimation rotate =
                new RotateAnimation(180, 360, RELATIVE_TO_SELF, .5f, RELATIVE_TO_SELF, .5f);
        rotate.setDuration(300);
        rotate.setFillAfter(true);
        arrow.startAnimation(rotate);
    }
}
