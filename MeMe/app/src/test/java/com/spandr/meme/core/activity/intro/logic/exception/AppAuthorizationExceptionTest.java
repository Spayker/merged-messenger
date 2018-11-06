package com.spandr.meme.core.activity.intro.logic.exception;

import com.spandr.meme.core.activity.authorization.logic.exception.AppAuthorizationException;

import org.hamcrest.CoreMatchers;
import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.robolectric.RobolectricTestRunner;

@RunWith(JUnit4.class)
public class AppAuthorizationExceptionTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void throwAppAuthorizationExceptionAndCheckMessageTest() {
        // given
        String exceptionMessage = "Test Exception Run";

        // when
        thrown.expect(AppAuthorizationException.class);
        thrown.expectMessage(CoreMatchers.containsString(exceptionMessage));

        // then
        throw new AppAuthorizationException(exceptionMessage);
    }

    @Test
    public void throwAppAuthorizationExceptionThenCheckCauseTest() {
        // given
        String exceptionMessage = "Test Exception Run";
        Throwable expectedCause = new AppAuthorizationException(exceptionMessage);

        // when
        thrown.expect(AppAuthorizationException.class);
        thrown.expectCause(IsEqual.equalTo(expectedCause));

        // then
        throw new AppAuthorizationException(expectedCause);
    }

    @Test
    public void throwAppAuthorizationExceptionThenCheckMessageAndCauseTest() {
        // given
        String exceptionMessage = "Test Exception Run";
        Throwable cause = new AppAuthorizationException();

        // when
        thrown.expect(AppAuthorizationException.class);
        thrown.expectMessage(CoreMatchers.containsString(exceptionMessage));

        // then
        throw new AppAuthorizationException(exceptionMessage, cause);
    }




}