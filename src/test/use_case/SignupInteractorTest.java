package test.use_case;

import static org.junit.jupiter.api.Assertions.*;


import entity.Doctor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.shared.FakeUserFactory;
import use_case.signup.SignupInputData;
import use_case.signup.SignupInteractor;
import use_case.signup.SignupOutputBoundary;
import use_case.signup.SignupUserDataAccessInterface;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SignupInteractorTest {

    private SignupInteractor signupInteractor;
    private FakeUserDataAccessObject fakeUserDataAccessObject;
    private FakeUserPresenter fakeUserPresenter;
    private FakeUserFactory fakeUserFactory;

    @BeforeEach
    void setUp() {
        fakeUserDataAccessObject = new FakeUserDataAccessObject();
        fakeUserPresenter = new FakeUserPresenter();
        fakeUserFactory = new FakeUserFactory();
        signupInteractor = new SignupInteractor(fakeUserDataAccessObject, fakeUserPresenter, fakeUserFactory);
    }

    @Test
    void testSuccessfulSignup() {
        SignupInputData inputData = new SignupInputData("newuser", "password", "password", "USA");
        signupInteractor.executeSubmit(inputData);
        assertTrue(fakeUserDataAccessObject.containsUser("newuser"));
        assertEquals("LoginView", fakeUserPresenter.lastViewShown);
    }

    @Test
    void testSignupWithExistingUsername() {
        fakeUserDataAccessObject.addUser("user", "password");
        SignupInputData inputData = new SignupInputData("user", "password", "password", "USA");
        signupInteractor.executeSubmit(inputData);
        assertEquals("This username already exists.", fakeUserPresenter.lastErrorMessage);
    }
    @Test
    void testSignupWithNonMatchingPasswords() {
        SignupInputData inputData = new SignupInputData("newuser", "password", "abcde", "USA");
        signupInteractor.executeSubmit(inputData);
        assertEquals("Entered passwords don't match.", fakeUserPresenter.lastErrorMessage);
        assertFalse(fakeUserDataAccessObject.existsByName("newuser"));
    }

    @Test
    void testSuccessfulSignupCreatesDoctor() {
        SignupInputData inputData = new SignupInputData("newuser", "password", "password", "USA");

        signupInteractor.executeSubmit(inputData);

        Doctor createdDoctor = fakeUserFactory.getLastCreatedDoctor();
        assertNotNull(createdDoctor);
        assertEquals("newuser", createdDoctor.getName());
        assertEquals("password", createdDoctor.getPassword());

        assertTrue(fakeUserDataAccessObject.existsByName("newuser"));
    }

    private static class FakeUserDataAccessObject implements SignupUserDataAccessInterface {
        private Set<String> usernames = new HashSet<>();

        @Override
        public boolean existsByName(String username) {
            return usernames.contains(username);
        }

        @Override
        public void saveNewDoctor(String username, String password) {
            usernames.add(username);
        }

        void addUser(String username, String password) {
            usernames.add(username);
        }

        boolean containsUser(String username) {
            return usernames.contains(username);
        }
    }

    private static class FakeUserPresenter implements SignupOutputBoundary {
        String lastErrorMessage;
        String lastViewShown;

        @Override
        public void prepareFailView(String errorMessage) {
            this.lastErrorMessage = errorMessage;
            this.lastViewShown = "FailView";
        }

        @Override
        public void prepareLoginView() {
            this.lastViewShown = "LoginView";
        }
    }


}