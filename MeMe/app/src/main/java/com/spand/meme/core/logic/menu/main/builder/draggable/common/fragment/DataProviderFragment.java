package com.spand.meme.core.logic.menu.main.builder.draggable.common.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.spand.meme.core.logic.menu.main.builder.draggable.common.data.AbstractDataProvider;
import com.spand.meme.core.logic.menu.main.builder.draggable.common.data.DataProvider;

public class DataProviderFragment extends Fragment {

    private AbstractDataProvider mDataProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);  // keep the mDataProvider instance
        mDataProvider = new DataProvider();
    }

    public AbstractDataProvider getDataProvider() {
        return mDataProvider;
    }




}
