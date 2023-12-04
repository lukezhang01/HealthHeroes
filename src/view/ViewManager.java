package view;
import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewManager implements PropertyChangeListener {
    private final CardLayout cardLayout;
    private final JPanel views;
    private ViewManagerModel viewManagerModel;

    public void addView(JPanel view, String viewName){
        this.views.add(view, viewName);
    }

    public JPanel getViews() {
        return this.views;
    }

    public ViewManager(ViewManagerModel viewManagerModel) {
        this.cardLayout = new CardLayout();
        this.views = new JPanel(cardLayout);
        this.viewManagerModel = viewManagerModel;
        this.viewManagerModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("view")) {
            String viewModelName = (String) evt.getNewValue();
            System.out.println(viewModelName);
            cardLayout.show(views, viewModelName);
        }
    }
}
