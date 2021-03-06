package com.spandr.meme.core.activity.main.logic.builder.draggable.common.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.spandr.meme.core.activity.main.logic.builder.draggable.common.data.AbstractDataProvider;
import com.spandr.meme.core.activity.main.logic.builder.draggable.common.data.DataProvider;

/**
 *  Defines a concrete data provider fragment. It inits a concrete data provider and keeps a link
 *  to it
 * @author  Spayker
 * @version 1.0
 * @since   3/6/2019
 */
public class DataProviderFragment extends Fragment {

    private AbstractDataProvider mDataProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    public AbstractDataProvider getDataProvider() {
        return mDataProvider;
    }


    public DataProviderFragment initActivity(AppCompatActivity mainActivity) {
        mDataProvider = new DataProvider(mainActivity);
        return this;
    }
}
