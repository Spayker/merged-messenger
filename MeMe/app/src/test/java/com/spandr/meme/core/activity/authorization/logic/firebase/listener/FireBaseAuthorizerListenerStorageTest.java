package com.spandr.meme.core.activity.authorization.logic.firebase.listener;

import com.spandr.meme.core.activity.authorization.logic.firebase.listener.FireBaseAuthorizerListenerStorage;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static junit.framework.Assert.assertTrue;

@RunWith(JUnit4.class)
public class FireBaseAuthorizerListenerStorageTest {

    @Test
    public void testValidatesThatClassFireBaseAuthorizerListenerStorageIsNotInstantiable() throws Exception  {
        Constructor<FireBaseAuthorizerListenerStorage> constructor =
                FireBaseAuthorizerListenerStorage.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }



}
