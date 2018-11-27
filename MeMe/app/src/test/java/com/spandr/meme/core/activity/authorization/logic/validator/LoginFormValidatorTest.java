package com.spandr.meme.core.activity.authorization.logic.validator;

import com.spandr.meme.core.activity.authorization.logic.validator.exception.AppValidationFormException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class LoginFormValidatorTest {

    @Test
    public void validate_Form_With_Empty_Login_And_Return_Empty_Login_Code(){
        LoginFormValidator loginFormValidator = new LoginFormValidator();
        String password = "password";
        String login = "";

        ValidationReturnCode emptyLogin = loginFormValidator.validateInputForm(login, password);
        assertTrue(emptyLogin.equals(ValidationReturnCode.EMPTY_LOGIN));
    }

    @Test
    public void validate_Form_With_Empty_Password_And_Return_Empty_Password_Code(){
        LoginFormValidator loginFormValidator = new LoginFormValidator();
        String password = "";
        String login = "login";

        ValidationReturnCode emptyLogin = loginFormValidator.validateInputForm(login, password);
        assertTrue(emptyLogin.equals(ValidationReturnCode.EMPTY_PASSWORD));
    }

    @Test(expected = AppValidationFormException.class)
    public void validate_Form_With_Null_Login_And_AppValidationForm_Exception(){
        LoginFormValidator loginFormValidator = new LoginFormValidator();
        String password = "password";
        String login = null;
        loginFormValidator.validateInputForm(login, password);
    }

    @Test(expected = AppValidationFormException.class)
    public void validate_Form_With_Null_Parameter_And_Throw_AppValidationForm_Exception(){
        LoginFormValidator loginFormValidator = new LoginFormValidator();
        loginFormValidator.validateInputForm(null);
    }

    @Test(expected = AppValidationFormException.class)
    public void validate_Form_With_Empty_String_Array_Of_Input_Parameters_And_Throw_AppValidationForm_Exception(){
        LoginFormValidator loginFormValidator = new LoginFormValidator();
        String[] inputStringArray = new String[]{};
        loginFormValidator.validateInputForm(inputStringArray);
    }

    @Test(expected = AppValidationFormException.class)
    public void validate_Form_With_Three_Parameters_And_Throw_AppValidationForm_Exception(){
        LoginFormValidator loginFormValidator = new LoginFormValidator();
        String password = "password";
        String confirmPassword = "confirmPassword";
        String login = null;
        loginFormValidator.validateInputForm(login, password, confirmPassword);
    }


    @Test
    public void validate_Form_With_And_Return_Ok_Code(){
        LoginFormValidator loginFormValidator = new LoginFormValidator();
        String login = "login";
        String password = "password";
        ValidationReturnCode returnCode = loginFormValidator.validateInputForm(login, password);
        assertTrue(returnCode.equals(ValidationReturnCode.OK));
    }

}
