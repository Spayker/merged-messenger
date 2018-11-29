package com.spandr.meme.core.activity.authorization;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.intro.WelcomeActivity;
import com.spandr.meme.core.activity.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

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

    @Test
    public void click_Sign_In_Button_Shows_MainActivity(){

        onView(withId(R.id.login_form_email))
                .perform(clearText(), typeText("spykerstar@gmail.com"));

        onView(withId(R.id.login_form_password))
                .perform(clearText(), typeText("qwerty"));

        onView(withId(R.id.email_sign_in_button)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }
}