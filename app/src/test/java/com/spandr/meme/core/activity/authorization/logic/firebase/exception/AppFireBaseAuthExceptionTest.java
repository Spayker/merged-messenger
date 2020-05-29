package com.spandr.meme.core.activity.authorization.logic.firebase.exception;

import com.spandr.meme.core.activity.authorization.logic.exception.AppAuthorizationActivityException;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class AppFireBaseAuthExceptionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void throw_AppFireBaseAuthException_And_Check_Message_Test() {
        // given
        String exceptionMessage = "Test Exception Run";

        // when
        thrown.expect(AppFireBaseAuthException.class);
        thrown.expectMessage(CoreMatchers.containsString(exceptionMessage));

        // then
        throw new AppFireBaseAuthException(exceptionMessage);
    }

    @Test
    public void throw_AppFireBaseAuthException_Then_Check_Cause_Test() {
        // given
        String exceptionMessage = "Test Exception Run";
        Throwable expectedCause = new AppFireBaseAuthException(exceptionMessage);

        // when
        thrown.expect(AppFireBaseAuthException.class);
        thrown.expectCause(IsEqual.equalTo(expectedCause));

        // then
        throw new AppFireBaseAuthException(expectedCause);
    }

    @Test
    public void throw_AppFireBaseAuthException_Then_Check_Message_And_Cause_Test() {
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
