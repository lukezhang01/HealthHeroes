package view;
import interface_adapter.ViewModel;
import interface_adapter.addPatient.AddPatientController;
import interface_adapter.patientList.PatientListController;
import use_case.patientList.PatientListOutputData;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalField;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PatientListView extends JPanel {

    // COLORS
    private JPanel patientPanel;


    public final String viewName = "Patients";
    private JScrollPane scrollPane;

    private final String[] filterOptions = {"All Patients", "Appointments Today", "Appointments This Week"};
    private final String[] sortOptions = {"Alphabetically", "Latest Appointment Date", "Date Added"};
    private JButton addButton;
    private JComboBox<String> sortBy, filterBy;
    private ArrayList<PatientListOutputData> patients;
    private PatientListController patientListController;
    private JPanel container;


    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private AddPatientController addPatientController;

    private ArrayList<PatientListOutputData> getPatientsSortedAlphabetically(){
        ArrayList<PatientListOutputData> patients = this.patients;
        Collections.sort(patients, new Comparator<PatientListOutputData>() {
            public int compare(PatientListOutputData v1, PatientListOutputData v2) {
                return v1.getFullName().compareTo(v2.getFullName());
            }
        });
        return patients;
    }

    private ArrayList<PatientListOutputData> getPatientsSortedByAddedDate(){
        ArrayList<PatientListOutputData> patients = this.patients;
        Collections.sort(patients, new Comparator<PatientListOutputData>() {
            public int compare(PatientListOutputData v1, PatientListOutputData v2) {
                return  LocalDate.parse(v1.getDateAdded(), formatter).compareTo(LocalDate.parse(v2.getDateAdded(), formatter));
            }
        });
        return patients;
    }

    public void updatePatientsByFilter(String filter) {
        ArrayList<PatientListOutputData> patients = this.patientListController.getPatients();
        ArrayList<PatientListOutputData> result = new ArrayList<>();
        if (!filter.equals("All Patients")){
            LocalDate currentDate = LocalDate.now();
            for (PatientListOutputData patient: patients) {
                if (!patient.getDateAdded().isEmpty()) {
                    LocalDate appointmentDate = LocalDate.parse(patient.getAppointmentDate(), formatter);
                    if (filter.equals("Appointments This Week")) {
                        TemporalField weekOfYear = WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear();
                        int currentWeek = currentDate.get(weekOfYear);
                        int appointmentWeek = appointmentDate.get(weekOfYear);
                        if (currentWeek == appointmentWeek) {
                            // appointment week is in the same week
                            result.add(patient);
                        }
                    }else if(filter.equals("Appointments Today")) {
                        if (currentDate.toString().equals(patient.getAppointmentDate())) {
                            // appointment is today
                            result.add(patient);
                        }
                    }
                }
            }
            this.patients = result;
        }else {
            this.patients = patients;
        }
    }

    public void updateDisplayBySort(String sort) {
        if(sort.equals("Alphabetically")) {
            // sort alphabetically
            display(getPatientsSortedAlphabetically());
        }else if (sort.equals("Date Added")){
            display(getPatientsSortedByAddedDate());
        }else{
            display(getPatientsSortedByAppointmentDate());
        }
    }

    private ArrayList<PatientListOutputData> getPatientsSortedByAppointmentDate(){
        ArrayList<PatientListOutputData> patients = this.patients;
        Collections.sort(patients, new Comparator<PatientListOutputData>() {
            public int compare(PatientListOutputData v1, PatientListOutputData v2) {
                return  LocalDate.parse(v1.getAppointmentDate(), formatter).compareTo(LocalDate.parse(v2.getAppointmentDate(), formatter));
            }
        });
        return patients;
    }

    private ArrayList<PatientListOutputData> getPatientsBySearchString(String search) {
        ArrayList<PatientListOutputData> result = new ArrayList<>();
        ArrayList<PatientListOutputData> patients = this.patients;
        for (PatientListOutputData patient: patients) {
            if (patient.getFullName().toLowerCase().contains(search.toLowerCase())) {
                result.add(patient);
            }
        }
        return result;
    }

    public PatientListView() {
        this.setLayout(new BorderLayout());

        this.container = new JPanel();
        this.setMaximumSize(ViewModel.VIEW_DIMENSION);
        this.setMinimumSize(ViewModel.VIEW_DIMENSION);
        this.setBackground(ViewModel.BACKGROUND_COLOR);

        // creating top panel + sorting dropdown menu
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(ViewModel.BACKGROUND_COLOR);
        // title label
        JLabel titleLabel = new JLabel("Patients");
        titleLabel.setFont(ViewModel.TITLE_FONT);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBackground(ViewModel.HEADER_COLOR);
        titleLabel.setForeground(ViewModel.TEXT_COLOR);
        topPanel.add(Box.createVerticalStrut(8));
        topPanel.add(titleLabel);
        topPanel.add(Box.createVerticalStrut(15));

        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // Add the "Sort by" label and combo box to the sub-panel
        sortPanel.setBackground(ViewModel.BACKGROUND_COLOR);
        JLabel sortByLabel = new JLabel("Sort");
        sortByLabel.setFont(ViewModel.HEADING_FONT_BOLD);
        sortByLabel.setForeground(ViewModel.TEXT_HIGHLIGHT_COLOR);
        sortByLabel.setBackground(ViewModel.BACKGROUND_COLOR);
        sortPanel.add(sortByLabel);
        sortBy = new JComboBox<>(sortOptions);
        sortBy.setForeground(ViewModel.TEXT_COLOR);
        sortBy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) sortBy.getSelectedItem();
                updateDisplayBySort(selectedOption);
            }
        });
        sortBy.setFont(ViewModel.HEADING_FONT_BOLD);
        JLabel filterByLabel = new JLabel("Show");
        filterByLabel.setFont(ViewModel.HEADING_FONT_BOLD);
        filterByLabel.setForeground(ViewModel.TEXT_HIGHLIGHT_COLOR);
        filterByLabel.setBackground(ViewModel.BACKGROUND_COLOR);
        sortPanel.add(filterByLabel);
        filterBy = new JComboBox<>(filterOptions);
        filterBy.setForeground(ViewModel.TEXT_COLOR);
        filterBy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) filterBy.getSelectedItem();
                updatePatientsByFilter(selectedOption);
                // update sort
                updateDisplayBySort((String) sortBy.getSelectedItem());
            }
        });
        // add filters / sorts
        filterBy.setFont(ViewModel.HEADING_FONT_BOLD);
        sortPanel.add(filterBy, BorderLayout.CENTER);
        sortPanel.add(sortByLabel, BorderLayout.CENTER);
        sortPanel.add(sortBy, BorderLayout.CENTER);
        // add search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(ViewModel.BACKGROUND_COLOR);
        searchPanel.setForeground(ViewModel.TEXT_COLOR);
        // search input
        JTextArea searchInput = new JTextArea(1, 30);
        searchInput.setBackground(ViewModel.HEADER_COLOR);
        searchInput.setForeground(ViewModel.TEXT_COLOR);
        searchInput.setFont(new Font("Lato", Font.PLAIN, 14));
        searchInput.setLineWrap(true);
        searchPanel.add(searchInput);
        // search button
        JButton searchPatientButton = new JButton();
        searchPatientButton.setOpaque(false);
        searchPatientButton.setBackground(new Color(69, 130, 222));
        searchPatientButton.setForeground(new Color(255, 255, 255));
        searchPatientButton.setFont(ViewModel.HEADING_FONT_BOLD);
        searchPatientButton.setForeground(new Color(255, 255, 255));
        searchPatientButton.setMinimumSize(new Dimension(30, 70));
        searchPatientButton.setMaximumSize(new Dimension(30, 70));
        searchPatientButton.setText("🔎 Search Patient");
        searchPatientButton.setOpaque(true);
        searchPatientButton.setContentAreaFilled(true);
        searchPatientButton.setBorderPainted(false);
        searchPatientButton.setFocusPainted(false);
        searchPanel.add(Box.createHorizontalStrut(8));
        searchPanel.add(searchPatientButton);

        searchInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {

            }

            @Override
            public void keyReleased(KeyEvent e) {
                // restore results when empty
                if (searchInput.getText().isEmpty()) {
                    // reset results
                    updatePatientsByFilter((String) filterBy.getSelectedItem());
                    updateDisplayBySort((String) sortBy.getSelectedItem());
                }
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    // reset results
                    updatePatientsByFilter((String) filterBy.getSelectedItem());
                }
            }
        });
        searchPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // search was pressed
                display(getPatientsBySearchString(searchInput.getText()));
            }
        });
        topPanel.add(sortPanel, BorderLayout.CENTER);
        topPanel.add(Box.createVerticalStrut(20));
        // this.container.add(topPanel, BorderLayout.CENTER);
        this.add(topPanel, BorderLayout.NORTH);
        // add header for added patients
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(searchPanel, BorderLayout.CENTER);
        // scrollable view for patients
        patientPanel = new JPanel();
        patientPanel.setLayout(new BoxLayout(patientPanel, BoxLayout.Y_AXIS));
        patientPanel.setBackground(new Color(212, 213, 214));
        this.scrollPane = new JScrollPane(patientPanel);
        this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setBackground(ViewModel.BACKGROUND_COLOR);
        this.scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        // set the scrollbar color
        this.scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(72, 108, 150);
                this.trackColor = new Color(212, 213, 214);
                this.scrollBarWidth = 8;
            }
        });
        // Create the bottom panel with add button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(ViewModel.BACKGROUND_COLOR);
        JButton addButton = new JButton("ADD PATIENT");
        addButton.setBackground(new Color(69, 130, 222));
        addButton.setForeground(new Color(255, 255, 255));
        addButton.setFont(ViewModel.HEADING_FONT_BOLD);
        addButton.setOpaque(true);
        addButton.setContentAreaFilled(true);
        addButton.setBorderPainted(false);
        addButton.setFocusPainted(false);
        addButton.addActionListener(e -> {
            AddPatientView addPatientView = addPatientController.handleAddPatient();
            addPatientView.addPatientButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // add patient was pressed
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    patients = patientListController.getPatients();
                    updatePatientsByFilter(filterOptions[0]);
                    updateDisplayBySort(sortOptions[0]);
                }
            });
        });
        bottomPanel.add(addButton);
        //this.container.add(bottomPanel);
        this.add(bottomPanel, BorderLayout.SOUTH);
        this.revalidate();
        this.repaint();
        this.setSize(600, 400);
        this.setVisible(true);

    }


    public void display(ArrayList<PatientListOutputData> patients) {
        patientPanel.removeAll();
        this.patients = patients;
        patientPanel.add(Box.createVerticalStrut(30));
        if (!this.patients.isEmpty()) {
            // if we have patients to display
            patientPanel.add(PatientListComponentBuilder.buildHeader());
            patientPanel.add(Box.createVerticalStrut(15));
            for (PatientListOutputData patient : patients) {
                JPanel patientComponent = PatientListComponentBuilder.build(patient);
                patientPanel.add(patientComponent);
                patientPanel.add(Box.createVerticalStrut(8));
            }
        }else {
            patientPanel.add(PatientListComponentBuilder.buildEmptyHeader());
        }
        patientPanel.revalidate();
        patientPanel.repaint();
        // this.container.add(patientPanel, BorderLayout.CENTER);
        this.add(scrollPane, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }


    public void setController(PatientListController controller) {
        this.patientListController = controller;
        this.patients = controller.getPatients();
        updatePatientsByFilter(filterOptions[0]);
        updateDisplayBySort(sortOptions[0]);

    }

    public void addPatientController(AddPatientController controller) {
        this.addPatientController = controller;
    }

}