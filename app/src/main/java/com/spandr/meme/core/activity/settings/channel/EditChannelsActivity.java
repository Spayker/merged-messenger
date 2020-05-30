package com.spandr.meme.core.activity.settings.channel;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.main.MainActivity;
import com.spandr.meme.core.activity.settings.channel.component.CheckAdapter;

import static com.spandr.meme.core.activity.settings.channel.model.ChannelDataFactory.makeSocialChannels;

public class EditChannelsActivity extends AppCompatActivity {

    // tag field is used for logging sub system to identify from coming logs were created
    private static final String TAG = EditChannelsActivity.class.getSimpleName();

    private CheckAdapter adapter;

    /**
     *  Perform initialization of all fragments of current activity.
     *  @param savedInstanceState an instance of Bundle instance
     *                            (A mapping from String keys to various Parcelable values)
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_channels);
        setTitle(R.string.channel_setting_title);

        RecyclerView recyclerView = findViewById(R.id.edit_channel_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        adapter = new CheckAdapter(makeSocialChannels(this), this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        adapter.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        adapter.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
