package com.spand.meme.core.submodule.logic.menu.main.builder;

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

import com.google.firebase.auth.FirebaseAuth;
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
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.SPACE_CHARACTER;

public class DynamicMenuBuilder implements MainMenuBuilder {

    private AppCompatActivity MAIN_ACTIVITY;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static DynamicMenuBuilder instance;

    private final int BUTTON_LIMIT_IN_ROW = 4;

    private DynamicMenuBuilder(){}

    public DynamicMenuBuilder(AppCompatActivity mainActivity) {
        MAIN_ACTIVITY = mainActivity;
        instance = this;
    }

    @Override
    public void build(SharedPreferences sharedPreferences) {
        MAIN_ACTIVITY.setTitle(mAuth.getCurrentUser().getDisplayName());

        LinearLayout mainLinearLayout = MAIN_ACTIVITY.findViewById(R.id.main_linear_layout);

        int activatedSocialGroupAmount = ChannelManager.getActiveChannels(SOCIAL).size();
        if (activatedSocialGroupAmount > 0) {
            createSocialGroupCategory(mainLinearLayout, activatedSocialGroupAmount);
        }

        int activatedChatGroupAmount = ChannelManager.getActiveChannels(CHAT).size();
        if (activatedChatGroupAmount > 0) {
            createChatGroupCategory(mainLinearLayout, activatedChatGroupAmount);
        }

        int activatedEmailGroupAmount = ChannelManager.getActiveChannels(EMAIL).size();
        if (activatedEmailGroupAmount > 0) {
            createEmailGroupCategory(mainLinearLayout, activatedEmailGroupAmount);
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
        Button channelButton = new Button(MAIN_ACTIVITY);
        String channelName = channel.getName();
        channelButton.setText(channelName);
        channelButton.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT));
        channelButton.setBackgroundColor(TRANSPARENT);
        Drawable icon = MAIN_ACTIVITY.getResources().getDrawable(channel.getIcon().getIconId());
        channelButton.setCompoundDrawablesWithIntrinsicBounds( null, icon, null, null );
        channelButton.setOnClickListener(view -> {
            Intent intent = new Intent(MAIN_ACTIVITY, WebViewActivity.class);
            intent.putExtra(HOME_URL, channel.getHomeUrl());
            MAIN_ACTIVITY.startActivity(intent);
        });
        return channelButton;
    }

    private void createSocialGroupCategory(LinearLayout mainLinearLayout, int activatedSocialGroupAmount) {
        mainLinearLayout.removeAllViews();
        LinearLayout socialGroupVerticalLayout = new LinearLayout(MAIN_ACTIVITY);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(MATCH_PARENT, WRAP_CONTENT);
        layoutParams.setLayoutDirection(LinearLayout.VERTICAL);
        layoutParams.gravity = Gravity.START|Gravity.CENTER_VERTICAL;
        socialGroupVerticalLayout.setLayoutParams(layoutParams);
        socialGroupVerticalLayout.setPadding(5, 5, 5, 5);

        TextView mainSocialCategoryTextView = new TextView(MAIN_ACTIVITY);
        mainSocialCategoryTextView.setText(R.string.social_net_group);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                MATCH_PARENT, WRAP_CONTENT);
        mainSocialCategoryTextView.setLayoutParams(params);
        mainLinearLayout.addView(mainSocialCategoryTextView);
        if(activatedSocialGroupAmount > BUTTON_LIMIT_IN_ROW){
            int lastIndex = 0;
            for (int i = 1; i <= activatedSocialGroupAmount/BUTTON_LIMIT_IN_ROW +1; i++) {
                ScrollView scrollView = new ScrollView(MAIN_ACTIVITY);
                scrollView.setLayoutParams(new ViewGroup.LayoutParams(MATCH_PARENT, WRAP_CONTENT));
                LinearLayout firstSocialHorizontalLayout = new LinearLayout(MAIN_ACTIVITY);
                LinearLayout.LayoutParams firstSocialHorizontalLayoutParams = new LinearLayout.LayoutParams(
                        MATCH_PARENT, WRAP_CONTENT);

                firstSocialHorizontalLayoutParams.gravity = Gravity.TOP|Gravity.START;
                firstSocialHorizontalLayout.setLayoutParams(firstSocialHorizontalLayoutParams);
                firstSocialHorizontalLayout.setOrientation(LinearLayout.HORIZONTAL);

                lastIndex = createChannelButtons(SOCIAL, firstSocialHorizontalLayout, lastIndex);
                scrollView.addView(firstSocialHorizontalLayout);
                mainLinearLayout.addView(scrollView);
            }
        } else {
            LinearLayout firstSocialHorizontalLayout = new LinearLayout(MAIN_ACTIVITY);
            LinearLayout.LayoutParams firstSocialHorizontalLayoutParams = new LinearLayout.LayoutParams(
                    MATCH_PARENT, WRAP_CONTENT);
            firstSocialHorizontalLayoutParams.gravity = Gravity.TOP|Gravity.START;
            firstSocialHorizontalLayout.setLayoutParams(firstSocialHorizontalLayoutParams);
            firstSocialHorizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            createChannelButtons(SOCIAL, firstSocialHorizontalLayout, 0);
            socialGroupVerticalLayout.addView(firstSocialHorizontalLayout);
            mainLinearLayout.addView(socialGroupVerticalLayout);
        }
    }

    private void createChatGroupCategory(LinearLayout mainLinearLayout, int activatedChatGroupAmount) {
        LinearLayout chatGroupVerticalLayout = new LinearLayout(MAIN_ACTIVITY);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                WRAP_CONTENT, MATCH_PARENT);
        layoutParams.setLayoutDirection(LinearLayout.VERTICAL);
        layoutParams.gravity = Gravity.START|Gravity.CENTER_VERTICAL;
        chatGroupVerticalLayout.setLayoutParams(layoutParams);
        chatGroupVerticalLayout.setPadding(5, 5, 5, 5);
        TextView mainChatCategoryTextView = new TextView(MAIN_ACTIVITY);
        mainChatCategoryTextView.setText(R.string.chat_group);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WRAP_CONTENT, MATCH_PARENT);
        mainChatCategoryTextView.setLayoutParams(params);
        mainLinearLayout.addView(mainChatCategoryTextView);
        if(activatedChatGroupAmount > BUTTON_LIMIT_IN_ROW){
            int lastIndex = 0;
            for (int i = 1; i <= activatedChatGroupAmount/BUTTON_LIMIT_IN_ROW +1; i++) {
                ScrollView scrollView = new ScrollView(MAIN_ACTIVITY);
                scrollView.setLayoutParams(new ViewGroup.LayoutParams(
                        MATCH_PARENT, WRAP_CONTENT));

                LinearLayout firstChatHorizontalLayout = new LinearLayout(MAIN_ACTIVITY);
                LinearLayout.LayoutParams firstChatHorizontalLayoutParams = new LinearLayout.LayoutParams(
                        MATCH_PARENT, WRAP_CONTENT);

                firstChatHorizontalLayoutParams.gravity = Gravity.TOP|Gravity.START;
                firstChatHorizontalLayout.setLayoutParams(firstChatHorizontalLayoutParams);
                firstChatHorizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                lastIndex = createChannelButtons(CHAT, firstChatHorizontalLayout, lastIndex);
                scrollView.addView(firstChatHorizontalLayout);
                mainLinearLayout.addView(scrollView);
            }
        } else {
            LinearLayout firstChatHorizontalLayout = new LinearLayout(MAIN_ACTIVITY);
            LinearLayout.LayoutParams firstChatHorizontalLayoutParams = new LinearLayout.LayoutParams(
                    MATCH_PARENT, WRAP_CONTENT);
            firstChatHorizontalLayoutParams.gravity = Gravity.TOP|Gravity.START;
            firstChatHorizontalLayout.setLayoutParams(firstChatHorizontalLayoutParams);
            firstChatHorizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            createChannelButtons(CHAT, firstChatHorizontalLayout, 0);
            chatGroupVerticalLayout.addView(firstChatHorizontalLayout);
            mainLinearLayout.addView(chatGroupVerticalLayout);
        }
    }

    private void createEmailGroupCategory(LinearLayout mainLinearLayout, int activatedEmailGroupAmount) {
        LinearLayout emailGroupVerticalLayout = new LinearLayout(MAIN_ACTIVITY);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                WRAP_CONTENT, MATCH_PARENT);
        layoutParams.setLayoutDirection(LinearLayout.VERTICAL);
        layoutParams.gravity = Gravity.START|Gravity.CENTER_VERTICAL;
        emailGroupVerticalLayout.setLayoutParams(layoutParams);
        emailGroupVerticalLayout.setPadding(5, 5, 5, 5);

        TextView mainEmailCategoryTextView = new TextView(MAIN_ACTIVITY);
        mainEmailCategoryTextView.setText(R.string.mail_group);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(WRAP_CONTENT, WRAP_CONTENT);
        mainEmailCategoryTextView.setLayoutParams(params);
        mainLinearLayout.addView(mainEmailCategoryTextView);
        if(activatedEmailGroupAmount > BUTTON_LIMIT_IN_ROW){
            int lastIndex = 0;
            for (int i = 1; i <= activatedEmailGroupAmount/BUTTON_LIMIT_IN_ROW +1; i++) {
                ScrollView scrollView = new ScrollView(MAIN_ACTIVITY);
                scrollView.setLayoutParams(new ViewGroup.LayoutParams(
                        MATCH_PARENT, WRAP_CONTENT));

                LinearLayout firstEmailHorizontalLayout = new LinearLayout(MAIN_ACTIVITY);
                LinearLayout.LayoutParams firstEmailHorizontalLayoutParams = new LinearLayout.LayoutParams(
                        MATCH_PARENT, WRAP_CONTENT);

                firstEmailHorizontalLayoutParams.gravity = Gravity.TOP|Gravity.START;
                firstEmailHorizontalLayout.setLayoutParams(firstEmailHorizontalLayoutParams);
                firstEmailHorizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
                lastIndex = createChannelButtons(EMAIL, firstEmailHorizontalLayout, lastIndex);
                scrollView.addView(firstEmailHorizontalLayout);
                mainLinearLayout.addView(scrollView);
            }
        } else {
            LinearLayout firstEmailHorizontalLayout = new LinearLayout(MAIN_ACTIVITY);
            LinearLayout.LayoutParams firstEmailHorizontalLayoutParams = new LinearLayout.LayoutParams(
                    MATCH_PARENT, WRAP_CONTENT);
            firstEmailHorizontalLayoutParams.gravity = Gravity.TOP|Gravity.START;
            firstEmailHorizontalLayout.setLayoutParams(firstEmailHorizontalLayoutParams);
            firstEmailHorizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            createChannelButtons(EMAIL, firstEmailHorizontalLayout, 0);
            emailGroupVerticalLayout.addView(firstEmailHorizontalLayout);
            mainLinearLayout.addView(emailGroupVerticalLayout);
        }
    }

    public static DynamicMenuBuilder getMenuBuilder() {
        return instance;
    }
}