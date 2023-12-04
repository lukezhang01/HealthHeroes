package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class HomeView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "Home";

    JLabel username;


    private JPanel container;

    final JButton logOut;



    /**
     * A window with a title and a JButton.
     */
    public HomeView(ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel) {
        this.setLayout(new BorderLayout());

        this.container = new JPanel();
        this.setMaximumSize(ViewModel.VIEW_DIMENSION);
        this.setMinimumSize(ViewModel.VIEW_DIMENSION);



        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("Home");
        title.setFont(new Font("Lato", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(title);

        // Create the bottom panel with add button
        JPanel bottomPanel = new JPanel();
        logOut = new JButton("Log Out");
        logOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewManagerModel.setActiveView("log in");
                viewManagerModel.firePropertyChanged();
            }
        });
        bottomPanel.add(logOut);
        //this.container.add(bottomPanel);
        this.add(bottomPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel();
        JLabel usernameInfo = new JLabel("Currently logged in: "+ loggedInViewModel.getLoggedInUser());
        centerPanel.add(usernameInfo);
        this.add(centerPanel, BorderLayout.CENTER);



        this.revalidate();
        this.repaint();
        this.setSize(600, 400);
        this.setVisible(true);
    }


    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoggedInState state = (LoggedInState) evt.getNewValue();
        username.setText(state.getUsername());
    }


}