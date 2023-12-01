package use_case.patientList;

import entity.Drug;
import entity.Patient;

import java.time.LocalDate;
import java.util.ArrayList;

public class AddPatientUseCase {
    public AddPatientUseCase() {

    }

    public Patient addPatient(int id, String fullName, float height, float weight,
                              ArrayList<LocalDate> appointmentDates, LocalDate date_added, ArrayList<Drug> prescribedDrugs,
                              ArrayList<String> allergies, ArrayList<String> illnesses, ArrayList<String> symptoms,
                              String lifestyleInformation, boolean isPregnant, String additionalNotes) {
        return new Patient(id, fullName, height, weight, appointmentDates, date_added, prescribedDrugs, allergies, illnesses, symptoms, lifestyleInformation, isPregnant, additionalNotes);
    }
}
