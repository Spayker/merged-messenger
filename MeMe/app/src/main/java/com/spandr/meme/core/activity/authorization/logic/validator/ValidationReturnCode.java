package com.spandr.meme.core.activity.authorization.logic.validator;

/**
 *
 *
 * @author  Spayker
 * @version 1.0
 * @since   3/6/2019
 */
public enum ValidationReturnCode {
    OK,EMPTY_LOGIN,
    EMPTY_PASSWORD,
    EMPTY_CONFIRM_PASSWORD,
    PASSWORD_NOT_EMPTY,
    EMAIL_INCORRECT_FORMAT,
    NON_EQUAL_PASSWORDS,
    SHORT_PASSWORD,
    UNSUPPORTED_EMAIL_DOMAIN
}
