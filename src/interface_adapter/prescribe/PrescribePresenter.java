package interface_adapter.prescribe;

import interface_adapter.ViewManagerModel;
import interface_adapter.login.LoginState;
import use_case.prescribe.PrescribeOutputBoundary;
import use_case.prescribe.PrescribeOutputData;

import java.io.IOException;

public class PrescribePresenter implements PrescribeOutputBoundary {
    private final PrescribeViewModel viewModel;
    private ViewManagerModel viewManagerModel;

    public PrescribePresenter(PrescribeViewModel viewModel, ViewManagerModel managerModel){
        this.viewModel = viewModel;
        this.viewManagerModel = managerModel;
    }

    public void prepareSuccessView(PrescribeOutputData outputData) throws IOException {
        PrescribeState prescribeState = viewModel.getState();
        prescribeState.setError(true);
        this.viewModel.setState(prescribeState);
        viewModel.firePropertyChanged();
        viewManagerModel.setActiveView("PrescribeSuccess");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) throws IOException {
        PrescribeState prescribeState = viewModel.getState();
        prescribeState.setError(false);
        viewModel.firePropertyChanged();
        viewManagerModel.setActiveView("PrescribeError");
        viewManagerModel.firePropertyChanged();
    }



}
