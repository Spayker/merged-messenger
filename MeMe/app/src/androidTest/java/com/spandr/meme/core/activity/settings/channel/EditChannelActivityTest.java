package com.spandr.meme.core.activity.settings.channel;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.spandr.meme.core.activity.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4.class)
public class EditChannelActivityTest {

    @Rule
    public IntentsTestRule <EditChannelsActivity> editChannelActivityTestRule =
            new IntentsTestRule<>(EditChannelsActivity.class);

    @Test
    public void click_Back_Button_Shows_MainActivity(){
        Espresso.pressBack();
        intended(hasComponent(MainActivity.class.getName()));
    }

}
