package app;

import data_access.CSVDatabaseAccessObject;
import data_access.DirectoryDatabaseAccessObject;
import interface_adapter.ViewManagerModel;
import interface_adapter.logged_in.LoggedInViewModel;
import view.*;
import entity.Doctor;

import java.io.IOException;

public class ApplicationFactory {

    private static final String HOME_ICON = "home_icon.png";
    private static final String PATIENTS_ICON = "patients_icon.png";
    private static final String DRUGS_ICON = "drugs_icon.png";

    public static void initializeApplicationViews(ViewManagerModel viewManagerModel, ViewManager viewManager, LoggedInViewModel loggedInViewModel, String doctorUserName, NavigationBar navBar) {
        DirectoryDatabaseAccessObject directoryDatabaseAccessObject = new DirectoryDatabaseAccessObject();
        Doctor doctor = directoryDatabaseAccessObject.get(doctorUserName);
        CSVDatabaseAccessObject databaseAccessObject = new CSVDatabaseAccessObject("data/Doctor " + doctor.getName() + ".csv");
        // create the Patients List View
        // create all the views
        PatientListView patientListView = PatientsListUseCaseFactory.create(databaseAccessObject);
        HomeView homeView = new HomeView(viewManagerModel, loggedInViewModel, patientListView.getPatients());
        DrugsView drugsView = new DrugsView();
        // add the views
        viewManager.addView(patientListView, patientListView.viewName);
        viewManager.addView(homeView, homeView.viewName);
        viewManager.addView(drugsView, drugsView.viewName);
        // intialize navigation bar
        navBar.addNavigationButton(homeView.viewName, HOME_ICON);
        navBar.addNavigationButton(patientListView.viewName, PATIENTS_ICON);
        navBar.addNavigationButton(drugsView.viewName, DRUGS_ICON);
    }

}
