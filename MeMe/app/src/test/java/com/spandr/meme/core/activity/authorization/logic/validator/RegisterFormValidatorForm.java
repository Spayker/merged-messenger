package com.spandr.meme.core.activity.authorization.logic.validator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.Assert.assertFalse;

@RunWith(JUnit4.class)
public class RegisterFormValidatorForm {

    @Test
    public void validateFormWithEmptyLoginAndReturnFalse(){
        // given
        /*RegisterActivity activity = Robolectric.setupActivity(RegisterActivity.class);
        String password = "password";
        String confirmPassword = "password";
        String login = "";

        boolean isValidated = activity.validateRegisterForm(login, password, confirmPassword);
        assertFalse(isValidated);*/
    }

    @Test
    public void validateFormWithNullLoginAndReturnFalse(){
        // given
        /*RegisterActivity activity = Robolectric.setupActivity(RegisterActivity.class);
        String password = "password";
        String confirmPassword = "password";
        String login = null;

        boolean isValidated = activity.validateRegisterForm(login, password, confirmPassword);
        assertFalse(isValidated);*/
    }

    @Test
    public void validateFormWithNullPasswordAndReturnFalse(){
        // given
        /*RegisterActivity activity = Robolectric.setupActivity(RegisterActivity.class);
        String password = null;
        String confirmPassword = "password";
        String login = "loginMeMe@gmail.com";

        boolean isValidated = activity.validateRegisterForm(login, password, confirmPassword);
        assertFalse(isValidated);*/
    }

    @Test
    public void validateFormWithEmptyPasswordAndReturnFalse(){
        // given
        /*RegisterActivity activity = Robolectric.setupActivity(RegisterActivity.class);
        String password = "";
        String confirmPassword = "password";
        String login = "loginMeMe@gmail.com";

        boolean isValidated = activity.validateRegisterForm(login, password, confirmPassword);
        assertFalse(isValidated);*/
    }

    @Test
    public void validateFormWithNullConfirmPasswordAndReturnFalse(){
        // given
        /*RegisterActivity activity = Robolectric.setupActivity(RegisterActivity.class);
        String password = "password";
        String confirmPassword = null;
        String login = "loginMeMe@gmail.com";

        boolean isValidated = activity.validateRegisterForm(login, password, confirmPassword);
        assertFalse(isValidated);*/
    }

    @Test
    public void validateFormWithEmptyConfirmPasswordAndReturnFalse(){
        // given
        /*RegisterActivity activity = Robolectric.setupActivity(RegisterActivity.class);
        String password = "password";
        String confirmPassword = "";
        String login = "loginMeMe@gmail.com";

        boolean isValidated = activity.validateRegisterForm(login, password, confirmPassword);
        assertFalse(isValidated);*/
    }

}
