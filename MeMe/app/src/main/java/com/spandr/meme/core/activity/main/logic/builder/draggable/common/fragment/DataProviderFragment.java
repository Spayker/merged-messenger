package com.spandr.meme.core.activity.main.logic.builder.draggable.common.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.spandr.meme.core.activity.main.logic.builder.draggable.common.data.AbstractDataProvider;
import com.spandr.meme.core.activity.main.logic.builder.draggable.common.data.DataProvider;

public class DataProviderFragment extends Fragment {

    private AbstractDataProvider mDataProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);  // keep the mDataProvider instance

    }

    public AbstractDataProvider getDataProvider() {
        return mDataProvider;
    }


    public DataProviderFragment initActivity(AppCompatActivity mainActivity) {
        mDataProvider = new DataProvider(mainActivity);
        return this;
    }
}
