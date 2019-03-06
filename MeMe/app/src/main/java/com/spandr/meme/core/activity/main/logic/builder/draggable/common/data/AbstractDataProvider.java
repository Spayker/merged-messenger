package com.spandr.meme.core.activity.main.logic.builder.draggable.common.data;

import android.graphics.drawable.Drawable;
import android.view.View;

/**
*
*
* @author  Spayker
* @version 1.0
* @since   3/6/2019
*/
public abstract class AbstractDataProvider {

    public static abstract class Data {
        public abstract int getId();

        public abstract boolean isSectionHeader();

        public abstract int getViewType();

        public abstract String getText();

        public abstract Drawable getIcon();

        public abstract View.OnClickListener getOnClickListener();

        public abstract void setPinned(boolean pinned);

        public abstract boolean isPinned();
    }

    public abstract int getCount();

    public abstract Data getItem(int index);

    public abstract View.OnClickListener getOnClickListener();

    public abstract void removeItem(int position);

    public abstract void moveItem(int fromPosition, int toPosition);

    public abstract void swapItem(int fromPosition, int toPosition);

    public abstract int undoLastRemoval();

}
