package com.spandr.meme.core.logic.menu.main.builder.draggable;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter;
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemConstants;
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange;
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder;
import com.spandr.meme.R;
import com.spandr.meme.core.logic.menu.main.builder.draggable.common.data.AbstractDataProvider;
import com.spandr.meme.core.logic.menu.main.builder.draggable.common.utils.DrawableUtils;

public class DraggableGridAdapter extends RecyclerView.Adapter<DraggableGridAdapter.CustomViewHolder>
        implements DraggableItemAdapter<DraggableGridAdapter.CustomViewHolder> {

    private static final String TAG = "DraggableGridAdapter";

    // NOTE: Make accessible with short name
    private interface Draggable extends DraggableItemConstants {
    }

    private AbstractDataProvider mProvider;

    static class CustomViewHolder extends AbstractDraggableItemViewHolder {
        private FrameLayout mContainer;
        private Button mImageButton;

        private CustomViewHolder(View v) {
            super(v);
            mContainer = v.findViewById(R.id.container);
            mImageButton = v.findViewById(R.id.channel_icon);
        }
    }

    DraggableGridAdapter(AbstractDataProvider dataProvider) {
        mProvider = dataProvider;
        setHasStableIds(true);
    }

    @Override
    public long getItemId(int position) {
        return mProvider.getItem(position).getId();
    }

    @Override
    public int getItemViewType(int position) {
        return mProvider.getItem(position).getViewType();
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        final View v = inflater.inflate(R.layout.list_grid_item, parent, false);
        return new CustomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final AbstractDataProvider.Data item = mProvider.getItem(position);

        // set text
        holder.mImageButton.setText(item.getText());

        // set icon
        Drawable icon = item.getIcon();
        holder.mImageButton.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
        holder.mImageButton.setOnClickListener(item.getOnClickListener());

        // set background resource (target view ID: container)
        final int dragState = holder.getDragStateFlags();

        if (((dragState & Draggable.STATE_FLAG_IS_UPDATED) != 0)) {
            int bgResId;

            if ((dragState & Draggable.STATE_FLAG_IS_ACTIVE) != 0) {
                bgResId = R.drawable.bg_item_dragging_active_state;

                // need to clear drawable state here to get correct appearance of the dragging item.
                DrawableUtils.clearState(holder.mContainer.getForeground());
            } else if ((dragState & Draggable.STATE_FLAG_DRAGGING) != 0) {
                bgResId = R.drawable.bg_item_dragging_state;
            } else {
                bgResId = R.drawable.bg_item_normal_state;
            }

            holder.mContainer.setBackgroundResource(bgResId);
        }
    }

    @Override
    public int getItemCount() {
        return mProvider.getCount();
    }

    @Override
    public void onMoveItem(int fromPosition, int toPosition) {
        Log.d(TAG, "onMoveItem(fromPosition = " + fromPosition + ", toPosition = " + toPosition + ")");
        mProvider.swapItem(fromPosition, toPosition);
    }

    @Override
    public boolean onCheckCanStartDrag(CustomViewHolder holder, int position, int x, int y) {
        return true;
    }

    @Override
    public ItemDraggableRange onGetItemDraggableRange(CustomViewHolder holder, int position) {
        return null;
    }

    @Override
    public boolean onCheckCanDrop(int draggingPosition, int dropPosition) {
        return true;
    }

    @Override
    public void onItemDragStarted(int position) {

    }

    @Override
    public void onItemDragFinished(int fromPosition, int toPosition, boolean result) {
        notifyDataSetChanged();
    }


}
