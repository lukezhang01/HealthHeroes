package use_case.prescribe;
import entity.*;

import java.io.IOException;


public class PrescribeInteractor implements PrescribeInputBoundary{
    final PrescribeUserDataInterface userDataAccessObject;
    final PrescribeOutputBoundary userPresenter;
    final UserFactory userFactory;


    public PrescribeInteractor(PrescribeUserDataInterface prescribeUserDataInterface,
                            PrescribeOutputBoundary prescribeOutputBoundary,
                            UserFactory userFactory) {
        this.userDataAccessObject = prescribeUserDataInterface;
        this.userPresenter = prescribeOutputBoundary;
        this.userFactory = userFactory;
    }



    @Override
    public void execute(PrescribeInputData inputData) throws IOException {
        if(userDataAccessObject.getPatient(inputData.getPatientid()) != null){
            userDataAccessObject.getPatient(inputData.getPatientid()).addDrug(inputData.getDrug());
            userPresenter.prepareSuccessView(new PrescribeOutputData(inputData.getDrug()));
        }
        else{
            userPresenter.prepareFailView("Patient does not exist");
        }


    }
}
