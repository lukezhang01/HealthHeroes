package interface_adapter.prescribe;
import java.io.IOException;
import java.time.LocalDate;

import entity.Drug;
import use_case.prescribe.PrescribeInputBoundary;
import use_case.prescribe.PrescribeInputData;

public class PrescribeController {
    final PrescribeInputBoundary prescribeInputBoundary;

    public PrescribeController(PrescribeInputBoundary prescribeInputBoundary){
        this.prescribeInputBoundary = prescribeInputBoundary;
    }

    public void execute(int patientId, String drugName,float dosage, LocalDate start_date, LocalDate end_date) throws IOException {
        PrescribeInputData inputData = new PrescribeInputData(new Drug(drugName,dosage,start_date,end_date),patientId);
        prescribeInputBoundary.execute(inputData);
    }
}
