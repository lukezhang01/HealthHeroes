package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;

public class NavigationBar extends JPanel implements ActionListener {

    // Fonts, Colors, and Dimensions

    private final Font NAV_FONT = new Font("Lato", Font.PLAIN, 14);
    private final Color NAVIGATION_ACTIVE = new Color(189, 223, 255);

    private final Color NAVIGATION_INACTIVE = new Color(227, 227, 227);;
    private final Dimension BAR_DIMENSIONS = new Dimension(200, 80);
    private final Dimension NAV_BUTTON_SIZE = new Dimension(85, 85);

    private final String defaultView;

    private ViewManagerModel viewManager; // Assuming this is a custom interface or class
    private JPanel navBar;
    private HashMap<String, JPanel> navButtons = new HashMap<>();

    public String getDefaultView(){
        return this.defaultView;
    }

    public NavigationBar(ViewManagerModel viewManager, String defaultView) {
        this.viewManager = viewManager;
        this.navBar = new JPanel();
        // set up the navigation bar
        navBar.setBackground(ViewModel.BACKGROUND_COLOR);
        navBar.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.defaultView = defaultView;
        this.setMaximumSize(BAR_DIMENSIONS);
        this.setMinimumSize(BAR_DIMENSIONS);

        // create a sepertor
        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(0, 8)); // Thin horizontal line
        separator.setMinimumSize(new Dimension(0, 8));
        separator.setBackground(ViewModel.TEXT_COLOR);
        // set up the NavigationBar panel
        this.setLayout(new BorderLayout());
        this.add(navBar, BorderLayout.NORTH);
        this.add(separator, BorderLayout.CENTER); // Add the separator
        // set the size of the NavigationBar pane
    }

    public void addNavigationButton(String viewName, String imageName) {
        if (!this.navButtons.containsKey(viewName)) {
            JPanel navigationButton = createNavigationButton(viewName, imageName);
            navBar.add(navigationButton);
        }
    }

    public void setVisible(boolean visible) {
        if (visible) {
            // set the default view
            this.setActiveView(this.defaultView);
        }
        super.setVisible(visible);
    }


    private JPanel createNavigationButton(String name, String imageName) {
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(NAVIGATION_INACTIVE);
        buttonPanel.setPreferredSize(NAV_BUTTON_SIZE); // Preferred size for buttons

        // create and configure the navigation button
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/" + imageName));
        JButton navButton = new JButton(icon);
        navButton.setBorderPainted(false);
        navButton.setContentAreaFilled(false);
        navButton.setActionCommand(name);
        navButton.setFocusPainted(false);
        navButton.setHorizontalAlignment(SwingConstants.CENTER);

        // create and configure the label
        JLabel label = new JLabel(name, SwingConstants.CENTER);
        label.setFont(NAV_FONT);

        // add the button and label to the panel
        buttonPanel.add(navButton, BorderLayout.NORTH);
        buttonPanel.add(label, BorderLayout.CENTER);

        // listen to button clicks
        navButton.addActionListener(this);
        navButtons.put(name, buttonPanel);
        this.setVisible(false);
        return buttonPanel;
    }

    public void setActiveView(String viewName) {
        // switch to view
        this.viewManager.setActiveView(viewName);
        this.viewManager.firePropertyChanged();
        // manage what is active
        for (String view: this.navButtons.keySet()) {
            if (view.equals(viewName)) {
                // set selected frame to be active
                JPanel button = this.navButtons.get(view);
                button.setBackground(NAVIGATION_ACTIVE);
            } else {
                // set it to inactive
                JPanel button = this.navButtons.get(view);
                button.setBackground(NAVIGATION_INACTIVE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setActiveView(e.getActionCommand());
    }

}
