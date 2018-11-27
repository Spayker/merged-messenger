package com.spandr.meme.core.activity.authorization.logic.validator;

import android.text.TextUtils;

import com.spandr.meme.core.activity.authorization.logic.validator.exception.AppValidationFormException;

import static com.spandr.meme.core.activity.authorization.logic.validator.ValidationReturnCode.EMAIL_INCORRECT_FORMAT;
import static com.spandr.meme.core.activity.authorization.logic.validator.ValidationReturnCode.EMPTY_CONFIRM_PASSWORD;
import static com.spandr.meme.core.activity.authorization.logic.validator.ValidationReturnCode.EMPTY_LOGIN;
import static com.spandr.meme.core.activity.authorization.logic.validator.ValidationReturnCode.EMPTY_PASSWORD;
import static com.spandr.meme.core.activity.authorization.logic.validator.ValidationReturnCode.NON_EQUAL_PASSWORDS;
import static com.spandr.meme.core.activity.authorization.logic.validator.ValidationReturnCode.OK;
import static com.spandr.meme.core.activity.authorization.logic.validator.ValidationReturnCode.SHORT_PASSWORD;

public class RegisterFormValidator implements FormValidator {

    @Override
    public ValidationReturnCode validateInputForm(String... fieldsToCheck) {
        if(fieldsToCheck == null){
            throw new AppValidationFormException("validateInputForm: fieldsToCheck variable can not be null");
        }

        if(fieldsToCheck.length == 0){
            throw new AppValidationFormException("validateInputForm: fieldsToCheck size can not be 0");
        }

        switch(fieldsToCheck.length){
            case 3:{
                String login = fieldsToCheck[0];
                if(login == null){
                    throw new AppValidationFormException("validateInputForm: login can not be null");
                }

                if (TextUtils.isEmpty(login)) {
                    return EMPTY_LOGIN;
                }

                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(login).matches()) {
                    return EMAIL_INCORRECT_FORMAT;
                }

                String password = fieldsToCheck[1];
                if(password == null){
                    throw new AppValidationFormException("validateInputForm: password can not be null");
                }

                if (TextUtils.isEmpty(password)) {
                    return EMPTY_PASSWORD;
                }

                String confirmPassword = fieldsToCheck[2];
                if(confirmPassword == null){
                    throw new AppValidationFormException("validateInputForm: confirmed password can not be null");
                }
                if (TextUtils.isEmpty(confirmPassword)) {
                    return EMPTY_CONFIRM_PASSWORD;
                }

                if (!isPasswordValid(password)) {
                    return SHORT_PASSWORD;
                }

                if (!password.equalsIgnoreCase(confirmPassword)) {
                    return NON_EQUAL_PASSWORDS;
                }

                break;
            }
            default:{
                throw new AppValidationFormException("validateInputForm: fields amount must be only 3");
            }
        }
        return OK;
    }

    /**
     * Returns true or false if a password is valid or not.
     *
     * @param password a String object which must be validated
     * @return a boolean value. Depends on validation result
     **/
    private boolean isPasswordValid(String password) {
        return (!password.isEmpty() && password.length() > 4);
    }
}
