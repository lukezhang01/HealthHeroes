package use_case.signup;

import java.util.ArrayList;
import entity.*;

public class SignupInteractor implements SignupInputBoundary {
    final SignupUserDataAccessInterface userDataAccessObject;
    final SignupOutputBoundary userPresenter;
    final UserFactory userFactory;

    public SignupInteractor(SignupUserDataAccessInterface signupDataAccessInterface,
                            SignupOutputBoundary signupOutputBoundary,
                            UserFactory userFactory) {
        this.userDataAccessObject = signupDataAccessInterface;
        this.userPresenter = signupOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignupInputData signupInputData) {
        if (userDataAccessObject.existsByName(signupInputData.getUsername())) {
            userPresenter.prepareFailView("User already exists.");
        } else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Passwords don't match.");
        } else {

            Doctor doctor = userFactory.create(signupInputData.getUsername(), signupInputData.getPassword(), signupInputData.getCountry(), new ArrayList<>());
            userDataAccessObject.save(doctor);

            SignupOutputData signupOutputData = new SignupOutputData();
            userPresenter.prepareSuccessView(signupOutputData);
        }
    }
}
