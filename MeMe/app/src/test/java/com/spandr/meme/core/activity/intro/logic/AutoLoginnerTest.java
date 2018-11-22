package com.spandr.meme.core.activity.intro.logic;

import com.spandr.meme.core.activity.intro.WelcomeActivity;
import com.spandr.meme.core.activity.intro.logic.exception.AppIntroActivityException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class AutoLoginnerTest {

    @Test(expected = AppIntroActivityException.class)
    public void performAutoLoginWithNullActivityTest() {
        // given
        WelcomeActivity activity = null;
        AutoLoginner autoLoginner = new AutoLoginner();

        // when
        autoLoginner.performAutoLogin(activity);

        // then
        fail("AutoLoginner, performAutoLogin: Activity can not be null");
    }


}
