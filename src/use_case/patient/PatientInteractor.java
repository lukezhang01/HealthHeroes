package use_case.patient;

import data_access.CSVDatabaseAccessObject;
import entity.Drug;
import entity.Patient;
import use_case.addPatient.AddPatientInputData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class PatientInteractor implements PatientInputBoundary {
    private final CSVDatabaseAccessObject databaseAccessObject;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public PatientInteractor(CSVDatabaseAccessObject databaseAccessObject) {
        this.databaseAccessObject = databaseAccessObject;
    }
    @Override
    public Map<String, String> execute(PatientInputData patientInputData) {
        int id = patientInputData.getId();
        assert databaseAccessObject.existsById(id);
        System.out.println(databaseAccessObject.getPatientData(id).values());
        return databaseAccessObject.getPatientData(id);
    }

    public Patient getPatientById(int id){
        return databaseAccessObject.getPatient(id);
    }

    @Override
    public ArrayList<String[]> getDrugs(int id) {
        return databaseAccessObject.getDrugsAsStringList(id);
    }

    @Override
    public void update(String id, AddPatientInputData data) {
        String fullName = data.getFullName();
        float height = data.getHeight();
        float weight = data.getWeight();
        LocalDate dateOfBirth = data.getDateOfBirth();
        String gender = data.getGender();
        ArrayList<LocalDate> appointmentDates = data.getAppointmentDates();
        System.out.println(data.getDateAdded());
        LocalDate date_added = data.getDateAdded();
        ArrayList<Drug> prescribedDrugs = convertToDrugs(data.getPrescribedDrugs());
        ArrayList<String> allergies = data.getAllergies();
        ArrayList<String> illnesses = data.getIllnesses();
        ArrayList<String> symptoms = data.getSymptoms();
        String lifestyleInformation = data.getLifestyleInformation();
        boolean isPregnant = data.getIsPregnant();
        String additionalNotes = data.getAdditionalNotes();
        Patient newPatient = new Patient(Integer.parseInt(id), fullName, height, weight, dateOfBirth, gender, appointmentDates, date_added, prescribedDrugs,
                allergies, illnesses, symptoms, lifestyleInformation, isPregnant, additionalNotes);
        databaseAccessObject.saveSinglePatient(id, newPatient);
    }

    @Override
    public void delete(int id) {
        databaseAccessObject.deletePatient(id);
    }

    private ArrayList<Drug> convertToDrugs(ArrayList<String[]> list) {
        ArrayList<Drug> drugs = new ArrayList<>();
        for (String[] sublist : list) {
            // this means nothing was entered so we skip this entry
            if (sublist[1].equals("Dosage")) {
                continue;
            }
            Drug drug = new Drug(sublist[0], Float.parseFloat(sublist[1]),
                    LocalDate.parse(sublist[2], formatter), LocalDate.parse(sublist[3], formatter));
            drugs.add(drug);
        }
        return drugs;
    }
}
