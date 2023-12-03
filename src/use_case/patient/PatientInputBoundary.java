package use_case.patient;

import java.util.Map;

public interface PatientInputBoundary {
    Map<String, String> execute(PatientInputData patientInputData);
}
