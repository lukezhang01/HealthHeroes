package app;

import data_access.CSVDatabaseAccessInterface;
import data_access.CSVDatabaseAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginController;
import interface_adapter.login.LoginPresenter;
import interface_adapter.login.LoginViewModel;
import interface_adapter.patientList.PatientListController;
import interface_adapter.patientList.PatientListPresenter;
import interface_adapter.signup.SignupViewModel;
import use_case.addPatient.AddPatientUseCase;
import use_case.patientList.*;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws IOException {
        // TEST PATIENT
        CSVDatabaseAccessObject databaseAccessObject = new CSVDatabaseAccessObject("data/Doctor1.csv");
        PatientListView view = new PatientListView();
        LoggedInViewModel login = new LoggedInViewModel();
        login.setLoggedInUser("asd");
        ArrayList<PatientListOutputData> testArray = new ArrayList<>();
        FetchPatientsUseCase fetch = new FetchPatientsUseCase(databaseAccessObject);
        AddPatientUseCase add = new AddPatientUseCase(databaseAccessObject);
        DeletePatientUseCase delete = new DeletePatientUseCase(databaseAccessObject);

        PatientListInteractor interactor = new PatientListInteractor(fetch, add, delete);

        PatientListPresenter presenter = new PatientListPresenter(view);
        PatientListController controller = new PatientListController(interactor, presenter);
        view.setController(controller);
        controller.loadPatients();
        ///
        JFrame application = new JFrame("Health Hero");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        CardLayout cardLayout = new CardLayout();

        JPanel views = new JPanel(cardLayout);
        application.add(views, BorderLayout.CENTER);
        application.setMaximumSize(ViewModel.VIEW_DIMENSION);
        application.setMinimumSize(ViewModel.VIEW_DIMENSION);

        ViewManagerModel viewManagerModel = new ViewManagerModel();
        NavigationBar navBar = new NavigationBar(viewManagerModel);
        new ViewManager(views, cardLayout, viewManagerModel);
        // add patient list
        views.add(view, view.viewName);
        // add home view
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        HomeView homeView = new HomeView(loggedInViewModel);
        views.add(homeView, homeView.viewName);
        // add drugs view
        DrugsView drugsView = new DrugsView();
        views.add(drugsView, drugsView.viewName);
        // add nav buttons
        navBar.addNavigationButton(homeView.viewName, "home_icon.png");
        navBar.addNavigationButton(view.viewName, "patients_icon.png");
        navBar.addNavigationButton(drugsView.viewName, "drugs_icon.png");
        application.add(navBar, BorderLayout.NORTH);
        //  add buttons


        navBar.setActiveView(homeView.viewName);

        application.pack();
        application.setVisible(true);
    }
}
