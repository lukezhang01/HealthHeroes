package use_case.prescribe;

public interface PrescribeOutputBoundary{
    void prepareSuccessView(PrescribeOutputData user);

    void prepareFailView(String error);

}
