package com.spandr.meme.core.activity.authorization.logic.validator;

/**
 *
 *
 * @author  Spayker
 * @version 1.0
 * @since   3/6/2019
 */
public interface FormValidator {

    ValidationReturnCode validateInputForm(String... fieldsToCheck);

}
