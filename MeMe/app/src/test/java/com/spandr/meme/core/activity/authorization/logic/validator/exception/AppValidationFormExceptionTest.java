package com.spandr.meme.core.activity.authorization.logic.validator.exception;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AppValidationFormExceptionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void throw_AppValidationFormException_And_Check_Message_Test() {
        // given
        String exceptionMessage = "Test Exception Run";

        // when
        thrown.expect(AppValidationFormException.class);
        thrown.expectMessage(CoreMatchers.containsString(exceptionMessage));

        // then
        throw new AppValidationFormException(exceptionMessage);
    }

    @Test
    public void throw_AppValidationFormException_Then_Check_Cause_Test() {
        // given
        String exceptionMessage = "Test Exception Run";
        Throwable expectedCause = new AppValidationFormException(exceptionMessage);

        // when
        thrown.expect(AppValidationFormException.class);
        thrown.expectCause(IsEqual.equalTo(expectedCause));

        // then
        throw new AppValidationFormException(expectedCause);
    }

    @Test
    public void throw_AppAuthorizationException_Then_CheckMessage_And_Cause_Test() {
        // given
        String exceptionMessage = "Test Exception Run";
        Throwable cause = new AppValidationFormException();

        // when
        thrown.expect(AppValidationFormException.class);
        thrown.expectMessage(CoreMatchers.containsString(exceptionMessage));

        // then
        throw new AppValidationFormException(exceptionMessage, cause);
    }

}