package use_case.prescribe;
import entity.*;

import java.io.IOException;

public interface PrescribeOutputBoundary{
    void prepareSuccessView(PrescribeOutputData user) throws IOException;

    void prepareFailView(String error) throws IOException;

}
