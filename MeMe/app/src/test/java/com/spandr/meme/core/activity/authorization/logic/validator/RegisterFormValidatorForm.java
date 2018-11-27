package com.spandr.meme.core.activity.authorization.logic.validator;

import com.spandr.meme.core.activity.authorization.logic.validator.exception.AppValidationFormException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.robolectric.RobolectricTestRunner;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class RegisterFormValidatorForm {

    @Test
    public void validateFormWithEmptyLoginAndReturnEmptyLoginCode(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "";
        String password = "password";
        String confirmPassword = "confirmPassword";

        ValidationReturnCode emptyLogin = registerFormValidator.validateInputForm(login, password, confirmPassword);
        assertTrue(emptyLogin.equals(ValidationReturnCode.EMPTY_LOGIN));
    }

    @Test(expected = AppValidationFormException.class)
    public void validateFormWithNullLoginAndAppValidationFormException(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = null;
        String password = "password";
        String confirmPassword = "confirmPassword";
        registerFormValidator.validateInputForm(login, password, confirmPassword);
    }

    @Test(expected = AppValidationFormException.class)
    public void validateFormWithNullPasswordAndReturnEmptyPasswordCode(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "login@gmail.com";
        String password = null;
        String confirmPassword = "confirmPassword";
        registerFormValidator.validateInputForm(login, password, confirmPassword);
    }

    @Test
    public void validateFormWithEmptyPasswordAndReturnEmptyPasswordCode(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "login@gmail.com";
        String password = "";
        String confirmPassword = "confirmPassword";

        ValidationReturnCode emptyLogin = registerFormValidator.validateInputForm(login, password, confirmPassword);
        assertTrue(emptyLogin.equals(ValidationReturnCode.EMPTY_PASSWORD));
    }

    @Test(expected = AppValidationFormException.class)
    public void validateFormWithNullConfirmPasswordAndReturnEmptyPasswordCode(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "login@gmail.com";
        String password = "pasword";
        String confirmPassword = null;
registerFormValidator.validateInputForm(login, password, confirmPassword);
    }

    @Test
    public void validateFormWithEmptyConfirmPasswordAndReturnEmptyPasswordCode(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "login@gmail.com";
        String password = "password";
        String confirmPassword = "";

        ValidationReturnCode emptyLogin = registerFormValidator.validateInputForm(login, password, confirmPassword);
        assertTrue(emptyLogin.equals(ValidationReturnCode.EMPTY_CONFIRM_PASSWORD));
    }

    @Test(expected = AppValidationFormException.class)
    public void validateFormWithNullParameterAndThrowAppValidationFormException(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        registerFormValidator.validateInputForm(null);
    }

    @Test(expected = AppValidationFormException.class)
    public void validateFormWithEmptyStringArrayOfInputParametersAndThrowAppValidationFormException(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String[] inputStringArray = new String[]{};
        registerFormValidator.validateInputForm(inputStringArray);
    }

    @Test(expected = AppValidationFormException.class)
    public void validateFormWithFourParametersAndThrowAppValidationFormException(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "login";
        String password = "password";
        String confirmPassword = "confirmPassword";
        String name = "name";
        registerFormValidator.validateInputForm(login, password, confirmPassword, name);
    }

    @Test(expected = AppValidationFormException.class)
    public void validateFormWithOneParametersAndThrowAppValidationFormException(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "login";
        registerFormValidator.validateInputForm(login);
    }

    @Test(expected = AppValidationFormException.class)
    public void validateFormWithTwoParametersAndThrowAppValidationFormException(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "login";
        String password = "password";
        registerFormValidator.validateInputForm(login, password);
    }

    @Test
    public void validateFormReturnOkCode(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "login@gmail.com";
        String password = "password";
        String confirmPassword = "password";
        ValidationReturnCode returnCode = registerFormValidator.validateInputForm(login, password, confirmPassword);
        assertTrue(returnCode.equals(ValidationReturnCode.OK));
    }

    @Test
    public void validateFormWithDifferentPasswordsAndReturnNonEqualPasswordsCode(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "login@gmail.com";
        String password = "password1";
        String confirmPassword = "password2";
        ValidationReturnCode returnCode = registerFormValidator.validateInputForm(login, password, confirmPassword);
        assertTrue(returnCode.equals(ValidationReturnCode.NON_EQUAL_PASSWORDS));
    }

    @Test
    public void validateFormWithShortPasswordsAndReturnShortPasswordCode(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "login@gmail.com";
        String password = "pas";
        String confirmPassword = "pas";
        ValidationReturnCode returnCode = registerFormValidator.validateInputForm(login, password, confirmPassword);
        assertTrue(returnCode.equals(ValidationReturnCode.SHORT_PASSWORD));
    }

}
