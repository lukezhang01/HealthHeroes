package use_case.prescribe;
import entity.*;

import java.io.IOException;

public interface PrescribeInputBoundary {
    void execute(PrescribeInputData PrescribeInputData) throws IOException;
}
