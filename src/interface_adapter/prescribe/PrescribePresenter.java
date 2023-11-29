package interface_adapter.prescribe;

import interface_adapter.ViewManagerModel;
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
    }

    @Override
    public void prepareFailView(String error) throws IOException {
    }



}
