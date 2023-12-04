package test.use_case;

import static org.junit.jupiter.api.Assertions.*;

import entity.Doctor;
import entity.Patient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import test.shared.FakeUserFactory; // Assuming this exists as in your previous test cases
import use_case.login.*;

import java.util.*;

public class LoginInteractorTest {

    private LoginInteractor loginInteractor;
    private FakeLoginUserDataAccessObject fakeUserDataAccessObject;
    private FakeLoginOutputBoundary fakeLoginPresenter;

    @BeforeEach
    void setUp() {
        fakeUserDataAccessObject = new FakeLoginUserDataAccessObject();
        fakeLoginPresenter = new FakeLoginOutputBoundary();
        loginInteractor = new LoginInteractor(fakeUserDataAccessObject, fakeLoginPresenter);
    }

    @Test
    void testSuccessfulLogin() {
        Doctor doctor = new Doctor("doctor1", "password1", new ArrayList<>());
        fakeUserDataAccessObject.addUser(doctor);
        LoginInputData inputData = new LoginInputData("doctor1", "password1");
        loginInteractor.executeLogin(inputData);

        assertEquals("doctor1", fakeLoginPresenter.lastOutputData.getUsername());
        assertEquals("SuccessView", fakeLoginPresenter.lastViewShown);
    }

    @Test
    void testLoginWithNonExistentUsername() {
        LoginInputData inputData = new LoginInputData("doctor1", "password1");
        loginInteractor.executeLogin(inputData);

        assertEquals("doctor1: Account does not exist.", fakeLoginPresenter.lastErrorMessage);
        assertEquals("FailView", fakeLoginPresenter.lastViewShown);
    }

    @Test
    void testLoginWithIncorrectPassword() {
        Doctor doctor = new Doctor("doctor1", "password1", new ArrayList<>());
        fakeUserDataAccessObject.addUser(doctor);

        LoginInputData inputData = new LoginInputData("doctor1", "abcde");
        loginInteractor.executeLogin(inputData);

        assertEquals("Incorrect password for doctor1.", fakeLoginPresenter.lastErrorMessage);
        assertNull(fakeLoginPresenter.lastOutputData);
        assertEquals("FailView", fakeLoginPresenter.lastViewShown);
    }


    private static class FakeLoginUserDataAccessObject implements LoginUserDataAccessInterface {
        private Map<String, Doctor> doctors = new HashMap<>();

        @Override
        public boolean existsByName(String username) {
            return doctors.containsKey(username);
        }

        @Override
        public boolean passwordMatch(String username, String password) {
            Doctor doctor = doctors.get(username);
            return doctor != null && doctor.getPassword().equals(password);
        }

        @Override
        public Doctor get(String username) {
            return doctors.get(username);
        }

        public void addUser(Doctor doctor) {
            doctors.put(doctor.getName(), doctor);
        }
    }

    private static class FakeLoginOutputBoundary implements LoginOutputBoundary {
        String lastErrorMessage;
        LoginOutputData lastOutputData;
        String lastViewShown;

        @Override
        public void prepareFailView(String errorMessage) {
            this.lastErrorMessage = errorMessage;
            this.lastViewShown = "FailView";
        }

        @Override
        public void prepareSuccessView(LoginOutputData loginOutputData) {
            this.lastOutputData = loginOutputData;
            this.lastViewShown = "SuccessView";
        }

        @Override
        public void prepareSignupView() {
            this.lastViewShown = "SignupView";
        }
    }
}