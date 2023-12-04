package app;

import data_access.CSVDatabaseAccessObject;
import interface_adapter.login.LoginController;
import interface_adapter.patient.PatientController;
import interface_adapter.patientList.PatientListController;
import interface_adapter.patientList.PatientListPresenter;
import use_case.addPatient.AddPatientUseCase;
import use_case.patientList.DeletePatientUseCase;
import use_case.patientList.FetchPatientsUseCase;
import use_case.patientList.PatientListInteractor;
import view.PatientListView;

public class PatientsListUseCaseFactory {



    public static PatientListView create(CSVDatabaseAccessObject databaseAccessObject) {
        FetchPatientsUseCase fetch = new FetchPatientsUseCase(databaseAccessObject);
        AddPatientUseCase add = new AddPatientUseCase(databaseAccessObject);
        DeletePatientUseCase delete = new DeletePatientUseCase(databaseAccessObject);
        PatientListView view = new PatientListView();
        PatientListController patientListController = createPatientListController(view, fetch, add, delete);
        // load the patients
        patientListController.loadPatients();
        view.setController(patientListController);
        return view;
    }



    private static PatientListController createPatientListController(PatientListView patientListView, FetchPatientsUseCase fetchPatientsUseCase, AddPatientUseCase addPatientUseCase, DeletePatientUseCase deletePatientUseCase) {
        PatientListInteractor interactor = new PatientListInteractor(fetchPatientsUseCase, addPatientUseCase, deletePatientUseCase);
        PatientListPresenter presenter = new PatientListPresenter(patientListView);
        return new PatientListController(interactor, presenter);
    }
}
