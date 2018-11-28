package com.spandr.meme.core.activity.authorization;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.spandr.meme.core.activity.intro.WelcomeActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

@RunWith(AndroidJUnit4.class)
public class LoginActivityTest {

    @Rule
    public IntentsTestRule<LoginActivity> loginActivityRule =
            new IntentsTestRule<>(LoginActivity.class);

    @Test
    public void click_Back_Button_Shows_LoginActivity(){
        Espresso.pressBack();
        intended(hasComponent(WelcomeActivity.class.getName()));
    }

}