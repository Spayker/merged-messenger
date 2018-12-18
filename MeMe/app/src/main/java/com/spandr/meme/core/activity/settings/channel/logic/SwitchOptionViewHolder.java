package com.spandr.meme.core.activity.settings.channel.logic;

import android.view.View;
import android.widget.Checkable;
import android.widget.Switch;

import com.spandr.meme.R;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

public class SwitchOptionViewHolder extends CheckableChildViewHolder {

  private Switch childSwitchView;

  public SwitchOptionViewHolder(View itemView) {
    super(itemView);
    childSwitchView = itemView.findViewById(R.id.list_item_switcher_artist_name);
  }

  @Override
  public Checkable getCheckable() {
    return childSwitchView;
  }

  public void setArtistName(String artistName) {
    childSwitchView.setText(artistName);
  }
}
