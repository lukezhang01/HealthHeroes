package use_case.addPatient;

import data_access.CSVDatabaseAccessObject;
import entity.Patient;

import java.util.UUID;

public class AddPatientInteractor implements AddPatientInputBoundary {
    private CSVDatabaseAccessObject databaseAccessObject;
    public AddPatientInteractor(CSVDatabaseAccessObject databaseAccessObject) {
        this.databaseAccessObject = databaseAccessObject;

    }
    @Override
    public void execute(AddPatientInputData addPatientInputData) {
        int id = generateGUID();
        Patient newPatient = new Patient(id, fullName, height, weight, dateOfBirth, gender, appointmentDates, date_added, prescribedDrugs,
                allergies, illnesses, symptoms, lifestyleInformation, isPregnant, additionalNotes);
        databaseAccessObject.addPatient(newPatient);
    }

    private int generateGUID() {
        UUID uuid = UUID.randomUUID();
        // UUID returns 128 bit value > java's 36 bit valued "int"
        long leastSignificantBits = uuid.getLeastSignificantBits();
        return (int)(leastSignificantBits);
    }
}
