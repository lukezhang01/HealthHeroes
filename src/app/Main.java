package app;

import data_access.CSVDatabaseAccessInterface;
import data_access.CSVDatabaseAccessObject;
import interface_adapter.patientList.PatientListController;
import interface_adapter.patientList.PatientListPresenter;
import use_case.patientList.FetchPatientsUseCase;
import view.PatientListView;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
//            CSVDatabaseAccessObject databaseAccessObject = new CSVDatabaseAccessObject("../data/Doctor1");
//            FetchPatientsUseCase fetchPatientsUseCase = new FetchPatientsUseCase(databaseAccessObject);
//            PatientListController controller = new PatientListController();
        } catch (IOException e) {

        }
    }
}
