package use_case.ll;

public interface SignupOutputBoundary {
    void prepareSuccessView(SignupOutputData user);

    void prepareFailView(String error);

}
