package interface_adapter.prescribe;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class PrescribeViewModel extends ViewModel {
    private PrescribeState state = new PrescribeState();

    public PrescribeViewModel() {
        super("Prescribe");
    }

    public void setState(PrescribeState state) {
        this.state = state;
    }

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public PrescribeState getState() {
        return state;
    }
}

