package com.spandr.meme.core.activity.intro.logic;

import com.spandr.meme.core.activity.authorization.LoginActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertFalse;

@RunWith(RobolectricTestRunner.class)
public class LoginActivityTest {

    @Test
    public void validateLoginFormWithEmptyLoginAndReturnFalse(){
        // given
        LoginActivity activity = Robolectric.setupActivity(LoginActivity.class);
        String password = "password";
        String login = "";

        boolean isValidated = activity.validateLoginForm(login, password);
        assertFalse(isValidated);
    }

    @Test
    public void validateLoginFormWithNullLoginAndReturnFalse(){
        // given
        LoginActivity activity = Robolectric.setupActivity(LoginActivity.class);
        String password = "password";
        String login = null;

        boolean isValidated = activity.validateLoginForm(login, password);
        assertFalse(isValidated);
    }

    @Test
    public void validateLoginFormWithNullPasswordAndReturnFalse(){
        // given
        LoginActivity activity = Robolectric.setupActivity(LoginActivity.class);
        String password = null;
        String login = "loginMeMe@gmail.com";

        boolean isValidated = activity.validateLoginForm(login, password);
        assertFalse(isValidated);
    }

    @Test
    public void validateLoginFormWithEmptyPasswordAndReturnFalse(){
        // given
        LoginActivity activity = Robolectric.setupActivity(LoginActivity.class);
        String password = "";
        String login = "loginMeMe@gmail.com";

        boolean isValidated = activity.validateLoginForm(login, password);
        assertFalse(isValidated);
    }

}
