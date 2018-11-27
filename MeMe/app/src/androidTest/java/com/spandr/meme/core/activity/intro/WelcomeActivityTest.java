package com.spandr.meme.core.activity.intro;

import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.authorization.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class WelcomeActivityTest {

    @Rule
    public IntentsTestRule <WelcomeActivity> mLoginActivityActivityTestRule =
            new IntentsTestRule<>(WelcomeActivity.class);

    @Test
    public void clickLoginButton_ShowsSnackBarRightCredentials() {
        onView(withId(R.id.sign_button)).perform(click());
        intended(hasComponent(LoginActivity.class.getName()));
    }

}