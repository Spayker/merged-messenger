package com.spandr.meme.core.activity.settings.channel.model;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

/**
 *  Represents dto for keeping most valuable info of channels. That data will displayed later on
 *  channels settings screen
 * @author  Spayker
 * @version 1.0
 * @since   3/10/2019
 */
public class Channel extends ExpandableGroup<Option> {

  private int iconResId;

  public Channel(String title, List<Option> items, int iconResId) {
    super(title, items);
    this.iconResId = iconResId;
  }

  private int getIconResId() {
    return iconResId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Channel)) return false;

    Channel channel = (Channel) o;

    return getIconResId() == channel.getIconResId();

  }

  @Override
  public int hashCode() {
    return getIconResId();
  }
}

