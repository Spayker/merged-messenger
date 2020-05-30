package com.spandr.meme.core.activity.authorization.logic.firebase.listener;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;

import static junit.framework.Assert.assertTrue;

@RunWith(JUnit4.class)
public class FireBaseAuthorizerListenerStorageTest {

    @Test
    public void test_Validates_That_Class_FireBase_Authorizer_Listener_Storage_Is_Not_Instantiable() throws Exception  {
        Constructor<FireBaseAuthorizerListenerStorage> constructor =
                FireBaseAuthorizerListenerStorage.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
        constructor.setAccessible(true);
        constructor.newInstance();
    }



}
