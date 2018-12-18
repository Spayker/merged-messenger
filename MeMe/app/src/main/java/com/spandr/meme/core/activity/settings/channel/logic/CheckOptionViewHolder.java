package com.spandr.meme.core.activity.settings.channel.logic;

import android.view.View;
import android.widget.Checkable;
import android.widget.CheckedTextView;

import com.spandr.meme.R;
import com.thoughtbot.expandablecheckrecyclerview.viewholders.CheckableChildViewHolder;

public class CheckOptionViewHolder extends CheckableChildViewHolder {

    private CheckedTextView childCheckedTextView;

    public CheckOptionViewHolder(View itemView) {
        super(itemView);
        childCheckedTextView = itemView.findViewById(R.id.list_item_check_artist_name);
    }

    @Override
    public Checkable getCheckable() {
        return childCheckedTextView;
    }

    public void setArtistName(String artistName) {
        childCheckedTextView.setText(artistName);
    }

}
