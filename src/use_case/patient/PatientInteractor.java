package use_case.patient;

import data_access.CSVDatabaseAccessObject;

import java.util.ArrayList;
import java.util.Map;

public class PatientInteractor implements PatientInputBoundary {
    private final CSVDatabaseAccessObject databaseAccessObject;

    public PatientInteractor(CSVDatabaseAccessObject databaseAccessObject) {
        this.databaseAccessObject = databaseAccessObject;
    }
    @Override
    public Map<String, String> execute(PatientInputData patientInputData) {
        int id = patientInputData.getId();
        return null;
    }

    public ArrayList<String[]> getDrugs() {
        return null;
    }
}
