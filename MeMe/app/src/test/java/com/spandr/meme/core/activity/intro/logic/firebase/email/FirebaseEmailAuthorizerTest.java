package com.spandr.meme.core.activity.intro.logic.firebase.email;

import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.spandr.meme.core.activity.authorization.LoginActivity;
import com.spandr.meme.core.activity.authorization.logic.firebase.email.FirebaseEmailAuthorizer;
import com.spandr.meme.core.activity.authorization.logic.firebase.exception.AppFireBaseAuthException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.Date;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class FirebaseEmailAuthorizerTest {

    /*
        createUserWithEmailAndPassword
        sendEmailVerification
        signInWithEmailAndPasswordAuthorize
        signOut
    */

    /*--- Approximate Service Test Structure:
        1. test input parameters for wrong values
        - max/min limits
        - negative values
        - null vlues
        - logically incorrect values
        - parameter object is incorrect
        2. test business exception calles
        3. test business logic (green scenario)
    */

    @Before
    public void setupTestEnv() {
        AppCompatActivity activity = Robolectric.setupActivity(LoginActivity.class);
        FirebaseApp.initializeApp(activity);
    }

    @Test
    public void checkInitOfPublicConstructor() {
        // given
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // when
        FirebaseEmailAuthorizer firebaseEmailAuthorizer = new FirebaseEmailAuthorizer();
        // then
        assertTrue(firebaseEmailAuthorizer.getmAuth().equals(mAuth));
    }

    @Test(expected = AppFireBaseAuthException.class)
    public void createUserWithEmailAndPasswordWithNullEmailParameterAndThrowExceptionTest() {
        // given
        String password = "password";
        String email = null;
        FirebaseEmailAuthorizer firebaseEmailAuthorizer = new FirebaseEmailAuthorizer();
        // when
        firebaseEmailAuthorizer.createUserWithEmailAndPassword(email, password);
        // then
        fail("createUserWithEmailAndPassword: email is null or empty");
    }

    @Test(expected = AppFireBaseAuthException.class)
    public void createUserWithEmailAndPasswordWithEmptyEmailParameterAndThrowExceptionTest() {
        // given
        String password = "password";
        String email = "";
        FirebaseEmailAuthorizer firebaseEmailAuthorizer = new FirebaseEmailAuthorizer();
        // when
        firebaseEmailAuthorizer.createUserWithEmailAndPassword(email, password);
        // then
        fail("createUserWithEmailAndPassword: email is null or empty");
    }

    @Test(expected = AppFireBaseAuthException.class)
    public void createUserWithEmailAndPasswordWithNullPasswordParameterAndThrowExceptionTest() {
        // given
        String password = null;
        String email = "example_email@gmail.com";
        FirebaseEmailAuthorizer firebaseEmailAuthorizer = new FirebaseEmailAuthorizer();
        // when
        firebaseEmailAuthorizer.createUserWithEmailAndPassword(email, password);
        // then
        fail("createUserWithEmailAndPassword: email is null or empty");
    }

    @Test(expected = AppFireBaseAuthException.class)
    public void createUserWithEmailAndPasswordWithEmptyPasswordParameterAndThrowExceptionTest() {
        // given
        String password = "";
        String email = "example_email@gmail.com";
        FirebaseEmailAuthorizer firebaseEmailAuthorizer = new FirebaseEmailAuthorizer();
        // when
        firebaseEmailAuthorizer.createUserWithEmailAndPassword(email, password);
        // then
        fail("createUserWithEmailAndPassword: email is null or empty");
    }

    @Test
    public void createUserWithEmailAndPasswordAndReturnTaskObject() {
        // given
        String email = "testUser" + new Date() + "@gmail.com";
        String password = "qwerty";
        FirebaseEmailAuthorizer firebaseEmailAuthorizer = new FirebaseEmailAuthorizer();

        // when
        Task<AuthResult> task = firebaseEmailAuthorizer.createUserWithEmailAndPassword(email, password);

        // then
        assertNotNull(task);
    }

    @Test
    public void createUserWithEmailAndPasswordAndReturnTaskObjectWithUnverifiedStatus() {
        // given
        String email = "spykerstar@gmail.com";
        String password = "qwerty";
        FirebaseEmailAuthorizer firebaseEmailAuthorizer = new FirebaseEmailAuthorizer();

        // when
        Task<AuthResult> task = firebaseEmailAuthorizer.createUserWithEmailAndPassword(email, password);

        // then
        assertNotNull(task);

    }


}