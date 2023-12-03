package interface_adapter.addPatient;

import use_case.addPatient.AddPatientInputBoundary;
import use_case.addPatient.AddPatientInputData;

import java.util.ArrayList;

public class AddPatientController {
    final AddPatientInputBoundary addPatientInteractor;

    public AddPatientController(AddPatientInputBoundary addPatientInteractor) {
        this.addPatientInteractor = addPatientInteractor;
    }

    public void execute(String fullName, String height, String weight, String dateOfBirth, String gender,
                        String[] appointmentDates, ArrayList<String[]> prescribedDrugs, String[] allergies,
                        String[] illnesses, String[] symptoms, String lifestyleInformation, String additionalNotes) {
        AddPatientInputData inputData = new AddPatientInputData(fullName, height, weight, dateOfBirth, gender,
                appointmentDates, prescribedDrugs, allergies, illnesses, symptoms, lifestyleInformation, additionalNotes);
        this.addPatientInteractor.execute(inputData);
    }
}
