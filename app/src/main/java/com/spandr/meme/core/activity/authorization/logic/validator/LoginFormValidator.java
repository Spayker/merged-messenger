package com.spandr.meme.core.activity.authorization.logic.validator;

import android.text.TextUtils;

import com.spandr.meme.core.activity.authorization.logic.validator.exception.AppValidationFormException;

import static com.spandr.meme.core.activity.authorization.logic.validator.ValidationReturnCode.EMPTY_LOGIN;
import static com.spandr.meme.core.activity.authorization.logic.validator.ValidationReturnCode.EMPTY_PASSWORD;
import static com.spandr.meme.core.activity.authorization.logic.validator.ValidationReturnCode.OK;

/**
 *  Represents sign in validation method that check all input data from sign in form.
 *  It happens when user wants to sign in
 * @author  Spayker
 * @version 1.0
 * @since   3/6/2019
 */
public class LoginFormValidator implements FormValidator{

    @Override
    public ValidationReturnCode validateInputForm(String... fieldsToCheck) {
        if(fieldsToCheck == null){
            throw new AppValidationFormException("validateInputForm: fieldsToCheck variable can not be null ");
        }

        if(fieldsToCheck.length == 0){
            throw new AppValidationFormException("validateInputForm: fieldsToCheck size can not be 0 ");
        }

        switch(fieldsToCheck.length){
            case 2:{
                String login = fieldsToCheck[0];
                if(login == null){
                    throw new AppValidationFormException("validateInputForm: login can not be null");
                }

                if (TextUtils.isEmpty(login)) {
                    return EMPTY_LOGIN;
                }

                String password = fieldsToCheck[1];
                if(password == null){
                    throw new AppValidationFormException("validateInputForm: password can not be null");
                }

                if (TextUtils.isEmpty(password)) {
                    return EMPTY_PASSWORD;
                }
                break;
            }
            default:{
                throw new AppValidationFormException("validateInputForm: fields amount must be only 2 ");
            }
        }
        return OK;
    }

}
