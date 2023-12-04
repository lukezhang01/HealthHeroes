package view;

import interface_adapter.ViewModel;
import interface_adapter.patient.PatientController;
import interface_adapter.patient.PatientViewModel;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * I use a builder and not an iterator because I need the fields to be referenced properly
 */

public class PatientView extends JFrame {
    private PatientController controller;
    private PatientViewModel model;
    private ArrayList<DrugEntryView> drugEntries;
    private final Dimension DIMENSION = new Dimension(500, 600);
    private JTextArea nameField;
    private JTextArea heightField;
    private JTextArea weightField;
    private JTextArea dateOfBirthField;
    private JTextArea genderField;
    private JComboBox<String> isPregnantField;
    private JTextArea appointmentDatesField;
    private JTextArea allergiesField;
    private JTextArea illnessesField;
    private JTextArea symptomsField;
    private JTextArea lifestyleInformationField;
    private JTextArea additionalNotesField;
    private JPanel drugsPanel;
    private JScrollPane drugsScrollPane;
    private JButton addDrugButton;
    private final int FIELD_SIZE = 20;
    public PatientView(PatientController controller, int id) {
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
        nameField = getField(controller.getNameField());

        JLabel heightLabel = new JLabel("Height:");
        heightField = getField(controller.getHeightField());

        JLabel weightLabel = new JLabel("Weight:");
        weightField = getField(controller.getWeightField());

        JLabel dateOfBirthLabel = new JLabel("Date of Birth:");
        dateOfBirthField = getField(controller.getDateOfBirthField());

        JLabel genderLabel = new JLabel("Gender:");
        genderField = getField(controller.getGenderField());

        JLabel appointmentDatesLabel = new JLabel("Appointment Dates:");
        appointmentDatesField = getField(controller.getAppointmentDatesField());

        JLabel allergiesLabel = new JLabel("Allergies:");
        allergiesField = getField(controller.getAllergiesField());

        JLabel illnessesLabel = new JLabel("Illnesses:");
        illnessesField = getField(controller.getIllnessesField());

        JLabel symptomsLabel = new JLabel("Symptoms:");
        symptomsField = getField(controller.getSymptomsField());

        JLabel lifestyleInformationLabel = new JLabel("Lifestyle Information:");
        lifestyleInformationField = getField(controller.getLifestyleInformationField());

        JLabel additionalNotesLabel = new JLabel("Additional Notes:");
        additionalNotesField = getField(controller.getAdditionalNotesField());

        mainPanel.add(createLabeledField(fullNameLabel, nameField));
        mainPanel.add(createLabeledField(heightLabel, heightField));
        mainPanel.add(createLabeledField(weightLabel, weightField));
        mainPanel.add(createLabeledField(dateOfBirthLabel, dateOfBirthField));
        mainPanel.add(createLabeledField(genderLabel, genderField));
        mainPanel.add(createLabeledField(appointmentDatesLabel, appointmentDatesField));

        JLabel drugsLabel = new JLabel("Prescribed Drugs");
        drugsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(drugsLabel);



        drugsPanel = new JPanel();
        drugsPanel.setLayout(new BoxLayout(drugsPanel, BoxLayout.Y_AXIS));

        drugsScrollPane = new JScrollPane(drugsPanel);
        drugsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        drugsScrollPane.setPreferredSize(new Dimension(350, 100));
        addExistingDrugFields(controller.getDrugs());
        mainPanel.add(drugsScrollPane);

        addDrugButton = new JButton("+ ADD DRUG");
        addDrugButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addDrugButton.setBackground(new Color(126, 175, 252));
        addDrugButton.setForeground(new Color(255, 255, 255));
        addDrugButton.setOpaque(true);
        addDrugButton.setContentAreaFilled(true);
        addDrugButton.setBorderPainted(false);
        addDrugButton.setFocusPainted(false);
        addDrugButton.addActionListener(e -> addDrugFields());
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
        doneButton.setFont(ViewModel.HEADING_FONT_BOLD);
        doneButton.setBackground(new Color(126, 175, 252));
        doneButton.setForeground(new Color(255, 255, 255));
        doneButton.setOpaque(true);
        doneButton.setContentAreaFilled(true);
        doneButton.setBorderPainted(false);
        doneButton.setFocusPainted(false);
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
                for (String[] array : prescribedDrugs) {
                    for (int i = 0; i < array.length; i++) {
                        System.out.print(array[i]);
                        if (i < array.length - 1) {
                            System.out.print(", "); // Separator between strings
                        }
                    }
                    System.out.println(); // New line after each array
                }
                String[] allergies = allergiesField.getText().split(",");
                String[] illnesses = illnessesField.getText().split(",");
                String[] symptoms = symptomsField.getText().split(",");
                String lifestyleInformation = lifestyleInformationField.getText();
                String isPregnant = (String) isPregnantField.getSelectedItem();
                String additionalNotes = additionalNotesField.getText();
                System.out.println(allergiesField.getText());
                controller.update(fullName, height, weight, dateOfBirth, gender, appointmentDates, prescribedDrugs,
                        allergies, illnesses, symptoms, lifestyleInformation, isPregnant, additionalNotes);
                close();
            }
        });

        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(ViewModel.HEADING_FONT_BOLD);
        deleteButton.setBackground(new Color(229, 56, 56));
        deleteButton.setForeground(new Color(255, 255, 255));
        deleteButton.setOpaque(true);
        deleteButton.setContentAreaFilled(true);
        deleteButton.setBorderPainted(false);
        deleteButton.setFocusPainted(false);
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.delete(id);
                close();
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(doneButton);
        buttonPanel.add(deleteButton);


        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));


        mainPanel.add(buttonPanel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());


        setContentPane(scrollPane);

        setVisible(true);
    }

    private JTextArea getField(String text) {
        JTextArea nameField = new JTextArea(text);
        nameField.setLineWrap(true);
        nameField.setWrapStyleWord(true);
        nameField.setBackground(ViewModel.HEADER_COLOR);
        nameField.setForeground(ViewModel.TEXT_COLOR);
        return nameField;
    }


    private ArrayList<String[]> getDrugsAsString() {
        ArrayList<String[]> data = new ArrayList<>();
        for (DrugEntryView entry : drugEntries) {
            String[] subData = entry.getEntryData();
            System.out.println(Arrays.toString(subData));
            if (subData[1].contains("mL")){
                subData[1] = subData[1].substring(0, subData[1].length() - 2);
            }
            data.add(subData);
        }
        return data;
    }

    private void close() {
        this.dispose();
    }

    private JPanel createLabeledField(JLabel label, JTextArea textField) {
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
