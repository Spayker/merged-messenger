package com.spandr.meme.core.activity.authorization.logic.validator;

/**
 *  Declares only one method that must validate input data provided by sign in/up forms
 *  It happens when user wants to sign up
 * @author  Spayker
 * @version 1.0
 * @since   3/6/2019
 */
public interface FormValidator {

    ValidationReturnCode validateInputForm(String... fieldsToCheck);

}
