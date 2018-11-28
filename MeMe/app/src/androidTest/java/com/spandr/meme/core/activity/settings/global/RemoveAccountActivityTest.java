package com.spandr.meme.core.activity.settings.global;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4.class)
public class RemoveAccountActivityTest {

    @Rule
    public IntentsTestRule<RemoveAccountActivity> removeAccountActivityTestRule =
            new IntentsTestRule<>(RemoveAccountActivity.class);

    @Test
    public void click_Back_Button_Shows_GlobalSettingsActivity(){
        Espresso.pressBack();
        intended(hasComponent(GlobalSettingsActivity.class.getName()));
    }

}
