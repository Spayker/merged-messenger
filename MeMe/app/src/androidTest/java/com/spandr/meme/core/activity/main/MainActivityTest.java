package com.spandr.meme.core.activity.main;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.authorization.LoginActivity;
import com.spandr.meme.core.activity.main.logic.builder.draggable.common.data.DataProvider;
import com.spandr.meme.core.activity.settings.channel.EditChannelsActivity;
import com.spandr.meme.core.activity.settings.global.GlobalSettingsActivity;
import com.spandr.meme.core.activity.webview.WebViewActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.init;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.release;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    private static final int ACCEPTABLE_CHANNEL_ICON_AMOUNT_ON_SCREEN = 15;

    @Rule
    public IntentsTestRule<MainActivity> mainIntentTestRule =
            new IntentsTestRule<>(MainActivity.class);


    /*
     * Remark: please do not keep app version upper than it exists on play market at the moment
     * Otherwise it brings exception when main activity runs and wants to display update dialog
     * */
    @Test
    public void click_Edit_Channels_Button_Shows_EditChannelActivity() {
        onView(withId(R.id.add_new_channel_button)).perform(click());
        intended(hasComponent(EditChannelsActivity.class.getName()));
    }

    @Test
    public void click_Global_Settings_Shows_GlobalSettingsActivity() {
        onView(withId(R.id.action_settings)).perform(click());
        intended(hasComponent(GlobalSettingsActivity.class.getName()));
    }

    @Test
    public void click_Each_Channel_From_Half_Available_Shows_WebViewActivity() {
        MainActivity targetContext = mainIntentTestRule.getActivity();
        DataProvider dataProvider = (DataProvider) targetContext.getDataProvider();
        //int displayedChannelAmount = dataProvider.getCount();
        for (int i = 0; i != ACCEPTABLE_CHANNEL_ICON_AMOUNT_ON_SCREEN; i++) {
            onView(withText(dataProvider.getItem(i).getText())).perform(click());
            intended(hasComponent(WebViewActivity.class.getName()));
            release();
            Espresso.pressBack();
            init();
        }
    }

    @Test
    public void click_Back_Button_Shows_LoginActivity() {
        Espresso.pressBack();
        intended(hasComponent(LoginActivity.class.getName()));
    }
}
