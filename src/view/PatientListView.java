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


    public PatientListView(PatientListController patientListController) {
        super("Patient List View");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        this.patientListController = patientListController;

        // creating top panel + sorting dropdown menu
        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Patients"));
        String[] sortOptions = {"Name", "Last Appointment Date", "Date Added"};
        sortBy = new JComboBox<>(sortOptions);
        topPanel.add(sortBy);

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


        this.setSize(600, 400);
        this.setVisible(true);
    }
    public void display(ArrayList<PatientListOutputData> patients) {
        patientPanel.removeAll();
        this.patients = patients;

        for (PatientListOutputData patient : patients) {
            JPanel patientComponent = PatientListComponentBuilder.build(patient, patientListController);
            patientPanel.add(patientComponent);
        }

        patientPanel.revalidate();
        patientPanel.repaint();
    }
}
