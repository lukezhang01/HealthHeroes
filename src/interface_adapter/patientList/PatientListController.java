package interface_adapter.patientList;

import use_case.patientList.FetchPatientsUseCase;
import use_case.patientList.PatientListInputData;
import use_case.patientList.PatientListInteractor;
import use_case.patientList.PatientListOutputData;
import view.AddPatientView;

import java.util.ArrayList;

public class PatientListController {
    private PatientListInteractor patientListInteractor;
    private PatientListPresenter patientListPresenter;

    public PatientListController(PatientListInteractor patientListInteractor, PatientListPresenter patientListPresenter) {
        this.patientListInteractor = patientListInteractor;
        this.patientListPresenter = patientListPresenter;
    }

    public void loadPatients() {
        ArrayList<PatientListOutputData> patientData = patientListInteractor.fetchPatients();
        System.out.println(patientData.toString());
        patientListPresenter.present(patientData);
    }

    public void handleAddPatient() {
        /*
        needs to call view manager to pull up new view for adding a new patient
         */
        System.out.println("reached controller");
        AddPatientView addPatientView = new AddPatientView(this.patientListInteractor);
        // this.patientListInteractor.addPatient();
    }

    public void handleDeletePatient(PatientListOutputData patient) {
        this.patientListInteractor.deletePatient(patient.getId());
    }

    public void handlePatientClick(PatientListOutputData patient) {
        /*
        needs to call view manager to pull up new view for single patient
         */
    }
}
