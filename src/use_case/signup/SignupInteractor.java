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
    public void executeSubmit(SignupInputData signupInputData) {
        if (userDataAccessObject.existsByName(signupInputData.getUsername())) {
            userPresenter.prepareFailView("This username already exists.");
        } else if (!signupInputData.getPassword().equals(signupInputData.getRepeatPassword())) {
            userPresenter.prepareFailView("Entered passwords don't match.");
        } else if (signupInputData.getCountry().equals("-- Select --")) {
            userPresenter.prepareFailView("No country selected.");
        } else {
            Doctor doctor = userFactory.create(signupInputData.getUsername(), signupInputData.getPassword(), signupInputData.getCountry(), new ArrayList<>());
            userDataAccessObject.saveNewDoctor(doctor.getName(), doctor.getPassword());
            SignupOutputData signupOutputData = new SignupOutputData(doctor.getName());
            userPresenter.prepareLoginView();
        }
    }

    @Override
    public void executeCancel(){
        userPresenter.prepareLoginView();
    }
}
