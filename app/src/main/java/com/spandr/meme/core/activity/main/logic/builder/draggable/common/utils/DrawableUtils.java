package com.spandr.meme.core.activity.main.logic.builder.draggable.common.utils;

import android.graphics.drawable.Drawable;

/**
 * Defines method to clear state of ViewHolder. State clears each time during the creation
 * of new ViewHolder
 * @author  Spayker
 * @version 1.0
 * @since   3/6/2019
 */
public class DrawableUtils {
    private static final int[] EMPTY_STATE = new int[] {};

    public static void clearState(Drawable drawable) {
        if (drawable != null) {
            drawable.setState(EMPTY_STATE);
        }
    }
}
