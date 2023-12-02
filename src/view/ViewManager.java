package view;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewManager implements PropertyChangeListener {
    private ViewManagerModel viewManagerModel;
    private JFrame currentView;

    public ViewManager() {
        viewManagerModel = new ViewManagerModel();
        viewManagerModel.addPropertyChangeListener(this);

        // Initial view setup
        viewManagerModel.setActiveView("LoginView");
        viewManagerModel.firePropertyChanged();
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("view".equals(evt.getPropertyName())) {
            if (currentView != null) {
                currentView.dispose();
            }
            switch (viewManagerModel.getActiveView()) {
                case "LoginView":
                    currentView = new LoginView(viewManagerModel);
                    break;
                case "SignupView":
                    currentView = new SignupView(viewManagerModel);
                    break;
                // Add other cases for different views
            }
            if (currentView != null) {
                currentView.setVisible(true);
            }
        }
    }
}
