package app;

import data_access.*;
import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.login.LoginViewModel;
import interface_adapter.signup.SignupViewModel;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;

public class Main {

    private static void setApplicationToLoggedIn(JFrame application) {
        application.setTitle("[HEALTH HEROES] Log In");
        application.setMaximumSize(ViewModel.LOGIN_DIMENSION);
        application.setMinimumSize(ViewModel.LOGIN_DIMENSION);
        application.setSize(ViewModel.LOGIN_DIMENSION);
    }

    private static void setApplicationToUI(JFrame application) {
        application.setMaximumSize(ViewModel.VIEW_DIMENSION);
        application.setMinimumSize(ViewModel.VIEW_DIMENSION);
        application.setSize(ViewModel.VIEW_DIMENSION);
    }

    public
    static void main(String[] args) throws IOException {

        // create main application
        JFrame application = new JFrame("");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        // create view manager and nav bar
        ViewManager viewManager = new ViewManager(viewManagerModel);
        NavigationBar navBar = new NavigationBar(viewManagerModel, "Home");
        // set login size
        setApplicationToLoggedIn(application);
        // log in data
        LoginViewModel loginViewModel = new LoginViewModel();
        LoggedInViewModel loggedInViewModel = new LoggedInViewModel();
        SignupViewModel signupViewModel = new SignupViewModel();

        DirectoryDatabaseAccessObject userDataAccessObject = new DirectoryDatabaseAccessObject();

        LoginView loginView = LoginUseCaseFactory.create(viewManagerModel, loginViewModel, loggedInViewModel, userDataAccessObject);
        SignupView signupView = SignupUseCaseFactory.create(viewManagerModel, loginViewModel, signupViewModel, userDataAccessObject);

        // detect when we are back to sign in view.
        viewManagerModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if (viewManagerModel.getActiveView().equals(loginView.viewName)){
                    // we switched to signupview
                    navBar.setVisible(false);
                    // change size
                    setApplicationToLoggedIn(application);
                }
            }
        });
        loggedInViewModel.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                LoggedInState state = (LoggedInState) evt.getNewValue();
                // we have logged in
                if (state.getUsername() != null) {
                    ApplicationFactory.initializeApplicationViews(viewManagerModel, viewManager, loggedInViewModel, state.getUsername(), navBar);
                    navBar.setVisible(true);
                    navBar.setActiveView(navBar.getDefaultView());
                    // set size
                    setApplicationToUI(application);
                    application.setTitle("[HEALTH HEROES] LOGGED IN AS: " + state.getUsername());
                }
            }
        });

        // add views to view manager
        viewManager.addView(signupView, signupView.viewName);
        viewManager.addView(loginView, loginView.viewName);

        // add components to the frame
        application.add(viewManager.getViews(), BorderLayout.CENTER);
        application.add(navBar, BorderLayout.NORTH);

        // set the initial active view
        viewManagerModel.setActiveView(loginView.viewName);
        viewManagerModel.firePropertyChanged();

        // pack and center the application
        application.pack();
        application.setLocationRelativeTo(null); // center the application window
        application.setVisible(true);
    }
}