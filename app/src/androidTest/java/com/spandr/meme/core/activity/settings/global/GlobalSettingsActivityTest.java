package com.spandr.meme.core.activity.settings.global;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class GlobalSettingsActivityTest {

    @Rule
    public IntentsTestRule<GlobalSettingsActivity> globalSettingsActivityTestRule =
            new IntentsTestRule<>(GlobalSettingsActivity.class);

    @Test
    public void click_ChangePassword_Button_Shows_changePasswordActivity() {
        onView(withText(R.string.change_password_action_change_password)).perform(click());
        intended(hasComponent(ChangePasswordActivity.class.getName()));
    }

    @Test
    public void click_RemoveAccount_Button_Shows_RemoveAccountActivity() {
        onView(withText(R.string.remove_account_action_remove_account)).perform(click());
        intended(hasComponent(RemoveAccountActivity.class.getName()));
    }

    @Test
    public void click_Back_Button_Shows_MainActivity(){
        Espresso.pressBack();
        intended(hasComponent(MainActivity.class.getName()));
    }



}
