package use_case.patientList;

import data_access.CSVDatabaseAccessInterface;
import data_access.CSVDatabaseAccessObject;
import entity.Drug;
import entity.Patient;

import java.time.LocalDate;
import java.util.ArrayList;

public class AddPatientUseCase implements PatientListInputBoundary {
    private CSVDatabaseAccessObject databaseAccessObject;
    public AddPatientUseCase(CSVDatabaseAccessObject databaseAccessObject) {
        this.databaseAccessObject = databaseAccessObject;

    }

    public void addPatient(int id, String fullName, float height, float weight, LocalDate dateOfBirth, String gender,
                              ArrayList<LocalDate> appointmentDates, LocalDate date_added, ArrayList<Drug> prescribedDrugs,
                              ArrayList<String> allergies, ArrayList<String> illnesses, ArrayList<String> symptoms,
                              String lifestyleInformation, boolean isPregnant, String additionalNotes) {
        Patient newPatient = new Patient(id, fullName, height, weight, dateOfBirth, gender, appointmentDates, date_added, prescribedDrugs,
                allergies, illnesses, symptoms, lifestyleInformation, isPregnant, additionalNotes);
        databaseAccessObject.addPatient(newPatient);
    }
}
