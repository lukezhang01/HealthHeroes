package test;

import data_access.CSVDatabaseAccessObject;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.patientList.PatientListController;
import interface_adapter.patientList.PatientListPresenter;
import use_case.addPatient.AddPatientUseCase;
import use_case.patientList.*;
import view.DrugsView;
import view.HomeView;
import view.PatientListView;
import view.SandwichBar;

import java.io.IOException;
import java.util.ArrayList;

public class TestPatientListFactory {
    public static void main(String[] args) {
        try {
            CSVDatabaseAccessObject databaseAccessObject = new CSVDatabaseAccessObject("data/Doctor1.csv");
            PatientListView view = new PatientListView();
            LoggedInViewModel login = new LoggedInViewModel();

            login.setLoggedInUser("asd");
            HomeView homeView = new HomeView(login);
            homeView.setVisible(false);
            DrugsView drugs = new DrugsView();
            drugs.setVisible(false);
            view.setHomeView(homeView);
            view.setDrugsView(drugs);
            view.setSandwichBar(new SandwichBar(view,homeView,view));
            ArrayList<PatientListOutputData> testArray = new ArrayList<>();
            view.display(testArray);

            FetchPatientsUseCase fetch = new FetchPatientsUseCase(databaseAccessObject);
            AddPatientUseCase add = new AddPatientUseCase(databaseAccessObject);
            DeletePatientUseCase delete = new DeletePatientUseCase(databaseAccessObject);

            PatientListInteractor interactor = new PatientListInteractor(fetch, add, delete);

            PatientListPresenter presenter = new PatientListPresenter(view);
            PatientListController controller = new PatientListController(interactor, presenter);
            view.setController(controller);
            controller.loadPatients();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
