package view;

import entity.Drug;
import interface_adapter.ViewModel;
import interface_adapter.addPatient.AddPatientController;
import interface_adapter.addPatient.AddPatientViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class AddPatientView extends JFrame {
    private AddPatientController controller;
    private AddPatientViewModel model;
    private ArrayList<DrugEntry> drugEntries;
    private final Dimension DIMENSION = new Dimension(450, 650);
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

    public AddPatientView(AddPatientController controller) {
        this.controller = controller;
        //this.model = model;

        // Initialize the list for drug entries
        drugEntries = new ArrayList<>();

        // Set up the frame
        setTitle("Add New Patient");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setMaximumSize(DIMENSION);
        setMinimumSize(DIMENSION);
        setSize(DIMENSION);

        // Create the main panel with a box layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(ViewModel.BACKGROUND_COLOR);
        // add input fields with labels on the same line
        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setFont(ViewModel.HEADING_FONT_BOLD);
        fullNameLabel.setForeground(ViewModel.TEXT_COLOR);
        nameField = new JTextField(FIELD_SIZE);

        JLabel heightLabel = new JLabel("Height:");
        heightField = new JTextField(FIELD_SIZE);

        JLabel weightLabel = new JLabel("Weight:");
        weightField = new JTextField(FIELD_SIZE);

        JLabel dateOfBirthLabel = new JLabel("Date of Birth:");
        dateOfBirthField = new JTextField(FIELD_SIZE);

        JLabel genderLabel = new JLabel("Gender:");
        genderField = new JTextField(FIELD_SIZE);

        JLabel appointmentDatesLabel = new JLabel("Appointment Dates:");
        appointmentDatesField = new JTextField(FIELD_SIZE);

        JLabel allergiesLabel = new JLabel("Allergies:");
        allergiesField = new JTextField(FIELD_SIZE);

        JLabel symptomsLabel = new JLabel("Symptoms:");
        symptomsField = new JTextField(FIELD_SIZE);

        JLabel lifestyleInformationLabel = new JLabel("Lifestyle Information:");
        lifestyleInformationField = new JTextField(FIELD_SIZE);

        JLabel additionalNotesLabel = new JLabel("Additional Notes:");
        additionalNotesField = new JTextField(FIELD_SIZE);

        JLabel illnessesLabel = new JLabel("Illnesses:");
        illnessesField = new JTextField(FIELD_SIZE);

        mainPanel.add(createLabeledField(fullNameLabel, nameField));
        mainPanel.add(createLabeledField(heightLabel, heightField));
        mainPanel.add(createLabeledField(weightLabel, weightField));
        mainPanel.add(createLabeledField(dateOfBirthLabel, dateOfBirthField));
        mainPanel.add(createLabeledField(genderLabel, genderField));
        mainPanel.add(createLabeledField(appointmentDatesLabel, appointmentDatesField));


        // set up drugs panel with a dynamic layout
        drugsPanel = new JPanel();
        drugsPanel.setLayout(new BoxLayout(drugsPanel, BoxLayout.Y_AXIS));
        addDrugFields(); // add the initial set of drug fields

        // create a scroll pane for drugs
        drugsScrollPane = new JScrollPane(drugsPanel);
        drugsScrollPane.setPreferredSize(new Dimension(350, 100));
        drugsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        // mainPanel.add(drugsScrollPane);

        // add button to add more drug fields
        addDrugButton = new JButton("+");
        addDrugButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDrugFields();
            }
        });
        drugsPanel.add(addDrugButton);

        JPanel drugsPanelWrapper = new JPanel();
        drugsPanelWrapper.setLayout(new BoxLayout(drugsPanelWrapper, BoxLayout.LINE_AXIS)); // Use BoxLayout for horizontal alignment

        JLabel drugsLabel = new JLabel("Drugs:");
        drugsPanelWrapper.add(drugsLabel); // Add the label to the wrapper

        // Add the scroll pane that contains the drugs panel
        drugsScrollPane = new JScrollPane(drugsPanel);
        drugsScrollPane.setPreferredSize(new Dimension(350, 50));
        drugsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        drugsPanelWrapper.add(drugsScrollPane); // Add the scroll pane to the wrapper next to the label

        mainPanel.add(drugsPanelWrapper);

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

        // Add patient button
        JButton addPatientButton = new JButton("Add Patient");
        addPatientButton.addActionListener(new ActionListener() {
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

                controller.execute(fullName, height, weight, dateOfBirth, gender, appointmentDates, prescribedDrugs,
                        allergies, illnesses, symptoms, lifestyleInformation, isPregnant, additionalNotes);
                close();
            }
        });
        mainPanel.add(addPatientButton);

        // Add the main panel to the frame
        add(mainPanel);

        // Set the frame's visibility
        setVisible(true);
    }

    private ArrayList<String[]> getDrugsAsString() {
        ArrayList<String[]> data = new ArrayList<>();
        for (DrugEntry entry : drugEntries) {
            data.add(entry.getEntryData());
        }
        return data;
    }

    private JPanel createLabeledField(JLabel label, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textField.getPreferredSize().height));
        panel.add(label);
        panel.add(textField);
        return panel;
    }

    private ArrayList<Object[]> getDrugsData() {
        ArrayList<Object[]> data = new ArrayList<>();
        for (DrugEntry entry : drugEntries) {
            data.add(entry.getEntryData());
        }
        return data;
    }

    private void addDrugFields() {
        DrugEntry drugEntry = new DrugEntry();
        drugsPanel.add(drugEntry.getPanel());
        drugEntries.add(drugEntry);
        drugsPanel.revalidate();
        drugsPanel.repaint();
    }

    private boolean getIsPregnant() {
        String selection = (String) isPregnantField.getSelectedItem();
        assert selection != null;
        return selection.equals("True");
    }

    private void close() {
        this.dispose();
    }

}
