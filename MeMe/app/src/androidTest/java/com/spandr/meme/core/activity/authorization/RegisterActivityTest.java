package com.spandr.meme.core.activity.authorization;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.CheckBox;

import com.spandr.meme.R;
import com.spandr.meme.core.activity.intro.WelcomeActivity;
import com.spandr.meme.core.activity.main.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.scrollTo;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class RegisterActivityTest {

    @Rule
    public IntentsTestRule<RegisterActivity> registerActivityRule =
            new IntentsTestRule<>(RegisterActivity.class);

    @Test
    public void click_Back_Button_Shows_WelcomeActivity(){
        Espresso.pressBack();
        intended(hasComponent(WelcomeActivity.class.getName()));
    }

    @Test
    public void click_Sign_Up_Button_Shows_MainActivity(){

        // new ui thread below is a workaround to make checkbox active. Otherwise espresso
        // functionality opens clicks on links that are in text of checkbox and test fails...
        RegisterActivity registryActivity = registerActivityRule.getActivity();
        registryActivity.runOnUiThread(() -> {
            CheckBox mAgreement = registryActivity.findViewById(R.id.agreement);
            mAgreement.setChecked(true);
        });

        onView(withId(R.id.register_form_name)).perform(clearText(), typeText("spayker"));
        onView(withId(R.id.register_form_email)).perform(clearText(), typeText("spykerstar@gmail.com"));
        onView(withId(R.id.register_form_password)).perform(clearText(), typeText("qwerty"));
        onView(withId(R.id.register_form_password_confirm)).perform(clearText(), typeText("qwerty"));

        onView(withId(R.id.email_sign_up_button)).perform(scrollTo()).perform(click());
        intended(hasComponent(LoginActivity.class.getName()));
    }

}
