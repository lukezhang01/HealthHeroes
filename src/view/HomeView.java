package view;

import interface_adapter.ViewModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class HomeView extends JFrame implements ActionListener, PropertyChangeListener {

    public final String viewName = "Home";
    private final LoggedInViewModel loggedInViewModel;

    JLabel username;

    private PatientListView patientListView;

    private JPanel container;

    final JButton logOut;


    /**
     * A window with a title and a JButton.
     */
    public HomeView(LoggedInViewModel loggedInViewModel) {
        super("Home");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        this.loggedInViewModel = loggedInViewModel;
        this.container = new JPanel();
        this.setMaximumSize(ViewModel.VIEW_DIMENSION);
        this.setMinimumSize(ViewModel.VIEW_DIMENSION);
        this.loggedInViewModel.addPropertyChangeListener(this);



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
                ;
            }
        });
        bottomPanel.add(logOut);
        //this.container.add(bottomPanel);
        this.add(bottomPanel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel();
        JLabel usernameInfo = new JLabel("Currently logged in: "+ loggedInViewModel.getLoggedInUser());
        centerPanel.add(usernameInfo);
        this.add(centerPanel, BorderLayout.CENTER);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(73, 93, 135));
        JButton homeButton = new JButton("Home");
        JButton patientButton = new JButton("Patients");
        homeButton.setBackground(new Color(99, 255, 147));
        homeButton.setForeground(new Color(45, 46, 45));
        homeButton.setFont(ViewModel.HEADING_FONT_BOLD);
        homeButton.setOpaque(true);
        homeButton.setContentAreaFilled(true);
        homeButton.setBorderPainted(false);
        homeButton.setFocusPainted(false);
        patientButton.setBackground(new Color(99, 255, 147));
        patientButton.setForeground(new Color(45, 46, 45));
        patientButton.setFont(ViewModel.HEADING_FONT_BOLD);
        patientButton.setOpaque(true);
        patientButton.setContentAreaFilled(true);
        patientButton.setBorderPainted(false);
        patientButton.setFocusPainted(false);
        leftPanel.add(homeButton);
        leftPanel.add(patientButton);
        this.add(leftPanel, BorderLayout.WEST);

        patientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                patientListView.setHomeView(HomeView.this);
                patientListView.setVisible(true);
                HomeView.this.setVisible(false);
                patientListView.revalidate();
                patientListView.repaint();
            }
        });

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

    public void setPatientListView(PatientListView view){
        this.patientListView = view;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        new HomeView(new LoggedInViewModel());
        });
    }

}