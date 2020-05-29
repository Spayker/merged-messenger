package com.spandr.meme.core.activity.webview;

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
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class WebViewActivityTest {

    @Rule
    public IntentsTestRule<WebViewActivity> webViewIntentsTestRule =
            new IntentsTestRule<>(WebViewActivity.class);

    @Test
    public void click_Back_Button_Shows_MainActivity() {
        onView(withId(R.id.backToMainMenu)).perform(click());
        intended(hasComponent(MainActivity.class.getName()));
    }

}