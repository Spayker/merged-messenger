package com.spand.meme.repository;

import com.spand.meme.model.AppUser;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import org.springframework.data.domain.Sort;

import static org.junit.Assert.*;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringJUnit4ClassRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@TestPropertySource("classpath:application-test.properties")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setup(){
        userRepository.deleteAll();
    }

    @After
    public void resetDb() { userRepository.deleteAll(); }

    @Test
    public void createSeveralEntitiesThenFindThemAllAndRemoveLaterTest() {
        // given
        userRepository.save(new AppUser("name1", "login1", "address1", "password1", true));
        userRepository.save(new AppUser("name2", "login2", "address2", "password2", true));
        userRepository.save(new AppUser("name3", "login3", "address3", "password3", true));

        // when
        List<AppUser> foundObjectList = userRepository.findAll();

        // then
        assertNotNull(foundObjectList);
        TestCase.assertEquals(3, foundObjectList.size());
        userRepository.deleteAll();
        foundObjectList = userRepository.findAll();
        assertNotNull(foundObjectList);
        TestCase.assertEquals(0, foundObjectList.size());
    }

    @Test
    public void createSeveralEntitiesThenFindThemAllSortedAndRemoveLaterTest() {
        //given
        userRepository.save(new AppUser("name1", "login1", "address1", "password1", true));
        userRepository.save(new AppUser("name2", "login2", "address2", "password2", true));
        userRepository.save(new AppUser("name3", "login3", "address3", "password3", true));

        //when
        List<AppUser> foundObjectList = userRepository.findAll(new Sort(Sort.Direction.ASC, "id"));

        //then
        assertNotNull(foundObjectList);
        TestCase.assertEquals(3, foundObjectList.size());

        Long firstId = foundObjectList.get(0).getId();
        Long secondId = foundObjectList.get(1).getId();
        assertTrue(firstId < secondId);

        userRepository.deleteAll();
        foundObjectList = userRepository.findAll();
        assertNotNull(foundObjectList);
        TestCase.assertEquals(0, foundObjectList.size());
    }

    @Test
    public void createSeveralEntitiesThenModifyAllFieldsInEachEntityAndCheckResultTest() {
        // given
        userRepository.save(new AppUser("name1", "login1", "address1", "password1", true));
        userRepository.save(new AppUser("name2", "login2", "address2", "password2", true));
        userRepository.save(new AppUser("name3", "login3", "address3", "password3", true));

        // when
        List<AppUser> foundAppUserList = userRepository.findAll();
        TestCase.assertEquals(3, foundAppUserList.size());
        foundAppUserList.forEach((v) -> {
            v.setName(v.getName() + "_NewAddedValue");
            v.setLogin(v.getLogin() + "_NewAddedValue");
            v.setAddress(v.getAddress() + "_NewAddedValue");
            v.setIsActive(false);
            v.setPassword(v.getPassword() + "_NewAddedValue");
            userRepository.save(v);
        });

        // then
        List<AppUser> updatedAppUserList = userRepository.findAll();
        assertEquals(updatedAppUserList.get(0).getName(), "name1_NewAddedValue");
        assertEquals(updatedAppUserList.get(0).getLogin(), "login1_NewAddedValue");
        assertEquals(updatedAppUserList.get(0).getAddress(), "address1_NewAddedValue");
        assertEquals(updatedAppUserList.get(0).getPassword(), "password1_NewAddedValue");
        assertEquals(updatedAppUserList.get(0).getIsActive(), false);
    }

    @Test
    public void createSeveralEntitiesThenRemoveThemInBatchTest() {
        //given
        userRepository.save(new AppUser("name1", "login1", "address1", "password1", true));
        userRepository.save(new AppUser("name2", "login2", "address2", "password2", true));
        userRepository.save(new AppUser("name3", "login3", "address3", "password3", true));

        //when
        Iterable<AppUser> iterator = userRepository.findAll();
        userRepository.deleteInBatch(iterator);

        //then
        List<AppUser> foundObjectList = userRepository.findAll();
        assertNotNull(foundObjectList);
        TestCase.assertEquals(0, foundObjectList.size());
    }

    @Test
    public void createCoupleEntitiesThenRemoveThemAllInBatchTest() {
        //given
        userRepository.save(new AppUser("name1", "login1", "address1", "password1", true));
        userRepository.save(new AppUser("name2", "login2", "address2", "password2", true));
        userRepository.save(new AppUser("name3", "login3", "address3", "password3", true));

        // when
        userRepository.deleteAllInBatch();

        // then
        List<AppUser> foundObjectList = userRepository.findAll();
        assertNotNull(foundObjectList);
        TestCase.assertEquals(0, foundObjectList.size());
    }

    @Test
    public void createOneEntityThenGetItAndRemoveTest() {
        // given
        AppUser savedObject1 = userRepository.save(new AppUser("name1", "login1", "address1", "password1", true));

        // when
        AppUser foundObject = userRepository.getOne(savedObject1.getId());

        // then
        assertNotNull(foundObject);
        assertEquals(savedObject1.getId(), foundObject.getId());
    }

    @Test
    public void createSeveralEntitiesThenCountThemAndRemoveAllTest() {
        // given
        userRepository.save(new AppUser("name1", "login1", "address1", "password1", true));
        userRepository.save(new AppUser("name2", "login2", "address2", "password2", true));
        userRepository.save(new AppUser("name3", "login3", "address3", "password3", true));
        // when
        long itemAmount = userRepository.count();

        // then
        assertEquals(itemAmount, 3);
    }

    @Test
    public void createOneEntityThenCheckItsExistanceAndRemoveItTest() {
        // given
        AppUser savedObject1 = userRepository.save(new AppUser("name1", "login1", "address1", "password1", true));

        // when
        boolean isExist = userRepository.exists(savedObject1.getId());

        // then
        assertTrue(isExist);
    }

    @Test
    public void createOneEntityThenFindItByIdAndRemoveAllUsersTest() {
        //given
        AppUser savedObject1 = userRepository.save(new AppUser("name1", "login1", "address1", "password1", true));

        //when
        AppUser foundAppUser = userRepository.findById(savedObject1.getId());

        //then
        assertNotNull(foundAppUser);
        assertEquals(savedObject1.getId(), foundAppUser.getId());
        userRepository.deleteAll();
    }

    @Test
    public void createOneEntityThenFindItByNameAndRemoveAllUsersTest() {
        //given
        AppUser appUser = new AppUser("name1", "login1", "address1", "password1", true);
        AppUser savedObject1 = userRepository.save(appUser);

        //when
        List<AppUser> foundAppUsers = userRepository.findByName(savedObject1.getName());

        //then
        assertNotNull(foundAppUsers);
        assertTrue(foundAppUsers.size() > 0);

        int savedObjectHash = savedObject1.hashCode();
        int foundObjectHash = foundAppUsers.get(0).hashCode();

        assertEquals(savedObjectHash, foundObjectHash);
        userRepository.deleteAll();
    }

    @Test
    public void createOneEntityThenFindItByLoginAndRemoveAllUsersTest() {
        //given
        AppUser savedObject1 = userRepository.save(new AppUser("name1", "login1", "address1", "password1", true));

        //when
        AppUser foundAppUsers = userRepository.findByLogin(savedObject1.getLogin());

        //then
        assertNotNull(foundAppUsers);
        int savedObjectHash = savedObject1.hashCode();
        int foundObjectHash = foundAppUsers.hashCode();
        assertEquals(savedObjectHash, foundObjectHash);
        userRepository.deleteAll();
    }

    @Test
    public void createOneEntityThenFindItByPasswordAndRemoveAllUsersTest() {
        //given
        AppUser savedObject1 = userRepository.save(new AppUser("name1", "login1", "address1", "password1", true));

        //when
        List<AppUser> foundAppUsers = userRepository.findByPassword(savedObject1.getPassword());

        //then
        assertNotNull(foundAppUsers);
        int savedObjectHash = savedObject1.hashCode();
        int foundObjectHash = foundAppUsers.get(0).hashCode();
        assertEquals(savedObjectHash, foundObjectHash);
        userRepository.deleteAll();
    }

    @Test
    public void createOneEntityThenFindItByAddressAndRemoveAllUsersTest() {
        //given
        AppUser savedObject1 = userRepository.save(new AppUser("name1", "login1", "address1", "password1", true));

        //when
        List<AppUser> foundAppUsers = userRepository.findByAddress(savedObject1.getAddress());

        //then
        assertNotNull(foundAppUsers);
        int savedObjectHash = savedObject1.hashCode();
        int foundObjectHash = foundAppUsers.get(0).hashCode();
        assertEquals(savedObjectHash, foundObjectHash);
        userRepository.deleteAll();
    }

    @Test
    public void createOneEntityThenFindItByActiveAndRemoveAllUsersTest() {
        //given
        AppUser savedObject1 = userRepository.save(new AppUser("name1", "login1", "address1", "password1", true));

        //when
        List<AppUser> foundAppUsers = userRepository.findByIsActive(true);

        //then
        assertNotNull(foundAppUsers);
        int savedObjectHash = savedObject1.hashCode();
        int foundObjectHash = foundAppUsers.get(0).hashCode();
        assertEquals(savedObjectHash, foundObjectHash);
        userRepository.deleteAll();
    }

    @Test
    public void createOneEntityThenFindItByLoginAndPasswordAndRemoveAllUsersTest(){
        //given
        AppUser savedObject1 = userRepository.save(new AppUser("name1", "login1", "address1", "password1", true));

        //when
        AppUser foundAppUser = userRepository.findByLoginAndPassword("login1", "password1");

        //then
        assertNotNull(foundAppUser);
        int savedObjectHash = savedObject1.hashCode();
        int foundObjectHash = foundAppUser.hashCode();
        assertEquals(savedObjectHash, foundObjectHash);
        userRepository.deleteAll();
    }
}