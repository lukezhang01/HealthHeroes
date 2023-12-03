package interface_adapter.addPatient;

import use_case.addPatient.AddPatientOutputBoundary;
import use_case.addPatient.AddPatientOutputData;

public class AddPatientPresenter implements AddPatientOutputBoundary {
    private final AddPatientViewModel viewModel;

    public AddPatientPresenter(AddPatientViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void display() {
        this.viewModel.display();
    }
}
