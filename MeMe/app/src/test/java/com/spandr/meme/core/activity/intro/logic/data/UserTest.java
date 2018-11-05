package com.spandr.meme.core.activity.intro.logic.data;

import android.support.v7.app.AppCompatActivity;

import com.spandr.meme.core.activity.authorization.logic.data.User;
import com.spandr.meme.core.activity.authorization.logic.exception.AppAuthorizationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static junit.framework.TestCase.fail;

@RunWith(JUnit4.class)
public class UserTest {

    @Test(expected = AppAuthorizationException.class)
    public void getInstanceWithNullActivityAndAppAuthorizationException() {
        // given
        AppCompatActivity appCompatActivity = null;
        // when
        User.getInstance(appCompatActivity);
        fail("app compat activity: object is null");
    }




}