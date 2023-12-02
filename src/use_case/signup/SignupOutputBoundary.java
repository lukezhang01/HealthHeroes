package use_case.signup;

public interface SignupOutputBoundary {
    void prepareLoginView();

    void prepareFailView(String error);

}
