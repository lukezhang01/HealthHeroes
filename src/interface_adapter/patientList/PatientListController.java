package interface_adapter.patientList;

import use_case.patientList.FetchPatientsUseCase;
import use_case.patientList.PatientListOutputData;

import java.util.ArrayList;

public class PatientListController {
    private FetchPatientsUseCase fetchPatientsUseCase;
    private PatientListPresenter patientListPresenter;

    public PatientListController(FetchPatientsUseCase fetchPatientsUseCase, PatientListPresenter patientListPresenter) {
        this.fetchPatientsUseCase = fetchPatientsUseCase;
        this.patientListPresenter = patientListPresenter;
    }

    public void handleFetchPatients() {
        ArrayList<PatientListOutputData> patients = fetchPatientsUseCase.execute();
        patientListPresenter.present(patients);
    }

    public void handleAddPatient() {
        // call a class to present AddPatientView
    }

    public void handleDeletePatient(PatientListOutputData patient) {

    }

    public void handlePatientClick(PatientListOutputData patient) {
    }
}
