package use_case.patient;

import entity.Patient;
import use_case.addPatient.AddPatientInputData;

import java.util.ArrayList;
import java.util.Map;

public interface PatientInputBoundary {
    Map<String, String> execute(PatientInputData patientInputData);

    ArrayList<String[]> getDrugs(int id);

    void update(String id, AddPatientInputData data);

    void delete(int id);

    Patient getPatientById(int id);
}
