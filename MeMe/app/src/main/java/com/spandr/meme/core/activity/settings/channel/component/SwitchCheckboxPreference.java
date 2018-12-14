package com.spandr.meme.core.activity.settings.channel.component;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.preference.PreferenceManager;
import android.preference.SwitchPreference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class SwitchCheckboxPreference extends SwitchPreference {

    public SwitchCheckboxPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SwitchCheckboxPreference(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

}
