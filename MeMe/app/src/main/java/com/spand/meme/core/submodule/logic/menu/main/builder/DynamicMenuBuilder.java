package com.spand.meme.core.submodule.logic.menu.main.builder;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.spand.meme.R;
import com.spand.meme.core.submodule.data.memory.channel.Channel;
import com.spand.meme.core.submodule.data.memory.channel.ChannelManager;
import com.spand.meme.core.submodule.data.memory.channel.TYPE;

import java.util.Set;

import static com.spand.meme.core.submodule.data.memory.channel.TYPE.CHAT;
import static com.spand.meme.core.submodule.data.memory.channel.TYPE.SOCIAL;
import static com.spand.meme.core.submodule.data.memory.channel.TYPE.EMAIL;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_ACTIVATED_CHAT_AMOUNT;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_ACTIVATED_EMAIL_AMOUNT;
import static com.spand.meme.core.submodule.logic.starter.SettingsConstants.KEY_ACTIVATED_SOCIAL_NET_AMOUNT;
import static com.spand.meme.core.submodule.ui.activity.ActivityConstants.SPACE_CHARACTER;

public class DynamicMenuBuilder implements MainMenuBuilder {

    private AppCompatActivity mainActivity;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public DynamicMenuBuilder(AppCompatActivity mainActivity) {
        this.mainActivity = mainActivity;
    }

    @Override
    public void build(SharedPreferences sharedPreferences) {
        final String loggedAs = mainActivity.getString(R.string.logged_as);
        mainActivity.setTitle(loggedAs + SPACE_CHARACTER + mAuth.getCurrentUser().getDisplayName());

        RelativeLayout mainRelativeLayout = null;//mainActivity.findViewById(R.id.main_relative_layout);

        int activatedSocialGroupAmount = sharedPreferences.getInt(KEY_ACTIVATED_SOCIAL_NET_AMOUNT, 0);
        if (activatedSocialGroupAmount > 0) {
            LinearLayout buttonLayout = createSocialGroupCategory(mainRelativeLayout);
            createChannelButtons(SOCIAL, buttonLayout);
        }

        int activatedChatGroupAmount = sharedPreferences.getInt(KEY_ACTIVATED_CHAT_AMOUNT, 0);
        if (activatedChatGroupAmount > 0) {
            LinearLayout buttonLayout = createChatGroupCategory(mainRelativeLayout);
            createChannelButtons(CHAT, buttonLayout);
        }

        int activatedEmailGroupAmount = sharedPreferences.getInt(KEY_ACTIVATED_EMAIL_AMOUNT, 0);
        if (activatedEmailGroupAmount > 0) {
            LinearLayout buttonLayout = createEmailGroupCategory(mainRelativeLayout);
            createChannelButtons(EMAIL, buttonLayout);
        }
    }

    private void createChannelButtons(TYPE type, LinearLayout buttonLayout) {
        Set<Channel> activeChannels = ChannelManager.getActiveChannels(type);
        for (Channel activeChannel : activeChannels) {
            Button button = createChannelButton(activeChannel.getName());
            buttonLayout.addView(button);
        }
    }

    private Button createChannelButton(String name) {
        Button channelButton = new Button(mainActivity);
        channelButton.setText(name);
        channelButton.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, .0f));
        channelButton.setBackgroundColor(Color.TRANSPARENT);
        Drawable icon = getButtonIcon(name);
        channelButton.setCompoundDrawablesWithIntrinsicBounds( null, icon, null, null );
        return channelButton;
    }

    private Drawable getButtonIcon(String name) {
        switch (name) {
            case "Facebook":{
                return mainActivity.getResources().getDrawable(R.mipmap.fb);
            }
            case "VKontakte":{
                return mainActivity.getResources().getDrawable(R.mipmap.vk);
            }
            case "Telegram":{
                return mainActivity.getResources().getDrawable(R.mipmap.telegram);
            }
            case "Skype":{
                return mainActivity.getResources().getDrawable(R.mipmap.skype);
            }
            case "OK":{
                return mainActivity.getResources().getDrawable(R.mipmap.ok);
            }
            case "Tumblr":{
                return mainActivity.getResources().getDrawable(R.mipmap.tumblr);
            }
            case "Discord":{
                return mainActivity.getResources().getDrawable(R.mipmap.discord);
            }
            case "Youtube":{
                return mainActivity.getResources().getDrawable(R.mipmap.youtube);
            }
            case "LinkedIn":{
                return mainActivity.getResources().getDrawable(R.mipmap.in);
            }
            case "Twitter":{
                return mainActivity.getResources().getDrawable(R.mipmap.twitter);
            }
            case "ICQ":{
                return mainActivity.getResources().getDrawable(R.mipmap.icq);
            }
            case "Instagram":{
                return mainActivity.getResources().getDrawable(R.mipmap.instagram);
            }
            case "Gmail":{
                return mainActivity.getResources().getDrawable(R.mipmap.gmail);
            }
            case "Mail.ru":{
                return mainActivity.getResources().getDrawable(R.mipmap.mail_ru);
            }
        }

        return null;
    }

    private LinearLayout createSocialGroupCategory(RelativeLayout mainRelativeLayout) {
        LinearLayout socialGroupVerticalLayout = new LinearLayout(mainActivity);
        String socialGroupVerticalLayoutText = mainActivity.getString(R.string.main_social_group_container);
        socialGroupVerticalLayout.setTag(socialGroupVerticalLayoutText);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layoutParams.setLayoutDirection(LinearLayout.VERTICAL);
        socialGroupVerticalLayout.setLayoutParams(layoutParams);
        socialGroupVerticalLayout.setPadding(5, 5, 5, 5);

        TextView mainSocialCategoryTextView = new TextView(mainActivity);
        mainSocialCategoryTextView.setTag(R.string.main_social_category_textView);
        mainSocialCategoryTextView.setId(View.generateViewId());
        mainSocialCategoryTextView.setText(R.string.social_net_group);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mainSocialCategoryTextView.setLayoutParams(params);

        LinearLayout mainSocialCategoryVerticalLayout = new LinearLayout(mainActivity);
        String mainSocialCategoryVerticalLayoutText = mainActivity.getString(R.string.main_social_category_vertical_layout);
        mainSocialCategoryVerticalLayout.setTag(mainSocialCategoryVerticalLayoutText);
        mainSocialCategoryVerticalLayout.setId(View.generateViewId());
        LinearLayout.LayoutParams mainSocialCategoryVerticalLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        mainSocialCategoryVerticalLayoutParams.setLayoutDirection(LinearLayout.VERTICAL);
        mainSocialCategoryVerticalLayoutParams.gravity = Gravity.TOP|Gravity.LEFT|Gravity.CENTER_VERTICAL;
        mainSocialCategoryVerticalLayout.setLayoutParams(mainSocialCategoryVerticalLayoutParams);
        mainSocialCategoryVerticalLayout.setPadding(0, 10, 0, 0);

        LinearLayout firstSocialHorizontalLayout = new LinearLayout(mainActivity);
        String firstSocialHorizontalLayoutText = mainActivity.getString(R.string.first_social_horizontal_layout);
        firstSocialHorizontalLayout.setTag(firstSocialHorizontalLayoutText);
        LinearLayout.LayoutParams firstSocialHorizontalLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        firstSocialHorizontalLayoutParams.setLayoutDirection(LinearLayout.HORIZONTAL);
        firstSocialHorizontalLayoutParams.gravity = Gravity.START;
        firstSocialHorizontalLayout.setLayoutParams(firstSocialHorizontalLayoutParams);

        mainSocialCategoryVerticalLayout.addView(firstSocialHorizontalLayout);
        //socialGroupVerticalLayout.addView(mainSocialCategoryTextView);
        socialGroupVerticalLayout.addView(mainSocialCategoryVerticalLayout);
        mainRelativeLayout.addView(socialGroupVerticalLayout);
        return firstSocialHorizontalLayout;
    }

    private LinearLayout createChatGroupCategory(RelativeLayout mainRelativeLayout) {
        LinearLayout chatGroupVerticalLayout = new LinearLayout(mainActivity);
        String chatGroupVerticalLayoutText = mainActivity.getString(R.string.main_chat_group_container);
        chatGroupVerticalLayout.setTag(chatGroupVerticalLayoutText);
        chatGroupVerticalLayout.setId(View.generateViewId());

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layoutParams.setLayoutDirection(LinearLayout.VERTICAL);
        chatGroupVerticalLayout.setLayoutParams(layoutParams);
        chatGroupVerticalLayout.setPadding(5, 5, 5, 5);

        TextView mainChatCategoryTextView = new TextView(mainActivity);
        mainChatCategoryTextView.setTag(R.string.main_chat_category_textView);
        mainChatCategoryTextView.setText(R.string.chat_group);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mainChatCategoryTextView.setLayoutParams(params);

        LinearLayout mainChatCategoryVerticalLayout = new LinearLayout(mainActivity);
        String mainChatCategoryVerticalLayoutText = mainActivity.getString(R.string.main_chat_category_vertical_layout);
        mainChatCategoryVerticalLayout.setTag(mainChatCategoryVerticalLayoutText);
        LinearLayout.LayoutParams mainChatCategoryVerticalLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        mainChatCategoryVerticalLayoutParams.setLayoutDirection(LinearLayout.VERTICAL);
        mainChatCategoryVerticalLayoutParams.gravity = Gravity.TOP|Gravity.LEFT|Gravity.CENTER_VERTICAL;
        mainChatCategoryVerticalLayout.setLayoutParams(mainChatCategoryVerticalLayoutParams);
        mainChatCategoryVerticalLayout.setPadding(0, 10, 0, 0);

        LinearLayout firstChatHorizontalLayout = new LinearLayout(mainActivity);
        String firstChatHorizontalLayoutText = mainActivity.getString(R.string.first_chat_horizontal_layout);
        firstChatHorizontalLayout.setTag(firstChatHorizontalLayoutText);
        LinearLayout.LayoutParams firstChatHorizontalLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        firstChatHorizontalLayoutParams.setLayoutDirection(LinearLayout.HORIZONTAL);
        firstChatHorizontalLayoutParams.gravity = Gravity.TOP|Gravity.LEFT|Gravity.CENTER_HORIZONTAL;
        firstChatHorizontalLayout.setLayoutParams(firstChatHorizontalLayoutParams);

        mainChatCategoryVerticalLayout.addView(firstChatHorizontalLayout);
        //chatGroupVerticalLayout.addView(mainChatCategoryTextView);
        chatGroupVerticalLayout.addView(mainChatCategoryVerticalLayout);
        mainRelativeLayout.addView(chatGroupVerticalLayout);
        return firstChatHorizontalLayout;
    }

    private LinearLayout createEmailGroupCategory(RelativeLayout mainRelativeLayout) {
        LinearLayout emailGroupVerticalLayout = new LinearLayout(mainActivity);
        String emailGroupVerticalLayoutText = mainActivity.getString(R.string.main_email_group_container);
        emailGroupVerticalLayout.setTag(emailGroupVerticalLayoutText);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        layoutParams.setLayoutDirection(LinearLayout.VERTICAL);
        emailGroupVerticalLayout.setLayoutParams(layoutParams);
        emailGroupVerticalLayout.setPadding(5, 5, 5, 5);

        TextView mainEmailCategoryTextView = new TextView(mainActivity);
        mainEmailCategoryTextView.setTag(R.string.main_email_category_textView);
        mainEmailCategoryTextView.setText(R.string.mail_group);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        mainEmailCategoryTextView.setLayoutParams(params);

        LinearLayout mainEmailCategoryVerticalLayout = new LinearLayout(mainActivity);
        String mainEmailCategoryVerticalLayoutText = mainActivity.getString(R.string.main_email_category_vertical_layout);
        mainEmailCategoryVerticalLayout.setTag(mainEmailCategoryVerticalLayoutText);
        LinearLayout.LayoutParams mainEmailCategoryVerticalLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        mainEmailCategoryVerticalLayoutParams.setLayoutDirection(LinearLayout.VERTICAL);
        mainEmailCategoryVerticalLayoutParams.gravity = Gravity.TOP|Gravity.LEFT|Gravity.CENTER_VERTICAL;
        mainEmailCategoryVerticalLayout.setLayoutParams(mainEmailCategoryVerticalLayoutParams);
        mainEmailCategoryVerticalLayout.setPadding(0, 10, 0, 0);

        LinearLayout firstEmailHorizontalLayout = new LinearLayout(mainActivity);
        String firstEmailHorizontalLayoutText = mainActivity.getString(R.string.first_email_horizontal_layout);
        firstEmailHorizontalLayout.setTag(firstEmailHorizontalLayoutText);
        LinearLayout.LayoutParams firstEmailHorizontalLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        firstEmailHorizontalLayoutParams.setLayoutDirection(LinearLayout.HORIZONTAL);
        firstEmailHorizontalLayoutParams.gravity = Gravity.TOP|Gravity.LEFT|Gravity.CENTER_HORIZONTAL;
        firstEmailHorizontalLayout.setLayoutParams(firstEmailHorizontalLayoutParams);

        mainEmailCategoryVerticalLayout.addView(firstEmailHorizontalLayout);
        //emailGroupVerticalLayout.addView(mainEmailCategoryTextView);
        emailGroupVerticalLayout.addView(mainEmailCategoryVerticalLayout);
        mainRelativeLayout.addView(emailGroupVerticalLayout);
        return firstEmailHorizontalLayout;
    }

}