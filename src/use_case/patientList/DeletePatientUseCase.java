package use_case.patientList;

import data_access.CSVDatabaseAccessObject;

public class DeletePatientUseCase {
    private CSVDatabaseAccessObject databaseAccessObject;

    public DeletePatientUseCase(CSVDatabaseAccessObject databaseAccessObject) {
        this.databaseAccessObject = databaseAccessObject;
    }

    public void execute(int id) {
        databaseAccessObject.deletePatient(id);
    }
}
