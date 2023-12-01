package view;

import entity.Drug;
import interface_adapter.patientList.PatientListController;
import use_case.patientList.AddPatientUseCase;
import use_case.patientList.PatientListInteractor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class AddPatientView extends JFrame {
    private PatientListInteractor interactor;

    private JTextField nameField;
    private JTextField heightField;
    private JTextField weightField;
    private JPanel drugsPanel;
    private JScrollPane drugsScrollPane;
    private JButton addDrugButton;
    private List<DrugEntry> drugEntries;

    public AddPatientView(PatientListInteractor interactor) {
        this.interactor = interactor;

        // Initialize the list for drug entries
        drugEntries = new ArrayList<>();

        // Set up the frame
        setTitle("Add New Patient");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create the main panel with a box layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Add input fields with labels on the same line
        mainPanel.add(createLabeledField("Full Name:", 20));
        mainPanel.add(createLabeledField("Height:", 20));
        mainPanel.add(createLabeledField("Weight:", 20));

        // Set up drugs panel with a dynamic layout
        drugsPanel = new JPanel();
        drugsPanel.setLayout(new BoxLayout(drugsPanel, BoxLayout.Y_AXIS));
        addDrugFields(); // add the initial set of drug fields

        // Create a scroll pane for drugs
        drugsScrollPane = new JScrollPane(drugsPanel);
        drugsScrollPane.setPreferredSize(new Dimension(350, 100));
        drugsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        mainPanel.add(drugsScrollPane);

        // Add button to add more drug fields
        addDrugButton = new JButton("+");
        addDrugButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addDrugFields();
            }
        });
        drugsPanel.add(addDrugButton);

        // Add patient button
        JButton addPatientButton = new JButton("Add Patient");
        mainPanel.add(addPatientButton);

        // Add the main panel to the frame
        add(mainPanel);

        // Set the frame's visibility
        setVisible(true);
    }

    private JPanel createLabeledField(String labelText, int fieldSize) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        JTextField textField = new JTextField(fieldSize);
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



}
