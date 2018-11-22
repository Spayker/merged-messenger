package com.spandr.meme.core.activity.authorization.logic.firebase.email;

import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spandr.meme.core.activity.authorization.LoginActivity;
import com.spandr.meme.core.activity.authorization.logic.firebase.email.FirebaseEmailAuthorizer;
import com.spandr.meme.core.activity.authorization.logic.firebase.exception.AppFireBaseAuthException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;

import java.util.Date;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class FirebaseEmailAuthorizerTest {

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

    @Test(expected = AppFireBaseAuthException.class)
    public void sendEmailVerificationWithNullUserAndThrowExceptionTest(){
        //given
        FirebaseUser user = null;
        FirebaseEmailAuthorizer firebaseEmailAuthorizer = new FirebaseEmailAuthorizer();

        // when
        firebaseEmailAuthorizer.sendEmailVerification(user);

        // then
        fail("sendEmailVerification: Fire base user is null. Sending is cancelled. returning null as result");
    }


    @Test(expected = AppFireBaseAuthException.class)
    public void signInWithEmailAndPasswordAuthorizeWithNullEmailAndThrowExceptionTest(){
        //given
        String email = null;
        String password = "qwerty";
        FirebaseEmailAuthorizer firebaseEmailAuthorizer = new FirebaseEmailAuthorizer();

        // when
        firebaseEmailAuthorizer.signInWithEmailAndPasswordAuthorize(email, password);

        // then
        fail("signInWithEmailAndPasswordAuthorize: emailAddress is null or empty");
    }

    @Test(expected = AppFireBaseAuthException.class)
    public void signInWithEmailAndPasswordAuthorizeWithEmptyEmailAndThrowExceptionTest(){
        //given
        String email = "";
        String password = "qwerty";
        FirebaseEmailAuthorizer firebaseEmailAuthorizer = new FirebaseEmailAuthorizer();

        // when
        firebaseEmailAuthorizer.signInWithEmailAndPasswordAuthorize(email, password);

        // then
        fail("signInWithEmailAndPasswordAuthorize: emailAddress is null or empty");
    }

    @Test(expected = AppFireBaseAuthException.class)
    public void signInWithEmailAndPasswordAuthorizeWithNullPasswordAndThrowExceptionTest(){
        //given
        String email = "example_email@gmail.com";
        String password = null;
        FirebaseEmailAuthorizer firebaseEmailAuthorizer = new FirebaseEmailAuthorizer();

        // when
        firebaseEmailAuthorizer.signInWithEmailAndPasswordAuthorize(email, password);

        // then
        fail("signInWithEmailAndPasswordAuthorize: password is null or empty");
    }

    @Test(expected = AppFireBaseAuthException.class)
    public void signInWithEmailAndPasswordAuthorizeWithEmptyPasswordAndThrowExceptionTest(){
        //given
        String email = "example_email@gmail.com";
        String password = "";
        FirebaseEmailAuthorizer firebaseEmailAuthorizer = new FirebaseEmailAuthorizer();

        // when
        firebaseEmailAuthorizer.signInWithEmailAndPasswordAuthorize(email, password);

        // then
        fail("signInWithEmailAndPasswordAuthorize: password is null or empty");
    }

    @Test
    public void signInWithEmailAndPasswordAuthorizeAndReturnTaskObject() {
        // given
        String email = "testUser" + new Date() + "@gmail.com";
        String password = "qwerty";
        FirebaseEmailAuthorizer firebaseEmailAuthorizer = new FirebaseEmailAuthorizer();

        // when
        Task<AuthResult> task = firebaseEmailAuthorizer.signInWithEmailAndPasswordAuthorize(email, password);

        // then
        assertNotNull(task);
    }

}