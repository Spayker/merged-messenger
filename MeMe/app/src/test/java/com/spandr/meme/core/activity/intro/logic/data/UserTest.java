package com.spandr.meme.core.activity.intro.logic.data;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.spandr.meme.core.activity.authorization.LoginActivity;
import com.spandr.meme.core.activity.authorization.logic.data.User;
import com.spandr.meme.core.activity.authorization.logic.exception.AppAuthorizationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.fail;

@RunWith(RobolectricTestRunner.class)
public class UserTest {

    @Test(expected = AppAuthorizationException.class)
    public void getUserInstanceWithNullActivityAndAppAuthorizationException() {
        // given
        AppCompatActivity appCompatActivity = null;
        // when
        User.getInstance(appCompatActivity);
        fail("app compat activity: object is null");
    }

    @Test
    public void getUserInstanceWithNonNullActivityTest() {
        // given
        AppCompatActivity activity = Robolectric.setupActivity(LoginActivity.class);

        // when
        User user = User.getInstance(activity);
        assertNotNull(user);
    }
}