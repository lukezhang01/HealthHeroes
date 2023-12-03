package use_case.addPatient;

import entity.Drug;

import java.time.LocalDate;
import java.util.ArrayList;

public class AddPatientInputData {
    private String fullName;
    private float height;
    private float weight;
    private LocalDate dateOfBirth;
    private String gender;
    private ArrayList<LocalDate> appointmentDates;
    private LocalDate dateAdded;
    private ArrayList<Drug> prescribed_drugs;
    private ArrayList<String> allergies;
    private ArrayList<String> illnesses;
    private ArrayList<String> symptoms;
    private String lifestyleInformation;
    private String additionalNotes;

    public AddPatientInputData(String fullName, String height, String weight, String dateOfBirth, String gender,
                               String[] appointmentDates, ArrayList<String[]> prescribedDrugs, String[] allergies,
                               String[] illnesses, String[] symptoms, String lifestyleInformation, String additionalNotes) {
        this.fullName = fullName;
        this.height = Float.parseFloat(height);
        this.weight = Float.parseFloat(weight);
        this.dateOfBirth = LocalDate.parse(dateOfBirth, formatter);
    }

}
