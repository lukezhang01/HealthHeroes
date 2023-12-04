package view;

import interface_adapter.ViewModel;
import interface_adapter.patient.PatientController;
import interface_adapter.patient.PatientViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * I use a builder and not an iterator because I need the fields to be referenced properly
 */

public class PatientView extends JFrame {
    private PatientController controller;
    private PatientViewModel model;
    private ArrayList<Object> drugEntries;
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
        nameField = new PatientViewEntry("Full Name:", controller.getNameField());

        JLabel heightLabel = new JLabel("Height:");
        heightField = new PatientViewEntry("Height:", controller.getHeightField());

        JLabel weightLabel = new JLabel("Weight:");
        weightField = new PatientViewEntry("Weight:", controller.getWeightField());

        JLabel dateOfBirthLabel = new JLabel("Date of Birth:");
        dateOfBirthField = new PatientViewEntry("Weight:", controller.getDateOfBirthField());

        JLabel genderLabel = new JLabel("Gender:");
        genderField = new PatientViewEntry("Gender:", controller.getGenderField());

        JLabel appointmentDatesLabel = new JLabel("Appointment Dates:");
        appointmentDatesField = new PatientViewEntry("Appointment Dates:", controller.getAppointmentDatesField());

        JLabel allergiesLabel = new JLabel("Allergies:");
        allergiesField = new PatientViewEntry("Allergies:", controller.getAllergiesField());

        JLabel illnessesLabel = new JLabel("Illnesses:");
        illnessesField = new PatientViewEntry("Illnesses:", controller.getIllnessesField());

        JLabel symptomsLabel = new JLabel("Symptoms:");
        symptomsField = new PatientViewEntry("Symptoms:", controller.getSymptomsField());

        JLabel lifestyleInformationLabel = new JLabel("Lifestyle Information:");
        lifestyleInformationField = new PatientViewEntry("Lifestyle Information:", controller.getLifestyleInformationField());

        JLabel additionalNotesLabel = new JLabel("Additional Notes:");
        additionalNotesField = new PatientViewEntry("Additional Notes:", controller.getAdditionalNotesField());

        mainPanel.add(createLabeledField(fullNameLabel, nameField));
        mainPanel.add(createLabeledField(heightLabel, heightField));
        mainPanel.add(createLabeledField(weightLabel, weightField));
        mainPanel.add(createLabeledField(appointmentDatesLabel, appointmentDatesField));

        drugsPanel = new JPanel();
        drugsPanel.setLayout(new BoxLayout(drugsPanel, BoxLayout.Y_AXIS));
        addExistingDrugFields(controller.getDrugs()); // add the existing set of drug fields

        drugsScrollPane = new JScrollPane(drugsPanel);
        drugsScrollPane.setPreferredSize(new Dimension(350, 100));
        drugsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JPanel drugsPanelWrapper = new JPanel();
        drugsPanelWrapper.setLayout(new BoxLayout(drugsPanelWrapper, BoxLayout.LINE_AXIS)); // Use BoxLayout for horizontal alignment

        JLabel drugsLabel = new JLabel("Drugs:");
        drugsPanelWrapper.add(drugsLabel); // Add the label to the wrapper

        // Add the scroll pane that contains the drugs panel
        drugsScrollPane = new JScrollPane(drugsPanel);
        drugsScrollPane.setPreferredSize(new Dimension(350, 50));
        drugsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        drugsPanelWrapper.add(drugsScrollPane);

        mainPanel.add(drugsPanelWrapper);

        addDrugButton = new JButton("+");
        addDrugButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDrugFields();
            }
        });

        mainPanel.add(addDrugButton);

        JLabel isPregnantLabel = new JLabel("Is Pregnant:");
        String[] options = {"True", "False"};
        isPregnantField = new JComboBox<>(options);
        JPanel tempPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        isPregnantField.setMaximumSize(new Dimension(Integer.MAX_VALUE, isPregnantField.getPreferredSize().height));
        tempPanel.add(isPregnantLabel);
        tempPanel.add(isPregnantField);

        mainPanel.add(tempPanel);
        mainPanel.add(createLabeledField(allergiesLabel, allergiesField));
        mainPanel.add(createLabeledField(illnessesLabel, illnessesField));
        mainPanel.add(createLabeledField(symptomsLabel, symptomsField));
        mainPanel.add(createLabeledField(lifestyleInformationLabel, lifestyleInformationField));
        mainPanel.add(createLabeledField(additionalNotesLabel, additionalNotesField));

        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fullName = nameField.getText();
                String height = heightField.getText();
                String weight = weightField.getText();
                String dateOfBirth = dateOfBirthField.getText();
                String gender = genderField.getText();
                String[] appointmentDates = appointmentDatesField.getText().split(",");
                ArrayList<String[]> prescribedDrugs = getDrugsAsString();
                String[] allergies = allergiesField.getText().split(",");
                String[] illnesses = illnessesField.getText().split(",");
                String[] symptoms = symptomsField.getText().split(",");
                String lifestyleInformation = lifestyleInformationField.getText();
                String isPregnant = (String) isPregnantField.getSelectedItem();
                String additionalNotes = additionalNotesField.getText();

//                controller.execute(fullName, height, weight, dateOfBirth, gender, appointmentDates, prescribedDrugs,
//                        allergies, illnesses, symptoms, lifestyleInformation, isPregnant, additionalNotes);
                close();
            }
        });
    }

    private ArrayList<String[]> getDrugsAsString() {
        ArrayList<String[]> data = new ArrayList<>();
        for (Object entry : drugEntries) {
//            data.add(entry.getEntryData());
        }
        return data;
    }

    private void close() {
        this.dispose();
    }

    private JPanel createLabeledField(JLabel label, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textField.getPreferredSize().height));
        panel.add(label);
        panel.add(textField);
        return panel;
    }

    private void addDrugFields() {
        DrugEntry drugEntry = new DrugEntry();
        drugsPanel.add(drugEntry.getPanel());
        drugEntries.add(drugEntry);
        drugsPanel.revalidate();
        drugsPanel.repaint();
    }

    private void addExistingDrugFields(ArrayList<String[]> drugs) {
        for (String[] drug : drugs) {
            PatientViewDrugEntry entry = new PatientViewDrugEntry(drug[0], drug[1], drug[2], drug[3]);
            drugsPanel.add(entry.getPanel());
            drugEntries.add(entry);
        }
    }
}
