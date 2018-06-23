package com.spand.meme.service.integration;

import com.spand.meme.exception.MeMeException;
import com.spand.meme.model.AppUser;
import com.spand.meme.repository.UserRepository;
import com.spand.meme.service.UserService;
import com.spand.meme.service.UserServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @TestConfiguration
    static class UserServiceImplTestContextConfiguration {
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @Test(expected = MeMeException.class)
    public void findUserByFakeIdThenInvokeMeMeExceptionTest() {
        // given
        Long fakeId = -1L;
        // when
        try {
            userService.findUserById(fakeId);
        } catch (MeMeException re) {
            String message = "The user with id: " + fakeId + " has not found";
            assertEquals(message, re.getMessage());
            throw re;
        }
        fail("The user with id: " + fakeId + " has not found");
    }

    @Test(expected = MeMeException.class)
    public void findUserByFakeNameThenInvokeMeMeExceptionTest() {
        // given
        String fakeName = "FakeName";
        // when
        try {
            userService.findUserByName(fakeName);
        } catch(MeMeException re) {
            String message = "The user with name: " + fakeName + " has not found";
            assertEquals(message, re.getMessage());
            throw re;
        }
        fail("The user with name: " + fakeName + " has not found");
    }

    @Test(expected = MeMeException.class)
    public void findUserByFakeLoginThenInvokeMeMeExceptionTest() {
        // given
        String fakeLogin = "fakeLogin1";
        // when
        try {
            userService.findUserByLogin(fakeLogin);
        } catch(MeMeException re) {
            String message = "The user with login: " + fakeLogin + " has not found";
            assertEquals(message, re.getMessage());
            throw re;
        }
        fail("The user with login: " + fakeLogin + " has not found");
    }

    @Test(expected = MeMeException.class)
    public void performLoginWithNotActiveUserThenInvokeMeMeExceptionAndRemoveAllUsersTest(){
        // given
        AppUser appUser = new AppUser("name1", "FakeLogin", "address1", "FakeLogin", false);
        AppUser savedAppUser = new AppUser(1L,"name1", "FakeLogin", "address1", "FakeLogin", false);
        // when
        try {
            when(userRepository.save(appUser)).thenReturn(savedAppUser);
            when(userRepository.findByLoginAndPassword(savedAppUser.getLogin(), savedAppUser.getPassword())).thenReturn(savedAppUser);
            appUser = userRepository.save(appUser);
            userService.performUserAuthorization(appUser.getLogin(), appUser.getPassword());
        } catch(MeMeException re) {
            String message = "User with login: " + appUser.getLogin() + " is not active anymore";
            assertEquals(message, re.getMessage());
            throw re;
        }
        fail("User with login: " + appUser.getLogin() + " is not active anymore");
    }

    @Test
    public void performLoginWithIncorrectLoginUserThenInvokeMeMeExceptionTest(){
        // given
        String fakeLogin = "FakeLogin";
        AppUser appUser = new AppUser("name1", "Login", "address1", "FakeLogin", true);
        AppUser savedAppUser = new AppUser(1L,"name1", "Login", "address1", "FakeLogin", true);
        // when
        try {
            when(userRepository.save(appUser)).thenReturn(savedAppUser);
            when(userRepository.findByLoginAndPassword(savedAppUser.getLogin(), savedAppUser.getPassword())).thenReturn(savedAppUser);
            userService.performUserAuthorization(appUser.getLogin() + fakeLogin, appUser.getPassword());
        } catch(MeMeException re) {
            String message = "Wrong credentials for login: " + appUser.getLogin() + fakeLogin + " . Login/Password is incorrect";
            assertEquals(message, re.getMessage());
            return;
        }
        fail("Wrong credentials for login: " + fakeLogin + " . Login/Password is incorrect");
    }

    @Test
    public void invokeExceptionWhenUserIsGoingToBeCreatedButLoginAlreadyExistsTest(){
        // given
        AppUser appUser = new AppUser("name1", "Login", "address1", "FakeLogin", true);
        AppUser savedAppUser = new AppUser(1L,"name1", "Login", "address1", "FakeLogin", true);
        // when
        try {
            when(userRepository.save(appUser)).thenReturn(savedAppUser);
            when(userRepository.findByLogin(savedAppUser.getLogin())).thenReturn(savedAppUser);
            userService.registerNewUser(appUser);
        } catch(MeMeException re) {
            String message = "user with login: " + appUser.getLogin() + " already exists";
            assertEquals(message, re.getMessage());
            return;
        }
        fail("user with login: " + appUser.getLogin() + " already exists");
    }

    @Test
    public void invokeExceptionWhenUserIsGoingToBeDeactivatedButItWasNowFoundTest(){
        // given
        AppUser appUser = new AppUser("name1", "Login", "address1", "FakeLogin", true);
        AppUser savedAppUser = new AppUser(1L,"name1", "Login", "address1", "FakeLogin", true);
        // when
        try {
            when(userRepository.findByLogin(savedAppUser.getLogin())).thenReturn(null);
            userService.deactivateUser(savedAppUser);
        } catch(MeMeException re) {
            String message = "The user with login: " + appUser.getLogin() + " has not found";
            assertEquals(message, re.getMessage());
            return;
        }
        fail("The user with login: " + appUser.getLogin() + " has not found");
    }

    @Test
    public void invokeExceptionWhenUserIsGoingToBeDeactivatedButItIsAlreadyInactiveTest(){
        // given
        AppUser appUser = new AppUser("name1", "Login", "address1", "FakeLogin", false);
        AppUser savedAppUser = new AppUser(1L,"name1", "Login", "address1", "FakeLogin", false);
        // when
        try {
            when(userRepository.save(appUser)).thenReturn(savedAppUser);
            when(userRepository.findByLogin(savedAppUser.getLogin())).thenReturn(savedAppUser);
            userService.deactivateUser(savedAppUser);
        } catch(MeMeException re) {
            String message = "User with login: " + appUser.getLogin() + " is already not active";
            assertEquals(message, re.getMessage());
            return;
        }
        fail("User with login: " + appUser.getLogin() + " is already not active");
    }

    @Test
    public void invokeExceptionWhenUserIsGoingToBeActivatedButItWasNowFoundTest(){
        // given
        AppUser appUser = new AppUser("name1", "Login", "address1", "FakeLogin", true);
        AppUser savedAppUser = new AppUser(1L,"name1", "Login", "address1", "FakeLogin", true);
        // when
        try {
            when(userRepository.findByLogin(savedAppUser.getLogin())).thenReturn(null);
            userService.activateUser(savedAppUser);
        } catch(MeMeException re) {
            String message = "The user with login: " + appUser.getLogin() + " has not found";
            assertEquals(message, re.getMessage());
            return;
        }
        fail("The user with login: " + appUser.getLogin() + " has not found");
    }

    @Test
    public void invokeExceptionWhenUserIsGoingToBeActivatedButItIsAlreadyInactiveTest(){
        // given
        AppUser appUser = new AppUser("name1", "Login", "address1", "FakeLogin", true);
        AppUser savedAppUser = new AppUser(1L,"name1", "Login", "address1", "FakeLogin", true);
        // when
        try {
            when(userRepository.save(appUser)).thenReturn(savedAppUser);
            when(userRepository.findByLogin(savedAppUser.getLogin())).thenReturn(savedAppUser);
            userService.activateUser(savedAppUser);
        } catch(MeMeException re) {
            String message = "User with login: " + appUser.getLogin() + " is already activated";
            assertEquals(message, re.getMessage());
            return;
        }
        fail("User with login: " + appUser.getLogin() + " is already activated");
    }

    @Test
    public void invokeExceptionWhenUserIsGoingToBeUpdatedButItDoesNotNullableIdTest(){
        // given
        AppUser appUser = new AppUser("name1", "Login", "address1", "FakeLogin", true);
        AppUser savedAppUser = new AppUser("name1", "Login", "address1", "FakeLogin", true);
        // when
        try {
            when(userRepository.save(appUser)).thenReturn(appUser);
            userService.updateUser(savedAppUser);
        } catch(MeMeException re) {
            String message = "User with login: " + appUser.getLogin() + " has id with value NULL";
            assertEquals(message, re.getMessage());
            return;
        }
        fail("User with login: " + appUser.getLogin() + " has id with value NULL");
    }


}
