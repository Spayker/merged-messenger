package com.spandr.meme.core.activity.authorization.logic.validator;

public interface FormValidator {

    ValidationReturnCode validateInputForm(String... fieldsToCheck);

}
