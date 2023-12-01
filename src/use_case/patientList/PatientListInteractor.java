package use_case.patientList;
import entity.Drug;

import java.time.LocalDate;
import java.util.ArrayList;

public class PatientListInteractor {
    private FetchPatientsUseCase fetchPatientsUseCase;
    private AddPatientUseCase addPatientUseCase;
    private DeletePatientUseCase deletePatientUseCase;

    public PatientListInteractor(FetchPatientsUseCase fetchPatientsUseCase, AddPatientUseCase addPatientUseCase, DeletePatientUseCase deletePatientUseCase) {
        this.fetchPatientsUseCase = fetchPatientsUseCase;
        this.addPatientUseCase = addPatientUseCase;
        this.deletePatientUseCase = deletePatientUseCase;
    }

    /*
        fetchPatientsUseCase.exceute gets patients from database
        this class is to return an arraylist of all patients for the controller
     */
    public ArrayList<PatientListOutputData> fetchPatients() {
        return fetchPatientsUseCase.execute();
    }

    /*
        this class will delete the patient with the given id from the database
        for the controller
     */
    public void deletePatient(int id) {
        this.deletePatientUseCase.deletePatient(id);
    }

    /*
        this class will add a patient to the database for the controller
     */
    public void addPatient(int id, String fullName, float height, float weight,
                           ArrayList<LocalDate> appointmentDates, ArrayList<Drug> prescribedDrugs,
                           ArrayList<String> allergies, ArrayList<String> illnesses, ArrayList<String> symptoms,
                           String lifestyleInformation, boolean isPregnant, String additionalNotes) {
        LocalDate now = LocalDate.now();
        this.addPatientUseCase.addPatient(id, fullName, height, weight, appointmentDates, now,
                prescribedDrugs, allergies, illnesses, symptoms, lifestyleInformation, isPregnant, additionalNotes);
    }

}
