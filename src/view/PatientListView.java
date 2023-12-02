package view;
import entity.Patient;
import interface_adapter.ViewModel;
import interface_adapter.patientList.PatientListController;
import use_case.patientList.PatientListOutputData;

import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PatientListView extends JFrame {

    // COLORS
    private JPanel patientPanel;

    private SandwichBar sandwichBar;

    private HomeView homeView;
    private JScrollPane scrollPane;
    private JButton addButton;
    private JComboBox<String> sortBy;
    private ArrayList<PatientListOutputData> patients;
    private PatientListController patientListController;
    private JPanel container;

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private ArrayList<PatientListOutputData> getPatientsSortedAlphabetically(){
        ArrayList<PatientListOutputData> patients = this.patientListController.getPatients();
        Collections.sort(patients, new Comparator<PatientListOutputData>() {
            public int compare(PatientListOutputData v1, PatientListOutputData v2) {
                return v1.getFullName().compareTo(v2.getFullName());
            }
        });
        return patients;
    }

    private ArrayList<PatientListOutputData> getPatientsSortedByAddedDate(){
        ArrayList<PatientListOutputData> patients = this.patientListController.getPatients();
        Collections.sort(patients, new Comparator<PatientListOutputData>() {
            public int compare(PatientListOutputData v1, PatientListOutputData v2) {
                return  LocalDate.parse(v1.getDateAdded(), formatter).compareTo(LocalDate.parse(v2.getDateAdded(), formatter));
            }
        });
        return patients;
    }

    private ArrayList<PatientListOutputData> getPatientsSortedByAppointmentDate(){
        ArrayList<PatientListOutputData> patients = this.patientListController.getPatients();
        Collections.sort(patients, new Comparator<PatientListOutputData>() {
            public int compare(PatientListOutputData v1, PatientListOutputData v2) {
                return  LocalDate.parse(v1.getAppointmentDate(), formatter).compareTo(LocalDate.parse(v2.getAppointmentDate(), formatter));
            }
        });
        return patients;
    }

    private ArrayList<PatientListOutputData> getPatientsBySearchString(String search) {
        ArrayList<PatientListOutputData> result = new ArrayList<>();
        ArrayList<PatientListOutputData> patients = this.patientListController.getPatients();
        for (PatientListOutputData patient: patients) {
            if (patient.getFullName().contains(search)) {
                result.add(patient);
            }
        }
        return result;
    }

    public PatientListView() {
        super("Patient List View");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
        JLabel sortByLabel = new JLabel("SORT BY");
        sortByLabel.setFont(ViewModel.HEADING_FONT_BOLD);
        sortByLabel.setForeground(ViewModel.TEXT_COLOR);
        sortByLabel.setBackground(ViewModel.BACKGROUND_COLOR);
        sortPanel.add(sortByLabel);
        String[] sortOptions = {"Alphabetically", "Last Appointment Date", "Date Added"};
        sortBy = new JComboBox<>(sortOptions);
        sortBy.setForeground(ViewModel.BACKGROUND_COLOR);
        sortBy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedOption = (String) sortBy.getSelectedItem();
                if(selectedOption.equals("Alphabetically")) {
                    // sort alphabetically
                    display(getPatientsSortedAlphabetically());
                }else if (selectedOption.equals("Date Added")){
                    display(getPatientsSortedByAddedDate());
                }else{
                    display(getPatientsSortedByAppointmentDate());
                }
            }
        });
        sortBy.setFont(ViewModel.HEADING_FONT_BOLD);
        sortPanel.add(sortBy, BorderLayout.CENTER);
        // add search bar
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setBackground(ViewModel.BACKGROUND_COLOR);
        searchPanel.setForeground(ViewModel.TEXT_COLOR);
        // search input
        JTextArea searchInput = new JTextArea(1, 30);
        searchInput.setBackground(new Color(34, 39, 48));
        searchInput.setForeground(ViewModel.TEXT_COLOR);
        searchInput.setFont(new Font("Lato", Font.BOLD, 14));
        searchInput.setLineWrap(true);
        searchPanel.add(searchInput);
        // search button
        JButton searchPatientButton = new JButton();
        searchPatientButton.setOpaque(false);
        searchPatientButton.setBackground(new Color(194, 112, 103));
        searchPatientButton.setFont(ViewModel.HEADING_FONT_BOLD);
        searchPatientButton.setForeground(ViewModel.HEADER_COLOR);
        searchPatientButton.setMinimumSize(new Dimension(80, 70));
        searchPatientButton.setText("🔎 Search Patient");
        searchPanel.add(Box.createHorizontalStrut(8));
        searchPanel.add(searchPatientButton);

        searchInput.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                // restore results when empty
                if (searchInput.getText().isEmpty()) {
                    display(getPatientsBySearchString(""));
                }
             }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    // enter key
                    display(getPatientsBySearchString(searchInput.getText()));
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

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
        JLabel addedPatientsTitle = new JLabel("Added Patients");
        addedPatientsTitle.setFont(new Font("Lato", Font.BOLD, 20));
        addedPatientsTitle.setSize(ViewModel.VIEW_DIMENSION.width / 2, 80);
        addedPatientsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        addedPatientsTitle.setOpaque(false);
        addedPatientsTitle.setForeground(ViewModel.TEXT_COLOR);
        topPanel.add(addedPatientsTitle);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(searchPanel, BorderLayout.CENTER);
        // scrollable view for patients
        patientPanel = new JPanel();
        patientPanel.setLayout(new BoxLayout(patientPanel, BoxLayout.Y_AXIS));
        patientPanel.setBackground(new Color(36, 45, 64));
        this.scrollPane = new JScrollPane(patientPanel);
        this.scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        this.scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        this.scrollPane.setBackground(ViewModel.BACKGROUND_COLOR);
        this.scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        // set the scrollbar color
        this.scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = ViewModel.TEXT_COLOR;
                this.trackColor = new Color(36, 45, 64);
                this.scrollBarWidth = 8;
            }
        });
        // Create the bottom panel with add button
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(new Color(73, 93, 135));
        JButton addButton = new JButton("➕ ADD PATIENT");
        addButton.setBackground(new Color(99, 255, 147));
        addButton.setForeground(new Color(45, 46, 45));
        addButton.setFont(ViewModel.HEADING_FONT_BOLD);
        addButton.setOpaque(true);
        addButton.setContentAreaFilled(true);
        addButton.setBorderPainted(false);
        addButton.setFocusPainted(false);

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                patientListController.handleAddPatient();
            }
        });
        bottomPanel.add(addButton);
        //this.container.add(bottomPanel);
        this.add(bottomPanel, BorderLayout.SOUTH);


        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(73, 93, 135));
        JButton homeButton = new JButton("🏠Home");
        JButton patientButton = new JButton("☺Patients");
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


        homeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                homeView.setPatientListView(PatientListView.this);
                homeView.setVisible(true);
                PatientListView.this.setVisible(false);
                homeView.revalidate();
                homeView.repaint();
            }
        });


        leftPanel.add(homeButton);
        leftPanel.add(patientButton);
        //this.add(container, BorderLayout.CENTER);
        this.add(leftPanel, BorderLayout.WEST);

        this.revalidate();
        this.repaint();
        this.setSize(600, 400);
        this.setVisible(true);
    }

    public void display(ArrayList<PatientListOutputData> patients) {
        patientPanel.removeAll();
        this.patients = patients;
        patientPanel.add(Box.createVerticalStrut(30));

        for (PatientListOutputData patient : patients) {
            JPanel patientComponent = PatientListComponentBuilder.build(patient, this.patientListController);
            patientPanel.add(patientComponent);
            patientPanel.add(Box.createVerticalStrut(8));
        }

        patientPanel.revalidate();
        patientPanel.repaint();
        // this.container.add(patientPanel, BorderLayout.CENTER);
        this.add(scrollPane, BorderLayout.CENTER);
        this.revalidate();
        this.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new PatientListView();

        });
    }


    public void setController(PatientListController controller) {
        this.patientListController = controller;
    }
    public void setHomeView(HomeView homeView){
        this.homeView = homeView;
    }

    public void setSandwichBar(SandwichBar sandwichBar){
        this.sandwichBar = sandwichBar;
    }
}