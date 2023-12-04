package interface_adapter.addPatient;

import interface_adapter.ViewModel;
import view.AddPatientView;

import java.beans.PropertyChangeListener;

public class AddPatientViewModel extends ViewModel {
    private AddPatientView view;
    public AddPatientViewModel(String viewName, AddPatientView view) {
        super(viewName);
        this.view = view;
    }

    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }

    public void display() {
        view.setVisible(true);
    }
}
