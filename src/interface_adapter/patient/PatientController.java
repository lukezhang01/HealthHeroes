package interface_adapter.patient;

import use_case.patient.PatientInputBoundary;
import use_case.patient.PatientInputData;
import use_case.patient.PatientOutputData;

import java.util.ArrayList;
import java.util.Map;

public class PatientController {
    private Map<String, String> outputData;
    private int id;

    private final PatientInputBoundary patientUseCaseInteractor;

    public PatientController(PatientInputBoundary patientUseCaseInteractor, int id) {
        this.id = id;
        this.patientUseCaseInteractor = patientUseCaseInteractor;
    }

    public void execute() {
        PatientInputData patientInputData = new PatientInputData(id);
        this.outputData = patientUseCaseInteractor.execute(patientInputData);
    }

    public void update(String fullName, String height, String weight, String dateOfBirth, String gender,
                       String[] appointmentDates, ArrayList<String[]> prescribedDrugs, String[] allergies,
                       String[] illnesses, String[] symptoms, String lifestyleInformation, String isPregnant, String additionalNotes) {

    }

    public String getNameField() {
        return outputData.get("fullName");
    }

    public String getHeightField() {
        return outputData.get("height");
    }

    public String getWeightField() {
        return outputData.get("weight");
    }

    public String getDateOfBirthField() {
        return outputData.get("dateOfBirth");
    }

    public String getGenderField() {
        return outputData.get("gender");
    }

    public String getAppointmentDatesField() {
        return outputData.get("appointmentDates");
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
        return outputData.get("lifestyleInformation");
    }

    public String getAdditionalNotesField() {
        return outputData.get("additionalNotes");
    }

    public ArrayList<String[]> getDrugs() {
        return null;
    }
}
