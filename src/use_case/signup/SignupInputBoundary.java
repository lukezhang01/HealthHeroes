package use_case.signup;

public interface SignupInputBoundary {
    void executeSubmit(SignupInputData signupInputData);
    void executeCancel();
}
