package view;

import entity.Drug;
import interface_adapter.ViewModel;
import interface_adapter.addPatient.AddPatientController;
import interface_adapter.addPatient.AddPatientViewModel;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class AddPatientView extends JFrame {
    private AddPatientController controller;
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
    public final JButton addPatientButton;
    private final Font LABEL_FONT = new Font("Lato", Font.BOLD, 12); // Replace with your constant font object


    public AddPatientView(AddPatientController controller) {
        this.controller = controller;
        drugEntries = new ArrayList<>();

        setTitle("Add New Patient");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(DIMENSION);
        setMaximumSize(DIMENSION);
        setMinimumSize(DIMENSION);
        pack();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(Color.WHITE); // Replace with your constant background color

        // Adding labels and fields to mainPanel
        nameField = createLabeledField(mainPanel, "Full Name:");
        heightField = createLabeledField(mainPanel, "Height:");
        weightField = createLabeledField(mainPanel, "Weight:");
        dateOfBirthField = createLabeledField(mainPanel, "Date Of Birth (YYYY-MM-DD):");
        genderField = createLabeledField(mainPanel, "Gender:");
        appointmentDatesField = createLabeledField(mainPanel, "Appointment Dates (YYYY-MM-DD):");


        // Drugs label and list
        JLabel drugsLabel = new JLabel("Prescribed Drugs");
        drugsLabel.setFont(LABEL_FONT);
        drugsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(drugsLabel);

        drugsPanel = new JPanel();
        drugsPanel.setLayout(new BoxLayout(drugsPanel, BoxLayout.Y_AXIS));

        drugsScrollPane = new JScrollPane(drugsPanel);
        drugsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        drugsScrollPane.setPreferredSize(new Dimension(350, 100));
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

        // Additional fields
        allergiesField = createLabeledField(mainPanel, "Allergies:");
        illnessesField = createLabeledField(mainPanel, "Illnesses:");
        symptomsField = createLabeledField(mainPanel, "Symptoms:");
        lifestyleInformationField = createLabeledField(mainPanel, "Lifestyle Information:");
        additionalNotesField = createLabeledField(mainPanel, "Additional Notes:");


        createIsPregnantField(mainPanel);

        // Add Patient button
        this.addPatientButton = new JButton("Add Patient");
        addPatientButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        addPatientButton.setBackground(new Color(126, 175, 252));
        addPatientButton.setForeground(new Color(255, 255, 255));
        addPatientButton.setOpaque(true);
        addPatientButton.setContentAreaFilled(true);
        addPatientButton.setBorderPainted(false);
        addPatientButton.setFocusPainted(false);
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

                if (fullName.isEmpty()) {
                    launchErrorMessage("Name is empty");
                }else{
                    controller.execute(fullName, height, weight, dateOfBirth, gender, appointmentDates, prescribedDrugs,
                            allergies, illnesses, symptoms, lifestyleInformation, isPregnant, additionalNotes);
                    close();
                }
            }
        });
        mainPanel.add(addPatientButton);

        add(mainPanel);
        setVisible(true);
    }

    private void launchErrorMessage(String message) {
        ImageIcon icon = new ImageIcon(getClass().getResource("/icons/error.png"));
        JOptionPane.showMessageDialog(
                this,
                message,
                "Sign Up Error",
                JOptionPane.ERROR_MESSAGE,
                icon
        );
    }

    private JTextField createLabeledField(JPanel mainPanel, String labelText) {
        JLabel label = new JLabel(labelText);
        label.setFont(LABEL_FONT);
        JTextField textField = new JTextField(FIELD_SIZE);
        textField.setMaximumSize(new Dimension(Integer.MAX_VALUE, textField.getPreferredSize().height));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.add(label);
        panel.add(textField);
        mainPanel.add(panel);
        return textField;
    }

    private void createIsPregnantField(JPanel mainPanel) {
        JLabel isPregnantLabel = new JLabel("Is Pregnant:");
        isPregnantLabel.setFont(LABEL_FONT);
        String[] options = {"True", "False"};
        isPregnantField = new JComboBox<>(options);
        isPregnantField.setMaximumSize(new Dimension(Integer.MAX_VALUE, isPregnantField.getPreferredSize().height));
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.add(isPregnantLabel);
        panel.add(isPregnantField);
        mainPanel.add(panel);
    }

    private ArrayList<String[]> getDrugsAsString() {
        ArrayList<String[]> data = new ArrayList<>();
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

    private void close() {
        dispose();
    }


}
