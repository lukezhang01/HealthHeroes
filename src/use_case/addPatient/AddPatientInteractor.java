package use_case.addPatient;

import data_access.CSVDatabaseAccessInterface;
import data_access.CSVDatabaseAccessObject;
import entity.Drug;
import entity.Patient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

public class AddPatientInteractor implements AddPatientInputBoundary {
    private CSVDatabaseAccessInterface databaseAccessObject;
    private AddPatientOutputBoundary presenter;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public AddPatientInteractor(CSVDatabaseAccessInterface databaseAccessObject, AddPatientOutputBoundary presenter) {
        this.databaseAccessObject = databaseAccessObject;
        this.presenter = presenter;
    }
    @Override
    public void execute(AddPatientInputData addPatientInputData) {
        int id = generateGUID();
        // System.out.println(id);
        String fullName = addPatientInputData.getFullName();
        float height = addPatientInputData.getHeight();
        float weight = addPatientInputData.getWeight();
        LocalDate dateOfBirth = addPatientInputData.getDateOfBirth();
        String gender = addPatientInputData.getGender();
        ArrayList<LocalDate> appointmentDates = addPatientInputData.getAppointmentDates();
        System.out.println(addPatientInputData.getDateAdded());
        LocalDate date_added = addPatientInputData.getDateAdded();
        ArrayList<Drug> prescribedDrugs = convertToDrugs(addPatientInputData.getPrescribedDrugs());
        ArrayList<String> allergies = addPatientInputData.getAllergies();
        ArrayList<String> illnesses = addPatientInputData.getIllnesses();
        ArrayList<String> symptoms = addPatientInputData.getSymptoms();
        String lifestyleInformation = addPatientInputData.getLifestyleInformation();
        boolean isPregnant = addPatientInputData.getIsPregnant();
        String additionalNotes = addPatientInputData.getAdditionalNotes();
        Patient newPatient = new Patient(id, fullName, height, weight, dateOfBirth, gender, appointmentDates, date_added, prescribedDrugs,
                allergies, illnesses, symptoms, lifestyleInformation, isPregnant, additionalNotes);
        databaseAccessObject.addPatient(newPatient);
    }

    @Override
    public void display() {
        presenter.display();
    }

    private int generateGUID() {
        UUID uuid = UUID.randomUUID();
        // UUID returns 128 bit value > java's 36 bit valued "int"
        long leastSignificantBits = uuid.getLeastSignificantBits();
        return (int)(leastSignificantBits);
    }

    // typically it'd make more sense to convert to ArrayList<Drug> in the InputData, but InputData does not have access
    // to entities, so it will be done here
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
