package com.spandr.meme.core.activity.intro.logic;

import com.spandr.meme.core.activity.intro.WelcomeActivity;
import com.spandr.meme.core.activity.intro.logic.exception.AppIntroActivityException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.fail;

@RunWith(JUnit4.class)
public class AutoLoginnerTest {

    @Test(expected = AppIntroActivityException.class)
    public void perform_Auto_Login_With_Null_Activity_Test() {
        // given
        WelcomeActivity activity = null;
        AutoLoginner autoLoginner = new AutoLoginner();

        // when
        autoLoginner.performAutoLogin(activity);

        // then
        fail("AutoLoginner, performAutoLogin: Activity can not be null");
    }

    @Test(expected = AppIntroActivityException.class)
    public void validate_Activity_With_Null_Activity_Object_Test() {
        // given
        WelcomeActivity activity = null;
        AutoLoginner autoLoginner = new AutoLoginner();

        // when
        autoLoginner.validateActivity(activity);

        // then
        fail("AutoLoginner, validateActivity: sharedPreferences is null");
    }

}
