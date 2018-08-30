package com.spand.meme.core.submodule.logic.menu.main.builder;

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
import com.spand.meme.core.submodule.data.memory.channel.Channel;
import com.spand.meme.core.submodule.data.memory.channel.ChannelManager;
import com.spand.meme.core.submodule.data.memory.channel.TYPE;
import com.spand.meme.core.submodule.ui.activity.webview.WebViewActivity;

import java.util.List;

import static android.graphics.Color.TRANSPARENT;
import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static com.spand.meme.core.submodule.data.memory.channel.TYPE.CHAT;
import static com.spand.meme.core.submodule.data.memory.channel.TYPE.EMAIL;
import static com.spand.meme.core.submodule.data.memory.channel.TYPE.SOCIAL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.HOME_URL;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.SHALL_LOAD_URL;

public class DynamicMenuBuilder implements MainMenuBuilder {

    private AppCompatActivity mainActivity;

    @SuppressLint("StaticFieldLeak")
    private static DynamicMenuBuilder instance;

    private final int BUTTON_LIMIT_IN_ROW = 4;

    private DynamicMenuBuilder(){}

    public DynamicMenuBuilder(AppCompatActivity mainActivity) {
        this.mainActivity = mainActivity;
        instance = this;
    }

    @Override
    public void build(SharedPreferences sharedPreferences) {

        LinearLayout mainLinearLayout = mainActivity.findViewById(R.id.main_linear_layout);
        mainLinearLayout.removeAllViews();
        int activatedSocialGroupAmount = ChannelManager.getActiveChannels(SOCIAL).size();
        if (activatedSocialGroupAmount > 0) {
            int groupNameResourceId = R.string.main_menu_social_net_group;
            createGroupCategory(mainLinearLayout, activatedSocialGroupAmount, groupNameResourceId, SOCIAL);
        }

        int activatedChatGroupAmount = ChannelManager.getActiveChannels(CHAT).size();
        if (activatedChatGroupAmount > 0) {
            int groupNameResourceId = R.string.main_menu_chat_group;
            createGroupCategory(mainLinearLayout, activatedChatGroupAmount, groupNameResourceId, CHAT);
        }

        int activatedEmailGroupAmount = ChannelManager.getActiveChannels(EMAIL).size();
        if (activatedEmailGroupAmount > 0) {
            int groupNameResourceId = R.string.main_menu_mail_group;
            createGroupCategory(mainLinearLayout, activatedEmailGroupAmount, groupNameResourceId, EMAIL);
        }

    }

    public void rebuild(SharedPreferences sharedPreferences) {
        build(sharedPreferences);
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

    private void createGroupCategory(LinearLayout mainLinearLayout, int activatedGroupAmount, int groupNameResId, TYPE type){
        LinearLayout firstGroupVerticalLayout = new LinearLayout(mainActivity);
        LinearLayout.LayoutParams firstLayoutParams = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        // in order to support sdk from v16
        //firstLayoutParams.setLayoutDirection(LinearLayout.VERTICAL);
        firstLayoutParams.gravity = Gravity.START|Gravity.CENTER_VERTICAL;
        firstGroupVerticalLayout.setLayoutParams(firstLayoutParams);
        firstGroupVerticalLayout.setPadding(5, 5, 5, 5);

        TextView groupNameTextView = new TextView(mainActivity);
        groupNameTextView.setText(groupNameResId);
        LinearLayout.LayoutParams textViewLayoutParams = new LinearLayout.LayoutParams(
                MATCH_PARENT, WRAP_CONTENT);
        groupNameTextView.setLayoutParams(textViewLayoutParams);
        mainLinearLayout.addView(groupNameTextView);

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

    public static DynamicMenuBuilder getMenuBuilder() {
        return instance;
    }
}