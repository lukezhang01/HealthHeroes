package view;
import interface_adapter.patientList.PatientListController;
import use_case.patientList.PatientListOutputData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class PatientListView extends JFrame {
    private JPanel patientPanel;
    private JScrollPane scrollPane;
    private JButton addButton;
    private JComboBox<String> sortBy;
    private ArrayList<PatientListOutputData> patients;
    private PatientListController patientListController;
    private JPanel container;

    public PatientListView() {
        super("Patient List View");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        this.container = new JPanel();

        // creating top panel + sorting dropdown menu
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        // title label
        JLabel titleLabel = new JLabel("Patients");
        titleLabel.setFont(new Font("Lato", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        topPanel.add(titleLabel);

        JPanel sortPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        // Add the "Sort by" label and combo box to the sub-panel
        sortPanel.add(new JLabel("Sort by:"));
        String[] sortOptions = {"Name", "Last Appointment Date", "Date Added"};
        sortBy = new JComboBox<>(sortOptions);
        sortPanel.add(sortBy, BorderLayout.CENTER);

        topPanel.add(sortPanel, BorderLayout.CENTER);
        // this.container.add(topPanel, BorderLayout.CENTER);
        this.add(topPanel, BorderLayout.NORTH);

        // scrollable view for patients
        patientPanel = new JPanel();
        patientPanel.setLayout(new BoxLayout(patientPanel, BoxLayout.Y_AXIS));
        this.scrollPane = new JScrollPane(patientPanel);

        // Create the bottom panel with add button
        JPanel bottomPanel = new JPanel();
        addButton = new JButton("Add new patient");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                patientListController.handleAddPatient();
            }
        });
        bottomPanel.add(addButton);
        //this.container.add(bottomPanel);
        this.add(bottomPanel, BorderLayout.SOUTH);

        //this.add(container, BorderLayout.CENTER);

        this.setSize(600, 400);
        this.setVisible(true);
    }
    public void display(ArrayList<PatientListOutputData> patients) {
        patientPanel.removeAll();
        this.patients = patients;
        System.out.println(patients);

        for (PatientListOutputData patient : patients) {
            JPanel patientComponent = PatientListComponentBuilder.build(patient, this.patientListController);
            patientPanel.add(patientComponent);
        }

        patientPanel.revalidate();
        patientPanel.repaint();
        // this.container.add(patientPanel, BorderLayout.CENTER);
        this.add(patientPanel, BorderLayout.CENTER);
    }

    public void setController(PatientListController controller) {
        this.patientListController = controller;
    }
}
