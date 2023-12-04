package interface_adapter.patient;

import use_case.addPatient.AddPatientInputData;
import use_case.patient.PatientInputBoundary;
import use_case.patient.PatientInputData;
import use_case.patient.PatientOutputData;
import view.PatientView;

import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatientController {
    private Map<String, String> outputData;

    private final PatientInputBoundary patientUseCaseInteractor;
    private ArrayList<String[]> drugs;

    public PatientController(PatientInputBoundary patientUseCaseInteractor) {
        this.patientUseCaseInteractor = patientUseCaseInteractor;
    }

    public void execute(int id) {
        PatientInputData patientInputData = new PatientInputData(id);
        this.outputData = patientUseCaseInteractor.execute(patientInputData);
        System.out.println(outputData.keySet());
        System.out.println(outputData.values());
        this.drugs = this.patientUseCaseInteractor.getDrugs(id);
        // System.out.println("reached execute");
        PatientView view = new PatientView(this, id);
    }

    public void update(String fullName, String height, String weight, String dateOfBirth, String gender,
                       String[] appointmentDates, ArrayList<String[]> prescribedDrugs, String[] allergies,
                       String[] illnesses, String[] symptoms, String lifestyleInformation, String isPregnant, String additionalNotes) {
        // check appointment dates is formatted right
        String trimmedHeight = extractNumber(height);
        String trimmedWeight = extractNumber(weight);
        System.out.println(outputData.get("id"));
        AddPatientInputData data = new AddPatientInputData(fullName, trimmedHeight, trimmedWeight, dateOfBirth, gender,
                appointmentDates, prescribedDrugs, allergies, illnesses, symptoms, lifestyleInformation, isPregnant, additionalNotes);
        this.patientUseCaseInteractor.update(outputData.get("id"), data);
    }

    private static String extractNumber(String str) {
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?"); // Regular expression to find floating point numbers
        Matcher matcher = pattern.matcher(str);

        if (matcher.find()) {
            return matcher.group(); // Return the first sequence of digits (and optional decimal part) found
        }

        return ""; // Return empty string if no numbers found
    }

    public String getNameField() {
        return outputData.get("full_name");
    }

    public String getHeightField() {
        return outputData.get("height");
    }

    public String getWeightField() {
        return outputData.get("weight");
    }

    public String getDateOfBirthField() {
        return outputData.get("date_of_birth");
    }

    public String getGenderField() {
        return outputData.get("gender");
    }

    public String getAppointmentDatesField() {
        return outputData.get("appointment_date");
    }

    public String getAllergiesField() {
        return outputData.get("allergies");
    }

    public String getIllnessesField() {
        return outputData.get("illnesses");
    }

    public String getSymptomsField() {
        return outputData.get("symptoms");
    }

    public String getLifestyleInformationField() {
        return outputData.get("lifestyle_information");
    }

    public String getAdditionalNotesField() {
        return outputData.get("additional_notes");
    }

    public ArrayList<String[]> getDrugs() {
        
        return this.drugs;
    }

    public void delete(int id) {
        this.patientUseCaseInteractor.delete(id);
    }
}
