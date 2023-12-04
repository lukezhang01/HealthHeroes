package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PatientViewDrugEntry {
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
        nameField = new JTextField("Name");
        nameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                nameText = nameField.getText();
            }
        });

        dosageField = new JTextField();
        dosageField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                dosageText = dosageField.getText();
            }
        });

        startField = new JTextField();
        startField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                startText = startField.getText();
            }
        });

        endField = new JTextField();
        endField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                endText = endField.getText();
            }
        });
        panel.add(nameField);
        panel.add(dosageField);
        panel.add(startField);
        panel.add(endField);
    }

    public String[] getInfo() {
        return new String[]{nameText, dosageText, startText, endText};
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
