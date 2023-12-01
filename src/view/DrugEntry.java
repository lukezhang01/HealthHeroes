package view;

import javax.swing.*;
import java.awt.*;

public class DrugEntry {
    private JTextField nameField;
    private JTextField dosageField;
    private JTextField startField;
    private JTextField endField;
    private JPanel panel;

    public DrugEntry() {
        panel = new JPanel(new GridLayout(0, 4));
        nameField = new JTextField();
        dosageField = new JTextField();
        startField = new JTextField();
        endField = new JTextField();

        panel.add(nameField);
        panel.add(dosageField);
        panel.add(startField);
        panel.add(endField);
    }

    public JPanel getPanel() {
        return panel;
    }
}
