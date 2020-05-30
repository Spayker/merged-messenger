package com.spandr.meme.core.activity.authorization.logic.validator;

import com.spandr.meme.core.activity.authorization.logic.validator.exception.AppValidationFormException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class RegisterFormValidatorForm {

    @Test
    public void validate_Form_With_Empty_Login_And_Return_Empty_Login_Code(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "";
        String password = "password";
        String confirmPassword = "confirmPassword";

        ValidationReturnCode emptyLogin = registerFormValidator.validateInputForm(login, password, confirmPassword);
        assertTrue(emptyLogin.equals(ValidationReturnCode.EMPTY_LOGIN));
    }

    @Test(expected = AppValidationFormException.class)
    public void validate_Form_With_Null_Login_And_AppValidationForm_Exception(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = null;
        String password = "password";
        String confirmPassword = "confirmPassword";
        registerFormValidator.validateInputForm(login, password, confirmPassword);
    }

    @Test(expected = AppValidationFormException.class)
    public void validate_Form_With_Null_Password_And_Return_Empty_Password_Code(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "login@gmail.com";
        String password = null;
        String confirmPassword = "confirmPassword";
        registerFormValidator.validateInputForm(login, password, confirmPassword);
    }

    @Test
    public void validate_Form_With_Empty_Password_And_Return_Empty_Password_Code(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "login@gmail.com";
        String password = "";
        String confirmPassword = "confirmPassword";

        ValidationReturnCode emptyLogin = registerFormValidator.validateInputForm(login, password, confirmPassword);
        assertTrue(emptyLogin.equals(ValidationReturnCode.EMPTY_PASSWORD));
    }

    @Test(expected = AppValidationFormException.class)
    public void validate_Form_With_Null_Confirm_Password_And_Return_Empty_Password_Code(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "login@gmail.com";
        String password = "pasword";
        String confirmPassword = null;
registerFormValidator.validateInputForm(login, password, confirmPassword);
    }

    @Test
    public void validate_Form_With_Empty_Confirm_Password_And_Return_Empty_Password_Code(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "login@gmail.com";
        String password = "password";
        String confirmPassword = "";

        ValidationReturnCode emptyLogin = registerFormValidator.validateInputForm(login, password, confirmPassword);
        assertTrue(emptyLogin.equals(ValidationReturnCode.EMPTY_CONFIRM_PASSWORD));
    }

    @Test(expected = AppValidationFormException.class)
    public void validate_Form_With_Null_Parameter_And_Throw_AppValidationForm_Exception(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        registerFormValidator.validateInputForm(null);
    }

    @Test(expected = AppValidationFormException.class)
    public void validate_Form_With_Empty_String_Array_Of_Input_Parameters_And_Throw_AppValidationForm_Exception(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String[] inputStringArray = new String[]{};
        registerFormValidator.validateInputForm(inputStringArray);
    }

    @Test(expected = AppValidationFormException.class)
    public void validate_Form_With_Four_Parameters_And_Throw_AppValidationForm_Exception(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "login";
        String password = "password";
        String confirmPassword = "confirmPassword";
        String name = "name";
        registerFormValidator.validateInputForm(login, password, confirmPassword, name);
    }

    @Test(expected = AppValidationFormException.class)
    public void validate_Form_With_One_Parameters_And_Throw_AppValidationForm_Exception(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "login";
        registerFormValidator.validateInputForm(login);
    }

    @Test(expected = AppValidationFormException.class)
    public void validate_Form_With_Two_Parameters_And_Throw_AppValidationForm_Exception(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "login";
        String password = "password";
        registerFormValidator.validateInputForm(login, password);
    }

    @Test
    public void validate_Form_Return_Ok_Code(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "login@gmail.com";
        String password = "password";
        String confirmPassword = "password";
        ValidationReturnCode returnCode = registerFormValidator.validateInputForm(login, password, confirmPassword);
        assertTrue(returnCode.equals(ValidationReturnCode.OK));
    }

    @Test
    public void validate_Form_With_Different_Passwords_And_Return_Non_Equal_Passwords_Code(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "login@gmail.com";
        String password = "password1";
        String confirmPassword = "password2";
        ValidationReturnCode returnCode = registerFormValidator.validateInputForm(login, password, confirmPassword);
        assertTrue(returnCode.equals(ValidationReturnCode.NON_EQUAL_PASSWORDS));
    }

    @Test
    public void validate_Form_With_Short_Passwords_And_Return_Short_Password_Code(){
        RegisterFormValidator registerFormValidator = new RegisterFormValidator();
        String login = "login@gmail.com";
        String password = "pas";
        String confirmPassword = "pas";
        ValidationReturnCode returnCode = registerFormValidator.validateInputForm(login, password, confirmPassword);
        assertTrue(returnCode.equals(ValidationReturnCode.SHORT_PASSWORD));
    }

}
