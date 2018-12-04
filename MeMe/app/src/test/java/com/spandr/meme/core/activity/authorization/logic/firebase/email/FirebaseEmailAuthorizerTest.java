package com.spandr.meme.core.activity.authorization.logic.firebase.email;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.spandr.meme.core.activity.authorization.logic.firebase.exception.AppFireBaseAuthException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Date;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

@RunWith(JUnit4.class)
public class FirebaseEmailAuthorizerTest {

    @Test
    public void check_Init_Of_Public_Constructor() {
        // given
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // when
        FirebaseEmailAuthorizer firebaseEmailAuthorizer = new FirebaseEmailAuthorizer();
        // then
        assertTrue(firebaseEmailAuthorizer.getmAuth().equals(mAuth));
    }

    @Test(expected = AppFireBaseAuthException.class)
    public void create_User_With_Email_And_Password_With_Null_Email_Parameter_And_ThrowException_Test() {
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
    public void create_User_With_Email_And_Password_With_Empty_Email_Parameter_And_Throw_Exception_Test() {
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
    public void create_User_With_Email_And_Password_With_Null_Password_Parameter_And_Throw_Exception_Test() {
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
    public void create_User_With_Email_And_Password_With_Empty_Password_Parameter_And_Throw_Exception_Test() {
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
    public void create_User_With_Email_And_Password_And_Return_Task_Object() {
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
    public void send_Email_Verification_With_Null_User_And_Throw_Exception_Test(){
        //given
        FirebaseUser user = null;
        FirebaseEmailAuthorizer firebaseEmailAuthorizer = new FirebaseEmailAuthorizer();

        // when
        firebaseEmailAuthorizer.sendEmailVerification(user);

        // then
        fail("sendEmailVerification: Fire base user is null. Sending is cancelled. returning null as result");
    }


    @Test(expected = AppFireBaseAuthException.class)
    public void sign_In_With_Email_And_Password_Authorize_With_Null_Email_And_Throw_Exception_Test(){
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
    public void sign_In_With_Email_And_Password_Authorize_With_Empty_Email_And_Throw_Exception_Test(){
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
    public void sign_In_With_Email_And_Password_Authorize_With_Null_Password_And_Throw_Exception_Test(){
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
    public void sign_In_With_Email_And_Password_Authorize_With_Empty_Password_And_Throw_Exception_Test(){
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
    public void sign_In_With_Email_And_Password_Authorize_And_Return_Task_Object() {
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