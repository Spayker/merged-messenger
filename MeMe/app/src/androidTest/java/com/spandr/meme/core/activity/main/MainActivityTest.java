package com.spandr.meme.core.activity.main;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.authorization.RegisterActivity;
import com.spandr.meme.core.activity.settings.channel.EditChannelsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public IntentsTestRule<MainActivity> mainActivityTestRule =
            new IntentsTestRule<>(MainActivity.class);

    @Test
    public void click_Sign_In_Button_Shows_LoginActivity() {
        onView(withId(R.id.add_new_channel_button)).perform(click());
        intended(hasComponent(EditChannelsActivity.class.getName()));
    }


}
