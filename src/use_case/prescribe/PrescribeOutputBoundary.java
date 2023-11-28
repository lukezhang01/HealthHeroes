package use_case.prescribe;
import entity.*;

public interface PrescribeOutputBoundary{
    void prepareSuccessView(PrescribeOutputData user);

    void prepareFailView(String error);

}
