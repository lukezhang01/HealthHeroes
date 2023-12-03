package interface_adapter.addPatient;

import use_case.addPatient.AddPatientOutputBoundary;

public class AddPatientPresenter implements AddPatientOutputBoundary {
    private final AddPatientViewModel viewModel;

    public AddPatientPresenter(AddPatientViewModel viewModel) {
        this.viewModel = viewModel;
    }

}
