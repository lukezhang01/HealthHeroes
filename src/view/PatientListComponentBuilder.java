package view;

import interface_adapter.ViewModel;
import interface_adapter.patient.PatientController;
import interface_adapter.patientList.PatientListController;
import use_case.patientList.PatientListOutputData;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.*;

public class PatientListComponentBuilder {
    private PatientController controller;

    private static final Dimension PATIENT_CELL_SIZE = new Dimension(400, 30);
    private static final Dimension NAME_LABEL_SIZE = new Dimension(140, 30);
    private static final Dimension VIEW_BUTTON_SIZE = new Dimension(100, 30);
    private static final Color PATIENT_BACKGROUND = new Color(33, 38, 48);
    private static final Font PATIENT_FONT = new Font("Lato", Font.PLAIN, 18);
    private static final Font HEADER_FONT = new Font("Lato", Font.BOLD, 13);

    public static JPanel buildEmptyHeader(){
        JPanel panel = new JPanel();
        JLabel nameLabel = new JLabel("No Entries Found");
        nameLabel.setForeground(new Color(255, 255, 255));
        nameLabel.setFont(new Font("Lato", Font.BOLD, 20));
        panel.setBackground(new Color(224, 92, 112));
        panel.add(nameLabel);
        // set size
        panel.setMaximumSize(PATIENT_CELL_SIZE);
        panel.setMinimumSize(PATIENT_CELL_SIZE);
        return panel;
    }
    public static JPanel buildHeader(){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JLabel nameLabel = new JLabel("Name");
        panel.setBackground(new Color(212, 213, 214));
        // name label
        nameLabel.setBackground(ViewModel.BACKGROUND_COLOR);
        nameLabel.setForeground(new Color(84, 164, 255));
        nameLabel.setFont(HEADER_FONT);
        nameLabel.setMinimumSize(NAME_LABEL_SIZE);
        nameLabel.setMaximumSize(NAME_LABEL_SIZE);
        // id label
        JLabel idLabel = new JLabel("Appointment Date");
        idLabel.setBackground(ViewModel.BACKGROUND_COLOR);
        idLabel.setForeground(new Color(84, 164, 255));
        idLabel.setFont(HEADER_FONT);
        idLabel.setMinimumSize(NAME_LABEL_SIZE);
        idLabel.setMaximumSize(NAME_LABEL_SIZE);
        // add to panel
        panel.add(Box.createHorizontalStrut(5));
        panel.add(idLabel);
        panel.add(Box.createHorizontalStrut(5));
        panel.add(nameLabel);
        panel.add(Box.createHorizontalStrut(5));
        // set size
        panel.setMaximumSize(PATIENT_CELL_SIZE);
        panel.setMinimumSize(PATIENT_CELL_SIZE);
        return panel;
    }
    public static JPanel build(PatientListOutputData patient, PatientController controller) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        JLabel nameLabel = new JLabel(patient.getFullName());
        panel.setBackground(ViewModel.HEADER_COLOR);
        // name label
        nameLabel.setBackground(ViewModel.BACKGROUND_COLOR);
        nameLabel.setForeground(ViewModel.TEXT_COLOR);
        nameLabel.setFont(PATIENT_FONT);
        nameLabel.setMinimumSize(NAME_LABEL_SIZE);
        nameLabel.setMaximumSize(NAME_LABEL_SIZE);
        // id label
        JLabel idLabel = new JLabel(patient.getAppointmentDate());
        idLabel.setBackground(ViewModel.BACKGROUND_COLOR);
        idLabel.setForeground(ViewModel.TEXT_HIGHLIGHT_COLOR);
        idLabel.setFont(PATIENT_FONT);
        idLabel.setMinimumSize(NAME_LABEL_SIZE);
        idLabel.setMaximumSize(NAME_LABEL_SIZE);
        // view patient button
        JButton viewPatientButton = new JButton();
        viewPatientButton.setOpaque(false);
        viewPatientButton.setFont(ViewModel.HEADING_FONT_BOLD);
        viewPatientButton.setBackground(new Color(18, 82, 161));
        viewPatientButton.setForeground(new Color(255, 255, 255  ));
        viewPatientButton.setMinimumSize(VIEW_BUTTON_SIZE);
        viewPatientButton.setText("🔎 View Patient");
        viewPatientButton.setOpaque(true);
        viewPatientButton.setContentAreaFilled(true);
        viewPatientButton.setBorderPainted(false);
        viewPatientButton.setFocusPainted(false);
        viewPatientButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.execute(patient.getId());
            }
        });
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