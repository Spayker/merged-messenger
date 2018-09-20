package com.spand.meme.core.logic.menu.main.builder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.spand.meme.R;
import com.spand.meme.core.data.memory.channel.Channel;
import com.spand.meme.core.data.memory.channel.ChannelManager;
import com.spand.meme.core.data.memory.channel.TYPE;
import com.spand.meme.core.ui.activity.webview.WebViewActivity;

import java.util.List;

import static android.graphics.Color.TRANSPARENT;
import static android.util.TypedValue.COMPLEX_UNIT_SP;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.spand.meme.core.data.memory.channel.TYPE.CHAT;
import static com.spand.meme.core.data.memory.channel.TYPE.EMAIL;
import static com.spand.meme.core.data.memory.channel.TYPE.SOCIAL;
import static com.spand.meme.core.ui.activity.ActivityConstants.HOME_URL;
import static com.spand.meme.core.ui.activity.ActivityConstants.SHALL_LOAD_URL;

public class AlphaDynamicMenuBuilder implements MainMenuBuilder {

    private static AppCompatActivity mainActivity;
    private final int BUTTON_TEXT_SIZE_IN_SP = 12;

    @SuppressLint("StaticFieldLeak")
    private static AlphaDynamicMenuBuilder instance;

    private final int BUTTON_LIMIT_IN_ROW = 4;

    private AlphaDynamicMenuBuilder() {
        instance = this;
    }

    public static MainMenuBuilder getInstance(AppCompatActivity mA) {
        if (instance == null){
            instance = new AlphaDynamicMenuBuilder();
        }
        mainActivity = mA;
        return instance;
    }

    @Override
    public void build(SharedPreferences sharedPreferences) {

        LinearLayout mainLinearLayout = mainActivity.findViewById(R.id.main_linear_layout);
        mainLinearLayout.removeAllViews();
        int activatedSocialGroupAmount = ChannelManager.getActiveChannels(SOCIAL).size();
        if (activatedSocialGroupAmount > 0) {
            createGroupCategory(mainLinearLayout, activatedSocialGroupAmount, SOCIAL);
        }

        int activatedChatGroupAmount = ChannelManager.getActiveChannels(CHAT).size();
        if (activatedChatGroupAmount > 0) {
            createGroupCategory(mainLinearLayout, activatedChatGroupAmount, CHAT);
        }

        int activatedEmailGroupAmount = ChannelManager.getActiveChannels(EMAIL).size();
        if (activatedEmailGroupAmount > 0) {
            createGroupCategory(mainLinearLayout, activatedEmailGroupAmount, EMAIL);
        }

    }

    private int createChannelButtons(TYPE type, LinearLayout buttonLayout, int lastIndex) {
        List<Channel> activeChannels = ChannelManager.getActiveChannels(type);
        for (int j = 0; j < BUTTON_LIMIT_IN_ROW && lastIndex < activeChannels.size(); j++) {
            Channel channel = activeChannels.get(lastIndex);
            Button button = createChannelButton(channel);
            buttonLayout.addView(button);
            lastIndex++;
        }
        return  lastIndex;
    }

    private Button createChannelButton(Channel channel) {
        Button channelButton = new Button(mainActivity);
        String channelName = channel.getName();
        channelButton.setText(channelName);
        channelButton.setTextSize(COMPLEX_UNIT_SP, BUTTON_TEXT_SIZE_IN_SP);
        channelButton.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        channelButton.setBackgroundColor(TRANSPARENT);
        Drawable icon = mainActivity.getResources().getDrawable(channel.getIcon().getIconId());
        channelButton.setCompoundDrawablesWithIntrinsicBounds( null, icon, null, null );
        channelButton.setOnClickListener(view -> {
            Intent intent = new Intent(mainActivity, WebViewActivity.class);
            intent.putExtra(HOME_URL, channel.getHomeUrl());
            intent.putExtra(SHALL_LOAD_URL, true);
            mainActivity.startActivity(intent);
        });
        return channelButton;
    }

    private void createGroupCategory(LinearLayout mainLinearLayout, int activatedGroupAmount, TYPE type){
        LinearLayout firstGroupVerticalLayout = new LinearLayout(mainActivity);
        LinearLayout.LayoutParams firstLayoutParams = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        firstLayoutParams.gravity = Gravity.START|Gravity.CENTER_VERTICAL;
        firstGroupVerticalLayout.setLayoutParams(firstLayoutParams);

        if(activatedGroupAmount > BUTTON_LIMIT_IN_ROW){
            int lastIndex = 0;
            for (int i = 1; i <= activatedGroupAmount/BUTTON_LIMIT_IN_ROW +1; i++) {
                ScrollView scrollView = new ScrollView(mainActivity);
                scrollView.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
                LinearLayout secondGroupHorizontalLayout = new LinearLayout(mainActivity);
                LinearLayout.LayoutParams secondGroupHorizontalLayoutParams = new LinearLayout.LayoutParams(
                        MATCH_PARENT, WRAP_CONTENT);

                secondGroupHorizontalLayoutParams.gravity = Gravity.TOP|Gravity.START;
                secondGroupHorizontalLayout.setLayoutParams(secondGroupHorizontalLayoutParams);
                secondGroupHorizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

                lastIndex = createChannelButtons(type, secondGroupHorizontalLayout, lastIndex);
                scrollView.addView(secondGroupHorizontalLayout);
                mainLinearLayout.addView(scrollView);
            }
        } else {
            LinearLayout secondGroupHorizontalLayout = new LinearLayout(mainActivity);
            LinearLayout.LayoutParams secondGroupHorizontalLayoutParams = new LinearLayout.LayoutParams(
                    MATCH_PARENT, WRAP_CONTENT);
            secondGroupHorizontalLayoutParams.gravity = Gravity.TOP|Gravity.START;
            secondGroupHorizontalLayout.setLayoutParams(secondGroupHorizontalLayoutParams);
            secondGroupHorizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            createChannelButtons(type, secondGroupHorizontalLayout, 0);
            firstGroupVerticalLayout.addView(secondGroupHorizontalLayout);
            mainLinearLayout.addView(firstGroupVerticalLayout);
        }
    }
}