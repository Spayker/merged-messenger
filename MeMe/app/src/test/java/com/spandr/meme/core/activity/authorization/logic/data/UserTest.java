package com.spandr.meme.core.activity.authorization.logic.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import com.spandr.meme.core.activity.authorization.LoginActivity;
import com.spandr.meme.core.activity.authorization.logic.exception.AppAuthorizationActivityException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;

import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_PASS;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_USER_EMAIL_OR_PHONE;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.KEY_USER_NAME;
import static com.spandr.meme.core.activity.main.logic.starter.SettingsConstants.PREF_NAME;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.fail;

@RunWith(RobolectricTestRunner.class)
public class UserTest {

    @Test
    public void testValidatesThatClassUserIsNotInstantiable() throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, InstantiationException {
        Constructor<User> constructor = User.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }

    @Test
    public void testUserThreeParametrizedConstructor(){
        // given
        String userName = "alex";
        String email    = "alex@gmail.com";
        String password = "qwerty";

        // when
        User user = new User(userName, email, password);

        // then
        assertEquals(user.getUserName(), userName);
        assertEquals(user.getEmailAddress(), email);
        assertEquals(user.getPassword(), password);
    }

    @Test
    public void testUserTwoParametrizedConstructor(){
        // given
        String email    = "alex@gmail.com";
        String password = "qwerty";

        // when
        User user = new User(email, password);

        // then
        assertEquals(user.getEmailAddress(), email);
        assertEquals(user.getPassword(), password);
    }

    @Test(expected = AppAuthorizationActivityException.class)
    public void getUserInstanceWithNullActivityAndAppAuthorizationException() {
        // given
        AppCompatActivity appCompatActivity = null;
        // when
        User.getInstance(appCompatActivity);
        // then
        fail("app compat activity: object is null");
    }

    @Test
    public void getUserInstanceWithNonNullActivityTest() {
        // given
        AppCompatActivity activity = Robolectric.setupActivity(LoginActivity.class);

        // when
        User user = User.getInstance(activity);
        // then
        assertNotNull(user);
    }

    @Test
    public void getUserInstanceWithEmptyFieldsTest(){
        // given
        AppCompatActivity activity = Robolectric.setupActivity(LoginActivity.class);

        // when
        User user = User.getInstance(activity);
        // then
        assertTrue(user != null);
        assertTrue(user.getUserName() != null);
        assertTrue(user.getEmailAddress() != null);
        assertTrue(user.getPassword() != null);
        assertTrue(user.getUserName().isEmpty());
        assertTrue(user.getEmailAddress().isEmpty());
        assertTrue(user.getPassword().isEmpty());
    }

    @Test
    public void getUserInstanceWithFieldsTest(){
        // given
        AppCompatActivity activity = Robolectric.setupActivity(LoginActivity.class);
        SharedPreferences sharedPreferences = activity.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String userName = "alex";
        String email    = "alex@gmail.com";
        String password = "qwerty";

        editor.putString(KEY_USER_NAME, userName);
        editor.putString(KEY_USER_EMAIL_OR_PHONE, email);
        editor.putString(KEY_PASS, password);
        editor.commit();

        // when
        User user = User.getInstance(activity);

        // then
        assertTrue(user != null);
        assertTrue(user.getUserName() != null);
        assertTrue(user.getEmailAddress() != null);
        assertTrue(user.getPassword() != null);

        assertEquals(user.getUserName(), userName);
        assertEquals(user.getEmailAddress(), email);
        assertEquals(user.getPassword(), password);
    }


}