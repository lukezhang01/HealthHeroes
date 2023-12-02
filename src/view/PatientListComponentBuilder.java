package view;

import interface_adapter.ViewModel;
import interface_adapter.patientList.PatientListController;
import use_case.patientList.PatientListOutputData;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.*;

public class PatientListComponentBuilder {

    private static final Dimension PATIENT_CELL_SIZE = new Dimension(400, 30);
    private static final Dimension NAME_LABEL_SIZE = new Dimension(140, 30);
    private static final Dimension VIEW_BUTTON_SIZE = new Dimension(100, 30);
    private static final Color PATIENT_BACKGROUND = new Color(33, 38, 48);
    private static final Font PATIENT_FONT = new Font("Lato", Font.PLAIN, 18);



    public static JPanel build(PatientListOutputData patient, PatientListController controller) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JLabel nameLabel = new JLabel(patient.getFullName());
        panel.setBackground(new Color(21, 26, 33));
        // name label
        nameLabel.setBackground(ViewModel.BACKGROUND_COLOR);
        nameLabel.setForeground(ViewModel.TEXT_COLOR);
        nameLabel.setFont(PATIENT_FONT);
        nameLabel.setMinimumSize(NAME_LABEL_SIZE);
        nameLabel.setMaximumSize(NAME_LABEL_SIZE);
        // id label
        JLabel idLabel = new JLabel("ID: " + patient.getId());
        idLabel.setBackground(ViewModel.BACKGROUND_COLOR);
        idLabel.setForeground(ViewModel.TITLE_RED_COLOR);
        idLabel.setFont(PATIENT_FONT);
        idLabel.setMinimumSize(NAME_LABEL_SIZE);
        idLabel.setMaximumSize(NAME_LABEL_SIZE);
        // view patient button
        JButton viewPatientButton = new JButton();
        viewPatientButton.setOpaque(false);
        viewPatientButton.setBackground(new Color(194, 112, 103));
        viewPatientButton.setFont(ViewModel.HEADING_FONT_BOLD);
        viewPatientButton.setForeground(ViewModel.HEADER_COLOR);
        viewPatientButton.setMinimumSize(VIEW_BUTTON_SIZE);
        viewPatientButton.setText("🔎 View Patient");
        // add to panel
        panel.add(Box.createHorizontalStrut(5));
        panel.add(idLabel);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(nameLabel);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(viewPatientButton);
        // set size
        panel.setMaximumSize(PATIENT_CELL_SIZE);
        panel.setMinimumSize(PATIENT_CELL_SIZE);
        return panel;
    }
}