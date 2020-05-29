package com.spandr.meme.core.activity.settings.channel.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Represents basic logic of switcher that displayed toward each channel in channels settings
 *
 * @author  Spayker
 * @version 1.0
 * @since   3/10/2019
 */
public class Option implements Parcelable {

    private String name;
    private boolean isSwitcher;

    Option(String name, boolean isSwitcher) {
        this.name = name;
        this.isSwitcher = isSwitcher;
    }

    private Option(Parcel in) {
        name = in.readString();
    }

    public String getName() {
        return name;
    }

    public boolean isSwitcher() {
        return isSwitcher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Option)) return false;

        Option Option = (Option) o;

        if (isSwitcher() != Option.isSwitcher()) return false;
        return getName() != null ? getName().equals(Option.getName()) : Option.getName() == null;

    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (isSwitcher() ? 1 : 0);
        return result;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Option> CREATOR = new Creator<Option>() {
        @Override
        public Option createFromParcel(Parcel in) {
            return new Option(in);
        }

        @Override
        public Option[] newArray(int size) {
            return new Option[size];
        }
    };

}
