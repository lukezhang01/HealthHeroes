package view;

import interface_adapter.patientList.PatientListController;
import use_case.patientList.PatientListOutputData;

import javax.swing.*;
import java.awt.event.*;

public class PatientListComponentBuilder {
    public static JPanel build(PatientListOutputData patient, PatientListController controller) {
        JPanel panel = new JPanel();
        JLabel patientLabel = new JLabel(patient.getFullName());
        JButton deleteButton = new JButton("Delete");

        deleteButton.addActionListener(e -> controller.handleDeletePatient(patient));
        patientLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                controller.handlePatientClick(patient);
            }
        });

        panel.add(patientLabel);
        panel.add(deleteButton);
        return panel;
    }
}