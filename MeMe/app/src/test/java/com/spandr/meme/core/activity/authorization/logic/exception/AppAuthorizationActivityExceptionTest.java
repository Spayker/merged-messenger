package com.spandr.meme.core.activity.authorization.logic.exception;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AppAuthorizationActivityExceptionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void throw_App_Authorization_Exception_And_Check_Message_Test() {
        // given
        String exceptionMessage = "Test Exception Run";

        // when
        thrown.expect(AppAuthorizationActivityException.class);
        thrown.expectMessage(CoreMatchers.containsString(exceptionMessage));

        // then
        throw new AppAuthorizationActivityException(exceptionMessage);
    }

    @Test
    public void throw_AppAuthorization_Exception_Then_Check_Cause_Test() {
        // given
        String exceptionMessage = "Test Exception Run";
        Throwable expectedCause = new AppAuthorizationActivityException(exceptionMessage);

        // when
        thrown.expect(AppAuthorizationActivityException.class);
        thrown.expectCause(IsEqual.equalTo(expectedCause));

        // then
        throw new AppAuthorizationActivityException(expectedCause);
    }

    @Test
    public void throw_AppAuthorizationException_Then_Check_Message_And_Cause_Test() {
        // given
        String exceptionMessage = "Test Exception Run";
        Throwable cause = new AppAuthorizationActivityException();

        // when
        thrown.expect(AppAuthorizationActivityException.class);
        thrown.expectMessage(CoreMatchers.containsString(exceptionMessage));

        // then
        throw new AppAuthorizationActivityException(exceptionMessage, cause);
    }

}