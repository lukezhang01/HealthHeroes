package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PatientViewDrugEntry extends DrugEntryView {
    private JTextField nameField;
    private String nameText = "";
    private JTextField dosageField;
    private String dosageText = "";
    private JTextField startField;
    private String startText = "";
    private JTextField endField;
    private String endText = "";
    private JPanel panel;
    public PatientViewDrugEntry(String existingName, String existingDosage, String existingStart, String existingEnd) {
        panel = new JPanel(new GridLayout(0, 4));
        nameField = new JTextField(existingName);

        dosageField = new JTextField(existingDosage);

        startField = new JTextField(existingStart);

        endField = new JTextField(existingEnd);
        panel.add(nameField);
        panel.add(dosageField);
        panel.add(startField);
        panel.add(endField);
    }

    public String[] getEntryData() {
        return new String[]{nameField.getText(),
                dosageField.getText(), startField.getText(), endField.getText()};
    }

    public JPanel getPanel() {
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, panel.getPreferredSize().height));
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JTextField) {
                JTextField field = (JTextField) comp;
                field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height));
            }
        }
        return panel;
    }
}
