package interface_adapter.signup;
import use_case.signup.SignupInputBoundary;
import use_case.signup.SignupInputData;
public class SignupController {
    final SignupInputBoundary userSignupUseCaseInteractor;
    public SignupController(SignupInputBoundary userSignupUseCaseInteractor) {
        this.userSignupUseCaseInteractor = userSignupUseCaseInteractor;
    }

    public void handleSubmit(String username, String password, String repeatPassword, String country) {
        SignupInputData signupInputData = new SignupInputData(username, password, repeatPassword, country);

        userSignupUseCaseInteractor.execute(signupInputData);
    }

    public void handleCancel() {
        // TODO: close the signup window and open login window
    }

}
