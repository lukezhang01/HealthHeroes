package view;

import entity.Patient;
import interface_adapter.ViewModel;
import interface_adapter.patient.PatientController;
import interface_adapter.patient.PatientViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * I use a builder and not an iterator because I need the fields to be referenced properly
 */

public class PatientView extends JFrame {
    private PatientController controller;
    private PatientViewModel model;
    private ArrayList<DrugEntry> drugEntries;
    private final Dimension DIMENSION = new Dimension(300, 600);
    private JTextField nameField;
    private JTextField heightField;
    private JTextField weightField;
    private JTextField dateOfBirthField;
    private JTextField genderField;
    private JComboBox<String> isPregnantField;
    private JTextField appointmentDatesField;
    private JTextField allergiesField;
    private JTextField illnessesField;
    private JTextField symptomsField;
    private JTextField lifestyleInformationField;
    private JTextField additionalNotesField;
    private JPanel drugsPanel;
    private JScrollPane drugsScrollPane;
    private JButton addDrugButton;
    private final int FIELD_SIZE = 20;
    public PatientView(PatientController controller, PatientViewModel model) {
        this.controller = controller;
        this.model = model;

        // Initialize the list for drug entries
        drugEntries = new ArrayList<>();

        // Set up the frame
        setTitle("Edit Patient");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMaximumSize(DIMENSION);
        setMinimumSize(DIMENSION);
        setSize(DIMENSION);

        // Create the main panel with a box layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(ViewModel.BACKGROUND_COLOR);

        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setFont(ViewModel.HEADING_FONT_BOLD);
        fullNameLabel.setForeground(ViewModel.TEXT_COLOR);
        nameField = new PatientViewEntryBuilder("Full Name:", controller.getNameField());

        JLabel heightLabel = new JLabel("Height:");
        heightField = new PatientViewEntryBuilder("Height:", controller.getHeightField());

        JLabel weightLabel = new JLabel("Weight:");
        weightField = new PatientViewEntryBuilder("Weight:", controller.getWeightField());

        JLabel dateOfBirthLabel = new JLabel("Date of Birth:");
        dateOfBirthField = new PatientViewEntryBuilder("Weight:", controller.getDateOfBirthField());

        JLabel genderLabel = new JLabel("Gender:");
        genderField = new PatientViewEntryBuilder("Gender:", controller.getGenderField());

        JLabel appointmentDatesLabel = new JLabel("Appointment Dates:");
        appointmentDatesField = new PatientViewEntryBuilder("Appointment Dates:", controller.getAppointmentDatesField());

        JLabel allergiesLabel = new JLabel("Allergies:");
        allergiesField = new PatientViewEntryBuilder("Allergies:", controller.getAllergiesField());

        JLabel illnessesLabel = new JLabel("Illnesses:");
        illnessesField = new PatientViewEntryBuilder("Illnesses:", controller.getIllnessesField());

        JLabel symptomsLabel = new JLabel("Symptoms:");
        symptomsField = new PatientViewEntryBuilder("Symptoms:", controller.getSymptomsField());

        JLabel lifestyleInformationLabel = new JLabel("Lifestyle Information:");
        lifestyleInformationField = new PatientViewEntryBuilder("Lifestyle Information:", controller.getLifestyleInformationField());

        JLabel additionalNotesLabel = new JLabel("Additional Notes:");
        additionalNotesField = new PatientViewEntryBuilder("Additional Notes:", controller.getAdditionalNotesField());

        mainPanel.add(createLabeledField(fullNameLabel, nameField));
        mainPanel.add(createLabeledField(heightLabel, heightField));
        mainPanel.add(createLabeledField(weightLabel, weightField));
        mainPanel.add(createLabeledField(appointmentDatesLabel, appointmentDatesField));
    }

    private JPanel createLabeledField(JLabel label, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textField.getPreferredSize().height));
        panel.add(label);
        panel.add(textField);
        return panel;
    }
}
