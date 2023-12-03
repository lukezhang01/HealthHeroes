package use_case.patientList;
import entity.Drug;
import use_case.addPatient.AddPatientUseCase;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import java.util.UUID;

public class PatientListInteractor {
    private FetchPatientsUseCase fetchPatientsUseCase;
    private AddPatientUseCase addPatientUseCase;
    private DeletePatientUseCase deletePatientUseCase;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

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
    public void addPatient(String fullName, float height, float weight, String dateOfBirthString, String gender,
                           String[] appointmentDatesString, ArrayList<Object[]> writtenDrugs,
                           ArrayList<String> allergies, ArrayList<String> illnesses, ArrayList<String> symptoms,
                           String lifestyleInformation, boolean isPregnant, String additionalNotes) {
        LocalDate now = LocalDate.now();
        int id = generateGUID();
        ArrayList<LocalDate> appointmentDates = convertToArrayList(appointmentDatesString);
        ArrayList<Drug> prescribedDrugs = convertToDrugs(writtenDrugs);
        LocalDate dateOfBirth = LocalDate.parse(dateOfBirthString, formatter);
        this.addPatientUseCase.addPatient(id, fullName, height, weight, dateOfBirth, gender, appointmentDates, now,
                prescribedDrugs, allergies, illnesses, symptoms, lifestyleInformation, isPregnant, additionalNotes);
    }

    private int generateGUID() {
        UUID uuid = UUID.randomUUID();
        // UUID returns 128 bit value > java's 36 bit valued "int"
        long leastSignificantBits = uuid.getLeastSignificantBits();
        return (int)(leastSignificantBits);
    }

    private ArrayList<LocalDate> convertToArrayList(String[] list) {
        ArrayList<LocalDate> temp = new ArrayList<>();
        for (String str : list) {
            temp.add(LocalDate.parse(str, formatter));
        }
        return temp;
    }

    private ArrayList<Drug> convertToDrugs(ArrayList<Object[]> list) {
        ArrayList<Drug> temp = new ArrayList<>();
        for (Object[] obj : list) {
            Drug newDrug = new Drug(obj[0].toString(), Float.parseFloat(obj[1].toString()),
                    LocalDate.parse(obj[2].toString(), formatter), LocalDate.parse(obj[3].toString(), formatter));
            temp.add(newDrug);
        }
        return temp;
    }
}
