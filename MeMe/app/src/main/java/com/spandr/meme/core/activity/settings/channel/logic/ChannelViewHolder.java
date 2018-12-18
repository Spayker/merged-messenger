package com.spandr.meme.core.activity.settings.channel.logic;

import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.settings.channel.model.Channel;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class ChannelViewHolder extends GroupViewHolder {

  private TextView genreName;
  private ImageView arrow;
  private ImageView icon;

  public ChannelViewHolder(View itemView) {
    super(itemView);
    genreName = itemView.findViewById(R.id.list_item_genre_name);
    arrow = itemView.findViewById(R.id.list_item_genre_arrow);
    icon = itemView.findViewById(R.id.list_item_genre_icon);
  }

  public void setGenreTitle(ExpandableGroup genre) {
    if (genre instanceof Channel) {
      genreName.setText(genre.getTitle());
      icon.setBackgroundResource(((Channel) genre).getIconResId());
    }
    if (genre instanceof MultiCheckChannel) {
      genreName.setText(genre.getTitle());
      icon.setBackgroundResource(((MultiCheckChannel) genre).getIconResId());
    }
    if (genre instanceof SingleCheckChannel) {
      genreName.setText(genre.getTitle());
      icon.setBackgroundResource(((SingleCheckChannel) genre).getIconResId());
    }
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
        new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
    rotate.setDuration(300);
    rotate.setFillAfter(true);
    arrow.setAnimation(rotate);
  }

  private void animateCollapse() {
    RotateAnimation rotate =
        new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
    rotate.setDuration(300);
    rotate.setFillAfter(true);
    arrow.setAnimation(rotate);
  }
}
