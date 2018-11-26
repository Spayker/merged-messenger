package com.spandr.meme.core.activity.authorization.logic.validator;

import com.spandr.meme.core.activity.authorization.logic.validator.exception.AppValidationFormException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class LoginFormValidatorTest {

    @Test
    public void validateFormWithEmptyLoginAndReturnEmptyLoginCode(){
        LoginFormValidator loginFormValidator = new LoginFormValidator();
        String password = "password";
        String login = "";

        ValidationReturnCode emptyLogin = loginFormValidator.validateInputForm(login, password);
        assertTrue(emptyLogin.equals(ValidationReturnCode.EMPTY_LOGIN));
    }

    @Test
    public void validateFormWithEmptyPasswordAndReturnEmptyPasswordCode(){
        LoginFormValidator loginFormValidator = new LoginFormValidator();
        String password = "";
        String login = "login";

        ValidationReturnCode emptyLogin = loginFormValidator.validateInputForm(login, password);
        assertTrue(emptyLogin.equals(ValidationReturnCode.EMPTY_PASSWORD));
    }

    @Test(expected = AppValidationFormException.class)
    public void validateFormWithNullLoginAndAppValidationFormException(){
        LoginFormValidator loginFormValidator = new LoginFormValidator();
        String password = "password";
        String login = null;
        loginFormValidator.validateInputForm(login, password);
    }

    @Test(expected = AppValidationFormException.class)
    public void validateFormWithNullParameterAndThrowAppValidationFormException(){
        LoginFormValidator loginFormValidator = new LoginFormValidator();
        loginFormValidator.validateInputForm(null);
    }

    @Test(expected = AppValidationFormException.class)
    public void validateFormWithEmptyStringArrayOfInputParametersAndThrowAppValidationFormException(){
        LoginFormValidator loginFormValidator = new LoginFormValidator();
        String[] inputStringArray = new String[]{};
        loginFormValidator.validateInputForm(inputStringArray);
    }

    @Test(expected = AppValidationFormException.class)
    public void validateFormWithThreeParametersAndThrowAppValidationFormException(){
        LoginFormValidator loginFormValidator = new LoginFormValidator();
        String password = "password";
        String confirmPassword = "confirmPassword";
        String login = null;
        loginFormValidator.validateInputForm(login, password, confirmPassword);
    }


    @Test
    public void validateFormWithAndReturnOkCode(){
        LoginFormValidator loginFormValidator = new LoginFormValidator();
        String login = "login";
        String password = "password";
        ValidationReturnCode returnCode = loginFormValidator.validateInputForm(login, password);
        assertTrue(returnCode.equals(ValidationReturnCode.OK));
    }

}
