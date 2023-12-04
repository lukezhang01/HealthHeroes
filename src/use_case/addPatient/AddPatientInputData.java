package use_case.addPatient;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddPatientInputData {
    private String fullName;
    private float height;
    private float weight;
    private LocalDate dateOfBirth;
    private String gender;
    private ArrayList<LocalDate> appointmentDates;
    private LocalDate dateAdded;
    private ArrayList<String[]> prescribed_drugs;
    private ArrayList<String> allergies;
    private ArrayList<String> illnesses;
    private ArrayList<String> symptoms;
    private String lifestyleInformation;
    private boolean isPregnant;
    private String additionalNotes;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");


    public AddPatientInputData(String fullName, String height, String weight, String dateOfBirth, String gender,
                               String[] appointmentDates, ArrayList<String[]> prescribedDrugs, String[] allergies,
                               String[] illnesses, String[] symptoms, String lifestyleInformation, String isPregnant, String additionalNotes) {
        this.fullName = fullName;
        this.height = Float.parseFloat(height);
        this.weight = Float.parseFloat(weight);
        this.dateOfBirth = LocalDate.parse(dateOfBirth, formatter);
        this.gender = gender;
        this.appointmentDates = convertDates(appointmentDates);
        this.prescribed_drugs = prescribedDrugs;
        this.allergies = convertToArrayList(allergies);
        this.illnesses = convertToArrayList(illnesses);
        this.symptoms = convertToArrayList(symptoms);
        this.lifestyleInformation = lifestyleInformation;
        this.isPregnant = convertIsPregnant(isPregnant);
        this.additionalNotes = additionalNotes;

        this.dateAdded = LocalDate.now();
    }

    public ArrayList<LocalDate> convertDates(String[] list) {
        ArrayList<LocalDate> dates = new ArrayList<>();
        System.out.println(Arrays.toString(list));
        if (list.length == 0 || Arrays.toString(list).equals("[]")) {
            // by default make the current date the appointment date
            dates.add(LocalDate.now());
            return dates;
        }
        for (String str : list) {
            dates.add(LocalDate.parse(str, formatter));
        }
        return dates;
    }

    public ArrayList<String> convertToArrayList(String[] list) {
        return new ArrayList<>(List.of(list));
    }

    public boolean convertIsPregnant(String str) {
        return str.equals("True");
    }

    public String getFullName() {
        return fullName;
    }

    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public ArrayList<LocalDate> getAppointmentDates() {
        return appointmentDates;
    }

    public LocalDate getDateAdded() {
        return dateAdded;
    }

    public ArrayList<String[]> getPrescribedDrugs() {
        return prescribed_drugs;
    }

    public ArrayList<String> getAllergies() {
        return allergies;
    }

    public ArrayList<String> getIllnesses() {
        return illnesses;
    }

    public ArrayList<String> getSymptoms() {
        return symptoms;
    }

    public String getLifestyleInformation() {
        return lifestyleInformation;
    }

    public boolean getIsPregnant() {
        return isPregnant;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

}
