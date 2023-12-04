package view;

import entity.Doctor;
import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;
import interface_adapter.logged_in.LoggedInState;
import interface_adapter.logged_in.LoggedInViewModel;
import interface_adapter.patientList.PatientListController;
import use_case.patientList.PatientListOutputData;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class HomeView extends JPanel implements ActionListener, PropertyChangeListener {

    public final String viewName = "Home";

    JLabel username;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private JPanel container;

    private PatientListController patientListController;

    final JButton logOut;

    ArrayList<PatientListOutputData> patients = new ArrayList<>();



    /**
     * A window with a title and a JButton.
     */
    public HomeView(ViewManagerModel viewManagerModel, LoggedInViewModel loggedInViewModel, ArrayList<PatientListOutputData> patients) {
        this.setLayout(new BorderLayout());

        this.container = new JPanel();
        this.setMaximumSize(ViewModel.VIEW_DIMENSION);
        this.setMinimumSize(ViewModel.VIEW_DIMENSION);
        this.patients = patients;


        setLayout(new BorderLayout());

        // Home Title
        JLabel homeTitleLabel = new JLabel("Home");
        homeTitleLabel.setFont(new Font("Lato",Font.PLAIN,24));

        homeTitleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(homeTitleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
        // Welcome Label
        JLabel welcomeLabel = new JLabel("Welcome, Dr. "+loggedInViewModel.getLoggedInUser());
        welcomeLabel.setFont(new Font("Lato", Font.PLAIN, 16));
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        welcomeLabel.setForeground(ViewModel.TEXT_HIGHLIGHT_COLOR);
        welcomeLabel.setPreferredSize(new Dimension((int) ViewModel.VIEW_DIMENSION.getWidth(),40));
        centerPanel.add(welcomeLabel, BorderLayout.NORTH);

        // Date Label
        JLabel dateLabel = new JLabel(getCurrentDate());
        dateLabel.setFont(new Font("Lato", Font.PLAIN, 16));
        dateLabel.setHorizontalAlignment(SwingConstants.CENTER);
        dateLabel.setPreferredSize(new Dimension((int) ViewModel.VIEW_DIMENSION.getWidth(),40));
        dateLabel.setBorder(new EmptyBorder(new Insets(0,0,20,0)));
        centerPanel.add(dateLabel, BorderLayout.CENTER);

        // Upcoming Appointments
        JTextArea upcomingAppointmentsTextArea = new JTextArea();
        upcomingAppointmentsTextArea.setFont(new Font("Lato", Font.BOLD, 18));
        upcomingAppointmentsTextArea.setEditable(false);
        upcomingAppointmentsTextArea.setPreferredSize(new Dimension((int) ViewModel.VIEW_DIMENSION.getWidth()-40,100));

        for (String appointment : getAppointments()) {
            upcomingAppointmentsTextArea.append(appointment + "\n");
        }





        JScrollPane scrollPane = new JScrollPane(upcomingAppointmentsTextArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Today's Appointments"));
        centerPanel.add(scrollPane, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);



        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));


        // Create the bottom panel with add button
        JPanel bottomPanel = new JPanel();
        logOut = new JButton("Log Out");
        logOut.setFont(ViewModel.HEADING_FONT_BOLD);
        logOut.setBackground(new Color(229, 56, 56));
        logOut.setForeground(new Color(255, 255, 255));
        logOut.setOpaque(true);
        logOut.setContentAreaFilled(true);
        logOut.setBorderPainted(false);
        logOut.setFocusPainted(false);
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




        this.revalidate();
        this.repaint();
        this.setSize(600, 400);
        this.setVisible(true);
    }


    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        return "Today's Date: " + dateFormat.format(new Date());
    }

    /**
     * React to a button click that results in evt.
     */
    public void actionPerformed(ActionEvent evt) {
        System.out.println("Click " + evt.getActionCommand());
    }

    public void setPatients(ArrayList<PatientListOutputData> patients) {
        this.patients = patients;
    }


    public ArrayList<String> getAppointments(){
        ArrayList<String> appointments = new ArrayList<>();
        if (!this.patients.isEmpty()) {
            LocalDate currentDate = LocalDate.now();
            for (PatientListOutputData patient : this.patients) {
                if (currentDate.toString().equals(patient.getAppointmentDate())) {
                    String a = patient.getFullName() + " has an appointment today.";
                    appointments.add(a);
                }
            }
        }
        return appointments;
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        LoggedInState state = (LoggedInState) evt.getNewValue();
        username.setText(state.getUsername());
    }


}