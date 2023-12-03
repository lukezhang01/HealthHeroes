package use_case.patientList;

/*
    This is a use case that fetches all users. It uses the CSVRepository to fetch the users and then maps them to a UserOutputData object.
 */

import data_access.CSVDatabaseAccessInterface;
import entity.Patient;

import java.util.ArrayList;
import java.util.Map;

public class FetchPatientsUseCase implements PatientListOutputBoundary {
    private CSVDatabaseAccessInterface dataAccessObject;

    public FetchPatientsUseCase(CSVDatabaseAccessInterface dataAccessObject) {
        this.dataAccessObject = dataAccessObject;
    }

    public ArrayList<PatientListOutputData> execute() {
        ArrayList<PatientListOutputData> allOutputData = new ArrayList<>();
        Map<Integer, Patient> patients = dataAccessObject.getAllPatients();
        for (Patient patient : patients.values()) {
            PatientListOutputData outputData = new PatientListOutputData(patient.fullName, patient.getID(),
                    patient.getLatestAppointmentDate(), patient.getDateAdded().toString());
            allOutputData.add(outputData);
        }
        return allOutputData;
    }
}
