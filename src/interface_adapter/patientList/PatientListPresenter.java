package interface_adapter.patientList;

import use_case.patientList.PatientListOutputData;
import view.PatientListView;

import java.util.ArrayList;

public class PatientListPresenter {
    private PatientListView patientListView;

    public PatientListPresenter(PatientListView patientListView) {
        this.patientListView = patientListView;
    }

    public void present(ArrayList<PatientListOutputData> patients) {
        patientListView.display(patients);
    }

}
