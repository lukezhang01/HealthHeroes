package interface_adapter.addPatient;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;

public class AddPatientViewModel extends ViewModel {
    public AddPatientViewModel(String viewName) {
        super(viewName);
    }

    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }
}
