package interface_adapter.patient;

import use_case.patient.PatientInputBoundary;
import use_case.patient.PatientInputData;
import use_case.patient.PatientOutputData;

import java.util.Map;

public class PatientController {
    private Map<String, String> outputData;

    private final PatientInputBoundary patientUseCaseInteractor;

    public PatientController(PatientInputBoundary patientUseCaseInteractor) {
        this.patientUseCaseInteractor = patientUseCaseInteractor;
    }

    public void execute(int id) {
        PatientInputData patientInputData = new PatientInputData(id);
        this.outputData = patientUseCaseInteractor.execute(patientInputData);
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
}
