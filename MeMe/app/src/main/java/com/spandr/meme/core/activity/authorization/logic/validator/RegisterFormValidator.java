package com.spandr.meme.core.activity.authorization.logic.validator;

import android.text.TextUtils;

import com.spandr.meme.core.activity.authorization.logic.validator.exception.AppValidationFormException;

import static com.spandr.meme.core.activity.authorization.logic.validator.ValidationReturnCode.EMAIL_INCORRECT_FORMAT;
import static com.spandr.meme.core.activity.authorization.logic.validator.ValidationReturnCode.EMPTY_CONFIRM_PASSWORD;
import static com.spandr.meme.core.activity.authorization.logic.validator.ValidationReturnCode.EMPTY_LOGIN;
import static com.spandr.meme.core.activity.authorization.logic.validator.ValidationReturnCode.EMPTY_PASSWORD;
import static com.spandr.meme.core.activity.authorization.logic.validator.ValidationReturnCode.OK;

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

                String confirmedPassword = fieldsToCheck[2];
                if(confirmedPassword == null){
                    throw new AppValidationFormException("validateInputForm: confirmed password can not be null");
                }
                if (TextUtils.isEmpty(confirmedPassword)) {
                    return EMPTY_CONFIRM_PASSWORD;
                }

                break;
            }
            default:{
                throw new AppValidationFormException("validateInputForm: fields amount must be only 3");
            }
        }
        return OK;
    }
}
