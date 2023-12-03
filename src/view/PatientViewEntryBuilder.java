package view;

import javax.swing.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class PatientViewEntryBuilder extends JTextField {
    // private JTextField input;
    private String text;
    private String name;
    public PatientViewEntryBuilder(String name, String originalText) {
        super(20);
        this.name = name;
        this.text = originalText;
        // this.input = new JTextField(name);
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                text = getText();
            }
        });
    }

    public String getInput() {
        return this.getText();
    }
}
