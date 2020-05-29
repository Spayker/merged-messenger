package com.spandr.meme.core.activity.settings.channel.logic;

import android.os.Parcel;

import com.thoughtbot.expandablecheckrecyclerview.models.SingleCheckExpandableGroup;

import java.util.List;

/**
 *  Defines Single check setting structure that represents channel setting category
 *
 * @author  Spayker
 * @version 1.0
 * @since   3/10/2019
 */
public class SingleCheckChannel extends SingleCheckExpandableGroup {

  private int iconResId;

  public SingleCheckChannel(String title, List items, int iconResId) {
    super(title, items);
    this.iconResId = iconResId;
  }

  private SingleCheckChannel(Parcel in) {
    super(in);
    iconResId = in.readInt();
  }

  int getIconResId() {
    return iconResId;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    super.writeToParcel(dest, flags);
    dest.writeInt(iconResId);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<SingleCheckChannel> CREATOR = new Creator<SingleCheckChannel>() {
    @Override
    public SingleCheckChannel createFromParcel(Parcel in) {
      return new SingleCheckChannel(in);
    }

    @Override
    public SingleCheckChannel[] newArray(int size) {
      return new SingleCheckChannel[size];
    }
  };
}
