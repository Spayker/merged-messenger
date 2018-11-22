package com.spandr.meme.core.activity.authorization;

import com.spandr.meme.core.activity.authorization.RegisterActivity;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertFalse;

@RunWith(RobolectricTestRunner.class)
public class RegisterActivityTest {

    @Test
    public void validateFormWithEmptyLoginAndReturnFalse(){
        // given
        RegisterActivity activity = Robolectric.setupActivity(RegisterActivity.class);
        String password = "password";
        String confirmPassword = "password";
        String login = "";

        boolean isValidated = activity.validateRegisterForm(login, password, confirmPassword);
        assertFalse(isValidated);
    }

    @Test
    public void validateFormWithNullLoginAndReturnFalse(){
        // given
        RegisterActivity activity = Robolectric.setupActivity(RegisterActivity.class);
        String password = "password";
        String confirmPassword = "password";
        String login = null;

        boolean isValidated = activity.validateRegisterForm(login, password, confirmPassword);
        assertFalse(isValidated);
    }

    @Test
    public void validateFormWithNullPasswordAndReturnFalse(){
        // given
        RegisterActivity activity = Robolectric.setupActivity(RegisterActivity.class);
        String password = null;
        String confirmPassword = "password";
        String login = "loginMeMe@gmail.com";

        boolean isValidated = activity.validateRegisterForm(login, password, confirmPassword);
        assertFalse(isValidated);
    }

    @Test
    public void validateFormWithEmptyPasswordAndReturnFalse(){
        // given
        RegisterActivity activity = Robolectric.setupActivity(RegisterActivity.class);
        String password = "";
        String confirmPassword = "password";
        String login = "loginMeMe@gmail.com";

        boolean isValidated = activity.validateRegisterForm(login, password, confirmPassword);
        assertFalse(isValidated);
    }

    @Test
    public void validateFormWithNullConfirmPasswordAndReturnFalse(){
        // given
        RegisterActivity activity = Robolectric.setupActivity(RegisterActivity.class);
        String password = "password";
        String confirmPassword = null;
        String login = "loginMeMe@gmail.com";

        boolean isValidated = activity.validateRegisterForm(login, password, confirmPassword);
        assertFalse(isValidated);
    }

    @Test
    public void validateFormWithEmptyConfirmPasswordAndReturnFalse(){
        // given
        RegisterActivity activity = Robolectric.setupActivity(RegisterActivity.class);
        String password = "password";
        String confirmPassword = "";
        String login = "loginMeMe@gmail.com";

        boolean isValidated = activity.validateRegisterForm(login, password, confirmPassword);
        assertFalse(isValidated);
    }

}
