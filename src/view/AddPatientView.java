package view;

import interface_adapter.patientList.PatientListController;
import use_case.patientList.AddPatientUseCase;
import use_case.patientList.PatientListInteractor;

public class AddPatientView {
    private PatientListInteractor interactor;
    public AddPatientView(PatientListInteractor interactor) {
        this.interactor = interactor;
    }

}
